#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <ifaddrs.h>
#include <stdbool.h>

#define REGISTER 8
#define PORT 0
#define MAXPENDING 5

//#define IP "192.168.37.171"
//#define TEMPPORTNO "51602"

#define ARG_CHAR    1
#define ARG_SHORT   2
#define ARG_INT     3
#define ARG_LONG    4
#define ARG_DOUBLE  5
#define ARG_FLOAT   6
#define BUFFER_SIZE 256

typedef int (*skeleton)(int *, void **);

/*Functions for converting an integer to char array
 * several bases are provided, eg: 10, 16, 2, 8*/
char* itoa(int num, char* str, int base);					/*choose base to convert to which kind of char*/
void reverse(char *str, int length);
void swap(char *a, char *b);

/*functions used for make tcp connections*/
int connectTo(char *address, char *port);
int socketSend(int socketID, char *message, int length);

/*functions specific for client side*/
int argInquiry(char *name, int *argTypes);
int rpcRequest(char *server_identifier, char *port, void **args, char *name, int *argTypes);

/*server specific functions*/
int connectBinder();
int createClientConn();
int recvFromClient(int clientSocketID, fd_set master);
int recvFromBinder(int binderSock, char *echoBinderBuffer);

/*functions used to parse argTypes and args*/
int equal_arrays(int a1 [], int a2 [], int n, int n1);
int token(char *input, char sep, char *result);
void argTypeToString(int *argTypes, char *result, int n);
void parseArgsToString(int *argTypes, void **args, char *result);
void parseStringToArgs(int *argTypes, void **args, char *str);
void argTypesCopy(int *argTypesOrigin, int *argTypesTarget);
void parseArgTypes(int argTypes, int *result);
int typeLen(int type);


/*server side global variables*/
int binderConnSocket;								/*binder socket id*/
int serverSocketID;									/*server socket id */
bool server_running = true;							/*server is running*/

char *server_port;									/*server port number*/
char *server_identifier ;							/*server ip/name*/

struct sk{
	skeleton f;
	int argTypes[BUFFER_SIZE];
	char name[BUFFER_SIZE];
} registerDB[20];									/*registerDB is used to store the registered function and servers*/
int ii = 0;											/*the index of registerDB */

/*client side global variables*/
int binderSockID;									/*the binder socket id is used for terminate request*/


