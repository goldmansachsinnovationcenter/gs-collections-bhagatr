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

public class HashBagBoundaryTest
{
    @Test
    public void newBag_emptyBag()
    {
        HashBag<String> bag = HashBag.newBag();
        Assert.assertTrue(bag.isEmpty());
        Assert.assertEquals(0, bag.size());
        Assert.assertEquals(0, bag.sizeDistinct());
    }
    
    @Test
    public void addOccurrences_zeroOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("test", 0);
        Assert.assertEquals(0, bag.occurrencesOf("test"));
        Assert.assertEquals(0, bag.size());
    }
    
    @Test
    public void addOccurrences_largeOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        int largeValue = Integer.MAX_VALUE / 2;
        bag.addOccurrences("test", largeValue);
        Assert.assertEquals(largeValue, bag.occurrencesOf("test"));
        Assert.assertEquals(largeValue, bag.size());
    }
    
    @Test
    public void removeOccurrences_zeroOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.add("test");
        bag.removeOccurrences("test", 0);
        Assert.assertEquals(1, bag.occurrencesOf("test"));
    }
    
    @Test
    public void removeOccurrences_moreThanExist()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("test", 5);
        bag.removeOccurrences("test", 10);
        Assert.assertEquals(0, bag.occurrencesOf("test"));
    }
    
    @Test
    public void setOccurrences_zero()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("test", 5);
        bag.setOccurrences("test", 0);
        Assert.assertEquals(0, bag.occurrencesOf("test"));
        Assert.assertFalse(bag.contains("test"));
    }
    
    @Test
    public void setOccurrences_largeValue()
    {
        HashBag<String> bag = HashBag.newBag();
        int largeValue = Integer.MAX_VALUE / 2;
        bag.setOccurrences("test", largeValue);
        Assert.assertEquals(largeValue, bag.occurrencesOf("test"));
    }
    
    @Test
    public void sizeDistinct_manyDuplicates()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("test", 1000);
        Assert.assertEquals(1000, bag.size());
        Assert.assertEquals(1, bag.sizeDistinct());
    }
    
    @Test
    public void toMapOfItemToCount_emptyBag()
    {
        HashBag<String> bag = HashBag.newBag();
        Assert.assertTrue(bag.toMapOfItemToCount().isEmpty());
    }
    
    @Test
    public void toMapOfItemToCount_withElements()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("A", 1);
        bag.addOccurrences("B", 2);
        bag.addOccurrences("C", 3);
        Assert.assertEquals(3, bag.toMapOfItemToCount().size());
        Assert.assertEquals(Integer.valueOf(1), bag.toMapOfItemToCount().get("A"));
        Assert.assertEquals(Integer.valueOf(2), bag.toMapOfItemToCount().get("B"));
        Assert.assertEquals(Integer.valueOf(3), bag.toMapOfItemToCount().get("C"));
    }
}
