#ifndef JACOBI_2D_H
#define JACOBI_2D_H

#include "../../../utilities/polybench.h"

#ifndef TSTEPS
# define TSTEPS _PB_NT
#endif
#ifndef N
# define N _PB_N
#endif

static void init_array (int n,
                        DATA_TYPE A[n][n],
                        DATA_TYPE B[n][n]);

static void print_array(int n,
                        DATA_TYPE A[n][n]);

static void kernel_jacobi_2d(int tsteps,
                             int n,
                             DATA_TYPE A[n][n],
                             DATA_TYPE B[n][n]);

#endif
