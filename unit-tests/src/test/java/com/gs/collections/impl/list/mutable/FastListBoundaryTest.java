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

package com.gs.collections.impl.list.mutable;

import org.junit.Assert;
import org.junit.Test;

public class FastListBoundaryTest
{
    @Test
    public void newList_emptyList()
    {
        FastList<String> list = FastList.newList();
        Assert.assertTrue(list.isEmpty());
        Assert.assertEquals(0, list.size());
    }
    
    @Test
    public void newList_initialCapacity()
    {
        // Test with minimum capacity
        FastList<String> list1 = FastList.newList(1);
        Assert.assertTrue(list1.isEmpty());
        
        // Test with large capacity
        FastList<String> list2 = FastList.newList(10000);
        Assert.assertTrue(list2.isEmpty());
    }
    
    @Test
    public void add_singleElement()
    {
        FastList<String> list = FastList.newList();
        Assert.assertTrue(list.add("element"));
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("element", list.get(0));
    }
    
    @Test
    public void add_manyElements()
    {
        FastList<String> list = FastList.newList();
        int size = 1000;
        
        for (int i = 0; i < size; i++)
        {
            list.add("element" + i);
        }
        
        Assert.assertEquals(size, list.size());
        
        for (int i = 0; i < size; i++)
        {
            Assert.assertEquals("element" + i, list.get(i));
        }
    }
    
    @Test
    public void addAtIndex_firstPosition()
    {
        FastList<String> list = FastList.newList();
        list.add("B");
        list.add(0, "A");
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("A", list.get(0));
        Assert.assertEquals("B", list.get(1));
    }
    
    @Test
    public void addAtIndex_lastPosition()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add(1, "B");
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("A", list.get(0));
        Assert.assertEquals("B", list.get(1));
    }
    
    @Test
    public void addAtIndex_middlePosition()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add("C");
        list.add(1, "B");
        Assert.assertEquals(3, list.size());
        Assert.assertEquals("A", list.get(0));
        Assert.assertEquals("B", list.get(1));
        Assert.assertEquals("C", list.get(2));
    }
    
    @Test
    public void remove_emptyList()
    {
        FastList<String> list = FastList.newList();
        Assert.assertFalse(list.remove("nonexistent"));
    }
    
    @Test
    public void remove_singleElement()
    {
        FastList<String> list = FastList.newList();
        list.add("element");
        Assert.assertTrue(list.remove("element"));
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void remove_firstElement()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add("B");
        list.add("C");
        Assert.assertEquals("A", list.remove(0));
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("B", list.get(0));
        Assert.assertEquals("C", list.get(1));
    }
    
    @Test
    public void remove_lastElement()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add("B");
        list.add("C");
        Assert.assertEquals("C", list.remove(2));
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("A", list.get(0));
        Assert.assertEquals("B", list.get(1));
    }
    
    @Test
    public void remove_middleElement()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add("B");
        list.add("C");
        Assert.assertEquals("B", list.remove(1));
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("A", list.get(0));
        Assert.assertEquals("C", list.get(1));
    }
    
    @Test
    public void clear_emptyList()
    {
        FastList<String> list = FastList.newList();
        list.clear();
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void clear_nonEmptyList()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add("B");
        list.clear();
        Assert.assertTrue(list.isEmpty());
    }
}
