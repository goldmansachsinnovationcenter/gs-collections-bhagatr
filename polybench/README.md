# PolyBench/C Benchmark Suite

This directory contains a C implementation of the PolyBench benchmark suite, a collection of 30 numerical computations with static control flow extracted from operations in various application domains including linear algebra, image processing, physics simulation, dynamic programming, and statistics.

## Overview

PolyBench/C is designed for:
- Performance measurement and comparison
- Compiler optimization evaluation
- Parallelization and vectorization studies
- Hardware performance analysis

This implementation follows the PolyBench/C 4.2.1 standard structure and includes:
- Parametric loop bounds for general-purpose implementation
- Non-null data initialization and live-out data dump
- Syntactic constructs to prevent dead code elimination
- Support for different data types (float, double, int)
- Optional timing and profiling capabilities

## Directory Structure

```
polybench/
├── utilities/           # Core PolyBench infrastructure
│   ├── polybench.h     # Main header with macros and configurations
│   └── polybench.c     # Utility functions for timing and memory management
├── linear-algebra/     # Linear algebra kernels
│   └── kernels/
│       ├── gemm/       # General Matrix Multiply
│       ├── 2mm/        # Two Matrix Multiplications
│       └── cholesky/   # Cholesky Decomposition
├── stencils/          # Stencil computation kernels
│   └── kernels/
│       ├── jacobi-1d/  # 1D Jacobi Stencil
│       └── jacobi-2d/  # 2D Jacobi Stencil
└── Makefile           # Build system
```

## Implemented Benchmarks

### Linear Algebra
- **GEMM**: General Matrix Multiply (C = αAB + βC)
- **2MM**: Two Matrix Multiplications (D = α(AB)C + βD)
- **Cholesky**: Cholesky Decomposition for symmetric positive definite matrices

### Stencils
- **Jacobi-1D**: 1D Jacobi stencil computation
- **Jacobi-2D**: 2D Jacobi stencil computation

## Compilation

### Basic Compilation
```bash
make                    # Compile all benchmarks with default settings
make gemm              # Compile specific benchmark
make clean             # Remove all executables
```

### Dataset Sizes
The benchmarks support multiple dataset sizes:
```bash
make test-mini         # MINI dataset (32x32)
make test-small        # SMALL dataset (128x128)
make test-medium       # MEDIUM dataset (1000x1000) - default
make test-large        # LARGE dataset (2000x2000)
```

### Debug Build
```bash
make debug             # Compile with -O0 -g for debugging
```

### Compilation Flags
- `-DPOLYBENCH_TIME`: Enable timing measurements
- `-DPOLYBENCH_DUMP_ARRAYS`: Enable array output for verification
- `-DPOLYBENCH_STACK_ARRAYS`: Use stack allocation instead of heap

## Usage

### Running Benchmarks
```bash
./gemm                 # Run GEMM benchmark
./2mm                  # Run 2MM benchmark
./jacobi-1d            # Run Jacobi-1D benchmark
./jacobi-2d            # Run Jacobi-2D benchmark
./cholesky             # Run Cholesky benchmark
```

### Example Output
```bash
$ ./gemm
0.051875
```
The output shows the execution time in seconds when compiled with `-DPOLYBENCH_TIME`.

### Verification Mode
To verify correctness, compile with array dumping enabled:
```bash
gcc -O3 -I utilities -DMEDIUM_DATASET -DPOLYBENCH_DUMP_ARRAYS \
    -I linear-algebra/kernels/gemm \
    utilities/polybench.c linear-algebra/kernels/gemm/gemm.c \
    -o gemm -lm
./gemm > output.txt
```

## Dataset Size Configuration

The benchmarks use preprocessor macros to define dataset sizes:

| Size | N | NI | NJ | NK | NL |
|------|---|----|----|----|----|
| MINI | 32 | 16 | 18 | 22 | 24 |
| SMALL | 128 | 60 | 70 | 80 | 90 |
| MEDIUM | 1000 | 800 | 900 | 1100 | 1200 |
| LARGE | 2000 | 1600 | 1800 | 2200 | 2400 |
| EXTRALARGE | 4000 | 3200 | 3600 | 4400 | 4800 |

## Performance Measurement

The benchmarks include built-in timing capabilities:
- Compile with `-DPOLYBENCH_TIME` to enable timing
- Uses high-resolution timers for accurate measurement
- Measures only the computational kernel, excluding initialization

## Extending the Suite

To add new benchmarks:
1. Create appropriate directory structure under the relevant category
2. Implement the benchmark following the PolyBench conventions:
   - Use `#pragma scop` and `#pragma endscop` to mark computational kernels
   - Include proper data initialization and array dumping functions
   - Use PolyBench macros for array declarations and memory management
3. Add compilation target to the Makefile

## Technical Details

### Memory Management
- Uses dynamic allocation by default via `polybench_alloc_data()`
- Supports stack allocation with `-DPOLYBENCH_STACK_ARRAYS`
- Automatic memory cleanup with `POLYBENCH_FREE_ARRAY()`

### Data Types
- Default: `double` precision floating point
- Configurable via `DATA_TYPE` macro
- Printf format controlled by `DATA_PRINTF_MODIFIER`

### Compiler Compatibility
- Tested with GCC (requires C99 support for variable-length arrays)
- Compatible with various optimization levels (-O0 to -O3)
- Supports common compiler flags for vectorization and parallelization

## References

- PolyBench/C official website: https://web.cse.ohio-state.edu/~pouchet.2/software/polybench/
- Original paper: "PolyBench/C: A Benchmark Suite for C Programs" by Louis-Noël Pouchet
