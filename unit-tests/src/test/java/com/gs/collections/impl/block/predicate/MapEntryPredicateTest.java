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

package com.gs.collections.impl.block.predicate;

import java.util.AbstractMap;
import java.util.Map;

import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class MapEntryPredicateTest
{
    @Test
    public void acceptWithMapEntry()
    {
        MapEntryPredicate<String, Integer> predicate = new MapEntryPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String key, Integer value)
            {
                return key.length() > 3 && value > 10;
            }
        };
        
        Map.Entry<String, Integer> entry1 = new AbstractMap.SimpleEntry<>("test", 20);
        Map.Entry<String, Integer> entry2 = new AbstractMap.SimpleEntry<>("abc", 20);
        Map.Entry<String, Integer> entry3 = new AbstractMap.SimpleEntry<>("test", 5);
        Map.Entry<String, Integer> entry4 = new AbstractMap.SimpleEntry<>("abc", 5);
        
        Assert.assertTrue(predicate.accept(entry1));
        Assert.assertFalse(predicate.accept(entry2));
        Assert.assertFalse(predicate.accept(entry3));
        Assert.assertFalse(predicate.accept(entry4));
    }
    
    @Test
    public void acceptWithKeyValue()
    {
        MapEntryPredicate<String, Integer> predicate = new MapEntryPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String key, Integer value)
            {
                return key.length() > 3 && value > 10;
            }
        };
        
        Assert.assertTrue(predicate.accept("test", 20));
        Assert.assertFalse(predicate.accept("abc", 20));
        Assert.assertFalse(predicate.accept("test", 5));
        Assert.assertFalse(predicate.accept("abc", 5));
    }
    
    @Test
    public void acceptWithNullKey()
    {
        MapEntryPredicate<String, Integer> predicate = new MapEntryPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String key, Integer value)
            {
                return key != null && key.length() > 3 && value > 10;
            }
        };
        
        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>(null, 20);
        Assert.assertFalse(predicate.accept(entry));
        Assert.assertFalse(predicate.accept(null, 20));
    }
    
    @Test
    public void acceptWithNullValue()
    {
        MapEntryPredicate<String, Integer> predicate = new MapEntryPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String key, Integer value)
            {
                return key.length() > 3 && value != null && value > 10;
            }
        };
        
        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("test", null);
        
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept(entry));
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept("test", null));
    }
    
    @Test
    public void serialization()
    {
        MapEntryPredicate<String, Integer> predicate = new MapEntryPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String key, Integer value)
            {
                return key.length() > 3 && value > 10;
            }
        };
        
        Verify.assertSerializedForm(predicate);
    }
}
