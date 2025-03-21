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

package com.gs.collections.impl.partition.bag.sorted;

import java.util.Comparator;

import com.gs.collections.api.bag.sorted.MutableSortedBag;
import com.gs.collections.api.partition.bag.sorted.PartitionImmutableSortedBag;
import com.gs.collections.impl.bag.sorted.mutable.TreeBag;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class PartitionTreeBagTest
{
    @Test
    public void getSelected()
    {
        PartitionTreeBag<Integer> partition = new PartitionTreeBag<Integer>();
        MutableSortedBag<Integer> selected = partition.getSelected();
        
        Assert.assertTrue(selected.isEmpty());
        selected.add(1);
        selected.add(3);
        selected.add(5);
        selected.add(1); // Add duplicate
        
        Assert.assertEquals(TreeBag.newBagWith(1, 1, 3, 5), partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        PartitionTreeBag<Integer> partition = new PartitionTreeBag<Integer>();
        MutableSortedBag<Integer> rejected = partition.getRejected();
        
        Assert.assertTrue(rejected.isEmpty());
        rejected.add(2);
        rejected.add(4);
        rejected.add(6);
        rejected.add(2); // Add duplicate
        
        Assert.assertEquals(TreeBag.newBagWith(2, 2, 4, 6), partition.getRejected());
    }
    
    @Test
    public void toImmutable()
    {
        PartitionTreeBag<Integer> partition = new PartitionTreeBag<Integer>();
        partition.getSelected().add(1);
        partition.getSelected().add(1); // Add duplicate
        partition.getRejected().add(2);
        
        PartitionImmutableSortedBag<Integer> immutable = partition.toImmutable();
        
        Assert.assertEquals(TreeBag.newBagWith(1, 1), immutable.getSelected());
        Assert.assertEquals(TreeBag.newBagWith(2), immutable.getRejected());
        Verify.assertInstanceOf(PartitionImmutableSortedBagImpl.class, immutable);
    }
    
    @Test
    public void emptyPartition()
    {
        PartitionTreeBag<Integer> partition = new PartitionTreeBag<Integer>();
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
        
        PartitionImmutableSortedBag<Integer> immutable = partition.toImmutable();
        Assert.assertTrue(immutable.getSelected().isEmpty());
        Assert.assertTrue(immutable.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        PartitionTreeBag<Integer> partition = new PartitionTreeBag<Integer>();
        partition.getSelected().add(1);
        partition.getRejected().add(2);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        PartitionTreeBag<Integer> partition1 = new PartitionTreeBag<Integer>();
        partition1.getSelected().add(1);
        partition1.getRejected().add(2);
        
        PartitionTreeBag<Integer> partition2 = new PartitionTreeBag<Integer>();
        partition2.getSelected().add(1);
        partition2.getRejected().add(2);
        
        PartitionTreeBag<Integer> partition3 = new PartitionTreeBag<Integer>();
        partition3.getSelected().add(3);
        partition3.getRejected().add(4);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        PartitionTreeBag<Integer> partition1 = new PartitionTreeBag<Integer>();
        partition1.getSelected().add(1);
        partition1.getRejected().add(2);
        
        PartitionTreeBag<Integer> partition2 = new PartitionTreeBag<Integer>();
        partition2.getSelected().add(1);
        partition2.getRejected().add(2);
        
        PartitionTreeBag<Integer> partition3 = new PartitionTreeBag<Integer>();
        partition3.getSelected().add(3);
        partition3.getRejected().add(4);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
    
    @Test
    public void withComparator()
    {
        Comparator<Integer> reverseComparator = Comparator.reverseOrder();
        PartitionTreeBag<Integer> partition = new PartitionTreeBag<Integer>(reverseComparator);
        
        partition.getSelected().add(1);
        partition.getSelected().add(3);
        partition.getSelected().add(5);
        
        // Verify the comparator is used (reverse order)
        Assert.assertEquals(Integer.valueOf(5), partition.getSelected().first());
        Assert.assertEquals(Integer.valueOf(1), partition.getSelected().last());
    }
}
