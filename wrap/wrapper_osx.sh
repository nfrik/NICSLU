#!/bin/bash

set -ex

cd ..
make
cd wrap
mkdir -p com/nicslu/jni
cp NativeUtils.java com/nicslu/jni
javac com/nicslu/jni/NativeUtils.java
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$PWD
swig -java -package com.nicslu.jni -outdir com/nicslu/jni -o nicslu_readfile_wrap.c nicslu_readfile.i
gcc -c -fPIC nicslu_readfile.c nicslu_readfile_wrap.c  -I"$(/usr/libexec/java_home)/include" -I"$(/usr/libexec/java_home)/include/darwin"
swig -java -package com.nicslu.jni -outdir com/nicslu/jni -o nicslu_wrap.c nicslu.i
gcc -c -fPIC nicslu.c nicslu_wrap.c  -I"$(/usr/libexec/java_home)/include" -I"$(/usr/libexec/java_home)/include/darwin"
gcc -dynamiclib  nicslu.o  nicslu_wrap.o nicslu_readfile.o nicslu_readfile_wrap.o ../lib/libnicslu.a -o libnicslu.dylib -lpthread -lm #-lrt
javac test.java
jar cvf nicslu.jar com/nicslu/jni/*.class libnicslu.so
java -cp . -Djava.library.path=. test 2

