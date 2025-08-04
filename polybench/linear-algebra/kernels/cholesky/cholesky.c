#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <math.h>

#include "cholesky.h"

static
void init_array(int n, DATA_TYPE A[n][n])
{
  int i, j;

  for (i = 0; i < n; i++)
    {
      for (j = 0; j <= i; j++)
        A[i][j] = (DATA_TYPE)(-j % n) / n + 1;
      for (j = i+1; j < n; j++) {
        A[i][j] = 0;
      }
      A[i][i] = 1;
    }

  DATA_TYPE (*B)[n] = (DATA_TYPE(*)[n])polybench_alloc_data(n * n, DATA_TYPE);
  for (i = 0; i < n; i++)
    for (j = 0; j < n; j++)
      B[i][j] = 0;
  for (i = 0; i < n; i++)
    for (j = 0; j < n; j++)
      for (int k = 0; k < n; k++)
        B[i][j] += A[i][k] * A[j][k];

  for (i = 0; i < n; i++)
    for (j = 0; j < n; j++)
      A[i][j] = B[i][j];
  polybench_free_data((void*)B);
}

static
void print_array(int n, DATA_TYPE A[n][n])
{
  int i, j;

  POLYBENCH_DUMP_START;
  POLYBENCH_DUMP_BEGIN("A");
  for (i = 0; i < n; i++)
    for (j = 0; j <= i; j++) {
      if ((i * n + j) % 20 == 0) fprintf (POLYBENCH_DUMP_TARGET, "\n");
      fprintf (POLYBENCH_DUMP_TARGET, DATA_PRINTF_MODIFIER, A[i][j]);
    }
  POLYBENCH_DUMP_END("A");
  POLYBENCH_DUMP_FINISH;
}

static
void kernel_cholesky(int n, DATA_TYPE A[n][n])
{
  int i, j, k;

#pragma scop
  for (i = 0; i < n; i++) {
    for (j = 0; j < i; j++) {
      for (k = 0; k < j; k++) {
        A[i][j] -= A[i][k] * A[j][k];
      }
      A[i][j] /= A[j][j];
    }
    for (k = 0; k < i; k++) {
      A[i][i] -= A[i][k] * A[i][k];
    }
    A[i][i] = sqrt(A[i][i]);
  }
#pragma endscop
}

int main(int argc, char** argv)
{
  int n = N;

  POLYBENCH_2D_ARRAY_DECL(A, DATA_TYPE, N, N, n, n);

  init_array (n, POLYBENCH_ARRAY(A));

  polybench_start_instruments;

  kernel_cholesky (n, POLYBENCH_ARRAY(A));

  polybench_stop_instruments;

#ifdef POLYBENCH_DUMP_ARRAYS
  print_array(n, POLYBENCH_ARRAY(A));
#endif

  POLYBENCH_FREE_ARRAY(A);

  return 0;
}
