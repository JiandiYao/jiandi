# GNU Make automatic variables
# $< is the first prerequisite
# $* is the stem of target with which rule match (e.g., foo of foo.c) - what matches with % in a rulek
# $@ is the name of the target

CC=g++
CD=gcc
AR=ar
RC=rc
CFLAGS=-c -Wall
ARFlAGS=-cvq
all: binder

binder: binder.cpp
	$(CC) -o $@ $<


all: rpc.o

rpc.o: rpc.c
	$(CD) $(CFLAGS) -I. -o $@ $< 
	

all: librpc.a

librpc.a: rpc.o
	$(AR) $(RC) $@ $<

	
	

