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

package com.gs.collections.impl.set.mutable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.gs.collections.api.set.MutableSet;
import org.junit.Assert;
import org.junit.Test;

public class SynchronizedSetConcurrencyTest
{
    private static final int THREAD_COUNT = 4;
    private static final int ITEMS_PER_THREAD = 1000;
    
    @Test
    public void concurrentAdd() throws InterruptedException
    {
        final MutableSet<Integer> set = UnifiedSet.<Integer>newSet().asSynchronized();
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            final int threadId = i;
            executor.submit(() -> {
                try
                {
                    startLatch.await();
                    for (int j = 0; j < ITEMS_PER_THREAD; j++)
                    {
                        set.add(threadId * ITEMS_PER_THREAD + j);
                    }
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                finally
                {
                    endLatch.countDown();
                }
            });
        }
        
        startLatch.countDown();
        Assert.assertTrue("Test timed out", endLatch.await(30, TimeUnit.SECONDS));
        executor.shutdown();
        
        Assert.assertEquals(THREAD_COUNT * ITEMS_PER_THREAD, set.size());
        
        // Verify all items were added
        MutableSet<Integer> expected = UnifiedSet.newSet();
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            for (int j = 0; j < ITEMS_PER_THREAD; j++)
            {
                expected.add(i * ITEMS_PER_THREAD + j);
            }
        }
        
        Assert.assertEquals(expected, set);
    }
    
    @Test
    public void concurrentRemove() throws InterruptedException
    {
        // Populate the set first
        final MutableSet<Integer> set = UnifiedSet.<Integer>newSet().asSynchronized();
        for (int i = 0; i < ITEMS_PER_THREAD; i++)
        {
            set.add(i);
        }
        
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        final AtomicInteger successfulRemoves = new AtomicInteger(0);
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            executor.submit(() -> {
                try
                {
                    startLatch.await();
                    for (int j = 0; j < ITEMS_PER_THREAD; j++)
                    {
                        if (set.remove(j))
                        {
                            successfulRemoves.incrementAndGet();
                        }
                    }
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                finally
                {
                    endLatch.countDown();
                }
            });
        }
        
        startLatch.countDown();
        Assert.assertTrue("Test timed out", endLatch.await(30, TimeUnit.SECONDS));
        executor.shutdown();
        
        // Each item should be removed exactly once
        Assert.assertEquals(ITEMS_PER_THREAD, successfulRemoves.get());
        Assert.assertEquals(0, set.size());
    }
    
    @Test
    public void concurrentAddAll() throws InterruptedException
    {
        final MutableSet<Integer> set = UnifiedSet.<Integer>newSet().asSynchronized();
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            final int threadId = i;
            executor.submit(() -> {
                try
                {
                    startLatch.await();
                    MutableSet<Integer> itemsToAdd = UnifiedSet.newSet();
                    for (int j = 0; j < ITEMS_PER_THREAD; j++)
                    {
                        itemsToAdd.add(threadId * ITEMS_PER_THREAD + j);
                    }
                    set.addAll(itemsToAdd);
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                finally
                {
                    endLatch.countDown();
                }
            });
        }
        
        startLatch.countDown();
        Assert.assertTrue("Test timed out", endLatch.await(30, TimeUnit.SECONDS));
        executor.shutdown();
        
        Assert.assertEquals(THREAD_COUNT * ITEMS_PER_THREAD, set.size());
        
        // Verify all items were added
        MutableSet<Integer> expected = UnifiedSet.newSet();
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            for (int j = 0; j < ITEMS_PER_THREAD; j++)
            {
                expected.add(i * ITEMS_PER_THREAD + j);
            }
        }
        
        Assert.assertEquals(expected, set);
    }
    
    @Test
    public void concurrentClear() throws InterruptedException
    {
        // Populate the set first
        final MutableSet<Integer> set = UnifiedSet.<Integer>newSet().asSynchronized();
        for (int i = 0; i < ITEMS_PER_THREAD * THREAD_COUNT; i++)
        {
            set.add(i);
        }
        
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            executor.submit(() -> {
                try
                {
                    startLatch.await();
                    set.clear();
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                finally
                {
                    endLatch.countDown();
                }
            });
        }
        
        startLatch.countDown();
        Assert.assertTrue("Test timed out", endLatch.await(30, TimeUnit.SECONDS));
        executor.shutdown();
        
        // Set should be empty after concurrent clears
        Assert.assertEquals(0, set.size());
    }
}
