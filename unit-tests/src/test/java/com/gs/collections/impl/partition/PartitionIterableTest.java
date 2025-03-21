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

package com.gs.collections.impl.partition;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.partition.PartitionIterable;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class PartitionIterableTest
{
    @Test
    public void partitionBasicList()
    {
        RichIterable<Integer> iterable = FastList.newListWith(1, 2, 3, 4, 5);
        Predicate<Integer> predicate = each -> each % 2 == 0;
        
        PartitionIterable<Integer> partition = iterable.partition(predicate);
        
        Assert.assertEquals(FastList.newListWith(2, 4), partition.getSelected());
        Assert.assertEquals(FastList.newListWith(1, 3, 5), partition.getRejected());
    }
    
    @Test
    public void partitionEmptyList()
    {
        RichIterable<Integer> iterable = FastList.newList();
        Predicate<Integer> predicate = each -> each % 2 == 0;
        
        PartitionIterable<Integer> partition = iterable.partition(predicate);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void partitionAllSelected()
    {
        RichIterable<Integer> iterable = FastList.newListWith(2, 4, 6, 8);
        Predicate<Integer> predicate = each -> each % 2 == 0;
        
        PartitionIterable<Integer> partition = iterable.partition(predicate);
        
        Assert.assertEquals(FastList.newListWith(2, 4, 6, 8), partition.getSelected());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void partitionAllRejected()
    {
        RichIterable<Integer> iterable = FastList.newListWith(1, 3, 5, 7);
        Predicate<Integer> predicate = each -> each % 2 == 0;
        
        PartitionIterable<Integer> partition = iterable.partition(predicate);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertEquals(FastList.newListWith(1, 3, 5, 7), partition.getRejected());
    }
    
    @Test
    public void partitionWithNullPredicate()
    {
        RichIterable<Integer> iterable = FastList.newListWith(1, 2, 3, 4, 5);
        
        Verify.assertThrows(NullPointerException.class, () -> iterable.partition(null));
    }
    
    @Test
    public void partitionWithCustomPredicate()
    {
        RichIterable<String> iterable = FastList.newListWith("apple", "banana", "car", "dog", "elephant");
        Predicate<String> predicate = each -> each.length() > 4;
        
        PartitionIterable<String> partition = iterable.partition(predicate);
        
        Assert.assertEquals(FastList.newListWith("apple", "banana", "elephant"), partition.getSelected());
        Assert.assertEquals(FastList.newListWith("car", "dog"), partition.getRejected());
    }
    
    @Test
    public void partitionWithNullElements()
    {
        RichIterable<String> iterable = FastList.newListWith("apple", null, "banana", null);
        Predicate<String> predicate = each -> each != null && each.length() > 5;
        
        PartitionIterable<String> partition = iterable.partition(predicate);
        
        Assert.assertEquals(FastList.newListWith("banana"), partition.getSelected());
        Assert.assertEquals(FastList.newListWith("apple", null, null), partition.getRejected());
    }
}
