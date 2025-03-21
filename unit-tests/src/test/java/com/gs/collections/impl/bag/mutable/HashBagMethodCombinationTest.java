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

package com.gs.collections.impl.bag.mutable;

import com.gs.collections.api.bag.MutableBag;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import org.junit.Assert;
import org.junit.Test;

public class HashBagMethodCombinationTest
{
    @Test
    public void addAndRemoveOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("test", 5);
        Assert.assertEquals(5, bag.occurrencesOf("test"));
        
        bag.removeOccurrences("test", 2);
        Assert.assertEquals(3, bag.occurrencesOf("test"));
        
        bag.addOccurrences("test", 4);
        Assert.assertEquals(7, bag.occurrencesOf("test"));
        
        bag.removeOccurrences("test", 7);
        Assert.assertEquals(0, bag.occurrencesOf("test"));
        Assert.assertFalse(bag.contains("test"));
    }
    
    @Test
    public void addAllAndRemoveAll()
    {
        HashBag<String> bag = HashBag.newBag();
        MutableList<String> list = FastList.newListWith("a", "b", "c", "a", "b", "a");
        
        bag.addAll(list);
        Assert.assertEquals(6, bag.size());
        Assert.assertEquals(3, bag.occurrencesOf("a"));
        Assert.assertEquals(2, bag.occurrencesOf("b"));
        Assert.assertEquals(1, bag.occurrencesOf("c"));
        
        bag.removeAll(FastList.newListWith("a", "c"));
        Assert.assertEquals(2, bag.size());
        Assert.assertEquals(0, bag.occurrencesOf("a"));
        Assert.assertEquals(2, bag.occurrencesOf("b"));
        Assert.assertEquals(0, bag.occurrencesOf("c"));
    }
    
    @Test
    public void selectAndReject()
    {
        HashBag<Integer> bag = HashBag.newBagWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        
        MutableBag<Integer> evens = bag.select(each -> each % 2 == 0);
        Assert.assertEquals(6, evens.size());
        Assert.assertEquals(0, evens.occurrencesOf(1));
        Assert.assertEquals(2, evens.occurrencesOf(2));
        Assert.assertEquals(0, evens.occurrencesOf(3));
        Assert.assertEquals(4, evens.occurrencesOf(4));
        
        MutableBag<Integer> odds = bag.reject(each -> each % 2 == 0);
        Assert.assertEquals(4, odds.size());
        Assert.assertEquals(1, odds.occurrencesOf(1));
        Assert.assertEquals(0, odds.occurrencesOf(2));
        Assert.assertEquals(3, odds.occurrencesOf(3));
        Assert.assertEquals(0, odds.occurrencesOf(4));
        
        // Original bag should be unchanged
        Assert.assertEquals(10, bag.size());
    }
    
    @Test
    public void collectAndFlatCollect()
    {
        HashBag<String> bag = HashBag.newBagWith("1", "2", "2", "3", "3", "3");
        
        MutableBag<Integer> collected = bag.collect(Integer::valueOf);
        Assert.assertEquals(6, collected.size());
        Assert.assertEquals(1, collected.occurrencesOf(1));
        Assert.assertEquals(2, collected.occurrencesOf(2));
        Assert.assertEquals(3, collected.occurrencesOf(3));
        
        MutableBag<Character> flatCollected = bag.flatCollect(s -> {
            MutableList<Character> result = FastList.newList();
            for (char c : s.toCharArray())
            {
                result.add(c);
            }
            return result;
        });
        
        Assert.assertEquals(6, flatCollected.size());
        Assert.assertEquals(1, flatCollected.occurrencesOf('1'));
        Assert.assertEquals(2, flatCollected.occurrencesOf('2'));
        Assert.assertEquals(3, flatCollected.occurrencesOf('3'));
    }
    
    @Test
    public void selectAndCollect()
    {
        HashBag<Integer> bag = HashBag.newBagWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        
        MutableBag<String> result = bag.selectAndCollect(
                each -> each % 2 == 0,
                String::valueOf);
        
        Assert.assertEquals(6, result.size());
        Assert.assertEquals(0, result.occurrencesOf("1"));
        Assert.assertEquals(2, result.occurrencesOf("2"));
        Assert.assertEquals(0, result.occurrencesOf("3"));
        Assert.assertEquals(4, result.occurrencesOf("4"));
    }
    
    @Test
    public void toMapOfItemToCount()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("a", 1);
        bag.addOccurrences("b", 2);
        bag.addOccurrences("c", 3);
        
        MutableMap<String, Integer> map = bag.toMapOfItemToCount();
        
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(Integer.valueOf(1), map.get("a"));
        Assert.assertEquals(Integer.valueOf(2), map.get("b"));
        Assert.assertEquals(Integer.valueOf(3), map.get("c"));
        
        // Verify changes to the map don't affect the bag
        map.put("d", 4);
        Assert.assertEquals(0, bag.occurrencesOf("d"));
    }
    
    @Test
    public void toSetAndToList()
    {
        HashBag<String> bag = HashBag.newBagWith("a", "b", "b", "c", "c", "c");
        
        MutableSet<String> set = bag.toSet();
        Assert.assertEquals(UnifiedSet.newSetWith("a", "b", "c"), set);
        
        MutableList<String> list = bag.toList();
        Assert.assertEquals(6, list.size());
        Assert.assertEquals(1, list.count(each -> each.equals("a")));
        Assert.assertEquals(2, list.count(each -> each.equals("b")));
        Assert.assertEquals(3, list.count(each -> each.equals("c")));
    }
    
    @Test
    public void forEachWithOccurrences()
    {
        HashBag<String> bag = HashBag.newBagWith("a", "b", "b", "c", "c", "c");
        MutableMap<String, Integer> result = UnifiedSet.<String>newSet().withAll(bag).toMap(s -> s, s -> 0);
        
        bag.forEachWithOccurrences((item, occurrences) -> {
            result.put(item, occurrences);
        });
        
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(Integer.valueOf(1), result.get("a"));
        Assert.assertEquals(Integer.valueOf(2), result.get("b"));
        Assert.assertEquals(Integer.valueOf(3), result.get("c"));
    }
    
    @Test
    public void selectByOccurrences()
    {
        HashBag<String> bag = HashBag.newBagWith("a", "b", "b", "c", "c", "c", "d", "d", "d", "d");
        
        MutableBag<String> result = bag.selectByOccurrences(occurrences -> occurrences > 2);
        
        Assert.assertEquals(7, result.size());
        Assert.assertEquals(0, result.occurrencesOf("a"));
        Assert.assertEquals(0, result.occurrencesOf("b"));
        Assert.assertEquals(3, result.occurrencesOf("c"));
        Assert.assertEquals(4, result.occurrencesOf("d"));
    }
    
    @Test
    public void addAllIterable()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addAllIterable(FastList.newListWith("a", "b", "b", "c", "c", "c"));
        
        Assert.assertEquals(6, bag.size());
        Assert.assertEquals(1, bag.occurrencesOf("a"));
        Assert.assertEquals(2, bag.occurrencesOf("b"));
        Assert.assertEquals(3, bag.occurrencesOf("c"));
    }
    
    @Test
    public void retainAllAndRetainAllIterable()
    {
        HashBag<String> bag = HashBag.newBagWith("a", "b", "b", "c", "c", "c", "d", "d", "d", "d");
        
        bag.retainAll(FastList.newListWith("b", "d"));
        
        Assert.assertEquals(6, bag.size());
        Assert.assertEquals(0, bag.occurrencesOf("a"));
        Assert.assertEquals(2, bag.occurrencesOf("b"));
        Assert.assertEquals(0, bag.occurrencesOf("c"));
        Assert.assertEquals(4, bag.occurrencesOf("d"));
        
        bag.retainAllIterable(FastList.newListWith("b"));
        
        Assert.assertEquals(2, bag.size());
        Assert.assertEquals(0, bag.occurrencesOf("a"));
        Assert.assertEquals(2, bag.occurrencesOf("b"));
        Assert.assertEquals(0, bag.occurrencesOf("c"));
        Assert.assertEquals(0, bag.occurrencesOf("d"));
    }
}
