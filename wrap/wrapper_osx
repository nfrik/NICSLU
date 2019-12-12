#!/bin/bash
cd ..
make
cd wrap
mkdir -p com/nicslu/jni
mv NativeUtils.java com/nicslu/jni
javac com/nicslu/jni/NativeUtils.java
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$PWD
swig -java -package com.nicslu.jni -outdir com/nicslu/jni -o nicslu_readfile_wrap.c nicslu_readfile.i
gcc -c -fPIC nicslu_readfile.c nicslu_readfile_wrap.c  -I"/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/include" -I"/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/include/darwin"
swig -java -package com.nicslu.jni -outdir com/nicslu/jni -o nicslu_wrap.c nicslu.i
gcc -c -fPIC nicslu.c nicslu_wrap.c  -I"/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/include" -I"/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/include/darwin"
gcc -dynamiclib  nicslu.o  nicslu_wrap.o nicslu_readfile.o nicslu_readfile_wrap.o ../lib/libnicslu.a -o libnicslu.so #-lpthread -lm #-lc #-lrt-lm #-lrt
#javac test.java
#jar cvf nicslu.jar com/nicslu/jni/*.class libnicslu.so
