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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class HashBagIteratorTest
{
    @Test
    public void iterator_emptyBag()
    {
        HashBag<String> bag = HashBag.newBag();
        Iterator<String> iterator = bag.iterator();
        
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void iterator_singleElement()
    {
        HashBag<String> bag = HashBag.newBagWith("one");
        Iterator<String> iterator = bag.iterator();
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("one", iterator.next());
        Assert.assertFalse(iterator.hasNext());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void iterator_multipleElements()
    {
        HashBag<String> bag = HashBag.newBagWith("one", "two", "three");
        Iterator<String> iterator = bag.iterator();
        
        Assert.assertTrue(iterator.hasNext());
        String first = iterator.next();
        Assert.assertTrue(bag.contains(first));
        
        Assert.assertTrue(iterator.hasNext());
        String second = iterator.next();
        Assert.assertTrue(bag.contains(second));
        
        Assert.assertTrue(iterator.hasNext());
        String third = iterator.next();
        Assert.assertTrue(bag.contains(third));
        
        Assert.assertFalse(iterator.hasNext());
    }
    
    @Test
    public void iterator_multipleOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("one", 3);
        
        Iterator<String> iterator = bag.iterator();
        
        int count = 0;
        while (iterator.hasNext())
        {
            Assert.assertEquals("one", iterator.next());
            count++;
        }
        
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void iterator_remove()
    {
        HashBag<String> bag = HashBag.newBagWith("one", "two", "three");
        Iterator<String> iterator = bag.iterator();
        
        String first = iterator.next();
        iterator.remove();
        Assert.assertEquals(2, bag.size());
        Assert.assertFalse(bag.contains(first));
        
        String second = iterator.next();
        iterator.remove();
        Assert.assertEquals(1, bag.size());
        Assert.assertFalse(bag.contains(second));
        
        String third = iterator.next();
        iterator.remove();
        Assert.assertEquals(0, bag.size());
        Assert.assertFalse(bag.contains(third));
        
        Assert.assertTrue(bag.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_removeWithoutNext()
    {
        HashBag<String> bag = HashBag.newBagWith("one", "two");
        Iterator<String> iterator = bag.iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_removeCalledTwice()
    {
        HashBag<String> bag = HashBag.newBagWith("one", "two");
        Iterator<String> iterator = bag.iterator();
        iterator.next();
        iterator.remove(); // First remove is fine
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test
    public void iterator_removeWithMultipleOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("one", 3);
        
        Iterator<String> iterator = bag.iterator();
        
        iterator.next();
        iterator.remove();
        Assert.assertEquals(2, bag.size());
        Assert.assertEquals(2, bag.occurrencesOf("one"));
        
        iterator.next();
        iterator.remove();
        Assert.assertEquals(1, bag.size());
        Assert.assertEquals(1, bag.occurrencesOf("one"));
        
        iterator.next();
        iterator.remove();
        Assert.assertEquals(0, bag.size());
        Assert.assertEquals(0, bag.occurrencesOf("one"));
        
        Assert.assertTrue(bag.isEmpty());
    }
    
    @Test
    public void iterator_hasNextDoesNotAdvance()
    {
        HashBag<String> bag = HashBag.newBagWith("one", "two");
        Iterator<String> iterator = bag.iterator();
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasNext()); // Multiple calls should not advance
        Assert.assertTrue(iterator.hasNext());
        
        String first = iterator.next();
        Assert.assertTrue(bag.contains(first));
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasNext()); // Multiple calls should not advance
        Assert.assertTrue(iterator.hasNext());
        
        String second = iterator.next();
        Assert.assertTrue(bag.contains(second));
        
        Assert.assertFalse(iterator.hasNext());
    }
}
