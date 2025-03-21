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

package com.gs.collections.impl.block.procedure.primitive;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class IntObjectProcedureTest
{
    @Test
    public void valueMethod()
    {
        // Arrange
        List<Integer> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        IntObjectProcedure<String> procedure = new IntObjectProcedure<String>()
        {
            @Override
            public void value(int key, String value)
            {
                keys.add(key);
                values.add(value);
            }
        };

        // Act
        procedure.value(1, "one");
        procedure.value(2, "two");
        procedure.value(3, "three");

        // Assert
        Assert.assertEquals(3, keys.size());
        Assert.assertEquals(Integer.valueOf(1), keys.get(0));
        Assert.assertEquals(Integer.valueOf(2), keys.get(1));
        Assert.assertEquals(Integer.valueOf(3), keys.get(2));
        
        Assert.assertEquals(3, values.size());
        Assert.assertEquals("one", values.get(0));
        Assert.assertEquals("two", values.get(1));
        Assert.assertEquals("three", values.get(2));
    }

    @Test
    public void valueMethodWithMinMaxValues()
    {
        // Arrange
        List<Integer> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        IntObjectProcedure<String> procedure = new IntObjectProcedure<String>()
        {
            @Override
            public void value(int key, String value)
            {
                keys.add(key);
                values.add(value);
            }
        };

        // Act
        procedure.value(Integer.MIN_VALUE, "min");
        procedure.value(Integer.MAX_VALUE, "max");
        procedure.value(0, "zero");

        // Assert
        Assert.assertEquals(3, keys.size());
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), keys.get(0));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), keys.get(1));
        Assert.assertEquals(Integer.valueOf(0), keys.get(2));
        
        Assert.assertEquals(3, values.size());
        Assert.assertEquals("min", values.get(0));
        Assert.assertEquals("max", values.get(1));
        Assert.assertEquals("zero", values.get(2));
    }
    
    @Test
    public void valueMethodWithNullValue()
    {
        // Arrange
        List<Integer> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        IntObjectProcedure<String> procedure = new IntObjectProcedure<String>()
        {
            @Override
            public void value(int key, String value)
            {
                keys.add(key);
                values.add(value);
            }
        };

        // Act
        procedure.value(1, null);

        // Assert
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(Integer.valueOf(1), keys.get(0));
        
        Assert.assertEquals(1, values.size());
        Assert.assertNull(values.get(0));
    }
    
    @Test
    public void anonymousClassImplementation()
    {
        // Arrange
        final StringBuilder sb = new StringBuilder();
        
        // Act
        IntObjectProcedure<String> appendProcedure = new IntObjectProcedure<String>()
        {
            @Override
            public void value(int key, String value)
            {
                sb.append(key).append(":").append(value).append(", ");
            }
        };
        
        appendProcedure.value(1, "one");
        appendProcedure.value(2, "two");
        appendProcedure.value(3, "three");
        
        // Assert
        Assert.assertEquals("1:one, 2:two, 3:three, ", sb.toString());
    }
}
