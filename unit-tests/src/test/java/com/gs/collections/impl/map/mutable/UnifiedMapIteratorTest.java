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

import org.junit.Test;

public class UnifiedMapIteratorTest
{
    @Test(expected = IllegalStateException.class)
    public void keySetIterator_removeWithoutNext()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("1", 1, "2", 2, "3", 3);
        Iterator<String> iterator = map.keySet().iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void keySetIterator_removeCalledTwice()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("1", 1, "2", 2, "3", 3);
        Iterator<String> iterator = map.keySet().iterator();
        iterator.next();
        iterator.remove(); // First remove is valid
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test(expected = NoSuchElementException.class)
    public void keySetIterator_nextBeyondEnd()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("1", 1);
        Iterator<String> iterator = map.keySet().iterator();
        iterator.next(); // Valid
        iterator.next(); // Should throw NoSuchElementException
    }
    
    @Test(expected = IllegalStateException.class)
    public void valuesIterator_removeWithoutNext()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("1", 1, "2", 2, "3", 3);
        Iterator<Integer> iterator = map.values().iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void entrySetIterator_removeWithoutNext()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("1", 1, "2", 2, "3", 3);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void entrySetIterator_setValue_immutableEntry()
    {
        UnifiedMap<String, Integer> map = UnifiedMap.newWithKeysValues("1", 1);
        Map.Entry<String, Integer> entry = map.entrySet().iterator().next();
        entry.setValue(100); // Should throw UnsupportedOperationException for immutable entries
    }
}
