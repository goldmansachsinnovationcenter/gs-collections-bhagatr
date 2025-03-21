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

package com.gs.collections.impl.partition.list;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.partition.list.PartitionImmutableList;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.list.mutable.FastList;
import org.junit.Assert;
import org.junit.Test;

public class PartitionImmutableListImplTest
{
    @Test
    public void getSelected()
    {
        ImmutableList<Integer> selected = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition = new PartitionImmutableListImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(selected, partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        ImmutableList<Integer> selected = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition = new PartitionImmutableListImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(rejected, partition.getRejected());
    }
    
    @Test
    public void emptyPartition()
    {
        ImmutableList<Integer> selected = Lists.immutable.empty();
        ImmutableList<Integer> rejected = Lists.immutable.empty();
        PartitionImmutableList<Integer> partition = new PartitionImmutableListImpl<Integer>(selected, rejected);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        ImmutableList<Integer> selected = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition = new PartitionImmutableListImpl<Integer>(selected, rejected);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        ImmutableList<Integer> selected1 = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected1 = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition1 = new PartitionImmutableListImpl<Integer>(selected1, rejected1);
        
        ImmutableList<Integer> selected2 = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected2 = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition2 = new PartitionImmutableListImpl<Integer>(selected2, rejected2);
        
        ImmutableList<Integer> selected3 = Lists.immutable.of(3, 5, 7);
        ImmutableList<Integer> rejected3 = Lists.immutable.of(4, 6, 8);
        PartitionImmutableList<Integer> partition3 = new PartitionImmutableListImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        ImmutableList<Integer> selected1 = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected1 = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition1 = new PartitionImmutableListImpl<Integer>(selected1, rejected1);
        
        ImmutableList<Integer> selected2 = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected2 = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition2 = new PartitionImmutableListImpl<Integer>(selected2, rejected2);
        
        ImmutableList<Integer> selected3 = Lists.immutable.of(3, 5, 7);
        ImmutableList<Integer> rejected3 = Lists.immutable.of(4, 6, 8);
        PartitionImmutableList<Integer> partition3 = new PartitionImmutableListImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
    
    @Test
    public void toImmutable()
    {
        ImmutableList<Integer> selected = Lists.immutable.of(1, 3, 5);
        ImmutableList<Integer> rejected = Lists.immutable.of(2, 4, 6);
        PartitionImmutableList<Integer> partition = new PartitionImmutableListImpl<Integer>(selected, rejected);
        
        PartitionImmutableList<Integer> immutable = partition.toImmutable();
        
        Assert.assertSame(partition, immutable);
    }
}
