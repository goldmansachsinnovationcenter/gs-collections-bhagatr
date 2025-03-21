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

import com.gs.collections.api.block.function.primitive.ShortFunction;
import com.gs.collections.api.collection.primitive.MutableShortCollection;
import com.gs.collections.impl.list.mutable.primitive.ShortArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectShortProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        ShortFunction<String> mockShortFunction = mock(ShortFunction.class);
        MutableShortCollection targetCollection = new ShortArrayList();
        CollectShortProcedure<String> procedure = new CollectShortProcedure<>(mockShortFunction, targetCollection);

        // Expect
        when(mockShortFunction.shortValueOf("test")).thenReturn((short) 42);

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains((short) 42));
        verify(mockShortFunction).shortValueOf("test");
    }

    @Test
    public void testGetShortCollection()
    {
        // Arrange
        ShortFunction<String> mockShortFunction = mock(ShortFunction.class);
        MutableShortCollection targetCollection = new ShortArrayList();
        CollectShortProcedure<String> procedure = new CollectShortProcedure<>(mockShortFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getShortCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        ShortFunction<String> mockShortFunction = mock(ShortFunction.class);
        MutableShortCollection targetCollection = new ShortArrayList();
        CollectShortProcedure<String> procedure = new CollectShortProcedure<>(mockShortFunction, targetCollection);

        // Expect
        when(mockShortFunction.shortValueOf("test1")).thenReturn((short) 10);
        when(mockShortFunction.shortValueOf("test2")).thenReturn((short) 20);
        when(mockShortFunction.shortValueOf("test3")).thenReturn((short) 30);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains((short) 10));
        assertTrue(targetCollection.contains((short) 20));
        assertTrue(targetCollection.contains((short) 30));
    }
    
    @Test
    public void testMinMaxValues()
    {
        // Arrange
        ShortFunction<String> mockShortFunction = mock(ShortFunction.class);
        MutableShortCollection targetCollection = new ShortArrayList();
        CollectShortProcedure<String> procedure = new CollectShortProcedure<>(mockShortFunction, targetCollection);

        // Expect
        when(mockShortFunction.shortValueOf("min")).thenReturn(Short.MIN_VALUE);
        when(mockShortFunction.shortValueOf("max")).thenReturn(Short.MAX_VALUE);

        // Act
        procedure.value("min");
        procedure.value("max");

        // Assert
        assertEquals(2, targetCollection.size());
        assertTrue(targetCollection.contains(Short.MIN_VALUE));
        assertTrue(targetCollection.contains(Short.MAX_VALUE));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        ShortFunction<String> mockShortFunction = mock(ShortFunction.class);
        MutableShortCollection targetCollection = new ShortArrayList();
        CollectShortProcedure<String> procedure = new CollectShortProcedure<>(mockShortFunction, targetCollection);

        // Expect
        when(mockShortFunction.shortValueOf(null)).thenReturn((short) 0);

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains((short) 0));
        verify(mockShortFunction).shortValueOf(null);
    }
}
