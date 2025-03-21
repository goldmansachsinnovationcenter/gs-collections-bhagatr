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

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class FastListIteratorPathTest
{
    @Test
    public void iterator_emptyList()
    {
        FastList<String> list = FastList.newList();
        Iterator<String> iterator = list.iterator();
        
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
        FastList<String> list = FastList.newListWith("one");
        Iterator<String> iterator = list.iterator();
        
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
        FastList<String> list = FastList.newListWith("one", "two", "three");
        Iterator<String> iterator = list.iterator();
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("one", iterator.next());
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("two", iterator.next());
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("three", iterator.next());
        
        Assert.assertFalse(iterator.hasNext());
    }
    
    @Test
    public void iterator_remove()
    {
        FastList<String> list = FastList.newListWith("one", "two", "three");
        Iterator<String> iterator = list.iterator();
        
        iterator.next(); // "one"
        iterator.remove();
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("two", list.get(0));
        
        iterator.next(); // "two"
        iterator.remove();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("three", list.get(0));
        
        iterator.next(); // "three"
        iterator.remove();
        Assert.assertEquals(0, list.size());
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_removeWithoutNext()
    {
        FastList<String> list = FastList.newListWith("one", "two");
        Iterator<String> iterator = list.iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_removeCalledTwice()
    {
        FastList<String> list = FastList.newListWith("one", "two");
        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove(); // First remove is fine
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test
    public void listIterator_emptyList()
    {
        FastList<String> list = FastList.newList();
        ListIterator<String> iterator = list.listIterator();
        
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(iterator.hasPrevious());
        Assert.assertEquals(0, iterator.nextIndex());
        Assert.assertEquals(-1, iterator.previousIndex());
        
        try
        {
            iterator.next();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
        
        try
        {
            iterator.previous();
            Assert.fail("Should throw NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // Expected
        }
    }
    
    @Test
    public void listIterator_singleElement()
    {
        FastList<String> list = FastList.newListWith("one");
        ListIterator<String> iterator = list.listIterator();
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertFalse(iterator.hasPrevious());
        Assert.assertEquals(0, iterator.nextIndex());
        Assert.assertEquals(-1, iterator.previousIndex());
        
        Assert.assertEquals("one", iterator.next());
        
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(iterator.hasPrevious());
        Assert.assertEquals(1, iterator.nextIndex());
        Assert.assertEquals(0, iterator.previousIndex());
        
        Assert.assertEquals("one", iterator.previous());
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertFalse(iterator.hasPrevious());
        Assert.assertEquals(0, iterator.nextIndex());
        Assert.assertEquals(-1, iterator.previousIndex());
    }
    
    @Test
    public void listIterator_multipleElements()
    {
        FastList<String> list = FastList.newListWith("one", "two", "three");
        ListIterator<String> iterator = list.listIterator();
        
        // Forward iteration
        Assert.assertEquals("one", iterator.next());
        Assert.assertEquals("two", iterator.next());
        Assert.assertEquals("three", iterator.next());
        
        // Backward iteration
        Assert.assertEquals("three", iterator.previous());
        Assert.assertEquals("two", iterator.previous());
        Assert.assertEquals("one", iterator.previous());
    }
    
    @Test
    public void listIterator_withIndex()
    {
        FastList<String> list = FastList.newListWith("one", "two", "three");
        ListIterator<String> iterator = list.listIterator(1); // Start at index 1
        
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasPrevious());
        Assert.assertEquals(1, iterator.nextIndex());
        Assert.assertEquals(0, iterator.previousIndex());
        
        Assert.assertEquals("two", iterator.next());
        Assert.assertEquals("three", iterator.next());
        
        Assert.assertFalse(iterator.hasNext());
        Assert.assertTrue(iterator.hasPrevious());
        
        Assert.assertEquals("three", iterator.previous());
        Assert.assertEquals("two", iterator.previous());
        Assert.assertEquals("one", iterator.previous());
    }
    
    @Test
    public void listIterator_remove()
    {
        FastList<String> list = FastList.newListWith("one", "two", "three");
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next(); // "one"
        iterator.remove();
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("two", list.get(0));
        
        iterator.next(); // "two"
        iterator.remove();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("three", list.get(0));
        
        iterator.next(); // "three"
        iterator.remove();
        Assert.assertEquals(0, list.size());
        Assert.assertTrue(list.isEmpty());
    }
    
    @Test
    public void listIterator_set()
    {
        FastList<String> list = FastList.newListWith("one", "two", "three");
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next(); // "one"
        iterator.set("ONE");
        
        iterator.next(); // "two"
        iterator.set("TWO");
        
        iterator.next(); // "three"
        iterator.set("THREE");
        
        Assert.assertEquals(FastList.newListWith("ONE", "TWO", "THREE"), list);
    }
    
    @Test
    public void listIterator_add()
    {
        FastList<String> list = FastList.newListWith("one", "three");
        ListIterator<String> iterator = list.listIterator();
        
        iterator.next(); // "one"
        iterator.add("two");
        
        Assert.assertEquals(FastList.newListWith("one", "two", "three"), list);
        
        // After add(), next() returns the element after the added element
        Assert.assertEquals("three", iterator.next());
        
        // Add at the end
        iterator.add("four");
        Assert.assertEquals(FastList.newListWith("one", "two", "three", "four"), list);
    }
    
    @Test(expected = IllegalStateException.class)
    public void listIterator_setWithoutNextOrPrevious()
    {
        FastList<String> list = FastList.newListWith("one", "two");
        ListIterator<String> iterator = list.listIterator();
        iterator.set("new"); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void listIterator_setAfterRemove()
    {
        FastList<String> list = FastList.newListWith("one", "two");
        ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.remove();
        iterator.set("new"); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void listIterator_setAfterAdd()
    {
        FastList<String> list = FastList.newListWith("one", "two");
        ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.add("new");
        iterator.set("newer"); // Should throw IllegalStateException
    }
}
