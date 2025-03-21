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
import java.util.concurrent.atomic.AtomicInteger;

import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class ProcedureFJTaskTest
{
    @Test
    public void testRunWithFastList()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5);
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        MockProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new MockProcedureFJTaskRunner<>();
        ProcedureFJTask<Integer, SumProcedure> task = new ProcedureFJTask<>(taskRunner, factory, list, 0, 1, true);
        
        task.run();
        
        Assert.assertEquals(15, sum.get());
        Assert.assertTrue(taskRunner.wasTaskCompleted());
        Assert.assertFalse(taskRunner.wasFailed());
    }
    
    @Test
    public void testRunWithArrayList()
    {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        MockProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new MockProcedureFJTaskRunner<>();
        ProcedureFJTask<Integer, SumProcedure> task = new ProcedureFJTask<>(taskRunner, factory, list, 0, 1, true);
        
        task.run();
        
        Assert.assertEquals(15, sum.get());
        Assert.assertTrue(taskRunner.wasTaskCompleted());
        Assert.assertFalse(taskRunner.wasFailed());
    }
    
    @Test
    public void testRunWithRegularList()
    {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        MockProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new MockProcedureFJTaskRunner<>();
        ProcedureFJTask<Integer, SumProcedure> task = new ProcedureFJTask<>(taskRunner, factory, list, 0, 1, true);
        
        task.run();
        
        Assert.assertEquals(15, sum.get());
        Assert.assertTrue(taskRunner.wasTaskCompleted());
        Assert.assertFalse(taskRunner.wasFailed());
    }
    
    @Test
    public void testRunWithSectionSize()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        MockProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new MockProcedureFJTaskRunner<>();
        
        // Process first 5 elements (index 0-4)
        ProcedureFJTask<Integer, SumProcedure> task1 = new ProcedureFJTask<>(taskRunner, factory, list, 0, 5, false);
        task1.run();
        Assert.assertEquals(15, sum.get()); // 1+2+3+4+5 = 15
        
        // Process next 5 elements (index 5-9)
        sum.set(0); // Reset sum
        ProcedureFJTask<Integer, SumProcedure> task2 = new ProcedureFJTask<>(taskRunner, factory, list, 1, 5, true);
        task2.run();
        Assert.assertEquals(40, sum.get()); // 6+7+8+9+10 = 40
    }
    
    @Test
    public void testRunWithException()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5);
        
        ProcedureFactory<ExceptionProcedure> factory = new ProcedureFactory<ExceptionProcedure>()
        {
            public ExceptionProcedure create()
            {
                return new ExceptionProcedure();
            }
        };
        
        MockProcedureFJTaskRunner<Integer, ExceptionProcedure> taskRunner = new MockProcedureFJTaskRunner<>();
        ProcedureFJTask<Integer, ExceptionProcedure> task = new ProcedureFJTask<>(taskRunner, factory, list, 0, 1, true);
        
        task.run();
        
        Assert.assertTrue(taskRunner.wasTaskCompleted());
        Assert.assertTrue(taskRunner.wasFailed());
        Assert.assertTrue(taskRunner.getError() instanceof RuntimeException);
        Assert.assertEquals("Test exception", taskRunner.getError().getMessage());
    }
    
    @Test
    public void testGetProcedure()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3);
        final AtomicInteger sum = new AtomicInteger(0);
        
        ProcedureFactory<SumProcedure> factory = new ProcedureFactory<SumProcedure>()
        {
            public SumProcedure create()
            {
                return new SumProcedure(sum);
            }
        };
        
        MockProcedureFJTaskRunner<Integer, SumProcedure> taskRunner = new MockProcedureFJTaskRunner<>();
        ProcedureFJTask<Integer, SumProcedure> task = new ProcedureFJTask<>(taskRunner, factory, list, 0, 1, true);
        
        Assert.assertNull(task.getProcedure()); // Procedure is null before run
        
        task.run();
        
        Assert.assertNotNull(task.getProcedure()); // Procedure is not null after run
        Assert.assertEquals(6, sum.get()); // 1+2+3 = 6
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
    
    private static final class MockProcedureFJTaskRunner<T, BT extends Procedure<? super T>> extends ProcedureFJTaskRunner<T, BT>
    {
        private boolean taskCompleted = false;
        private boolean failed = false;
        private Throwable error;
        
        public MockProcedureFJTaskRunner()
        {
            super(null, 1);
        }
        
        @Override
        public synchronized void taskCompleted(ProcedureFJTask<T, BT> task)
        {
            this.taskCompleted = true;
        }
        
        @Override
        public synchronized void setFailed(Throwable newError)
        {
            this.failed = true;
            this.error = newError;
        }
        
        public boolean wasTaskCompleted()
        {
            return this.taskCompleted;
        }
        
        public boolean wasFailed()
        {
            return this.failed;
        }
        
        public Throwable getError()
        {
            return this.error;
        }
    }
}
