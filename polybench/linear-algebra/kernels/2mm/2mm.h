#ifndef TWOMM_H
#define TWOMM_H

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
#ifndef NL
# define NL _PB_NL
#endif

static void init_array(int ni, int nj, int nk, int nl,
                       DATA_TYPE *alpha,
                       DATA_TYPE *beta,
                       DATA_TYPE A[ni][nk],
                       DATA_TYPE B[nk][nj],
                       DATA_TYPE C[nj][nl],
                       DATA_TYPE D[ni][nl]);

static void print_array(int ni, int nl,
                        DATA_TYPE D[ni][nl]);

static void kernel_2mm(int ni, int nj, int nk, int nl,
                       DATA_TYPE alpha,
                       DATA_TYPE beta,
                       DATA_TYPE tmp[ni][nj],
                       DATA_TYPE A[ni][nk],
                       DATA_TYPE B[nk][nj],
                       DATA_TYPE C[nj][nl],
                       DATA_TYPE D[ni][nl]);

#endif
