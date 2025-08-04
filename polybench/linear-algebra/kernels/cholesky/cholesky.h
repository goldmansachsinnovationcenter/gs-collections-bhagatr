#ifndef CHOLESKY_H
#define CHOLESKY_H

#include "../../../utilities/polybench.h"

#ifndef N
# define N _PB_N
#endif

static void init_array(int n, DATA_TYPE A[n][n]);

static void print_array(int n, DATA_TYPE A[n][n]);

static void kernel_cholesky(int n, DATA_TYPE A[n][n]);

#endif
