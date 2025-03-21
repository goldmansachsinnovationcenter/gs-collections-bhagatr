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

import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class PartitionPredicateTest
{
    @Test
    public void partitionEven()
    {
        Predicate<Integer> isEven = each -> each % 2 == 0;
        PartitionPredicate<Integer> partitionPredicate = new PartitionPredicate<Integer>(isEven);
        
        Assert.assertTrue(partitionPredicate.accept(2));
        Assert.assertFalse(partitionPredicate.accept(3));
        Assert.assertTrue(partitionPredicate.accept(0));
        Assert.assertFalse(partitionPredicate.accept(-1));
        Assert.assertTrue(partitionPredicate.accept(-2));
    }
    
    @Test
    public void partitionPositive()
    {
        Predicate<Integer> isPositive = each -> each > 0;
        PartitionPredicate<Integer> partitionPredicate = new PartitionPredicate<Integer>(isPositive);
        
        Assert.assertTrue(partitionPredicate.accept(1));
        Assert.assertTrue(partitionPredicate.accept(100));
        Assert.assertFalse(partitionPredicate.accept(0));
        Assert.assertFalse(partitionPredicate.accept(-1));
        Assert.assertFalse(partitionPredicate.accept(-100));
    }
    
    @Test
    public void partitionWithNullPredicate()
    {
        Verify.assertThrows(NullPointerException.class, () -> new PartitionPredicate<Integer>(null));
    }
    
    @Test
    public void partitionWithNullInput()
    {
        Predicate<String> isNotNull = each -> each != null;
        PartitionPredicate<String> partitionPredicate = new PartitionPredicate<String>(isNotNull);
        
        Assert.assertTrue(partitionPredicate.accept("test"));
        Assert.assertFalse(partitionPredicate.accept(null));
    }
    
    @Test
    public void partitionWithCustomPredicate()
    {
        Predicate<String> startsWithA = each -> each != null && each.startsWith("a");
        PartitionPredicate<String> partitionPredicate = new PartitionPredicate<String>(startsWithA);
        
        Assert.assertTrue(partitionPredicate.accept("apple"));
        Assert.assertTrue(partitionPredicate.accept("ant"));
        Assert.assertFalse(partitionPredicate.accept("banana"));
        Assert.assertFalse(partitionPredicate.accept(""));
        Assert.assertFalse(partitionPredicate.accept(null));
    }
    
    @Test
    public void partitionWithList()
    {
        MutableList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5);
        Predicate<Integer> isEven = each -> each % 2 == 0;
        PartitionPredicate<Integer> partitionPredicate = new PartitionPredicate<Integer>(isEven);
        
        MutableList<Integer> selected = list.select(partitionPredicate);
        MutableList<Integer> rejected = list.reject(partitionPredicate);
        
        Assert.assertEquals(FastList.newListWith(2, 4), selected);
        Assert.assertEquals(FastList.newListWith(1, 3, 5), rejected);
    }
}
