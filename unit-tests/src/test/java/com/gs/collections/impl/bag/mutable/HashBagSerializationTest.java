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

import com.gs.collections.impl.test.SerializeTestHelper;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class HashBagSerializationTest
{
    @Test
    public void serialization_empty()
    {
        HashBag<String> bag = HashBag.newBag();
        HashBag<String> deserializedBag = SerializeTestHelper.serializeDeserialize(bag);
        
        Assert.assertEquals(0, deserializedBag.size());
        Assert.assertEquals(0, deserializedBag.sizeDistinct());
        Verify.assertEmpty(deserializedBag);
    }
    
    @Test
    public void serialization_singleElement()
    {
        HashBag<String> bag = HashBag.newBagWith("one");
        HashBag<String> deserializedBag = SerializeTestHelper.serializeDeserialize(bag);
        
        Assert.assertEquals(1, deserializedBag.size());
        Assert.assertEquals(1, deserializedBag.sizeDistinct());
        Assert.assertEquals(1, deserializedBag.occurrencesOf("one"));
    }
    
    @Test
    public void serialization_multipleElements()
    {
        HashBag<String> bag = HashBag.newBagWith("one", "two", "two", "three", "three", "three");
        HashBag<String> deserializedBag = SerializeTestHelper.serializeDeserialize(bag);
        
        Assert.assertEquals(6, deserializedBag.size());
        Assert.assertEquals(3, deserializedBag.sizeDistinct());
        Assert.assertEquals(1, deserializedBag.occurrencesOf("one"));
        Assert.assertEquals(2, deserializedBag.occurrencesOf("two"));
        Assert.assertEquals(3, deserializedBag.occurrencesOf("three"));
    }
    
    @Test
    public void serialization_withNullElement()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences(null, 5);
        HashBag<String> deserializedBag = SerializeTestHelper.serializeDeserialize(bag);
        
        Assert.assertEquals(5, deserializedBag.size());
        Assert.assertEquals(1, deserializedBag.sizeDistinct());
        Assert.assertEquals(5, deserializedBag.occurrencesOf(null));
    }
    
    @Test
    public void serialization_withMixedElements()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("one", 1);
        bag.addOccurrences("two", 2);
        bag.addOccurrences(null, 3);
        HashBag<String> deserializedBag = SerializeTestHelper.serializeDeserialize(bag);
        
        Assert.assertEquals(6, deserializedBag.size());
        Assert.assertEquals(3, deserializedBag.sizeDistinct());
        Assert.assertEquals(1, deserializedBag.occurrencesOf("one"));
        Assert.assertEquals(2, deserializedBag.occurrencesOf("two"));
        Assert.assertEquals(3, deserializedBag.occurrencesOf(null));
    }
    
    @Test
    public void serialization_largeNumberOfOccurrences()
    {
        HashBag<String> bag = HashBag.newBag();
        bag.addOccurrences("many", 1000);
        HashBag<String> deserializedBag = SerializeTestHelper.serializeDeserialize(bag);
        
        Assert.assertEquals(1000, deserializedBag.size());
        Assert.assertEquals(1, deserializedBag.sizeDistinct());
        Assert.assertEquals(1000, deserializedBag.occurrencesOf("many"));
    }
}
