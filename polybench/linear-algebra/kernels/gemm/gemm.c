#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <math.h>

#include "gemm.h"

static
void init_array(int ni, int nj, int nk,
                DATA_TYPE *alpha,
                DATA_TYPE *beta,
                DATA_TYPE C[ni][nj],
                DATA_TYPE A[ni][nk],
                DATA_TYPE B[nk][nj])
{
  int i, j;

  *alpha = 1.5;
  *beta = 1.2;
  for (i = 0; i < ni; i++)
    for (j = 0; j < nj; j++)
      C[i][j] = (DATA_TYPE) ((i*j+1) % ni) / ni;
  for (i = 0; i < ni; i++)
    for (j = 0; j < nk; j++)
      A[i][j] = (DATA_TYPE) (i*(j+1) % nk) / nk;
  for (i = 0; i < nk; i++)
    for (j = 0; j < nj; j++)
      B[i][j] = (DATA_TYPE) (i*(j+2) % nj) / nj;
}

static
void print_array(int ni, int nj,
                 DATA_TYPE C[ni][nj])
{
  int i, j;

  POLYBENCH_DUMP_START;
  POLYBENCH_DUMP_BEGIN("C");
  for (i = 0; i < ni; i++)
    for (j = 0; j < nj; j++) {
      if ((i * nj + j) % 20 == 0) fprintf (POLYBENCH_DUMP_TARGET, "\n");
      fprintf (POLYBENCH_DUMP_TARGET, DATA_PRINTF_MODIFIER, C[i][j]);
    }
  POLYBENCH_DUMP_END("C");
  POLYBENCH_DUMP_FINISH;
}

static
void kernel_gemm(int ni, int nj, int nk,
                 DATA_TYPE alpha,
                 DATA_TYPE beta,
                 DATA_TYPE C[ni][nj],
                 DATA_TYPE A[ni][nk],
                 DATA_TYPE B[nk][nj])
{
  int i, j, k;

#pragma scop
  for (i = 0; i < ni; i++) {
    for (j = 0; j < nj; j++)
      C[i][j] *= beta;
    for (k = 0; k < nk; k++) {
      for (j = 0; j < nj; j++)
        C[i][j] += alpha * A[i][k] * B[k][j];
    }
  }
#pragma endscop
}

int main(int argc, char** argv)
{
  int ni = NI;
  int nj = NJ;
  int nk = NK;

  DATA_TYPE alpha;
  DATA_TYPE beta;
  POLYBENCH_2D_ARRAY_DECL(C, DATA_TYPE, NI, NJ, ni, nj);
  POLYBENCH_2D_ARRAY_DECL(A, DATA_TYPE, NI, NK, ni, nk);
  POLYBENCH_2D_ARRAY_DECL(B, DATA_TYPE, NK, NJ, nk, nj);

  init_array (ni, nj, nk, &alpha, &beta,
              POLYBENCH_ARRAY(C),
              POLYBENCH_ARRAY(A),
              POLYBENCH_ARRAY(B));

  polybench_start_instruments;

  kernel_gemm (ni, nj, nk,
               alpha, beta,
               POLYBENCH_ARRAY(C),
               POLYBENCH_ARRAY(A),
               POLYBENCH_ARRAY(B));

  polybench_stop_instruments;

#ifdef POLYBENCH_DUMP_ARRAYS
  print_array(ni, nj, POLYBENCH_ARRAY(C));
#endif

  POLYBENCH_FREE_ARRAY(C);
  POLYBENCH_FREE_ARRAY(A);
  POLYBENCH_FREE_ARRAY(B);

  return 0;
}
