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

package com.gs.collections.impl.partition.set;

import com.gs.collections.api.partition.set.PartitionImmutableSet;
import com.gs.collections.api.set.ImmutableSet;
import com.gs.collections.impl.factory.Sets;
import org.junit.Assert;
import org.junit.Test;

public class PartitionImmutableSetImplTest
{
    @Test
    public void getSelected()
    {
        ImmutableSet<Integer> selected = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition = new PartitionImmutableSetImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(selected, partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        ImmutableSet<Integer> selected = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition = new PartitionImmutableSetImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(rejected, partition.getRejected());
    }
    
    @Test
    public void emptyPartition()
    {
        ImmutableSet<Integer> selected = Sets.immutable.empty();
        ImmutableSet<Integer> rejected = Sets.immutable.empty();
        PartitionImmutableSet<Integer> partition = new PartitionImmutableSetImpl<Integer>(selected, rejected);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        ImmutableSet<Integer> selected = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition = new PartitionImmutableSetImpl<Integer>(selected, rejected);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        ImmutableSet<Integer> selected1 = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected1 = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition1 = new PartitionImmutableSetImpl<Integer>(selected1, rejected1);
        
        ImmutableSet<Integer> selected2 = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected2 = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition2 = new PartitionImmutableSetImpl<Integer>(selected2, rejected2);
        
        ImmutableSet<Integer> selected3 = Sets.immutable.of(3, 5, 7);
        ImmutableSet<Integer> rejected3 = Sets.immutable.of(4, 6, 8);
        PartitionImmutableSet<Integer> partition3 = new PartitionImmutableSetImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        ImmutableSet<Integer> selected1 = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected1 = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition1 = new PartitionImmutableSetImpl<Integer>(selected1, rejected1);
        
        ImmutableSet<Integer> selected2 = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected2 = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition2 = new PartitionImmutableSetImpl<Integer>(selected2, rejected2);
        
        ImmutableSet<Integer> selected3 = Sets.immutable.of(3, 5, 7);
        ImmutableSet<Integer> rejected3 = Sets.immutable.of(4, 6, 8);
        PartitionImmutableSet<Integer> partition3 = new PartitionImmutableSetImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
    
    @Test
    public void toImmutable()
    {
        ImmutableSet<Integer> selected = Sets.immutable.of(1, 3, 5);
        ImmutableSet<Integer> rejected = Sets.immutable.of(2, 4, 6);
        PartitionImmutableSet<Integer> partition = new PartitionImmutableSetImpl<Integer>(selected, rejected);
        
        PartitionImmutableSet<Integer> immutable = partition.toImmutable();
        
        Assert.assertSame(partition, immutable);
    }
}
