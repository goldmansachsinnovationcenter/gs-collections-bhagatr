#ifndef JACOBI_1D_H
#define JACOBI_1D_H

#include "../../../utilities/polybench.h"

#ifndef TSTEPS
# define TSTEPS _PB_NT
#endif
#ifndef N
# define N _PB_N
#endif

static void init_array (int n,
                        DATA_TYPE A[n],
                        DATA_TYPE B[n]);

static void print_array(int n,
                        DATA_TYPE A[n]);

static void kernel_jacobi_1d(int tsteps,
                             int n,
                             DATA_TYPE A[n],
                             DATA_TYPE B[n]);

#endif
