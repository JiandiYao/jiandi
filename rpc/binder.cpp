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
#include <queue>
#include <set>

#define LOC_REQUEST 1
#define TERMINATE 2
#define EXECUTE 3
#define LOC_SUCCESS 4
#define LOC_FAILURE 5
#define REGISTER_SUCCESS 6
#define REGISTER_FAILURE 7
#define REGISTER 8
#define EXECUTTE_SUCCESS 9
#define EXECUTE_FAILURE 0


#define PORT 0
#define MAXPENDING 5
#define BUFFER_SIZE 256
#define SPLIT_TOKEN ','

 int equal_arrays(int a1 [], int a2 []);
 int token(char *input, char sep, char *result);
 void argTypeToString(int *argTypes, char *result, int n);
 char * getKeyboard();
 int socketSend(int socketID, char *message);
 int connectTo(char *address, char *port);

char* itoa(int num, char* str, int base);
void reverse(char *str, int length);
void swap(char *a, char *b);

int parseRegister(char *echoString);
int parseLoc(char *echoString, int sock, fd_set master);

int rpcTerminate(void);

std::set<int> server_list;

struct argType{

	char name[BUFFER_SIZE];
	int argTypes[BUFFER_SIZE];
	std::queue<char *> q;//each argType has a queue for round robin

}argTypeDB[30];

//struct serverObj{
//	char server_identifier[BUFFER_SIZE];
//	char port[10];
//}serverInformation;

int k; //use k to count the number of registered argTYpes
bool running = true;


