Compile flow:
1. make (The make file does not include make clean or other operations)
2. gcc -Wall -c *.c  
3. g++ -L. client1.o -lrpc -o client
4. g++ -L. server_functions.o server_function_skels.o server.o -lrpc -o server
5. ./binder
6. export BINDER_ADDRESS=
   export BINDER_PORT=
	on both client and server machines
7. ./server
8. ./client


Group memebers:
ID		Name			Email
20499890 	King Yiu Tam 		ky4tam@uwaterloo.ca
20459717 	Jiandi Yao 		j25yao@uwaterloo.ca