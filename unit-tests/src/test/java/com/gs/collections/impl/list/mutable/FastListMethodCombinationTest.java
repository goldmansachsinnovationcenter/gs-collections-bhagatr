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

import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.partition.list.PartitionMutableList;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import org.junit.Assert;
import org.junit.Test;

public class FastListMethodCombinationTest
{
    @Test
    public void addAndRemove()
    {
        FastList<String> list = FastList.newList();
        
        Assert.assertTrue(list.add("one"));
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("one", list.get(0));
        
        Assert.assertTrue(list.add("two"));
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("two", list.get(1));
        
        Assert.assertTrue(list.remove("one"));
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("two", list.get(0));
        
        Assert.assertTrue(list.remove("two"));
        Assert.assertEquals(0, list.size());
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void addAllAndRemoveAll()
    {
        FastList<String> list = FastList.newList();
        MutableList<String> listToAdd = FastList.newListWith("one", "two", "three");
        
        Assert.assertTrue(list.addAll(listToAdd));
        Assert.assertEquals(3, list.size());
        Assert.assertEquals("one", list.get(0));
        Assert.assertEquals("two", list.get(1));
        Assert.assertEquals("three", list.get(2));
        
        Assert.assertTrue(list.removeAll(FastList.newListWith("one", "three")));
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("two", list.get(0));
        
        Assert.assertFalse(list.removeAll(FastList.newListWith("four")));
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("two", list.get(0));
    }
    
    @Test
    public void addAtIndexAndRemoveAtIndex()
    {
        FastList<String> list = FastList.newListWith("one", "three");
        
        list.add(1, "two");
        Assert.assertEquals(3, list.size());
        Assert.assertEquals("one", list.get(0));
        Assert.assertEquals("two", list.get(1));
        Assert.assertEquals("three", list.get(2));
        
        Assert.assertEquals("two", list.remove(1));
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("one", list.get(0));
        Assert.assertEquals("three", list.get(1));
    }
    
    @Test
    public void addAllAtIndexAndRemoveAll()
    {
        FastList<String> list = FastList.newListWith("one", "four");
        MutableList<String> listToAdd = FastList.newListWith("two", "three");
        
        Assert.assertTrue(list.addAll(1, listToAdd));
        Assert.assertEquals(4, list.size());
        Assert.assertEquals("one", list.get(0));
        Assert.assertEquals("two", list.get(1));
        Assert.assertEquals("three", list.get(2));
        Assert.assertEquals("four", list.get(3));
        
        Assert.assertTrue(list.removeAll(FastList.newListWith("two", "four")));
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("one", list.get(0));
        Assert.assertEquals("three", list.get(1));
    }
    
    @Test
    public void selectAndReject()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5, 6);
        
        MutableList<Integer> evens = list.select(each -> each % 2 == 0);
        Assert.assertEquals(FastList.newListWith(2, 4, 6), evens);
        
        MutableList<Integer> odds = list.reject(each -> each % 2 == 0);
        Assert.assertEquals(FastList.newListWith(1, 3, 5), odds);
        
        // Original list should be unchanged
        Assert.assertEquals(FastList.newListWith(1, 2, 3, 4, 5, 6), list);
    }
    
    @Test
    public void collectAndFlatCollect()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        
        MutableList<Integer> collected = list.collect(Integer::valueOf);
        Assert.assertEquals(FastList.newListWith(1, 2, 3), collected);
        
        MutableList<Character> flatCollected = list.flatCollect(s -> {
            MutableList<Character> result = FastList.newList();
            for (char c : s.toCharArray())
            {
                result.add(c);
            }
            return result;
        });
        
        Assert.assertEquals(FastList.newListWith('1', '2', '3'), flatCollected);
    }
    
    @Test
    public void selectAndCollect()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5, 6);
        
        MutableList<String> result = list.selectAndCollect(
                each -> each % 2 == 0,
                String::valueOf);
        
        Assert.assertEquals(FastList.newListWith("2", "4", "6"), result);
    }
    
    @Test
    public void partition()
    {
        FastList<Integer> list = FastList.newListWith(1, 2, 3, 4, 5, 6);
        
        PartitionMutableList<Integer> partition = list.partition(each -> each % 2 == 0);
        
        Assert.assertEquals(FastList.newListWith(2, 4, 6), partition.getSelected());
        Assert.assertEquals(FastList.newListWith(1, 3, 5), partition.getRejected());
    }
    
    @Test
    public void toSetAndToList()
    {
        FastList<String> list = FastList.newListWith("a", "b", "c", "a", "b");
        
        MutableSet<String> set = list.toSet();
        Assert.assertEquals(UnifiedSet.newSetWith("a", "b", "c"), set);
        
        MutableList<String> newList = list.toList();
        Assert.assertEquals(FastList.newListWith("a", "b", "c", "a", "b"), newList);
        Assert.assertNotSame(list, newList);
    }
    
    @Test
    public void retainAllAndClear()
    {
        FastList<String> list = FastList.newListWith("a", "b", "c", "d", "e");
        
        Assert.assertTrue(list.retainAll(FastList.newListWith("a", "c", "e")));
        Assert.assertEquals(FastList.newListWith("a", "c", "e"), list);
        
        list.clear();
        Assert.assertEquals(0, list.size());
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void withAndWithout()
    {
        FastList<String> list = FastList.newList();
        
        FastList<String> listWith = list.with("a");
        Assert.assertSame(list, listWith);
        Assert.assertEquals(FastList.newListWith("a"), list);
        
        FastList<String> listWithMore = list.with("b").with("c");
        Assert.assertSame(list, listWithMore);
        Assert.assertEquals(FastList.newListWith("a", "b", "c"), list);
        
        FastList<String> listWithout = list.without("b");
        Assert.assertSame(list, listWithout);
        Assert.assertEquals(FastList.newListWith("a", "c"), list);
    }
    
    @Test
    public void withAllAndWithoutAll()
    {
        FastList<String> list = FastList.newListWith("a");
        
        FastList<String> listWithAll = list.withAll(FastList.newListWith("b", "c"));
        Assert.assertSame(list, listWithAll);
        Assert.assertEquals(FastList.newListWith("a", "b", "c"), list);
        
        FastList<String> listWithoutAll = list.withoutAll(FastList.newListWith("a", "c"));
        Assert.assertSame(list, listWithoutAll);
        Assert.assertEquals(FastList.newListWith("b"), list);
    }
    
    @Test
    public void reverseAndShuffle()
    {
        FastList<String> list = FastList.newListWith("a", "b", "c");
        
        list.reverseThis();
        Assert.assertEquals(FastList.newListWith("c", "b", "a"), list);
        
        list.reverseThis();
        Assert.assertEquals(FastList.newListWith("a", "b", "c"), list);
        
        MutableList<String> reversed = list.toReversed();
        Assert.assertEquals(FastList.newListWith("c", "b", "a"), reversed);
        Assert.assertEquals(FastList.newListWith("a", "b", "c"), list);
    }
    
    @Test
    public void sortThis()
    {
        FastList<String> list = FastList.newListWith("c", "a", "b");
        
        list.sortThis();
        Assert.assertEquals(FastList.newListWith("a", "b", "c"), list);
        
        list = FastList.newListWith("3", "1", "2");
        list.sortThis((o1, o2) -> Integer.valueOf(o1).compareTo(Integer.valueOf(o2)));
        Assert.assertEquals(FastList.newListWith("1", "2", "3"), list);
    }
}
