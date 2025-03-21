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

import java.util.concurrent.atomic.AtomicInteger;

import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class BatchIterableTest
{
    @Test
    public void testSimpleBatchIterable()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        final AtomicInteger sum = new AtomicInteger(0);
        batchIterable.forEach(each -> sum.addAndGet(each));
        
        Assert.assertEquals(55, sum.get()); // Sum of 1-10 = 55
    }
    
    @Test
    public void testBatchForEachWithSingleSection()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        final AtomicInteger sum = new AtomicInteger(0);
        batchIterable.batchForEach(each -> sum.addAndGet(each), 0, 1);
        
        Assert.assertEquals(55, sum.get()); // Sum of 1-10 = 55
    }
    
    @Test
    public void testBatchForEachWithMultipleSections()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        // Test with 2 sections
        final AtomicInteger sum1 = new AtomicInteger(0);
        batchIterable.batchForEach(each -> sum1.addAndGet(each), 0, 2); // First half: 1,2,3,4,5
        Assert.assertEquals(15, sum1.get()); // Sum of 1-5 = 15
        
        final AtomicInteger sum2 = new AtomicInteger(0);
        batchIterable.batchForEach(each -> sum2.addAndGet(each), 1, 2); // Second half: 6,7,8,9,10
        Assert.assertEquals(40, sum2.get()); // Sum of 6-10 = 40
        
        // Test with 5 sections
        final AtomicInteger sum3 = new AtomicInteger(0);
        batchIterable.batchForEach(each -> sum3.addAndGet(each), 2, 5); // Third section: 5,6
        Assert.assertEquals(11, sum3.get()); // Sum of 5-6 = 11
    }
    
    @Test
    public void testBatchForEachWithEmptyList()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(FastList.<Integer>newList());
        
        final AtomicInteger sum = new AtomicInteger(0);
        batchIterable.batchForEach(each -> sum.addAndGet(each), 0, 1);
        
        Assert.assertEquals(0, sum.get());
    }
    
    @Test
    public void testGetBatchCount()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        Assert.assertEquals(10, batchIterable.getBatchCount(1)); // 10 batches of size 1
        Assert.assertEquals(5, batchIterable.getBatchCount(2)); // 5 batches of size 2
        Assert.assertEquals(4, batchIterable.getBatchCount(3)); // 4 batches of size 3 (with last batch smaller)
        Assert.assertEquals(1, batchIterable.getBatchCount(10)); // 1 batch of size 10
        Assert.assertEquals(1, batchIterable.getBatchCount(20)); // 1 batch of size 20 (larger than list)
    }
    
    @Test
    public void testGetBatchCountWithEmptyList()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(FastList.<Integer>newList());
        
        Assert.assertEquals(0, batchIterable.getBatchCount(1));
        Assert.assertEquals(0, batchIterable.getBatchCount(10));
    }
    
    @Test
    public void testSize()
    {
        SimpleBatchIterable<Integer> batchIterable1 = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5));
        Assert.assertEquals(5, batchIterable1.size());
        
        SimpleBatchIterable<Integer> batchIterable2 = new SimpleBatchIterable<>(FastList.<Integer>newList());
        Assert.assertEquals(0, batchIterable2.size());
    }
    
    @Test
    public void testWithNullProcedure()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5));
        
        Verify.assertThrows(NullPointerException.class, () -> batchIterable.forEach(null));
        Verify.assertThrows(NullPointerException.class, () -> batchIterable.batchForEach(null, 0, 1));
    }
    
    @Test
    public void testWithInvalidSectionIndexOrCount()
    {
        SimpleBatchIterable<Integer> batchIterable = new SimpleBatchIterable<>(
                FastList.newListWith(1, 2, 3, 4, 5));
        
        final AtomicInteger sum = new AtomicInteger(0);
        
        // Invalid section index (negative)
        batchIterable.batchForEach(each -> sum.addAndGet(each), -1, 2);
        Assert.assertEquals(0, sum.get()); // Nothing processed
        
        // Invalid section index (too large)
        sum.set(0);
        batchIterable.batchForEach(each -> sum.addAndGet(each), 2, 2);
        Assert.assertEquals(0, sum.get()); // Nothing processed
        
        // Invalid section count (negative or zero)
        sum.set(0);
        batchIterable.batchForEach(each -> sum.addAndGet(each), 0, 0);
        Assert.assertEquals(0, sum.get()); // Nothing processed
        
        sum.set(0);
        batchIterable.batchForEach(each -> sum.addAndGet(each), 0, -1);
        Assert.assertEquals(0, sum.get()); // Nothing processed
    }
    
    /**
     * A simple implementation of BatchIterable for testing purposes.
     */
    private static final class SimpleBatchIterable<T> implements BatchIterable<T>
    {
        private final FastList<T> list;
        
        private SimpleBatchIterable(FastList<T> list)
        {
            this.list = list;
        }
        
        @Override
        public void batchForEach(Procedure<? super T> procedure, int sectionIndex, int sectionCount)
        {
            if (procedure == null)
            {
                throw new NullPointerException("Procedure cannot be null");
            }
            
            if (this.list.isEmpty() || sectionIndex < 0 || sectionCount <= 0 || sectionIndex >= sectionCount)
            {
                return;
            }
            
            int size = this.list.size();
            int sectionSize = size / sectionCount;
            int start = sectionIndex * sectionSize;
            int end = sectionIndex == sectionCount - 1 ? size : start + sectionSize;
            
            for (int i = start; i < end; i++)
            {
                procedure.value(this.list.get(i));
            }
        }
        
        @Override
        public int size()
        {
            return this.list.size();
        }
        
        @Override
        public int getBatchCount(int batchSize)
        {
            if (batchSize < 1 || this.list.isEmpty())
            {
                return 0;
            }
            return (this.list.size() + batchSize - 1) / batchSize; // Ceiling division
        }
        
        @Override
        public void forEach(Procedure<? super T> procedure)
        {
            if (procedure == null)
            {
                throw new NullPointerException("Procedure cannot be null");
            }
            this.list.forEach(procedure);
        }
    }
}
