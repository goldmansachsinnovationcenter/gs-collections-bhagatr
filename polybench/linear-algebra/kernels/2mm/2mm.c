#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <math.h>

#include "2mm.h"

static
void init_array(int ni, int nj, int nk, int nl,
                DATA_TYPE *alpha,
                DATA_TYPE *beta,
                DATA_TYPE A[ni][nk],
                DATA_TYPE B[nk][nj],
                DATA_TYPE C[nj][nl],
                DATA_TYPE D[ni][nl])
{
  int i, j;

  *alpha = 1.5;
  *beta = 1.2;
  for (i = 0; i < ni; i++)
    for (j = 0; j < nk; j++)
      A[i][j] = (DATA_TYPE) ((i*j+1) % ni) / ni;
  for (i = 0; i < nk; i++)
    for (j = 0; j < nj; j++)
      B[i][j] = (DATA_TYPE) (i*(j+1) % nj) / nj;
  for (i = 0; i < nj; i++)
    for (j = 0; j < nl; j++)
      C[i][j] = (DATA_TYPE) ((i*(j+3)+1) % nl) / nl;
  for (i = 0; i < ni; i++)
    for (j = 0; j < nl; j++)
      D[i][j] = (DATA_TYPE) (i*(j+2) % nk) / nk;
}

static
void print_array(int ni, int nl,
                 DATA_TYPE D[ni][nl])
{
  int i, j;

  POLYBENCH_DUMP_START;
  POLYBENCH_DUMP_BEGIN("D");
  for (i = 0; i < ni; i++)
    for (j = 0; j < nl; j++) {
      if ((i * nl + j) % 20 == 0) fprintf (POLYBENCH_DUMP_TARGET, "\n");
      fprintf (POLYBENCH_DUMP_TARGET, DATA_PRINTF_MODIFIER, D[i][j]);
    }
  POLYBENCH_DUMP_END("D");
  POLYBENCH_DUMP_FINISH;
}

static
void kernel_2mm(int ni, int nj, int nk, int nl,
                DATA_TYPE alpha,
                DATA_TYPE beta,
                DATA_TYPE tmp[ni][nj],
                DATA_TYPE A[ni][nk],
                DATA_TYPE B[nk][nj],
                DATA_TYPE C[nj][nl],
                DATA_TYPE D[ni][nl])
{
  int i, j, k;

#pragma scop
  for (i = 0; i < ni; i++)
    for (j = 0; j < nj; j++)
      {
        tmp[i][j] = 0.0;
        for (k = 0; k < nk; ++k)
          tmp[i][j] += alpha * A[i][k] * B[k][j];
      }
  for (i = 0; i < ni; i++)
    for (j = 0; j < nl; j++)
      {
        D[i][j] *= beta;
        for (k = 0; k < nj; ++k)
          D[i][j] += tmp[i][k] * C[k][j];
      }
#pragma endscop
}

int main(int argc, char** argv)
{
  int ni = NI;
  int nj = NJ;
  int nk = NK;
  int nl = NL;

  DATA_TYPE alpha;
  DATA_TYPE beta;
  POLYBENCH_2D_ARRAY_DECL(tmp, DATA_TYPE, NI, NJ, ni, nj);
  POLYBENCH_2D_ARRAY_DECL(A, DATA_TYPE, NI, NK, ni, nk);
  POLYBENCH_2D_ARRAY_DECL(B, DATA_TYPE, NK, NJ, nk, nj);
  POLYBENCH_2D_ARRAY_DECL(C, DATA_TYPE, NJ, NL, nj, nl);
  POLYBENCH_2D_ARRAY_DECL(D, DATA_TYPE, NI, NL, ni, nl);

  init_array (ni, nj, nk, nl, &alpha, &beta,
              POLYBENCH_ARRAY(A),
              POLYBENCH_ARRAY(B),
              POLYBENCH_ARRAY(C),
              POLYBENCH_ARRAY(D));

  polybench_start_instruments;

  kernel_2mm (ni, nj, nk, nl,
              alpha, beta,
              POLYBENCH_ARRAY(tmp),
              POLYBENCH_ARRAY(A),
              POLYBENCH_ARRAY(B),
              POLYBENCH_ARRAY(C),
              POLYBENCH_ARRAY(D));

  polybench_stop_instruments;

#ifdef POLYBENCH_DUMP_ARRAYS
  print_array(ni, nl, POLYBENCH_ARRAY(D));
#endif

  POLYBENCH_FREE_ARRAY(tmp);
  POLYBENCH_FREE_ARRAY(A);
  POLYBENCH_FREE_ARRAY(B);
  POLYBENCH_FREE_ARRAY(C);
  POLYBENCH_FREE_ARRAY(D);

  return 0;
}
