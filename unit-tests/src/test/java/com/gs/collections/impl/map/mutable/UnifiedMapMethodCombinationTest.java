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

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.block.factory.Functions;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import com.gs.collections.impl.tuple.Tuples;
import org.junit.Assert;
import org.junit.Test;

public class UnifiedMapMethodCombinationTest
{
    @Test
    public void putAndGet()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        
        Assert.assertNull(map.put("one", 1));
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(1), map.put("one", 11));
        Assert.assertEquals(Integer.valueOf(11), map.get("one"));
        
        Assert.assertNull(map.put("two", 2));
        Assert.assertEquals(Integer.valueOf(2), map.get("two"));
        
        Assert.assertEquals(2, map.size());
    }
    
    @Test
    public void putAllAndClear()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        map.put("one", 1);
        
        MutableMap<String, Integer> mapToPut = UnifiedMap.newMap();
        mapToPut.put("two", 2);
        mapToPut.put("three", 3);
        
        map.putAll(mapToPut);
        
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        Assert.assertEquals(Integer.valueOf(2), map.get("two"));
        Assert.assertEquals(Integer.valueOf(3), map.get("three"));
        
        map.clear();
        Assert.assertEquals(0, map.size());
        Assert.assertTrue(map.isEmpty());
    }
    
    @Test
    public void getIfAbsentPut()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        
        Assert.assertEquals(Integer.valueOf(1), map.getIfAbsentPut("one", () -> 1));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(1), map.getIfAbsentPut("one", () -> 11));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(2), map.getIfAbsentPut("two", () -> 2));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals(Integer.valueOf(2), map.get("two"));
    }
    
    @Test
    public void getIfAbsentPutWith()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Function<String, Integer> lengthFunction = String::length;
        
        Assert.assertEquals(Integer.valueOf(3), map.getIfAbsentPutWith("one", lengthFunction, "one"));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(3), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(3), map.getIfAbsentPutWith("one", lengthFunction, "oneone"));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(3), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(3), map.getIfAbsentPutWith("two", lengthFunction, "two"));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals(Integer.valueOf(3), map.get("two"));
    }
    
    @Test
    public void updateValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        Function0<Integer> factory = () -> 0;
        Function<Integer, Integer> add = value -> value + 1;
        
        Assert.assertEquals(Integer.valueOf(1), map.updateValue("one", factory, add));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(2), map.updateValue("one", factory, add));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(2), map.get("one"));
        
        Assert.assertEquals(Integer.valueOf(1), map.updateValue("two", factory, add));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("two"));
    }
    
    @Test
    public void selectAndReject()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4);
        
        MutableMap<String, Integer> evens = map.select((key, value) -> value % 2 == 0);
        Assert.assertEquals(2, evens.size());
        Assert.assertEquals(Integer.valueOf(2), evens.get("two"));
        Assert.assertEquals(Integer.valueOf(4), evens.get("four"));
        
        MutableMap<String, Integer> odds = map.reject((key, value) -> value % 2 == 0);
        Assert.assertEquals(2, odds.size());
        Assert.assertEquals(Integer.valueOf(1), odds.get("one"));
        Assert.assertEquals(Integer.valueOf(3), odds.get("three"));
        
        // Original map should be unchanged
        Assert.assertEquals(4, map.size());
    }
    
    @Test
    public void collectAndFlatCollect()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3);
        
        MutableMap<String, String> collected = map.collect((key, value) -> value.toString());
        Assert.assertEquals(3, collected.size());
        Assert.assertEquals("1", collected.get("one"));
        Assert.assertEquals("2", collected.get("two"));
        Assert.assertEquals("3", collected.get("three"));
        
        MutableMap<String, Character> flatCollected = map.flatCollect((key, value) -> {
            MutableList<Character> result = FastList.newList();
            for (char c : value.toString().toCharArray())
            {
                result.add(c);
            }
            return result;
        }, (key, character) -> character);
        
        Assert.assertEquals(3, flatCollected.size());
        Assert.assertEquals(Character.valueOf('1'), flatCollected.get("one"));
        Assert.assertEquals(Character.valueOf('2'), flatCollected.get("two"));
        Assert.assertEquals(Character.valueOf('3'), flatCollected.get("three"));
    }
    
    @Test
    public void collectValues()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3);
        
        MutableMap<String, String> collected = map.collectValues((key, value) -> key + value);
        Assert.assertEquals(3, collected.size());
        Assert.assertEquals("one1", collected.get("one"));
        Assert.assertEquals("two2", collected.get("two"));
        Assert.assertEquals("three3", collected.get("three"));
    }
    
    @Test
    public void toSetAndToList()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3);
        
        MutableSet<String> keySet = map.keySet().toSet();
        Assert.assertEquals(UnifiedSet.newSetWith("one", "two", "three"), keySet);
        
        MutableList<Integer> valueList = map.values().toList();
        Assert.assertEquals(3, valueList.size());
        Assert.assertTrue(valueList.containsAll(FastList.newListWith(1, 2, 3)));
    }
    
    @Test
    public void forEachKeyValue()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3);
        
        MutableMap<String, Integer> result = UnifiedMap.newMap();
        map.forEachKeyValue((key, value) -> result.put(key, value * 2));
        
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Integer.valueOf(2), result.get("one"));
        Assert.assertEquals(Integer.valueOf(4), result.get("two"));
        Assert.assertEquals(Integer.valueOf(6), result.get("three"));
    }
    
    @Test
    public void flip()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3);
        
        MutableMap<Integer, String> flipped = map.flip();
        
        Assert.assertEquals(3, flipped.size());
        Assert.assertEquals("one", flipped.get(1));
        Assert.assertEquals("two", flipped.get(2));
        Assert.assertEquals("three", flipped.get(3));
    }
    
    @Test
    public void withKeyValueAndWithAllKeyValues()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newMap();
        
        UnifiedMap<String, Integer> mapWithOne = map.withKeyValue("one", 1);
        Assert.assertSame(map, mapWithOne);
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        
        UnifiedMap<String, Integer> mapWithMore = map.withAllKeyValues(
                FastList.newListWith(
                        Tuples.pair("two", 2),
                        Tuples.pair("three", 3)));
        
        Assert.assertSame(map, mapWithMore);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("one"));
        Assert.assertEquals(Integer.valueOf(2), map.get("two"));
        Assert.assertEquals(Integer.valueOf(3), map.get("three"));
    }
    
    @Test
    public void withoutKeyAndWithoutAllKeys()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4);
        
        UnifiedMap<String, Integer> mapWithoutOne = map.withoutKey("one");
        Assert.assertSame(map, mapWithoutOne);
        Assert.assertEquals(3, map.size());
        Assert.assertFalse(map.containsKey("one"));
        
        UnifiedMap<String, Integer> mapWithoutMore = map.withoutAllKeys(
                FastList.newListWith("two", "three"));
        
        Assert.assertSame(map, mapWithoutMore);
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(Integer.valueOf(4), map.get("four"));
    }
}
