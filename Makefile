all:
	(cd lib; $(MAKE))
	(cd util; $(MAKE))
	(cd demo; $(MAKE))

lib:
	(cd lib; $(MAKE))

util:
	(cd util; $(MAKE))

demo:
	(cd demo; $(MAKE))

clean:
	(cd lib; $(MAKE) clean)
	(cd util; $(MAKE) clean)
	(cd demo; $(MAKE) clean)

install:
	mkdir ../../../build/include/omc/c/nicslu
	cp include/*	../../../build/include/omc/c/nicslu/
	cp lib/libnicslu.a ../../../build/lib/x86_64-linux-gnu/omc/
