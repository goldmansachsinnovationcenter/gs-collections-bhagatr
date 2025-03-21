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

import org.junit.Test;

public class MutableBagExceptionTest
{
    @Test(expected = IllegalArgumentException.class)
    public void hashBag_addOccurrences_negativeOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("test", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void hashBag_removeOccurrences_negativeOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.removeOccurrences("test", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void hashBag_setOccurrences_negativeOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.setOccurrences("test", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void sortedBag_addOccurrences_negativeOccurrences()
    {
        TreeBag<String> bag = TreeBag.newBag();
        bag.addOccurrences("test", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void sortedBag_removeOccurrences_negativeOccurrences()
    {
        TreeBag<String> bag = TreeBag.newBag();
        bag.removeOccurrences("test", -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void sortedBag_setOccurrences_negativeOccurrences()
    {
        TreeBag<String> bag = TreeBag.newBag();
        bag.setOccurrences("test", -1);
    }
}
