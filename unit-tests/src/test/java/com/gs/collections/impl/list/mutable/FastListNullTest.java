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

public class FastListNullTest
{
    @Test
    public void add_nullElement()
    {
        FastList<String> list = FastList.newList();
        Assert.assertTrue(list.add(null));
        Assert.assertEquals(1, list.size());
        Assert.assertNull(list.get(0));
    }
    
    @Test
    public void addAtIndex_nullElement()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add(1, null);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("A", list.get(0));
        Assert.assertNull(list.get(1));
    }
    
    @Test
    public void set_nullElement()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        Assert.assertEquals("A", list.set(0, null));
        Assert.assertNull(list.get(0));
    }
    
    @Test
    public void remove_nullElement()
    {
        FastList<String> list = FastList.newList();
        Assert.assertFalse(list.remove(null));
        list.add(null);
        Assert.assertTrue(list.remove(null));
        Assert.assertEquals(0, list.size());
    }
    
    @Test
    public void indexOf_nullElement()
    {
        FastList<String> list = FastList.newList();
        Assert.assertEquals(-1, list.indexOf(null));
        list.add("A");
        list.add(null);
        list.add("B");
        list.add(null);
        Assert.assertEquals(1, list.indexOf(null));
    }
    
    @Test
    public void lastIndexOf_nullElement()
    {
        FastList<String> list = FastList.newList();
        Assert.assertEquals(-1, list.lastIndexOf(null));
        list.add("A");
        list.add(null);
        list.add("B");
        list.add(null);
        Assert.assertEquals(3, list.lastIndexOf(null));
    }
    
    @Test
    public void contains_nullElement()
    {
        FastList<String> list = FastList.newList();
        Assert.assertFalse(list.contains(null));
        list.add(null);
        Assert.assertTrue(list.contains(null));
    }
    
    @Test
    public void with_nullElement()
    {
        FastList<String> list = FastList.newList();
        FastList<String> listWithNull = list.with(null);
        Assert.assertEquals(1, listWithNull.size());
        Assert.assertNull(listWithNull.get(0));
    }
    
    @Test
    public void without_nullElement()
    {
        FastList<String> list = FastList.newList();
        list.add("A");
        list.add(null);
        list.add("B");
        FastList<String> listWithoutNull = list.without(null);
        Assert.assertEquals(2, listWithoutNull.size());
        Assert.assertEquals("A", listWithoutNull.get(0));
        Assert.assertEquals("B", listWithoutNull.get(1));
    }
}
