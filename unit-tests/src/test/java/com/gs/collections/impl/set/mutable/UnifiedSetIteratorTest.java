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

package com.gs.collections.impl.set.mutable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class UnifiedSetIteratorTest
{
    @Test(expected = IllegalStateException.class)
    public void iterator_removeWithoutNext()
    {
        UnifiedSet<Integer> set = UnifiedSet.newSetWith(1, 2, 3);
        Iterator<Integer> iterator = set.iterator();
        iterator.remove(); // Should throw IllegalStateException
    }
    
    @Test(expected = IllegalStateException.class)
    public void iterator_removeCalledTwice()
    {
        UnifiedSet<Integer> set = UnifiedSet.newSetWith(1, 2, 3);
        Iterator<Integer> iterator = set.iterator();
        iterator.next();
        iterator.remove(); // First remove is valid
        iterator.remove(); // Second remove should throw IllegalStateException
    }
    
    @Test(expected = NoSuchElementException.class)
    public void iterator_nextBeyondEnd()
    {
        UnifiedSet<Integer> set = UnifiedSet.newSetWith(1);
        Iterator<Integer> iterator = set.iterator();
        iterator.next(); // Valid
        iterator.next(); // Should throw NoSuchElementException
    }
}
