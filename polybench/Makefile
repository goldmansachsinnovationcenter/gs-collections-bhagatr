CC = gcc
CFLAGS = -O3 -Wall
POLYBENCH_FLAGS = -I utilities
DATASET_SIZE = -DMEDIUM_DATASET
TIMING_FLAGS = -DPOLYBENCH_TIME

# Default target
all: gemm 2mm jacobi-1d cholesky jacobi-2d

# Linear algebra benchmarks
gemm: linear-algebra/kernels/gemm/gemm.c utilities/polybench.c
	$(CC) $(CFLAGS) $(POLYBENCH_FLAGS) $(DATASET_SIZE) $(TIMING_FLAGS) \
		-I linear-algebra/kernels/gemm \
		utilities/polybench.c linear-algebra/kernels/gemm/gemm.c \
		-o gemm -lm

2mm: linear-algebra/kernels/2mm/2mm.c utilities/polybench.c
	$(CC) $(CFLAGS) $(POLYBENCH_FLAGS) $(DATASET_SIZE) $(TIMING_FLAGS) \
		-I linear-algebra/kernels/2mm \
		utilities/polybench.c linear-algebra/kernels/2mm/2mm.c \
		-o 2mm -lm

# Stencil benchmarks
jacobi-1d: stencils/kernels/jacobi-1d/jacobi-1d.c utilities/polybench.c
	$(CC) $(CFLAGS) $(POLYBENCH_FLAGS) $(DATASET_SIZE) $(TIMING_FLAGS) \
		-I stencils/kernels/jacobi-1d \
		utilities/polybench.c stencils/kernels/jacobi-1d/jacobi-1d.c \
		-o jacobi-1d -lm

cholesky: linear-algebra/kernels/cholesky/cholesky.c utilities/polybench.c
	$(CC) $(CFLAGS) $(POLYBENCH_FLAGS) $(DATASET_SIZE) $(TIMING_FLAGS) \
		-I linear-algebra/kernels/cholesky \
		utilities/polybench.c linear-algebra/kernels/cholesky/cholesky.c \
		-o cholesky -lm

jacobi-2d: stencils/kernels/jacobi-2d/jacobi-2d.c utilities/polybench.c
	$(CC) $(CFLAGS) $(POLYBENCH_FLAGS) $(DATASET_SIZE) $(TIMING_FLAGS) \
		-I stencils/kernels/jacobi-2d \
		utilities/polybench.c stencils/kernels/jacobi-2d/jacobi-2d.c \
		-o jacobi-2d -lm

# Test targets with different dataset sizes
test-mini: DATASET_SIZE = -DMINI_DATASET
test-mini: all

test-small: DATASET_SIZE = -DSMALL_DATASET
test-small: all

test-medium: DATASET_SIZE = -DMEDIUM_DATASET
test-medium: all

test-large: DATASET_SIZE = -DLARGE_DATASET
test-large: all

# Debug builds
debug: CFLAGS = -O0 -g -Wall
debug: all

# Clean
clean:
	rm -f gemm 2mm jacobi-1d cholesky jacobi-2d

.PHONY: all test-mini test-small test-medium test-large debug clean
