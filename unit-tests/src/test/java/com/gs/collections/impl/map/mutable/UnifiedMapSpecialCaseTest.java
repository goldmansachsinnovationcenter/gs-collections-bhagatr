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

import java.util.HashMap;
import java.util.Map;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.Procedure2;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.block.factory.Functions;
import com.gs.collections.impl.factory.Maps;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import com.gs.collections.impl.tuple.Tuples;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

public class UnifiedMapSpecialCaseTest
{
    @Test
    public void nullKeyHandling()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Assert.assertNull(map.put(null, 1));
        Assert.assertEquals(Integer.valueOf(1), map.get(null));
        Assert.assertEquals(Integer.valueOf(1), map.put(null, 2));
        Assert.assertEquals(Integer.valueOf(2), map.get(null));
        Assert.assertTrue(map.containsKey(null));
        Assert.assertEquals(Integer.valueOf(2), map.remove(null));
        Assert.assertFalse(map.containsKey(null));
    }
    
    @Test
    public void collisionHandling()
    {
        // Create a map with a small capacity to force collisions
        UnifiedMap<CollidingKey, Integer> map = UnifiedMap.newMap(2);
        
        // Add keys that will collide
        CollidingKey key1 = new CollidingKey("key1", 1);
        CollidingKey key2 = new CollidingKey("key2", 1); // Same hash code as key1
        CollidingKey key3 = new CollidingKey("key3", 1); // Same hash code as key1 and key2
        
        map.put(key1, 1);
        map.put(key2, 2);
        map.put(key3, 3);
        
        // Verify all keys are accessible despite collisions
        Assert.assertEquals(Integer.valueOf(1), map.get(key1));
        Assert.assertEquals(Integer.valueOf(2), map.get(key2));
        Assert.assertEquals(Integer.valueOf(3), map.get(key3));
        
        // Verify size is correct
        Assert.assertEquals(3, map.size());
        
        // Verify removal works correctly with collisions
        Assert.assertEquals(Integer.valueOf(2), map.remove(key2));
        Assert.assertEquals(2, map.size());
        Assert.assertNull(map.get(key2));
        Assert.assertEquals(Integer.valueOf(1), map.get(key1));
        Assert.assertEquals(Integer.valueOf(3), map.get(key3));
    }
    
    @Test
    public void chainedKeyRemoval()
    {
        // Create a map with a small capacity to force chaining
        UnifiedMap<CollidingKey, Integer> map = UnifiedMap.newMap(2);
        
        // Add keys that will collide and chain
        CollidingKey key1 = new CollidingKey("key1", 1);
        CollidingKey key2 = new CollidingKey("key2", 1);
        CollidingKey key3 = new CollidingKey("key3", 1);
        CollidingKey key4 = new CollidingKey("key4", 1);
        
        map.put(key1, 1);
        map.put(key2, 2);
        map.put(key3, 3);
        map.put(key4, 4);
        
        // Remove middle key in chain
        Assert.assertEquals(Integer.valueOf(2), map.remove(key2));
        
        // Verify other keys still accessible
        Assert.assertEquals(Integer.valueOf(1), map.get(key1));
        Assert.assertEquals(Integer.valueOf(3), map.get(key3));
        Assert.assertEquals(Integer.valueOf(4), map.get(key4));
        
        // Remove first key in chain
        Assert.assertEquals(Integer.valueOf(1), map.remove(key1));
        
        // Verify other keys still accessible
        Assert.assertEquals(Integer.valueOf(3), map.get(key3));
        Assert.assertEquals(Integer.valueOf(4), map.get(key4));
        
        // Remove last key in chain
        Assert.assertEquals(Integer.valueOf(4), map.remove(key4));
        
        // Verify remaining key still accessible
        Assert.assertEquals(Integer.valueOf(3), map.get(key3));
    }
    
    @Test
    public void resizeWithCollisions()
    {
        // Create a map with a small capacity
        UnifiedMap<CollidingKey, Integer> map = UnifiedMap.newMap(2);
        
        // Add enough colliding keys to trigger resize
        int count = 20;
        for (int i = 0; i < count; i++)
        {
            map.put(new CollidingKey("key" + i, 1), i);
        }
        
        // Verify all keys are accessible after resize
        for (int i = 0; i < count; i++)
        {
            Assert.assertEquals(Integer.valueOf(i), map.get(new CollidingKey("key" + i, 1)));
        }
        
        // Verify size is correct
        Assert.assertEquals(count, map.size());
    }
    
    @Test
    public void iterationWithCollisions()
    {
        // Create a map with a small capacity
        UnifiedMap<CollidingKey, Integer> map = UnifiedMap.newMap(2);
        
        // Add colliding keys
        int count = 10;
        for (int i = 0; i < count; i++)
        {
            map.put(new CollidingKey("key" + i, 1), i);
        }
        
        // Track which keys we've seen during iteration
        boolean[] seen = new boolean[count];
        
        // Iterate and mark keys as seen
        map.forEachKeyValue((key, value) -> {
            int index = Integer.parseInt(key.getValue().substring(3));
            seen[index] = true;
        });
        
        // Verify all keys were seen during iteration
        for (int i = 0; i < count; i++)
        {
            Assert.assertTrue("Key at index " + i + " was not seen during iteration", seen[i]);
        }
    }
    
    @Test
    public void equalsWithCollisions()
    {
        // Create two maps with small capacities
        UnifiedMap<CollidingKey, Integer> map1 = UnifiedMap.newMap(2);
        UnifiedMap<CollidingKey, Integer> map2 = UnifiedMap.newMap(2);
        
        // Add same colliding keys to both maps
        int count = 10;
        for (int i = 0; i < count; i++)
        {
            CollidingKey key = new CollidingKey("key" + i, 1);
            map1.put(key, i);
            map2.put(key, i);
        }
        
        // Verify maps are equal despite collisions
        Assert.assertEquals(map1, map2);
        
        // Modify one map
        map1.put(new CollidingKey("keyExtra", 1), 100);
        
        // Verify maps are no longer equal
        Assert.assertNotEquals(map1, map2);
    }
    
    /**
     * A key class that allows controlling the hash code for testing collisions.
     */
    private static final class CollidingKey
    {
        private final String value;
        private final int hashCode;
        
        public CollidingKey(String value, int hashCode)
        {
            this.value = value;
            this.hashCode = hashCode;
        }
        
        public String getValue()
        {
            return this.value;
        }
        
        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            
            CollidingKey that = (CollidingKey) o;
            return this.value.equals(that.value);
        }
        
        @Override
        public int hashCode()
        {
            return this.hashCode;
        }
        
        @Override
        public String toString()
        {
            return this.value + " (hash=" + this.hashCode + ")";
        }
    }
}
