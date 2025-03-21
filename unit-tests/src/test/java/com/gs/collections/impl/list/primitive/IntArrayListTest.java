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

package com.gs.collections.impl.list.primitive;

import com.gs.collections.api.iterator.IntIterator;
import com.gs.collections.impl.test.Verify;
import org.junit.Test;

public class IntArrayListTest
{
    @Test(expected = IndexOutOfBoundsException.class)
    public void get_indexOutOfBounds_negative()
    {
        IntArrayList list = new IntArrayList();
        list.get(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void get_indexOutOfBounds_tooLarge()
    {
        IntArrayList list = new IntArrayList();
        list.get(0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void set_indexOutOfBounds_negative()
    {
        IntArrayList list = new IntArrayList();
        list.set(-1, 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void set_indexOutOfBounds_tooLarge()
    {
        IntArrayList list = new IntArrayList();
        list.set(0, 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndex_indexOutOfBounds_negative()
    {
        IntArrayList list = new IntArrayList();
        list.addAtIndex(-1, 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndex_indexOutOfBounds_tooLarge()
    {
        IntArrayList list = new IntArrayList();
        list.addAtIndex(1, 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndex_indexOutOfBounds_negative()
    {
        IntArrayList list = new IntArrayList();
        list.removeAtIndex(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndex_indexOutOfBounds_tooLarge()
    {
        IntArrayList list = new IntArrayList();
        list.removeAtIndex(0);
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_remove_withoutNext()
    {
        IntArrayList list = new IntArrayList();
        list.add(1);
        IntIterator iterator = list.intIterator();
        iterator.remove();
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_remove_calledTwice()
    {
        IntArrayList list = new IntArrayList();
        list.add(1);
        IntIterator iterator = list.intIterator();
        iterator.next();
        iterator.remove();
        iterator.remove();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void newList_negativeInitialCapacity()
    {
        new IntArrayList(-1);
    }
}