int rpcInit(){
	server_identifier = (char *)malloc(BUFFER_SIZE);
	server_port = (char *)malloc(10);
	int temp;

	if((binderConnSocket = connectBinder())<0){
		return binderConnSocket;
	}
	if((temp = createClientConn()) < 0){
		return temp;
	}
	return 0;
}
int rpcRegister(char* name, int* argTypes, skeleton f){

	int returnVal = 0;
    char *echoString;
    int j = 0;

    for(j = 0; j< ii; j++){		/*check for duplicate register*/
		int nn1 = 0;
		int nn2 = 0;
		while(registerDB[j].argTypes[nn1] != 0)
			nn1++;
		while(argTypes[nn2] != 0)
			nn2++;

    	if(strcmp(name, registerDB[j].name) == 0){
    		if(equal_arrays(argTypes, registerDB[j].argTypes, nn1,nn2) == 1){
    			printf("duplicate register\n");
    			returnVal = 1; 							/* return code  = 1 means duplicate register*/
    		}
    	}
    }
    if(returnVal == 0){
		char *argTypesCharArray = (char *)malloc(100);
		memset(argTypesCharArray, 0, 100);
		int n = 0;
		while(argTypes[n] != 0)
			n++;
		argTypeToString(argTypes, argTypesCharArray, n);
		int strlength = 1+1+10+1+strlen(server_identifier)+1+strlen(name)+1+strlen(argTypesCharArray);

		char *charLength = (char *)malloc(10);
		charLength = itoa(strlength, charLength, 10);

		strlength = strlength+strlen(charLength) + 1;

		echoString = (char *)malloc(strlength+1);

		strcpy(echoString, charLength);

		strcat(echoString, ",");

		memset(charLength, 0, 10);

		strcat(echoString, itoa(REGISTER, charLength, 10));
		strcat(echoString, ",");
		strcat(echoString, server_identifier);
		strcat(echoString, ",");

		memset(charLength, 0, 10);
		strcat(echoString, server_port);
		strcat(echoString, ",");
		strcat(echoString, name);
		strcat(echoString, ",");

		strcat(echoString, argTypesCharArray);
		free(argTypesCharArray);
		free(charLength);

		/* Send the string to the server */
		if (send(binderConnSocket, echoString, strlength, 0) != strlength){
			perror("send() sent a different number of bytes than expected\n");
			return -7;
		}
		free(echoString);

		char echoBuffer[10];
		memset(echoBuffer, 0 , 10);
		//recv message from the binder

		int bytesRcvd;
		if((bytesRcvd = recv(binderConnSocket, echoBuffer,9, 0)) <= 0){
			printf("recv() failed or connection closed prematurely\n");
			return -8;
		}

		if(echoBuffer[2] == '6'){
			registerDB[ii].f = f;
			argTypesCopy(argTypes, registerDB[ii].argTypes);
			strcpy(registerDB[ii].name, name);
			ii++;
		}else if(echoBuffer[2] == '7'){
			printf("Register failed\n");
			returnVal = -9;

		}

    }
	return returnVal;
}
int rpcExecute(){

	int returnValue = 0;
	char *echoBinderBuffer = (char *)malloc(10);//used for recving binder terminate message
	int clientSocketID;
	struct sockaddr_in clientAddress;
	socklen_t clientAddressLength = sizeof(clientAddress);
	// Select attributes
	int largestFileDescriptorIndex = serverSocketID > binderConnSocket ? serverSocketID:binderConnSocket;
	// We will add clients to the master list, select will use a worker copy of our master list
	fd_set master, worker;
	//initialize the file descriptor list
	FD_ZERO(&master);
	FD_ZERO(&worker);
	FD_SET(serverSocketID, &master);
	FD_SET(binderConnSocket, &master);

	// Specify how long to block and wait for a client to do something
	struct timeval fileDescriptorWaitTime;
	// Wait for 1 second to check if there is data coming in
	fileDescriptorWaitTime.tv_sec = 1;
	fileDescriptorWaitTime.tv_usec = 0;

	int i;
	while(server_running) { // This is the big red switch that makes the server run
		/*the following operations are for servers to select connections from clients
		 * and execute the function operations*/
		worker = master; // Resets the select list
		if (select(largestFileDescriptorIndex + 1, &worker, NULL, NULL, &fileDescriptorWaitTime) == -1) {
			perror("select() failed");
			close(serverSocketID);
			return -10;
		}
		// Loop through the state of all file descriptors
		for (i = 0; i <= largestFileDescriptorIndex; i++) {
			// Check if any file descriptor changed state
			if (FD_ISSET(i, &worker)) {
				// A new client is trying to connect
				if (i == serverSocketID) {
					// Client connect successfully
					if ((clientSocketID = accept(serverSocketID,
							(struct sockaddr*) &clientAddress, &clientAddressLength)) != -1) {
						// Register client into master list
						FD_SET(clientSocketID, &master);
						if (clientSocketID > largestFileDescriptorIndex) {
							// Update length of list to loop
							largestFileDescriptorIndex = clientSocketID;
						}
					}
				}
				else
				{
					if(i == binderConnSocket){
						returnValue = recvFromBinder(binderConnSocket, echoBinderBuffer);
						if(returnValue != 0)
							return returnValue;
					}
					else{
						returnValue = recvFromClient(clientSocketID, master);
						if(returnValue != 0)
							return returnValue;
						close(clientSocketID);
						FD_CLR(clientSocketID, &master);
					}

				}
			}
		}
	}

	free(server_identifier);
	free(server_port);
	close(binderConnSocket);
	close(serverSocketID);
	return 0;
}
/*first call the binder to request server information
 * Then make another request to the server for function execution */
