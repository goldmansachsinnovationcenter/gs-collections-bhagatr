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

import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.partition.list.PartitionMutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class PartitionMutableListTest
{
    @Test
    public void getSelected()
    {
        MutableList<Integer> selected = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition = new PartitionMutableListImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(selected, partition.getSelected());
    }
    
    @Test
    public void getRejected()
    {
        MutableList<Integer> selected = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition = new PartitionMutableListImpl<Integer>(selected, rejected);
        
        Assert.assertEquals(rejected, partition.getRejected());
    }
    
    @Test
    public void toImmutable()
    {
        MutableList<Integer> selected = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition = new PartitionMutableListImpl<Integer>(selected, rejected);
        
        PartitionImmutableList<Integer> immutable = partition.toImmutable();
        
        Assert.assertEquals(selected, immutable.getSelected());
        Assert.assertEquals(rejected, immutable.getRejected());
        Verify.assertInstanceOf(PartitionImmutableListImpl.class, immutable);
    }
    
    @Test
    public void emptyPartition()
    {
        MutableList<Integer> selected = FastList.newList();
        MutableList<Integer> rejected = FastList.newList();
        PartitionMutableList<Integer> partition = new PartitionMutableListImpl<Integer>(selected, rejected);
        
        Assert.assertTrue(partition.getSelected().isEmpty());
        Assert.assertTrue(partition.getRejected().isEmpty());
        
        PartitionImmutableList<Integer> immutable = partition.toImmutable();
        Assert.assertTrue(immutable.getSelected().isEmpty());
        Assert.assertTrue(immutable.getRejected().isEmpty());
    }
    
    @Test
    public void testToString()
    {
        MutableList<Integer> selected = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition = new PartitionMutableListImpl<Integer>(selected, rejected);
        
        String toString = partition.toString();
        Assert.assertTrue(toString.contains("selected"));
        Assert.assertTrue(toString.contains("rejected"));
        Assert.assertTrue(toString.contains("1"));
        Assert.assertTrue(toString.contains("2"));
    }
    
    @Test
    public void testEquals()
    {
        MutableList<Integer> selected1 = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected1 = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition1 = new PartitionMutableListImpl<Integer>(selected1, rejected1);
        
        MutableList<Integer> selected2 = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected2 = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition2 = new PartitionMutableListImpl<Integer>(selected2, rejected2);
        
        MutableList<Integer> selected3 = FastList.newListWith(3, 5, 7);
        MutableList<Integer> rejected3 = FastList.newListWith(4, 6, 8);
        PartitionMutableList<Integer> partition3 = new PartitionMutableListImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1, partition2);
        Assert.assertNotEquals(partition1, partition3);
        Assert.assertNotEquals(partition1, null);
        Assert.assertNotEquals(partition1, "String");
    }
    
    @Test
    public void testHashCode()
    {
        MutableList<Integer> selected1 = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected1 = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition1 = new PartitionMutableListImpl<Integer>(selected1, rejected1);
        
        MutableList<Integer> selected2 = FastList.newListWith(1, 3, 5);
        MutableList<Integer> rejected2 = FastList.newListWith(2, 4, 6);
        PartitionMutableList<Integer> partition2 = new PartitionMutableListImpl<Integer>(selected2, rejected2);
        
        MutableList<Integer> selected3 = FastList.newListWith(3, 5, 7);
        MutableList<Integer> rejected3 = FastList.newListWith(4, 6, 8);
        PartitionMutableList<Integer> partition3 = new PartitionMutableListImpl<Integer>(selected3, rejected3);
        
        Assert.assertEquals(partition1.hashCode(), partition2.hashCode());
        Assert.assertNotEquals(partition1.hashCode(), partition3.hashCode());
    }
}
