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

import org.junit.Assert;
import org.junit.Test;

public class UnifiedMapBoundaryTest
{
    @Test
    public void newMap_emptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertTrue(map.isEmpty());
        Assert.assertEquals(0, map.size());
    }
    
    @Test
    public void newMap_initialCapacity()
    {
        // Test with minimum capacity
        UnifiedMap<String, Integer> map1 = UnifiedMap.newMap(1);
        Assert.assertTrue(map1.isEmpty());
        
        // Test with large capacity
        UnifiedMap<String, Integer> map2 = UnifiedMap.newMap(10000);
        Assert.assertTrue(map2.isEmpty());
    }
    
    @Test
    public void newMap_initialCapacityAndLoadFactor()
    {
        // Test with minimum capacity and minimum load factor
        UnifiedMap<String, Integer> map1 = new UnifiedMap<String, Integer>(1, 0.1f);
        Assert.assertTrue(map1.isEmpty());
        
        // Test with large capacity and maximum load factor
        UnifiedMap<String, Integer> map2 = new UnifiedMap<String, Integer>(10000, 0.9f);
        Assert.assertTrue(map2.isEmpty());
    }
    
    @Test
    public void put_singleEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.put("key", 1));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("key"));
    }
    
    @Test
    public void put_manyEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        int size = 1000;
        
        for (int i = 0; i < size; i++)
        {
            map.put("key" + i, i);
        }
        
        Assert.assertEquals(size, map.size());
        
        for (int i = 0; i < size; i++)
        {
            Assert.assertEquals(Integer.valueOf(i), map.get("key" + i));
        }
    }
    
    @Test
    public void remove_emptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.remove("nonexistent"));
    }
    
    @Test
    public void remove_singleEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put("key", 1);
        Assert.assertEquals(Integer.valueOf(1), map.remove("key"));
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test
    public void remove_allEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        int size = 100;
        
        for (int i = 0; i < size; i++)
        {
            map.put("key" + i, i);
        }
        
        for (int i = 0; i < size; i++)
        {
            Assert.assertEquals(Integer.valueOf(i), map.remove("key" + i));
        }
        
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test
    public void clear_emptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test
    public void clear_nonEmptyMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put("key1", 1);
        map.put("key2", 2);
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }
}