int rpcCall(char* name, int* argTypes, void** args){
	//make a inquiry to the binder to check if the function exists
//	binderSockID = (int)malloc(sizeof(int));
	binderSockID = argInquiry(name, argTypes);

	if(binderSockID == -1){
		printf("unable to connect to binder\n");
		return -11;
	}

	char *echoBuffer = (char *)malloc(6);
	memset(echoBuffer, 0, 6);
	int bytesRcvd;
	if((bytesRcvd = recv(binderSockID, echoBuffer,5, 0)) <= 0){
		printf("recv() failed or connection closed prematurely\n");
		return -12;
	}

	char temp[6];
	int a = token(echoBuffer, ',', temp);

	int length = atoi(temp);


	/*if inquiry failed, no argments found on the binder
	 * return the failure code */
	if(length == 3){
		if(echoBuffer[2] == '5')
			return echoBuffer[4]-'0';
	}

	/*if success
	 * return the server_identifier and port */
	char str[length+1];
	memset(str, 0, length+1);

	strcpy(str, echoBuffer+a);

	echoBuffer = (char *)realloc(echoBuffer, length-bytesRcvd+a+2);
	memset(echoBuffer, 0, length-bytesRcvd+a+1);
	if((bytesRcvd = recv(binderSockID, echoBuffer,length-bytesRcvd+a+1, 0)) <= 0){
		printf("recv() failed or connection closed prematurely\n");
		return -12;
	}
	//final result of the string, containing server information
	strcat(str, echoBuffer);

	char server_identifier[BUFFER_SIZE];
	memset(server_identifier, 0, BUFFER_SIZE);
	char port[10];

	char request[5];
	a = token(str, ',', request);

	if(request[0] == '4'){
		a += token(str+a, ',', server_identifier);
		strcpy(port, str+a);

	}
	/*send request the server for function execution */
	int serverfd = rpcRequest(server_identifier, port, args, name, argTypes);

	if(serverfd < 0)
		return -13;
	/*recv message from server for function execution result*/
	echoBuffer = (char *)realloc(echoBuffer, 10);
	memset(echoBuffer, 0, 10);
	if((bytesRcvd = recv(serverfd, echoBuffer,9, 0)) <= 0){
		printf("recv() failed or connection closed prematurely\n");
		return -14;
	}

	/*if the function execution fails, such as invalid input*/
	if(echoBuffer[0] == 'F'){
		return -15;
	}else{
		char charLength[6];

		int b = token(echoBuffer, ',', charLength);

		length = atoi(charLength);


		/*if execution failed, no argments found on the binder
		 * return the failure code */
		if(length == 3){
			if(echoBuffer[2] == '0')
				return -16;
		}
		/*if success
		 * reset the expected result of void **args and return 0 */
		/*parse the result and assign args a different value*/
		memset(request, 0, 5);
		b += token(echoBuffer+b, ',', request);

		/*request code is 9 means execution successful*/
		if(request[0] == '9'){


			char echoStr[length];
			memset(echoStr, 0, length);

			strcpy(echoStr, echoBuffer+b);
			free(echoBuffer);

			char echoString[BUFFER_SIZE];
			memset(echoString, 0, BUFFER_SIZE);

			/*recv the rest*/
			if((bytesRcvd = recv(serverfd, echoString,length-8+strlen(charLength), 0)) <= 0){
				printf("recv() failed or connection closed prematurely\n");
				return -14;
			}

			memcpy(echoStr+strlen(echoStr), echoString, length-8+strlen(charLength));



			char reName[BUFFER_SIZE];
			memset(reName, 0, BUFFER_SIZE);
			b = token(echoStr, ',', reName);

			char argTypesArray[BUFFER_SIZE];
			memset(argTypesArray, 0, BUFFER_SIZE);
			int i = 0;
			while(true){
				if(echoStr[i+b] == '0' && echoStr[i+b-1] == ',' && echoStr[i+b+1] == ',')
					break;
				i++;
			}

			/*if the return name is consistent with the requested name*/
			if(strcmp(reName, name) == 0){
				parseStringToArgs(argTypes, args, echoStr+i+b+2);
			}
		}

		return 0;
	}

}

