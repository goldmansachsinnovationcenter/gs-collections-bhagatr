/*
 * Copyright 2015 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProcedureFJTaskRunnerTest
{
    private ExecutorService executor;
    
    @Before
    public void setUp()
    {
        this.executor = Executors.newFixedThreadPool(2);
    }
    
    @After
    public void tearDown()
    {
        this.executor.shutdown();
    }
    
    @Test
    public void executeAndCombineWithCombineOne()
    {
        List<Integer> list = FastList.newListWith(1, 2, 3, 4, 5);
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        SumCombiner combiner = new SumCombiner(sum);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new ProcedureFJTaskRunner<>(combiner, 2);
        
        taskRunner.executeAndCombine(this.executor, factory, list);
        
        Assert.assertEquals(15, sum.get()); // 1+2+3+4+5 = 15
    }
    
    @Test
    public void executeAndCombineWithCombineAll()
    {
        List<Integer> list = FastList.newListWith(1, 2, 3, 4, 5);
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        AllSumCombiner combiner = new AllSumCombiner(sum);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new ProcedureFJTaskRunner<>(combiner, 2);
        
        taskRunner.executeAndCombine(this.executor, factory, list);
        
        Assert.assertEquals(15, sum.get()); // 1+2+3+4+5 = 15
    }
    
    @Test
    public void executeAndCombineWithEmptyList()
    {
        List<Integer> list = FastList.newList();
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        SumCombiner combiner = new SumCombiner(sum);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new ProcedureFJTaskRunner<>(combiner, 2);
        
        taskRunner.executeAndCombine(this.executor, factory, list);
        
        Assert.assertEquals(0, sum.get());
    }
    
    @Test
    public void executeAndCombineWithLargeList()
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 1000; i++)
        {
            list.add(i);
        }
        
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        SumCombiner combiner = new SumCombiner(sum);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new ProcedureFJTaskRunner<>(combiner, 10);
        
        taskRunner.executeAndCombine(this.executor, factory, list);
        
        // Sum of numbers 1 to 1000 = 500500
        Assert.assertEquals(500500, sum.get());
    }
    
    @Test
    public void executeAndCombineWithException()
    {
        List<Integer> list = FastList.newListWith(1, 2, 3, 4, 5);
        
        ProcedureFactory<ExceptionProcedure> factory = new ProcedureFactory<ExceptionProcedure>()
        {
            public ExceptionProcedure create()
            {
                return new ExceptionProcedure();
            }
        };
        
        PassThruCombiner<ExceptionProcedure> combiner = new PassThruCombiner<>();
        ProcedureFJTaskRunner<Integer, ExceptionProcedure> taskRunner = new ProcedureFJTaskRunner<>(combiner, 2);
        
        RuntimeException exception = Verify.assertThrows(
                RuntimeException.class,
                () -> taskRunner.executeAndCombine(this.executor, factory, list));
        
        Assert.assertEquals("One or more parallel tasks failed", exception.getMessage());
        Assert.assertTrue(exception.getCause() instanceof RuntimeException);
        Assert.assertEquals("Test exception", exception.getCause().getMessage());
    }
    
    @Test
    public void testWithDifferentTaskCounts()
    {
        List<Integer> list = FastList.newListWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Test with 1 task
        AtomicInteger sum1 = new AtomicInteger(0);
        ProcedureFactory<SumProcedure> factory1 = () -> new SumProcedure(sum1);
        SumCombiner combiner1 = new SumCombiner(sum1);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner1 = new ProcedureFJTaskRunner<>(combiner1, 1);
        taskRunner1.executeAndCombine(this.executor, factory1, list);
        Assert.assertEquals(55, sum1.get()); // Sum of 1-10 = 55
        
        // Test with task count equal to list size
        AtomicInteger sum2 = new AtomicInteger(0);
        ProcedureFactory<SumProcedure> factory2 = () -> new SumProcedure(sum2);
        SumCombiner combiner2 = new SumCombiner(sum2);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner2 = new ProcedureFJTaskRunner<>(combiner2, 10);
        taskRunner2.executeAndCombine(this.executor, factory2, list);
        Assert.assertEquals(55, sum2.get());
        
        // Test with task count greater than list size
        AtomicInteger sum3 = new AtomicInteger(0);
        ProcedureFactory<SumProcedure> factory3 = () -> new SumProcedure(sum3);
        SumCombiner combiner3 = new SumCombiner(sum3);
        ProcedureFJTaskRunner<Integer, SumProcedure> taskRunner3 = new ProcedureFJTaskRunner<>(combiner3, 20);
        taskRunner3.executeAndCombine(this.executor, factory3, list);
        Assert.assertEquals(55, sum3.get());
    }
    
    private static final class SumProcedure implements Procedure<Integer>
    {
        private final AtomicInteger sum;
        
        private SumProcedure(AtomicInteger sum)
        {
            this.sum = sum;
        }
        
        public void value(Integer each)
        {
            this.sum.addAndGet(each);
        }
    }
    
    private static final class ExceptionProcedure implements Procedure<Integer>
    {
        public void value(Integer each)
        {
            throw new RuntimeException("Test exception");
        }
    }
    
    private static final class SumCombiner implements Combiner<SumProcedure>
    {
        private final AtomicInteger sum;
        
        private SumCombiner(AtomicInteger sum)
        {
            this.sum = sum;
        }
        
        @Override
        public void combineOne(SumProcedure procedure)
        {
            // The sum is already updated by the procedure through the shared AtomicInteger
        }
        
        @Override
        public void combineAll(Iterable<SumProcedure> procedures)
        {
            // Not used in this test since useCombineOne returns true
        }
        
        @Override
        public boolean useCombineOne()
        {
            return true;
        }
    }
    
    private static final class AllSumCombiner implements Combiner<SumProcedure>
    {
        private final AtomicInteger sum;
        
        private AllSumCombiner(AtomicInteger sum)
        {
            this.sum = sum;
        }
        
        @Override
        public void combineOne(SumProcedure procedure)
        {
            // Not used in this test since useCombineOne returns false
        }
        
        @Override
        public void combineAll(Iterable<SumProcedure> procedures)
        {
            // The sum is already updated by the procedures through the shared AtomicInteger
        }
        
        @Override
        public boolean useCombineOne()
        {
            return false;
        }
    }
}
