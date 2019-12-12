#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "nicslu.h"
#include "nicslu_util.h"

int readFromFile(char *file, uint__t *n, uint__t *nnz, uint__t *ai, uint__t *ap, real__t *ax, real__t *x, real__t *b)
{
	int ret;
	uint__t *Ap, *Ai;
	real__t *Ax, *B, *X;

	Ax = NULL;
	Ai = NULL;
	Ap = NULL;
	X = NULL;
	B = NULL;

	ret = NicsLU_ReadTripletColumnToSparse(file, n, nnz, &Ax, &Ai, &Ap);
	if (ret != NICS_OK) return 0;

	B = x + *n;

	for (int i=0;i<*nnz;i++) {
		ax[i]=Ax[i];
		ai[i]=Ai[i];
		if (i<=*n) ap[i]=Ap[i];
		if (i<*n+*n) x[i]=1.;
	}

	for (int i=0;i<*n;i++) b[i]=B[i];

	return 1;
}
