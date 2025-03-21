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

package com.gs.collections.impl.partition.bag;

import com.gs.collections.api.bag.ImmutableBag;
import com.gs.collections.api.partition.bag.PartitionImmutableBag;
import com.gs.collections.impl.factory.Bags;
import org.junit.Assert;
import org.junit.Test;

public class PartitionImmutableBagImplTest
{
    @Test
    public void getSelected()
    {
        ImmutableBag<Integer> selected = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition = new PartitionImmutableBagImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(selected, partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        ImmutableBag<Integer> selected = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition = new PartitionImmutableBagImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(rejected, partition.getRejected());
    }
    
    @Test
    public void emptyPartition()
    {
        ImmutableBag<Integer> selected = Bags.immutable.empty();
        ImmutableBag<Integer> rejected = Bags.immutable.empty();
        PartitionImmutableBag<Integer> partition = new PartitionImmutableBagImpl<Integer>(selected, rejected);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        ImmutableBag<Integer> selected = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition = new PartitionImmutableBagImpl<Integer>(selected, rejected);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        ImmutableBag<Integer> selected1 = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected1 = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition1 = new PartitionImmutableBagImpl<Integer>(selected1, rejected1);
        
        ImmutableBag<Integer> selected2 = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected2 = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition2 = new PartitionImmutableBagImpl<Integer>(selected2, rejected2);
        
        ImmutableBag<Integer> selected3 = Bags.immutable.of(3, 3, 5, 7);
        ImmutableBag<Integer> rejected3 = Bags.immutable.of(4, 4, 6, 8);
        PartitionImmutableBag<Integer> partition3 = new PartitionImmutableBagImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        ImmutableBag<Integer> selected1 = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected1 = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition1 = new PartitionImmutableBagImpl<Integer>(selected1, rejected1);
        
        ImmutableBag<Integer> selected2 = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected2 = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition2 = new PartitionImmutableBagImpl<Integer>(selected2, rejected2);
        
        ImmutableBag<Integer> selected3 = Bags.immutable.of(3, 3, 5, 7);
        ImmutableBag<Integer> rejected3 = Bags.immutable.of(4, 4, 6, 8);
        PartitionImmutableBag<Integer> partition3 = new PartitionImmutableBagImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
    
    @Test
    public void toImmutable()
    {
        ImmutableBag<Integer> selected = Bags.immutable.of(1, 1, 3, 5);
        ImmutableBag<Integer> rejected = Bags.immutable.of(2, 2, 4, 6);
        PartitionImmutableBag<Integer> partition = new PartitionImmutableBagImpl<Integer>(selected, rejected);
        
        PartitionImmutableBag<Integer> immutable = partition.toImmutable();
        
        Assert.assertSame(partition, immutable);
    }
}
