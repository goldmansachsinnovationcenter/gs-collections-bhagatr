#include "polybench.h"

#ifdef POLYBENCH_TIME
static double polybench_program_total_time = 0;
#endif

#ifdef POLYBENCH_PAPI
static int polybench_papi_eventset;
static int polybench_papi_eventlist[POLYBENCH_MAX_NB_PAPI_COUNTERS];
static long long polybench_papi_values[POLYBENCH_MAX_NB_PAPI_COUNTERS];
#endif

void* polybench_alloc_data_impl(unsigned long long int n, int elt_size)
{
  size_t val = n;
  val *= elt_size;
  void* ret = malloc(val);
  return ret;
}

void polybench_free_data(void* ptr)
{
  free(ptr);
}

#ifdef POLYBENCH_TIME
void polybench_timer_start()
{
  struct timeval time;
  gettimeofday(&time, NULL);
  polybench_program_total_time = time.tv_sec + time.tv_usec * 1e-6;
}

void polybench_timer_stop()
{
  struct timeval time;
  gettimeofday(&time, NULL);
  polybench_program_total_time = (time.tv_sec + time.tv_usec * 1e-6) - polybench_program_total_time;
}

void polybench_timer_print()
{
  printf("%0.6f\n", polybench_program_total_time);
}
#endif

#ifdef POLYBENCH_PAPI
void polybench_papi_init()
{
  int retval;
  polybench_papi_eventset = PAPI_NULL;
  if ((retval = PAPI_library_init(PAPI_VER_CURRENT)) != PAPI_VER_CURRENT)
    {
      printf("Error initializing PAPI library\n");
      exit(1);
    }
  if ((retval = PAPI_create_eventset(&polybench_papi_eventset)) != PAPI_OK)
    {
      printf("Error creating PAPI eventset\n");
      exit(1);
    }
}

void polybench_papi_close()
{
  int retval;
  if ((retval = PAPI_destroy_eventset(&polybench_papi_eventset)) != PAPI_OK)
    {
      printf("Error destroying PAPI eventset\n");
      exit(1);
    }
  PAPI_shutdown();
}

void polybench_papi_print()
{
  int i;
  for (i = 0; i < POLYBENCH_MAX_NB_PAPI_COUNTERS; ++i)
    if (polybench_papi_eventlist[i] != 0)
      printf("%lld ", polybench_papi_values[i]);
  printf("\n");
}
#endif

void polybench_prepare_instruments()
{
#ifndef POLYBENCH_NO_FLUSH_CACHE
  int cs = _PB_N;
  cs *= cs;
  cs *= cs;
  cs *= sizeof(double);
  double* flush = (double*) calloc(cs, sizeof(double));
  int i;
  double tmp = 0.0;
  for (i = 0; i < cs; i++)
    tmp += flush[i];
  polybench_prevent_dce(tmp);
  free(flush);
#endif

#ifdef POLYBENCH_LINUX_FIFO_SCHEDULER
  struct sched_param schedParam;
  schedParam.sched_priority = sched_get_priority_max(SCHED_FIFO);
  sched_setscheduler(0, SCHED_FIFO, &schedParam);
#endif

#ifdef POLYBENCH_PAPI
  polybench_papi_init();
#endif
}
