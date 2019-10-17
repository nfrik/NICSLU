#gcc -c nicslu_readfile.c nicslu_util_wrap.c -I /usr/lib/jvm/java-8-openjdk-amd64/include -I /usr/lib/jvm/java-8-openjdk-amd64/include/linux
gcc -fpic -c nicslu_readfile.c nicslu_util_wrap.c -I /usr/lib/jvm/java-8-openjdk-amd64/include -I /usr/lib/jvm/java-8-openjdk-amd64/include/linux
gcc -shared nicslu_readfile.o nicslu_util_wrap.o -o nicslu_util.so

#with linking
gcc -shared nicslu_readfile.o nicslu_util_wrap.o -o nicslu_util.so -L. libnicslu.a -L. nicslu_util.a -lrt -lpthread -lm