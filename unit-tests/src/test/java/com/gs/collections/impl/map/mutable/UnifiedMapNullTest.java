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

public class UnifiedMapNullTest
{
    @Test
    public void put_nullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.put(null, 1));
        Assert.assertEquals(Integer.valueOf(1), map.get(null));
        Assert.assertEquals(Integer.valueOf(1), map.put(null, 2));
        Assert.assertEquals(Integer.valueOf(2), map.get(null));
    }
    
    @Test
    public void put_nullValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.put("key", null));
        Assert.assertNull(map.get("key"));
        Assert.assertNull(map.put("key", 1));
        Assert.assertEquals(Integer.valueOf(1), map.get("key"));
    }
    
    @Test
    public void put_nullKeyAndValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.put(null, null));
        Assert.assertNull(map.get(null));
        Assert.assertNull(map.put(null, 1));
        Assert.assertEquals(Integer.valueOf(1), map.get(null));
    }
    
    @Test
    public void remove_nullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.remove(null));
        map.put(null, 1);
        Assert.assertEquals(Integer.valueOf(1), map.remove(null));
        Assert.assertNull(map.remove(null));
    }
    
    @Test
    public void containsKey_nullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertFalse(map.containsKey(null));
        map.put(null, 1);
        Assert.assertTrue(map.containsKey(null));
        map.remove(null);
        Assert.assertFalse(map.containsKey(null));
    }
    
    @Test
    public void containsValue_nullValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertFalse(map.containsValue(null));
        map.put("key", null);
        Assert.assertTrue(map.containsValue(null));
        map.remove("key");
        Assert.assertFalse(map.containsValue(null));
    }
    
    @Test
    public void getIfAbsent_nullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertEquals(Integer.valueOf(42), map.getIfAbsent(null, 42));
        map.put(null, 1);
        Assert.assertEquals(Integer.valueOf(1), map.getIfAbsent(null, 42));
    }
    
    @Test
    public void getIfAbsentPut_nullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertEquals(Integer.valueOf(42), map.getIfAbsentPut(null, 42));
        Assert.assertEquals(Integer.valueOf(42), map.get(null));
        Assert.assertEquals(Integer.valueOf(42), map.getIfAbsentPut(null, 43));
    }
    
    @Test
    public void getIfAbsentPutWithKey_nullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertEquals(Integer.valueOf(1), map.getIfAbsentPutWithKey(null, key -> 1));
        Assert.assertEquals(Integer.valueOf(1), map.get(null));
        Assert.assertEquals(Integer.valueOf(1), map.getIfAbsentPutWithKey(null, key -> 2));
    }
    
    @Test
    public void getIfAbsentPut_nullValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.getIfAbsentPut("key", null));
        Assert.assertNull(map.get("key"));
    }
}
