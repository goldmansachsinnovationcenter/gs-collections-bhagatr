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

package com.gs.collections.impl.partition.set.sorted;

import java.util.Comparator;

import com.gs.collections.api.partition.set.sorted.PartitionImmutableSortedSet;
import com.gs.collections.api.set.sorted.ImmutableSortedSet;
import com.gs.collections.impl.factory.SortedSets;
import com.gs.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.junit.Assert;
import org.junit.Test;

public class PartitionImmutableSortedSetImplTest
{
    @Test
    public void getSelected()
    {
        ImmutableSortedSet<Integer> selected = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition = new PartitionImmutableSortedSetImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(selected, partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        ImmutableSortedSet<Integer> selected = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition = new PartitionImmutableSortedSetImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(rejected, partition.getRejected());
    }
    
    @Test
    public void emptyPartition()
    {
        ImmutableSortedSet<Integer> selected = SortedSets.immutable.empty();
        ImmutableSortedSet<Integer> rejected = SortedSets.immutable.empty();
        PartitionImmutableSortedSet<Integer> partition = new PartitionImmutableSortedSetImpl<Integer>(selected, rejected);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        ImmutableSortedSet<Integer> selected = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition = new PartitionImmutableSortedSetImpl<Integer>(selected, rejected);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        ImmutableSortedSet<Integer> selected1 = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected1 = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition1 = new PartitionImmutableSortedSetImpl<Integer>(selected1, rejected1);
        
        ImmutableSortedSet<Integer> selected2 = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected2 = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition2 = new PartitionImmutableSortedSetImpl<Integer>(selected2, rejected2);
        
        ImmutableSortedSet<Integer> selected3 = SortedSets.immutable.of(3, 5, 7);
        ImmutableSortedSet<Integer> rejected3 = SortedSets.immutable.of(4, 6, 8);
        PartitionImmutableSortedSet<Integer> partition3 = new PartitionImmutableSortedSetImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        ImmutableSortedSet<Integer> selected1 = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected1 = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition1 = new PartitionImmutableSortedSetImpl<Integer>(selected1, rejected1);
        
        ImmutableSortedSet<Integer> selected2 = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected2 = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition2 = new PartitionImmutableSortedSetImpl<Integer>(selected2, rejected2);
        
        ImmutableSortedSet<Integer> selected3 = SortedSets.immutable.of(3, 5, 7);
        ImmutableSortedSet<Integer> rejected3 = SortedSets.immutable.of(4, 6, 8);
        PartitionImmutableSortedSet<Integer> partition3 = new PartitionImmutableSortedSetImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
    
    @Test
    public void toImmutable()
    {
        ImmutableSortedSet<Integer> selected = SortedSets.immutable.of(1, 3, 5);
        ImmutableSortedSet<Integer> rejected = SortedSets.immutable.of(2, 4, 6);
        PartitionImmutableSortedSet<Integer> partition = new PartitionImmutableSortedSetImpl<Integer>(selected, rejected);
        
        PartitionImmutableSortedSet<Integer> immutable = partition.toImmutable();
        
        Assert.assertSame(partition, immutable);
    }
    
    @Test
    public void withComparator()
    {
        Comparator<Integer> reverseComparator = Comparator.reverseOrder();
        TreeSortedSet<Integer> selectedSet = TreeSortedSet.newSet(reverseComparator);
        selectedSet.add(1);
        selectedSet.add(3);
        selectedSet.add(5);
        
        TreeSortedSet<Integer> rejectedSet = TreeSortedSet.newSet(reverseComparator);
        rejectedSet.add(2);
        rejectedSet.add(4);
        rejectedSet.add(6);
        
        ImmutableSortedSet<Integer> selected = selectedSet.toImmutable();
        ImmutableSortedSet<Integer> rejected = rejectedSet.toImmutable();
        
        PartitionImmutableSortedSet<Integer> partition = new PartitionImmutableSortedSetImpl<Integer>(selected, rejected);
        
        // Verify the comparator is used (reverse order)
        Assert.assertEquals(Integer.valueOf(5), partition.getSelected().first());
        Assert.assertEquals(Integer.valueOf(1), partition.getSelected().last());
        Assert.assertEquals(Integer.valueOf(6), partition.getRejected().first());
        Assert.assertEquals(Integer.valueOf(2), partition.getRejected().last());
    }
}
