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

import com.gs.collections.impl.test.SerializeTestHelper;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class FastListSerializationTest
{
    @Test
    public void serialization_empty()
    {
        FastList<String> list = FastList.newList();
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(0, deserializedList.size());
        Verify.assertEmpty(deserializedList);
    }
    
    @Test
    public void serialization_singleElement()
    {
        FastList<String> list = FastList.newListWith("one");
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(1, deserializedList.size());
        Assert.assertEquals("one", deserializedList.get(0));
    }
    
    @Test
    public void serialization_multipleElements()
    {
        FastList<String> list = FastList.newListWith("one", "two", "three");
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(3, deserializedList.size());
        Assert.assertEquals("one", deserializedList.get(0));
        Assert.assertEquals("two", deserializedList.get(1));
        Assert.assertEquals("three", deserializedList.get(2));
    }
    
    @Test
    public void serialization_withNullElement()
    {
        FastList<String> list = FastList.newList();
        list.add(null);
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(1, deserializedList.size());
        Assert.assertNull(deserializedList.get(0));
    }
    
    @Test
    public void serialization_withMixedElements()
    {
        FastList<String> list = FastList.newList();
        list.add("one");
        list.add(null);
        list.add("three");
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(3, deserializedList.size());
        Assert.assertEquals("one", deserializedList.get(0));
        Assert.assertNull(deserializedList.get(1));
        Assert.assertEquals("three", deserializedList.get(2));
    }
    
    @Test
    public void serialization_largeList()
    {
        FastList<String> list = FastList.newList();
        for (int i = 0; i < 1000; i++)
        {
            list.add("item" + i);
        }
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(1000, deserializedList.size());
        for (int i = 0; i < 1000; i++)
        {
            Assert.assertEquals("item" + i, deserializedList.get(i));
        }
    }
    
    @Test
    public void serialization_withInitialCapacity()
    {
        FastList<String> list = FastList.newList(100);
        list.add("one");
        list.add("two");
        FastList<String> deserializedList = SerializeTestHelper.serializeDeserialize(list);
        
        Assert.assertEquals(2, deserializedList.size());
        Assert.assertEquals("one", deserializedList.get(0));
        Assert.assertEquals("two", deserializedList.get(1));
    }
}
