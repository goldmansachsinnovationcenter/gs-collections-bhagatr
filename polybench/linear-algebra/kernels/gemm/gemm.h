#ifndef GEMM_H
#define GEMM_H

#include "../../../utilities/polybench.h"

#ifndef NI
# define NI _PB_NI
#endif
#ifndef NJ
# define NJ _PB_NJ
#endif
#ifndef NK
# define NK _PB_NK
#endif

static void init_array(int ni, int nj, int nk,
                       DATA_TYPE *alpha,
                       DATA_TYPE *beta,
                       DATA_TYPE C[ni][nj],
                       DATA_TYPE A[ni][nk],
                       DATA_TYPE B[nk][nj]);

static void print_array(int ni, int nj,
                        DATA_TYPE C[ni][nj]);

static void kernel_gemm(int ni, int nj, int nk,
                        DATA_TYPE alpha,
                        DATA_TYPE beta,
                        DATA_TYPE C[ni][nj],
                        DATA_TYPE A[ni][nk],
                        DATA_TYPE B[nk][nj]);

#endif
