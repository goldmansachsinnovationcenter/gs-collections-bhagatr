#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <math.h>

#include "jacobi-2d.h"

static
void init_array (int n,
                 DATA_TYPE A[n][n],
                 DATA_TYPE B[n][n])
{
  int i, j;

  for (i = 0; i < n; i++)
    for (j = 0; j < n; j++)
      {
        A[i][j] = ((DATA_TYPE) i*(j+2) + 2) / n;
        B[i][j] = ((DATA_TYPE) i*(j+3) + 3) / n;
      }
}

static
void print_array(int n,
                 DATA_TYPE A[n][n])
{
  int i, j;

  POLYBENCH_DUMP_START;
  POLYBENCH_DUMP_BEGIN("A");
  for (i = 0; i < n; i++)
    for (j = 0; j < n; j++) {
      if ((i * n + j) % 20 == 0) fprintf(POLYBENCH_DUMP_TARGET, "\n");
      fprintf(POLYBENCH_DUMP_TARGET, DATA_PRINTF_MODIFIER, A[i][j]);
    }
  POLYBENCH_DUMP_END("A");
  POLYBENCH_DUMP_FINISH;
}

static
void kernel_jacobi_2d(int tsteps,
                      int n,
                      DATA_TYPE A[n][n],
                      DATA_TYPE B[n][n])
{
  int t, i, j;

#pragma scop
  for (t = 0; t < tsteps; t++)
    {
      for (i = 1; i < n - 1; i++)
        for (j = 1; j < n - 1; j++)
          B[i][j] = 0.2 * (A[i][j] + A[i][j-1] + A[i][1+j] + A[1+i][j] + A[i-1][j]);
      for (i = 1; i < n - 1; i++)
        for (j = 1; j < n - 1; j++)
          A[i][j] = 0.2 * (B[i][j] + B[i][j-1] + B[i][1+j] + B[1+i][j] + B[i-1][j]);
    }
#pragma endscop
}

int main(int argc, char** argv)
{
  int n = N;
  int tsteps = TSTEPS;

  POLYBENCH_2D_ARRAY_DECL(A, DATA_TYPE, N, N, n, n);
  POLYBENCH_2D_ARRAY_DECL(B, DATA_TYPE, N, N, n, n);

  init_array (n, POLYBENCH_ARRAY(A), POLYBENCH_ARRAY(B));

  polybench_start_instruments;

  kernel_jacobi_2d(tsteps, n, POLYBENCH_ARRAY(A), POLYBENCH_ARRAY(B));

  polybench_stop_instruments;

#ifdef POLYBENCH_DUMP_ARRAYS
  print_array(n, POLYBENCH_ARRAY(A));
#endif

  POLYBENCH_FREE_ARRAY(A);
  POLYBENCH_FREE_ARRAY(B);

  return 0;
}
