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

import com.gs.collections.api.bag.sorted.ImmutableSortedBag;
import com.gs.collections.api.partition.bag.sorted.PartitionImmutableSortedBag;
import com.gs.collections.impl.bag.sorted.mutable.TreeBag;
import org.junit.Assert;
import org.junit.Test;

public class PartitionImmutableSortedBagImplTest
{
    @Test
    public void getSelected()
    {
        TreeBag<Integer> selectedBag = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected = selectedBag.toImmutable();
        ImmutableSortedBag<Integer> rejected = rejectedBag.toImmutable();
        PartitionImmutableSortedBag<Integer> partition = new PartitionImmutableSortedBagImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(selected, partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        TreeBag<Integer> selectedBag = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected = selectedBag.toImmutable();
        ImmutableSortedBag<Integer> rejected = rejectedBag.toImmutable();
        PartitionImmutableSortedBag<Integer> partition = new PartitionImmutableSortedBagImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(rejected, partition.getRejected());
    }
    
    @Test
    public void emptyPartition()
    {
        TreeBag<Integer> selectedBag = TreeBag.newBag();
        TreeBag<Integer> rejectedBag = TreeBag.newBag();
        ImmutableSortedBag<Integer> selected = selectedBag.toImmutable();
        ImmutableSortedBag<Integer> rejected = rejectedBag.toImmutable();
        PartitionImmutableSortedBag<Integer> partition = new PartitionImmutableSortedBagImpl<Integer>(selected, rejected);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        TreeBag<Integer> selectedBag = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected = selectedBag.toImmutable();
        ImmutableSortedBag<Integer> rejected = rejectedBag.toImmutable();
        PartitionImmutableSortedBag<Integer> partition = new PartitionImmutableSortedBagImpl<Integer>(selected, rejected);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        TreeBag<Integer> selectedBag1 = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag1 = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected1 = selectedBag1.toImmutable();
        ImmutableSortedBag<Integer> rejected1 = rejectedBag1.toImmutable();
        PartitionImmutableSortedBag<Integer> partition1 = new PartitionImmutableSortedBagImpl<Integer>(selected1, rejected1);
        
        TreeBag<Integer> selectedBag2 = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag2 = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected2 = selectedBag2.toImmutable();
        ImmutableSortedBag<Integer> rejected2 = rejectedBag2.toImmutable();
        PartitionImmutableSortedBag<Integer> partition2 = new PartitionImmutableSortedBagImpl<Integer>(selected2, rejected2);
        
        TreeBag<Integer> selectedBag3 = TreeBag.newBagWith(3, 3, 5, 7);
        TreeBag<Integer> rejectedBag3 = TreeBag.newBagWith(4, 4, 6, 8);
        ImmutableSortedBag<Integer> selected3 = selectedBag3.toImmutable();
        ImmutableSortedBag<Integer> rejected3 = rejectedBag3.toImmutable();
        PartitionImmutableSortedBag<Integer> partition3 = new PartitionImmutableSortedBagImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        TreeBag<Integer> selectedBag1 = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag1 = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected1 = selectedBag1.toImmutable();
        ImmutableSortedBag<Integer> rejected1 = rejectedBag1.toImmutable();
        PartitionImmutableSortedBag<Integer> partition1 = new PartitionImmutableSortedBagImpl<Integer>(selected1, rejected1);
        
        TreeBag<Integer> selectedBag2 = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag2 = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected2 = selectedBag2.toImmutable();
        ImmutableSortedBag<Integer> rejected2 = rejectedBag2.toImmutable();
        PartitionImmutableSortedBag<Integer> partition2 = new PartitionImmutableSortedBagImpl<Integer>(selected2, rejected2);
        
        TreeBag<Integer> selectedBag3 = TreeBag.newBagWith(3, 3, 5, 7);
        TreeBag<Integer> rejectedBag3 = TreeBag.newBagWith(4, 4, 6, 8);
        ImmutableSortedBag<Integer> selected3 = selectedBag3.toImmutable();
        ImmutableSortedBag<Integer> rejected3 = rejectedBag3.toImmutable();
        PartitionImmutableSortedBag<Integer> partition3 = new PartitionImmutableSortedBagImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
    
    @Test
    public void toImmutable()
    {
        TreeBag<Integer> selectedBag = TreeBag.newBagWith(1, 1, 3, 5);
        TreeBag<Integer> rejectedBag = TreeBag.newBagWith(2, 2, 4, 6);
        ImmutableSortedBag<Integer> selected = selectedBag.toImmutable();
        ImmutableSortedBag<Integer> rejected = rejectedBag.toImmutable();
        PartitionImmutableSortedBag<Integer> partition = new PartitionImmutableSortedBagImpl<Integer>(selected, rejected);
        
        PartitionImmutableSortedBag<Integer> immutable = partition.toImmutable();
        
        Assert.assertSame(partition, immutable);
    }
    
    @Test
    public void withComparator()
    {
        Comparator<Integer> reverseComparator = Comparator.reverseOrder();
        TreeBag<Integer> selectedBag = TreeBag.newBag(reverseComparator);
        selectedBag.add(1);
        selectedBag.add(3);
        selectedBag.add(5);
        
        TreeBag<Integer> rejectedBag = TreeBag.newBag(reverseComparator);
        rejectedBag.add(2);
        rejectedBag.add(4);
        rejectedBag.add(6);
        
        ImmutableSortedBag<Integer> selected = selectedBag.toImmutable();
        ImmutableSortedBag<Integer> rejected = rejectedBag.toImmutable();
        
        PartitionImmutableSortedBag<Integer> partition = new PartitionImmutableSortedBagImpl<Integer>(selected, rejected);
        
        // Verify the comparator is used (reverse order)
        Assert.assertEquals(Integer.valueOf(5), partition.getSelected().first());
        Assert.assertEquals(Integer.valueOf(1), partition.getSelected().last());
        Assert.assertEquals(Integer.valueOf(6), partition.getRejected().first());
        Assert.assertEquals(Integer.valueOf(2), partition.getRejected().last());
    }
}
