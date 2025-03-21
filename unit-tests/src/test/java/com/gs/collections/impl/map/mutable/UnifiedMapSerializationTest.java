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

import com.gs.collections.impl.test.SerializeTestHelper;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class UnifiedMapSerializationTest
{
    @Test
    public void serialization_empty()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(0, deserializedMap.size());
        Verify.assertEmpty(deserializedMap);
    }
    
    @Test
    public void serialization_singleEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1);
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(1, deserializedMap.size());
        Assert.assertEquals(Integer.valueOf(1), deserializedMap.get("one"));
    }
    
    @Test
    public void serialization_multipleEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("one", 1, "two", 2, "three", 3);
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(3, deserializedMap.size());
        Assert.assertEquals(Integer.valueOf(1), deserializedMap.get("one"));
        Assert.assertEquals(Integer.valueOf(2), deserializedMap.get("two"));
        Assert.assertEquals(Integer.valueOf(3), deserializedMap.get("three"));
    }
    
    @Test
    public void serialization_withNullKey()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put(null, 1);
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(1, deserializedMap.size());
        Assert.assertEquals(Integer.valueOf(1), deserializedMap.get(null));
    }
    
    @Test
    public void serialization_withNullValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put("key", null);
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(1, deserializedMap.size());
        Assert.assertNull(deserializedMap.get("key"));
    }
    
    @Test
    public void serialization_withNullKeyAndValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put(null, null);
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(1, deserializedMap.size());
        Assert.assertNull(deserializedMap.get(null));
    }
    
    @Test
    public void serialization_withMixedEntries()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put("one", 1);
        map.put(null, 2);
        map.put("three", null);
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(3, deserializedMap.size());
        Assert.assertEquals(Integer.valueOf(1), deserializedMap.get("one"));
        Assert.assertEquals(Integer.valueOf(2), deserializedMap.get(null));
        Assert.assertNull(deserializedMap.get("three"));
    }
    
    @Test
    public void serialization_largeMap()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        for (int i = 0; i < 1000; i++)
        {
            map.put("key" + i, i);
        }
        UnifiedMap<String, Integer> deserializedMap = SerializeTestHelper.serializeDeserialize(map);
        
        Assert.assertEquals(1000, deserializedMap.size());
        for (int i = 0; i < 1000; i++)
        {
            Assert.assertEquals(Integer.valueOf(i), deserializedMap.get("key" + i));
        }
    }
}
