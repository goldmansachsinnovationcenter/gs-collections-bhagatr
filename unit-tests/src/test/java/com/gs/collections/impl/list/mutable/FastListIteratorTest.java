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

import org.junit.Test;

public class FastListIteratorTest
{
    @Test(expected = IllegalStateException.class)
    public void iterator_removeWithoutNext()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        Iterator<String> iterator = list.iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_removeCalledTwice()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove(); // First remove is valid
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iterator_nextBeyondEnd()
    {
        FastList<String> list = FastList.newListWith("1");
        Iterator<String> iterator = list.iterator();
        iterator.next(); // Valid
        iterator.next(); // Should throw NoSuchElementException
    }
    
    @Test(expected = IllegalStateException.class)
    public void listIterator_removeWithoutNextOrPrevious()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        ListIterator<String> iterator = list.listIterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void listIterator_setWithoutNextOrPrevious()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        ListIterator<String> iterator = list.listIterator();
        iterator.set("value"); // Should throw IllegalStateException
    }
    
    @Test(expected = NoSuchElementException.class)
    public void listIterator_previousAtStart()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        ListIterator<String> iterator = list.listIterator();
        iterator.previous(); // Should throw NoSuchElementException
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void listIterator_invalidIndex_negative()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        list.listIterator(-1); // Should throw IndexOutOfBoundsException
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void listIterator_invalidIndex_tooLarge()
    {
        FastList<String> list = FastList.newListWith("1", "2", "3");
        list.listIterator(4); // Should throw IndexOutOfBoundsException
    }
}
