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

package com.gs.collections.impl.map.mutable;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.gs.collections.api.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class UnifiedMapIteratorPathTest
{
    @Test
    public void keySetIterator_emptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Iterator<String> iterator = map.keySet().iterator();
        
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void keySetIterator_singleEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1);
        Iterator<String> iterator = map.keySet().iterator();
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("one", iterator.next());
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void keySetIterator_multipleEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        Iterator<String> iterator = map.keySet().iterator();
        
        int count = 0;
        while (iterator.hasNext())
        {
            String key = iterator.next();
            Assert.assertTrue(map.containsKey(key));
            count++;
        }
        
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void keySetIterator_remove()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        Iterator<String> iterator = map.keySet().iterator();
        
        String first = iterator.next();
        iterator.remove();
        Assert.assertEquals(2, map.size());
        Assert.assertFalse(map.containsKey(first));
        
        String second = iterator.next();
        iterator.remove();
        Assert.assertEquals(1, map.size());
        Assert.assertFalse(map.containsKey(second));
        
        String third = iterator.next();
        iterator.remove();
        Assert.assertEquals(0, map.size());
        Assert.assertFalse(map.containsKey(third));
        
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void keySetIterator_removeWithoutNext()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2);
        Iterator<String> iterator = map.keySet().iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void keySetIterator_removeCalledTwice()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2);
        Iterator<String> iterator = map.keySet().iterator();
        iterator.next();
        iterator.remove(); // First remove is fine
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test
    public void valuesIterator_emptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Iterator<Integer> iterator = map.values().iterator();
        
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void valuesIterator_singleEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1);
        Iterator<Integer> iterator = map.values().iterator();
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(1), iterator.next());
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void valuesIterator_multipleEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        Iterator<Integer> iterator = map.values().iterator();
        
        int count = 0;
        while (iterator.hasNext())
        {
            Integer value = iterator.next();
            Assert.assertTrue(map.containsValue(value));
            count++;
        }
        
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void valuesIterator_remove()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        Iterator<Integer> iterator = map.values().iterator();
        
        Integer first = iterator.next();
        iterator.remove();
        Assert.assertEquals(2, map.size());
        Assert.assertFalse(map.containsValue(first));
        
        Integer second = iterator.next();
        iterator.remove();
        Assert.assertEquals(1, map.size());
        Assert.assertFalse(map.containsValue(second));
        
        Integer third = iterator.next();
        iterator.remove();
        Assert.assertEquals(0, map.size());
        Assert.assertFalse(map.containsValue(third));
        
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesIterator_removeWithoutNext()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2);
        Iterator<Integer> iterator = map.values().iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesIterator_removeCalledTwice()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2);
        Iterator<Integer> iterator = map.values().iterator();
        iterator.next();
        iterator.remove(); // First remove is fine
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test
    public void entrySetIterator_emptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void entrySetIterator_singleEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        
        Assert.assertTrue(iterator.hasNext());
        Map.Entry<String, Integer> entry = iterator.next();
        Assert.assertEquals("one", entry.getKey());
        Assert.assertEquals(Integer.valueOf(1), entry.getValue());
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void entrySetIterator_multipleEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        
        int count = 0;
        while (iterator.hasNext())
        {
            Map.Entry<String, Integer> entry = iterator.next();
            Assert.assertEquals(entry.getValue(), map.get(entry.getKey()));
            count++;
        }
        
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void entrySetIterator_remove()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        
        Map.Entry<String, Integer> first = iterator.next();
        iterator.remove();
        Assert.assertEquals(2, map.size());
        Assert.assertFalse(map.containsKey(first.getKey()));
        
        Map.Entry<String, Integer> second = iterator.next();
        iterator.remove();
        Assert.assertEquals(1, map.size());
        Assert.assertFalse(map.containsKey(second.getKey()));
        
        Map.Entry<String, Integer> third = iterator.next();
        iterator.remove();
        Assert.assertEquals(0, map.size());
        Assert.assertFalse(map.containsKey(third.getKey()));
        
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void entrySetIterator_removeWithoutNext()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void entrySetIterator_removeCalledTwice()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        iterator.next();
        iterator.remove(); // First remove is fine
        iterator.remove(); // Second remove should throw IllegalStateException
    }
}
