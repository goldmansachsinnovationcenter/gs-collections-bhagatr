#ifndef POLYBENCH_H
#define POLYBENCH_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include <time.h>
#include <sys/time.h>

#ifdef POLYBENCH_TIME
# include <sys/time.h>
#endif

#ifdef POLYBENCH_PAPI
# include <papi.h>
# define POLYBENCH_MAX_NB_PAPI_COUNTERS 4
#endif

#ifndef DATA_TYPE
# define DATA_TYPE double
#endif

#ifndef DATA_PRINTF_MODIFIER
# define DATA_PRINTF_MODIFIER "%0.2lf "
#endif

#ifdef MINI_DATASET
# define _PB_N 32
# define _PB_M 32
# define _PB_NI 32
# define _PB_NJ 32
# define _PB_NK 32
# define _PB_NL 32
# define _PB_NP 32
# define _PB_NQ 32
# define _PB_NR 32
# define _PB_NS 32
# define _PB_NT 32
# define _PB_NX 32
# define _PB_NY 32
# define _PB_NZ 32
#endif

#ifdef SMALL_DATASET
# define _PB_N 128
# define _PB_M 128
# define _PB_NI 128
# define _PB_NJ 128
# define _PB_NK 128
# define _PB_NL 128
# define _PB_NP 128
# define _PB_NQ 128
# define _PB_NR 128
# define _PB_NS 128
# define _PB_NT 128
# define _PB_NX 128
# define _PB_NY 128
# define _PB_NZ 128
#endif

#ifdef MEDIUM_DATASET
# define _PB_N 512
# define _PB_M 512
# define _PB_NI 512
# define _PB_NJ 512
# define _PB_NK 512
# define _PB_NL 512
# define _PB_NP 512
# define _PB_NQ 512
# define _PB_NR 512
# define _PB_NS 512
# define _PB_NT 512
# define _PB_NX 512
# define _PB_NY 512
# define _PB_NZ 512
#endif

#ifdef LARGE_DATASET
# define _PB_N 2000
# define _PB_M 2000
# define _PB_NI 2000
# define _PB_NJ 2000
# define _PB_NK 2000
# define _PB_NL 2000
# define _PB_NP 2000
# define _PB_NQ 2000
# define _PB_NR 2000
# define _PB_NS 2000
# define _PB_NT 2000
# define _PB_NX 2000
# define _PB_NY 2000
# define _PB_NZ 2000
#endif

#ifdef EXTRALARGE_DATASET
# define _PB_N 4000
# define _PB_M 4000
# define _PB_NI 4000
# define _PB_NJ 4000
# define _PB_NK 4000
# define _PB_NL 4000
# define _PB_NP 4000
# define _PB_NQ 4000
# define _PB_NR 4000
# define _PB_NS 4000
# define _PB_NT 4000
# define _PB_NX 4000
# define _PB_NY 4000
# define _PB_NZ 4000
#endif

#ifndef _PB_N
# define _PB_N 1000
# define _PB_M 1000
# define _PB_NI 1000
# define _PB_NJ 1000
# define _PB_NK 1000
# define _PB_NL 1000
# define _PB_NP 1000
# define _PB_NQ 1000
# define _PB_NR 1000
# define _PB_NS 1000
# define _PB_NT 1000
# define _PB_NX 1000
# define _PB_NY 1000
# define _PB_NZ 1000
#endif

#define polybench_prevent_dce(func) asm volatile("" : : "r,m"(func) : "memory")

#define polybench_alloc_data(n, type) \
  (type*)polybench_alloc_data_impl(n, sizeof(type))

void* polybench_alloc_data_impl(unsigned long long int n, int elt_size);

void polybench_free_data(void* ptr);

#ifdef POLYBENCH_STACK_ARRAYS
# define POLYBENCH_ARRAY(var) var
# define POLYBENCH_FREE_ARRAY(var)
# define POLYBENCH_DECL_VAR(var)
# define POLYBENCH_1D_ARRAY_DECL(var, type, size1, arg1) type var[size1]
# define POLYBENCH_2D_ARRAY_DECL(var, type, size1, size2, arg1, arg2) type var[size1][size2]
# define POLYBENCH_3D_ARRAY_DECL(var, type, size1, size2, size3, arg1, arg2, arg3) type var[size1][size2][size3]
#else
# define POLYBENCH_ARRAY(var) var
# define POLYBENCH_FREE_ARRAY(var) polybench_free_data((void*)var)
# define POLYBENCH_DECL_VAR(var) (*var)
# define POLYBENCH_1D_ARRAY_DECL(var, type, size1, arg1) \
  type *var = (type*)polybench_alloc_data(size1, type)
# define POLYBENCH_2D_ARRAY_DECL(var, type, size1, size2, arg1, arg2) \
  type (*var)[arg2] = (type(*)[arg2])polybench_alloc_data((size1) * (size2), type)
# define POLYBENCH_3D_ARRAY_DECL(var, type, size1, size2, size3, arg1, arg2, arg3) \
  type (*var)[arg2][arg3] = (type(*)[arg2][arg3])polybench_alloc_data((size1) * (size2) * (size3), type)
#endif

#define POLYBENCH_1D(var, size1, arg1) var
#define POLYBENCH_2D(var, size1, size2, arg1, arg2) var
#define POLYBENCH_3D(var, size1, size2, size3, arg1, arg2, arg3) var

#ifdef POLYBENCH_DUMP_ARRAYS
# define POLYBENCH_DUMP_TARGET stderr
# define POLYBENCH_DUMP_START fprintf(POLYBENCH_DUMP_TARGET, "==BEGIN DUMP_ARRAYS==\n")
# define POLYBENCH_DUMP_FINISH fprintf(POLYBENCH_DUMP_TARGET, "==END   DUMP_ARRAYS==\n")
# define POLYBENCH_DUMP_BEGIN(s) fprintf(POLYBENCH_DUMP_TARGET, "%s begin dump: %s", s, #s)
# define POLYBENCH_DUMP_END(s) fprintf(POLYBENCH_DUMP_TARGET, "\n%s end   dump: %s\n", s, #s)
#else
# define POLYBENCH_DUMP_TARGET stderr
# define POLYBENCH_DUMP_START
# define POLYBENCH_DUMP_FINISH
# define POLYBENCH_DUMP_BEGIN(s)
# define POLYBENCH_DUMP_END(s)
#endif

#ifdef POLYBENCH_TIME
void polybench_timer_start();
void polybench_timer_stop();
void polybench_timer_print();
#endif

#ifdef POLYBENCH_PAPI
void polybench_papi_init();
void polybench_papi_close();
void polybench_papi_print();
#endif

#define polybench_start_instruments \
  polybench_prepare_instruments(); \
  polybench_timer_start();

#define polybench_stop_instruments \
  polybench_timer_stop(); \
  polybench_timer_print();

void polybench_prepare_instruments();

#endif