int main(){
	k = 0;
	struct sockaddr_in serverAddress, clientAddress; // These stores the network settings
	socklen_t serverAddressLength = sizeof(serverAddress), clientAddressLength = sizeof(clientAddress);
	int serverSocketID, clientSocketID;


	if ((serverSocketID = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		perror("socket() failed");
		exit(1);
	}

	// Specifying preference for IP address and port number lookup
	memset(&serverAddress, 0, sizeof(serverAddress)); // Initialize memory for
	serverAddress.sin_family = AF_INET;
	serverAddress.sin_addr.s_addr = htonl(INADDR_ANY);
	serverAddress.sin_port = htons(PORT);

	if (bind(serverSocketID, (struct sockaddr *) &serverAddress, serverAddressLength) != 0) {
		perror("bind() failed");
		close(serverSocketID);
		exit(1);
	}

	// Server starts to listen
	if (listen(serverSocketID, MAXPENDING) == -1) {
		perror("listen() failed");
		close(serverSocketID);
		exit(1);
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
	char serverPort[10];
	sprintf(serverPort, "%d", (int) ntohs(serverAddress.sin_port));
	getaddrinfo(serverName, serverPort, &lookupSetting, &lookupResult);
	fprintf(stdout, "BINDER_ADDRESS %s\nBINDER_PORT %s\n", lookupResult->ai_canonname, serverPort);
	freeaddrinfo(lookupResult); // Make sure to cleanup before exit


	// Select attributes
	int largestFileDescriptorIndex = serverSocketID;
	// We will add clients to the master list, select will use a worker copy of our master list
	fd_set master, worker;
	//initialize the file descriptor list
	FD_ZERO(&master);
	FD_ZERO(&worker);
	FD_SET(serverSocketID, &master);
	// Add keyboard to allow control over server
	FD_SET(STDIN_FILENO, &master);

	// Specify how long to block and wait for a client to do something
	struct timeval fileDescriptorWaitTime;
	// Wait for 1 second to check if there is data coming in
	fileDescriptorWaitTime.tv_sec = 1;
	fileDescriptorWaitTime.tv_usec = 0;

	int running = 1, i;
	while(running) { // This is the big red switch that makes the server run
		worker = master; // Resets the select list
		if (select(largestFileDescriptorIndex + 1, &worker, NULL, NULL, &fileDescriptorWaitTime) == -1) {
			perror("select() failed");
			close(serverSocketID);
			exit(1);
		}
		// Loop through the state of all file descriptors
		for (i = 0; i <= largestFileDescriptorIndex; i++) {
			// Check if any file descriptor changed state
			if (FD_ISSET(i, &worker)) {
				// A new client is trying to connect
				if (i == serverSocketID) {
					// Client connect successfully
					if ((clientSocketID = accept(serverSocketID,(struct sockaddr*) &clientAddress, &clientAddressLength)) != -1) {
						// Register client into master list
						FD_SET(clientSocketID, &master);
						if (clientSocketID > largestFileDescriptorIndex) {
							// Update length of list to loop
							largestFileDescriptorIndex = clientSocketID;
						}
					}
				}
				else if (i == STDIN_FILENO) { // Check keyboard input
					fprintf(stdout, "Server is Shutting down\n");
					getchar();
					running = 0;
					continue;
				}else{

					char *echoBuffer;        /* Buffer for echo string */
					int recvMsgSize;         /* Size of received message */
					int j;

					echoBuffer = (char *)malloc(10);
					memset(echoBuffer, 0, 10);
					/* Receive message from client, get the first 9 bytes first to know the length of the string*/
					if ((recvMsgSize = recv(clientSocketID, echoBuffer, 9, 0)) < 0){
						perror("recv() failed");
						close(clientSocketID);
						FD_CLR(clientSocketID, &master);
					}
					else if(echoBuffer[0] != '\0'){
						echoBuffer[9] = '\0';
						//get the length of the string
						char temp[BUFFER_SIZE];
						memset(temp, 0, BUFFER_SIZE);
						int a = token(echoBuffer, ',', temp);
						j = strlen(echoBuffer)-strlen(temp);
						int strlength = atoi(temp);

						memset(temp, 0, BUFFER_SIZE);
						a += token(echoBuffer+a, ',', temp);
						//int request = atoi(temp);
						char request = temp[0];


						//the recved string is stored in echostring, except the request code
						char echoString[strlength+10];
						strcpy(echoString, echoBuffer+a);

						char s[strlength+10];
						memset(s, 0, strlength+10);
						//recv the rest
						if(strlength+j+1 > recvMsgSize){
							recv(clientSocketID, s, strlength-(recvMsgSize-j-1), 0);
							strcat(echoString, s);
						}
						/*different handling according to request type*/
						switch(request){
						case '8':
							char str[5];
							//register is successful
							if(parseRegister(echoString) == 0){
								str[0] = '1';
								str[1] = ',';
								str[2] = '6';
								str[3] = '\0';
								if (send(clientSocketID, str, recvMsgSize, 0) != recvMsgSize){
									perror("send() failed");
									close(clientSocketID);
									FD_CLR(clientSocketID, &master);
								}
								server_list.insert(clientSocketID);
							//register failed
							}else{
								str[0] = '3';
								str[1] = ',';
								str[2] = '7';
								str[3] = ',';
								str[4] = '\0';
								if (send(clientSocketID, str, recvMsgSize, 0) != recvMsgSize){
									perror("send() failed");
									close(clientSocketID);
									FD_CLR(clientSocketID, &master);
								}
							}
							break;
						case '1':
							//argument is found
							parseLoc(echoString, clientSocketID, master);
							break;
						case '2':
							rpcTerminate();
							FD_ZERO(&master);
							close(serverSocketID);
							return 0;
						default:
							break;
						}
					}
				}//operations on the data finishes
			}//if the client socket descriptor is in the list
		}//loop through all the file descriptors
	}//busy waiting

	close(serverSocketID);
	return 0;
}


int rpcTerminate(void){
	/*send request to servers to shut down*/
	std::set<int>::iterator it;
	for (it = server_list.begin(); it != server_list.end(); it++)
	{
		int current = *it;
		char str[5];
		str[0] = '1';
		str[1] = ',';
		str[2] = '2';
		str[3] = '\0';

		send(current, str, 4, 0);

		char echoBuffer[2];
		recv(current, echoBuffer, 1, 0);
//		printf("Terminate knowledge: c%", echoBuffer[0]);
		if(echoBuffer[0] != 'Y')   /*if server fails to terminate*/
			return -1;
	}

	/*shut down binder*/
	running = false;
	return 0;

}

int parseLoc(char *echoString, int sock, fd_set master){


	char name[30];
	memset(name, 0, 30);
	int a = token(echoString, ',', name);


	char temp[20];
	memset(temp, 0, 30);
	int argTypes[20];
	int m = -1;
	do{
		m++;
		a += token(echoString+a, ',', temp);
		argTypes[m] = atoi(temp);
		memset(temp, 0, 20);
	}while(argTypes[m] != 0);
	bool exist = false;
	//if there is such an argment, send a loc_success request to the client
	for(int m = 0; m < k; m++){
		if(strcmp(argTypeDB[m].name, name) == 0  && equal_arrays(argTypeDB[m].argTypes, argTypes)==1 ){
			char *serverInfo = (char*)malloc(BUFFER_SIZE/2);
			memset(serverInfo, 0, BUFFER_SIZE/2);
			strcpy(serverInfo, argTypeDB[m].q.front());
			argTypeDB[m].q.pop();
			argTypeDB[m].q.push(serverInfo);

			int length = strlen(serverInfo)+2;
			char len[10];
			itoa(length, len, 10);

			char str[length+strlen(len)+1];
			memset(str, 0, length+strlen(len)+1);

			strcat(str, len);
			strcat(str, ",");
			strcat(str, "4");
			strcat(str, ",");
			strcat(str, serverInfo);

			if (send(sock, str, length+strlen(len)+1, 0) != (length+strlen(len)+1)){
				perror("send() failed");
				close(sock);
				FD_CLR(sock, &master);
			}
			exist = true;
		}
	}

	if(!exist){
		char strg[6];
		strg[0] = '3';
		strg[1] = ',';
		strg[2] = '5';
		strg[3] = ',';
		strg[4] = '1';
		strg[5] = '\0';
		if (send(sock, strg, 5, 0) != 5){
			perror("send() failed");
			close(sock);
			FD_CLR(sock, &master);
		}
	}
	return 0;
}



int parseRegister(char *echoString){
//	*echoString = '\0';
//	echoString++;
	/*get server address or name*/
	char addr[BUFFER_SIZE];
	memset(addr, 0, BUFFER_SIZE);
	int a=token(echoString, ',', addr);
	/*get server port number*/
	char port[10];
	memset(port, 0, 10);
	a += token(echoString+a, ',', port);

	/*get server argument name*/
	char name[BUFFER_SIZE];
	memset(name, 0, BUFFER_SIZE);
	a += token(echoString+a, ',', name);
	/*parse argment types and put them into int array*/
	char temp[20];
	memset(temp, 0, 20);
	int argTypes[BUFFER_SIZE];
	int m = -1;
	do{
		m++;
		a += token(echoString+a, ',', temp);
		argTypes[m] = atoi(temp);
		memset(temp, 0, 20);

	}while(argTypes[m] != 0);

	char *serverInfo = (char *)malloc(strlen(addr)+strlen(port)+2);
	memset(serverInfo, 0,strlen(addr)+strlen(port)+2 );

	strcpy(serverInfo, addr);
	strcat(serverInfo, ",");
	strcat(serverInfo, port);

	/*insert server information into the set*/
//	server_list.insert(serverInfo);

	/*check to see if such a arg type has been registered
	 * if so, save the server ip and port number into the queue
	 * if not, create a new argTypeDB and save them
	 */
	int kk;
	bool exist = false;
	for(kk=0; kk < k; kk++){
		if(strcmp(argTypeDB[kk].name, name) == 0  && equal_arrays(argTypeDB[kk].argTypes, argTypes)==1 ){

			argTypeDB[kk].q.push(serverInfo);
			exist = true;
			break;
		}
	}

	if(!exist){
		//argTypeDB = new argType();
		strcpy(argTypeDB[k].name, name);
		memcpy(argTypeDB[k].argTypes, argTypes, BUFFER_SIZE);

		argTypeDB[k].q.push(serverInfo);
		k++;
	}

	//free(name);

//	printf("name is %s from server IP: %s\n", argTypeDB[k-1].name, serverInfo);

	return 0;
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
//	int j =strlen(input);
//	memmove(input, input+3, j-3+1 );

	return ++i;
}


int connectTo(char *address, char *port) {

	// Try to find the destination based on the address and port
	struct addrinfo lookupSetting, *lookupResult;
	memset(&lookupSetting, 0, sizeof(lookupSetting));
	lookupSetting.ai_flags = AI_PASSIVE;
	lookupSetting.ai_family = AF_INET;
	lookupSetting.ai_socktype = SOCK_STREAM;
	getaddrinfo(address, port, &lookupSetting, &lookupResult);

	if (lookupResult == NULL) { // We did not find it...
		return -1;
	} else { // We found it
		int socketID;
		if ((socketID = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) { // Fail to create socket
			return -2;
		}

		// loop through all the results and connect to the first one
		struct addrinfo *r;
		for (r = lookupResult; r != NULL; r = r->ai_next) {
			if (connect(socketID, r->ai_addr, r->ai_addrlen) == 0) {
				break;
			}
		}
		freeaddrinfo(lookupResult);
		if (r == NULL) { // Fail to connect
			return -3;
		}
		return socketID;
	}
}


int socketSend(int socketID, char *message) {

	// Package the message length to the start of the message
	char lengthString[10];
	itoa(strlen(message), lengthString, 10);
	char *package = (char *)malloc(strlen(lengthString) + strlen(message) + 2);
	strcpy(package, lengthString);
	strcat(package, ",");
	strcat(package, message);

	int status;
	int bytesSent = 0;
	int bytesLeft = sizeof(char) * strlen(package);
	while (bytesLeft > 0) { // Keep on retry as long as some of the bytes are not sent
		if ((status = send(socketID, package + bytesSent, bytesLeft, 0)) < 0) {
			break;
		}
		bytesSent = bytesSent + status;
		bytesLeft = bytesLeft - status;
	}
	free(package);
	if (status < 0) {
		return status;
	} else {
		return 0;
	}
}


char * getKeyboard() {
	char *buffer = (char *)malloc(sizeof(char) * (BUFFER_SIZE + 1));
	fgets(buffer, sizeof(char) * (BUFFER_SIZE + 1), stdin);
	char *input = (char *)malloc(sizeof(char) * (strlen(buffer) + 1));
	strcpy(input, buffer);
	int filledLength, inputLength = strlen(input);
	while (strchr(buffer, '\n') == NULL) {
		fgets(buffer, sizeof(char) * (BUFFER_SIZE + 1), stdin);
		filledLength = strlen(buffer);
		inputLength = inputLength + filledLength;
		input = (char *)realloc(input, inputLength + 1);
		strcat(input, buffer);
	}
	input[strlen(input)-1] = '\0';
	free(buffer);
	return input;
}


#include <stdbool.h>
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

// Implementation of itoa()
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


// the correct one:
int equal_arrays(int a1 [], int a2 [])
{
    int i;
    int n = sizeof(a1)/sizeof(a1[0]);
    int n1 = sizeof(a2)/sizeof(a2[0]);

    if(n != n1)
    	return 0;
	for (i=0; i<n; i++)
		if (a1[i] != a2[i])
			return 0;
    return 1;
}

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
