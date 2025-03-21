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

import org.junit.Assert;
import org.junit.Test;

public class HashBagNullTest
{
    @Test
    public void add_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        Assert.assertTrue(bag.add(null));
        Assert.assertEquals(1, bag.occurrencesOf(null));
        Assert.assertTrue(bag.add(null));
        Assert.assertEquals(2, bag.occurrencesOf(null));
    }
    
    @Test
    public void remove_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.add(null);
        bag.add(null);
        Assert.assertTrue(bag.remove(null));
        Assert.assertEquals(1, bag.occurrencesOf(null));
        Assert.assertTrue(bag.remove(null));
        Assert.assertEquals(0, bag.occurrencesOf(null));
        Assert.assertFalse(bag.remove(null));
    }
    
    @Test
    public void addOccurrences_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences(null, 3);
        Assert.assertEquals(3, bag.occurrencesOf(null));
    }
    
    @Test
    public void removeOccurrences_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences(null, 5);
        bag.removeOccurrences(null, 2);
        Assert.assertEquals(3, bag.occurrencesOf(null));
        bag.removeOccurrences(null, 3);
        Assert.assertEquals(0, bag.occurrencesOf(null));
    }
    
    @Test
    public void setOccurrences_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.setOccurrences(null, 5);
        Assert.assertEquals(5, bag.occurrencesOf(null));
        bag.setOccurrences(null, 0);
        Assert.assertEquals(0, bag.occurrencesOf(null));
    }
    
    @Test
    public void contains_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        Assert.assertFalse(bag.contains(null));
        bag.add(null);
        Assert.assertTrue(bag.contains(null));
    }
    
    @Test
    public void forEachWithOccurrences_nullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences(null, 3);
        final int[] sum = new int[1];
        bag.forEachWithOccurrences((each, parameter) -> {
            if (each == null)
            {
                sum[0] += parameter;
            }
        });
        Assert.assertEquals(3, sum[0]);
    }
}