int rpcTerminate(){

	char echoString[6];
	echoString[0] = '3';
	echoString[1] = ',';
	echoString[2] = '2';
	echoString[3] = ',';
	echoString[4] = 'a';
	echoString[5] = '\0';
	socketSend(binderSockID, echoString, 5);
	return 0;
}
/*when server starts, it first connects to the binder*/
int connectBinder(){
	char* binderIP;                    /* Server IP address (dotted quad) */
	char* binderPort;

	//fetch environment variables from the system
	binderIP = getenv ("BINDER_ADDRESS");
	binderPort = getenv("BINDER_PORT");
//
//	binderIP = IP;
//	binderPort = TEMPPORTNO;
	if(strlen(binderIP) == 0 || strlen(binderPort) == 0){
		perror("DOES NOT SET ENVIRONMENT VARIABLES FOR SERVER_ADDRESS OR SERVER_PORT\n");
		return -1;
	}
	return connectTo(binderIP, binderPort);
}

/*when a server starts, it needs to create a new socket for clients and listens to this socket*/
int createClientConn(){

	struct sockaddr_in serverAddress; 							// These stores the network settings
	socklen_t serverAddressLength = sizeof(serverAddress);


	if ((serverSocketID = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		perror("socket() failed");
		return -4;
	}

	// Specifying preference for IP address and port number lookup
	memset(&serverAddress, 0, sizeof(serverAddress)); 			// Initialize memory for
	serverAddress.sin_family = AF_INET;
	serverAddress.sin_addr.s_addr = htonl(INADDR_ANY);
	serverAddress.sin_port = htons(PORT);

	if (bind(serverSocketID, (struct sockaddr *) &serverAddress, serverAddressLength) != 0) {
		perror("bind() failed");
		close(serverSocketID);
		return -5;
	}

	// Server starts to listen
	if (listen(serverSocketID, MAXPENDING) == -1) {
		perror("listen() failed");
		close(serverSocketID);
		return -6;
	}

	// If we reach here, we have successfully setup the server
	struct addrinfo lookupSetting, *lookupResult;
	memset(&lookupSetting, 0, sizeof(lookupSetting)); // Initialize memory for
	lookupSetting.ai_flags = AI_CANONNAME;
	lookupSetting.ai_family = AF_INET;
	lookupSetting.ai_socktype = SOCK_STREAM;
	char serverName[256];
	gethostname(serverName, sizeof(serverName));
	getsockname(serverSocketID, (struct sockaddr *) &serverAddress, &serverAddressLength);

	sprintf(server_port, "%d", (int) ntohs(serverAddress.sin_port));
	getaddrinfo(serverName, server_port, &lookupSetting, &lookupResult);

//	fprintf(stdout, "REMEMBER TO DELETE THIS: \nServer_ADDRESS %s\nserver_PORT %s\n", lookupResult->ai_canonname, server_port);
	strcpy(server_identifier, lookupResult->ai_canonname);

	freeaddrinfo(lookupResult); // Make sure to cleanup before exit

	return serverSocketID;
}
/*server recv data from binder and parse it */
int recvFromBinder(int binderSock, char *echoBinderBuffer){
	int bytesRcvd;
	char termin[2];
	if((bytesRcvd = recv(binderSock, echoBinderBuffer,9, 0))>0){
		//check if the message if from binder ip
		switch(echoBinderBuffer[2]){
		case '6':
			/*register successful*/
			return 0;
		case '7':
			/*register failed*/
			return -9;
		case '2':
			/*terminate the server*/
			
			termin[0] = 'Y';
			termin[1] = '\0';
			socketSend(binderSock, termin, 1);
			server_running = false;
			break;
		default:
			break;
		}
	}
	return 0;
}
/*server recv data from client and parse it */
int recvFromClient(int clientSocketID, fd_set master){
	int recvMsgSize;
	char echoBuffer[11];
	/* Receive message from client, get the first 10 bytes first to know the length of the string
	 * and the type of message*/
	if ((recvMsgSize = recv(clientSocketID, echoBuffer, 10, 0)) < 0){
		perror("recv() failed");
		close(clientSocketID);
		FD_CLR(clientSocketID, &master);
		return -17;
	}
	echoBuffer[10] = '\0';

	char length[10];
	memset(length, 0, 10);
	int a = token(echoBuffer, ',', length);

	int strlength = atoi(length);

	char request[2];//REGISTER request
	a += token(echoBuffer+a, ',', request);

	char echoString[BUFFER_SIZE*2];
	memset(echoString, 0, BUFFER_SIZE*2);
	strcpy(echoString, echoBuffer+a);
	/*parse the recved string and call skel_functions*/
	if(request[0] == '3'){
		//recv the rest string
		char echoStr[strlength+1];
		memset(echoStr, 0, strlength+1);
		/*Already get 10 bytes including strlen(length)+1 bytes of header
		 * 2 bytes for request
		 * and some other bytes of message
		 * strlength-(10 - strlen(length)-1) = strlength - 9 + strlen(length)*/
		recvMsgSize = recv(clientSocketID, echoStr,	strlength - 9 + strlen(length), 0);

		//parse and save the variables
		char name[BUFFER_SIZE];
		memset(name, 0, BUFFER_SIZE);
		void ** args = (void **)malloc(sizeof(void *)*BUFFER_SIZE);
		memset(args, 0, BUFFER_SIZE);
		int argTypes[BUFFER_SIZE];
		memset(argTypes, 0, BUFFER_SIZE);

		/*cannot do a simple strcat, need to use memcpy*/
		memcpy(echoString+10-strlen(length)-3, echoStr,
				strlength - 9 + strlen(length));
		a = token(echoString, ',', name);

		/*save argTypes into int array*/
		char temp[50];
		int i = 0;
		do{
			memset(temp, 0, 50);
			a += token(echoString+a, ',', temp);
			argTypes[i] = atoi(temp);
			i++;
		}while(atoi(temp) != 0);

		/*save args into void** */
		parseStringToArgs(argTypes, args, echoString+a);


		int k =  sizeof(registerDB)/sizeof(registerDB[0]);
		//check with the registerDB to see if it is a right function call
		int nn1, nn2;
		while(k >= 0){
			nn1 = 0;
			nn2 = 0;
			while(registerDB[k].argTypes[nn1] != 0)
				nn1++;
			while(argTypes[nn2] != 0)
				nn2++;

			if(strcmp(registerDB[k].name, name) == 0)
				if( equal_arrays(registerDB[k].argTypes, argTypes, nn1, nn2) == 1)
				break;
			k--;
		}

		/*if the function is not registered*/
		if(k < 0){
			char echoStr[6];

			echoStr[0] = '3';
			echoStr[1] = ',';
			echoStr[2] = '0';
			echoStr[3] = ',';
			echoStr[4] = '2';	/*No such a function registered*/
			echoStr[5] = '\0';
			/* Echo message back to client */
			if (send(clientSocketID, echoStr, strlen(echoStr)+1, 0) != (signed int)strlen(echoStr)+1){
				perror("send() failed");
				close(clientSocketID);
				FD_CLR(clientSocketID, &master);
				return -18;
			}
			free(args);

		}else{													/*the function is registered*/
			int returnVal = registerDB[k].f(argTypes, args);	/*execute the function*/

			/*parse argTypes to char array*/
			char argTypesCharArray[BUFFER_SIZE];
			memset(argTypesCharArray, 0, BUFFER_SIZE);

			/*length of argTypes array*/
			int n = 0;
			while(argTypes[n] != 0)
				n++;
			argTypeToString(argTypes, argTypesCharArray, n);

			/*parse args to char array*/
			char argsCharArray[BUFFER_SIZE];
			parseArgsToString(argTypes, args, argsCharArray);

			/*The length of argsCharArray, cannot use strlen to get*/
			int i, re[4];

			int argsLength = 0;
			for(i = 0; i < n; i++){
				parseArgTypes(argTypes[i], re);
				if(re[2] != 0){
					argsLength += re[2]*typeLen(re[1]);
				}
				else{
					argsLength += typeLen(re[1]);
				}
			}

			if(returnVal == 0){
				/*string length for the response without including the length */
				strlength = strlen(name)+1+strlen(argTypesCharArray)+argsLength+2;

				/*convert strlength to char array*/
				char length[10];
				memset(length, 0, 10);
				itoa(strlength, length, 10);

				/*response buffer*/
				char echoStr[strlen(length)+1+strlength+1];

				memset(echoStr, 0, strlen(length)+1+strlength+1);
				strcpy(echoStr, length);

				strcat(echoStr, ",");

				strcat(echoStr, "9");
				strcat(echoStr, ",");
				strcat(echoStr, name);
				strcat(echoStr, ",");
				strcat(echoStr, argTypesCharArray);
				memcpy(echoStr+strlen(echoStr), argsCharArray, argsLength);

				/* Echo message back to client */
				if (send(clientSocketID, echoStr, strlen(length)+1+strlength, 0) != (signed int)strlen(length)+1+strlength){
					perror("send() failed");
					close(clientSocketID);
					FD_CLR(clientSocketID, &master);
				}
			}else{							/*if the function execution fails*/
				strlength = 2+strlen(name)+1+strlen(argTypesCharArray);
				char length[10];
				memset(length, 0, 10);
				itoa(strlength, length, 10);

				char echoStr[strlen(length)+1+strlength+1];
				memset(echoStr, 0, strlen(length)+1+strlength+1);
				strcpy(echoStr, "F");
				strcat(echoStr, length);
				strcat(echoStr, "9");
				strcat(echoStr, ",");
				strcat(echoStr, name);
				strcat(echoStr, ",");
				strcat(echoStr, argTypesCharArray);

				/* Echo message back to client */
				if (send(clientSocketID, echoStr, strlen(length)+1+strlength, 0) != (signed int)strlen(length)+1+strlength){
					perror("send() failed");
					close(clientSocketID);
					FD_CLR(clientSocketID, &master);
				}
			}
			free(args);
		}
	}
	return 0;
}

/*this request is sent from client to the server for function execution */
int rpcRequest(char *server_identifier, char * port, void **args, char *name, int *argTypes){

	int sock = connectTo(server_identifier, port);
	/*parse argTypes to char pointer*/
	char argTypesCharArray[100];
	memset(argTypesCharArray, 0, 100);
	int n = 0;
	while(argTypes[n] != 0)
		n++;
	argTypeToString(argTypes, argTypesCharArray, n);

	char echoString[BUFFER_SIZE*2];                /* String to send to echo server */
	memset(echoString, 0, BUFFER_SIZE*2);

	char argsCharArray[BUFFER_SIZE];/*the char array for argment*/
	parseArgsToString(argTypes, args, argsCharArray);

	/*can not use strlen to get the length of argsCharArray*/
	int argsLength = 0, type;
	n=n-1;
	while(n>=0){
		type = ((argTypes[n] & 0xf0000) >> 16);
		argsLength += typeLen(type) * ((argTypes[n] & 0xffff) == 0 ? 1 :(argTypes[n] & 0xffff) );
		n--;
	}

	int length = 2+strlen(name)+1+strlen(argTypesCharArray)+ argsLength;
	char len[6];
	memset(len, 0, 6);
	itoa(length, len, 10);

	strcat(echoString, len);
	strcat(echoString, ",");
	strcat(echoString, "3");/*a request to server for function execution */
	strcat(echoString, ",");
	strcat(echoString, name);
	strcat(echoString, ",");
	strcat(echoString, argTypesCharArray);
	memcpy(echoString + strlen(len) + 1 + strlen(name) +1 + strlen(argTypesCharArray) +2,
			argsCharArray, argsLength);					/*cannot use strcat here since argsCharArray is not actually a char array*/

	socketSend(sock, echoString, length+1+strlen(len));
	return sock;
}
/*From client side, an inqury to the binder for which server can execute the request*/
int argInquiry(char *name, int *argTypes){
    char* binderIP;                    				/* Server IP address (dotted quad) */
    char echoString[BUFFER_SIZE];                	/* String to send to echo server */
    char* binderPort;
    int sock;

    //fetch environment variables from the system
	binderIP = getenv ("BINDER_ADDRESS");
	binderPort = getenv("BINDER_PORT");

//    binderIP = IP1;
//    binderPort = TEMPPORTNO;
	if(strlen(binderIP) == 0 || strlen(binderPort) == 0){
		perror("DOES NOT SET ENVIRONMENT VARIABLES FOR SERVER_ADDRESS OR SERVER_PORT\n");
		return -1;
	}

	sock = connectTo(binderIP, binderPort);
	/*parse argTypes to char pointer*/
	char result[100];
	memset(result, 0, 100);
	int n = 0;
	while(argTypes[n] != 0)
		n++;
	argTypeToString(argTypes, result, n);

	int length = strlen(name)+strlen(result)+1+2;
	char len[6];
	itoa(length, len, 10);

	//echoString = (char *)malloc(strlen(len)+1+length+1);
	memset(echoString, 0, BUFFER_SIZE);
	strcpy(echoString, len);
	strcat(echoString, ",");
	strcat(echoString, "1");
	strcat(echoString, ",");
	strcat(echoString, name);
	strcat(echoString, ",");
	strcat(echoString, result);

	int status;
	return (status = socketSend(sock, echoString, strlen(echoString))) == 0 ? sock:status;
}

/* get sockaddr, IPv4 or IPv6*/
void *get_in_addr(struct sockaddr *sa)
{
	if (sa->sa_family == AF_INET) {
		return &(((struct sockaddr_in*)sa)->sin_addr);
	}

	return &(((struct sockaddr_in6*)sa)->sin6_addr);
}
int connectTo(char *address, char *port) {

	int sockfd;
	struct addrinfo hints, *servinfo, *p;
	int rv;
	char s[INET6_ADDRSTRLEN];

	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;

	if ((rv = getaddrinfo(address, port, &hints, &servinfo)) != 0) {
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
		return -2;
	}
	// loop through all the results and connect to the first we can
	for(p = servinfo; p != NULL; p = p->ai_next) {
		if ((sockfd = socket(p->ai_family, p->ai_socktype,
				p->ai_protocol)) == -1) {
			perror("client: socket");
			continue;
		}

		if (connect(sockfd, p->ai_addr, p->ai_addrlen) == -1) {
			close(sockfd);
			perror("client: connect");
			continue;
		}
		break;
	}
	if (p == NULL) {
		fprintf(stderr, "client: failed to connect\n");
		return -3;
	}
	inet_ntop(p->ai_family, get_in_addr((struct sockaddr *)p->ai_addr),s, sizeof s);
	freeaddrinfo(servinfo); // all done with this structure
	return sockfd;
}
/*send message*/
int socketSend(int socketID, char *message, int length) {

	int status;
	int bytesSent = 0;
	int bytesLeft = length;
	while (bytesLeft > 0) { // Keep on retry as long as some of the bytes are not sent
		if ((status = send(socketID, message + bytesSent, bytesLeft, 0)) < 0) {
			break;
		}
		bytesSent = bytesSent + status;
		bytesLeft = bytesLeft - status;
	}

	if (status < 0) {
		return status;
	} else {
		return 0;
	}
}




void parseArgTypes(int argTypes, int *result){

	result[0] = (argTypes & 0xc0000000) >> 30; //possible result could be 1, 2, 3, the types of input or output
	result[1] = (argTypes & 0xf0000) >> 16; //possible result could be 1 to 6, argTypes
	result[2] = (argTypes & 0xffff); // possible result could be 0-2^16, args length

}

int typeLen(int type){
	switch(type){
	case 1:
		return sizeof(char);
	case 2:
		return sizeof(short);
	case 3:
		return sizeof(int);
	case 4:
		return sizeof(long);
	case 5:
		return sizeof(double);
	case 6:
		return sizeof(float);
	}
	return 0;
}

void parseArgsToString(int *argTypes, void **args, char *result){
	int n = 0;
	while(argTypes[n] != 0)
		n++;
	int i;
	char tempResult[50];/*assume max length for each arg is 50 bytes*/

	int re[4];
	memset(re, 0, 4);
	int a = 0;
	for(i = 0; i < n; i++){
		parseArgTypes(argTypes[i], re);
		memset(tempResult, 0, 50);

		if(re[2] != 0){
			// copy the relevant length of memory space to result
			memcpy(result+a, args[i], re[2]*typeLen(re[1]));
//			printf("%d\n", (int)*(result+a));
			a += re[2]*typeLen(re[1]);
		}
		else{
			/*copy only one unit*/
			memcpy(result+a, args[i], typeLen(re[1]));
//			printf("%d\n", (int)*(result+a));
			a += typeLen(re[1]);
		}
	}
}

/*parse the void array to **args format*/
void parseStringToArgs(int *argTypes, void **args, char *str){
	int n = 0;
	while(argTypes[n] != 0)
		n++;

	int i, re[4];
	int count = 0;
	int byteLength;
	for(i = 0; i < n; i++){
		parseArgTypes(argTypes[i], re);

		if(re[2] != 0){

			byteLength = re[2]*typeLen(re[1]);
			args[i] = (void*)malloc(byteLength);
			memcpy(args[i], str+count, byteLength);

			count += byteLength;
		}
		else{
			byteLength = typeLen(re[1]);
			args[i] = (void*)malloc(byteLength);
			memcpy(args[i], str+count, byteLength);

			count += byteLength;
		}

	}
}

void argTypesCopy(int *argTypesOrigin, int *argTypesTarget){
	int i = 0;
	while(argTypesOrigin[i] != 0){
		argTypesTarget[i] = argTypesOrigin[i];
		i++;
	}
}

/*
 * return value is the number of positions that
 * input pointer needs to move to get the result
 * discarding the separator at the same time
 */
int token(char *input, char sep, char *result){
	int i = 0;
	while(input[i] != sep){
		result[i] = input[i];
		i++;
	}

	return ++i;
}


/*check if two int arrays are the same*/
int equal_arrays(int a1 [], int a2 [], int n , int n1)
{
    int i;
    if(n != n1)
    	return 0;
	for (i=0; i<n; i++)
		if (a1[i] != a2[i])
			return 0;
    return 1;
}
/*convert argTypes array into char array and use ',' to separate each space*/
void argTypeToString(int *argTypes, char *result, int n){
	int i;
	char *temp = (char *)malloc(10);
	for(i = 0; i <= n; i++){
		temp = itoa(argTypes[i], temp, 10);
		strcat(result, temp);
		strcat(result, ",");
	}
	free(temp);
}
/*swap two char*/
void swap(char *a, char *b){
	char temp = *a;
	*a = *b;
	*b = temp;
}

/* A utility function to reverse a string  */
void reverse(char *str, int length)
{
    int start = 0;
    int end = length -1;
    while (start < end)
    {
        swap((str+start), (str+end));
        start++;
        end--;
    }
}

/*Implementation of itoa()*/
char* itoa(int num, char* str, int base)
{
    int i = 0;
    bool isNegative = false;

    /* Handle 0 explicitely, otherwise empty string is printed for 0 */
    if (num == 0)
    {
        str[i++] = '0';
        str[i] = '\0';
        return str;
    }
    // In standard itoa(), negative numbers are handled only with
    // base 10. Otherwise numbers are considered unsigned.
    if (num < 0 && base == 10)
    {
        isNegative = true;
        num = -num;
    }
    // Process individual digits
    while (num != 0)
    {
        int rem = num % base;
        str[i++] = (rem > 9)? (rem) + 'a' : rem + '0';
        num = num/base;
    }
    // If number is negative, append '-'
    if (isNegative)
        str[i++] = '-';

    str[i] = '\0'; // Append string terminator
    // Reverse the string
    reverse(str, i);

    return str;
}
