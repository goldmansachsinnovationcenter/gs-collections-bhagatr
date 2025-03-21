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

import com.gs.collections.api.block.function.primitive.ByteFunction;
import com.gs.collections.api.collection.primitive.MutableByteCollection;
import com.gs.collections.impl.list.mutable.primitive.ByteArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectByteProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        ByteFunction<String> mockByteFunction = mock(ByteFunction.class);
        MutableByteCollection targetCollection = new ByteArrayList();
        CollectByteProcedure<String> procedure = new CollectByteProcedure<>(mockByteFunction, targetCollection);

        // Expect
        when(mockByteFunction.byteValueOf("test")).thenReturn((byte) 42);

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains((byte) 42));
        verify(mockByteFunction).byteValueOf("test");
    }

    @Test
    public void testGetByteCollection()
    {
        // Arrange
        ByteFunction<String> mockByteFunction = mock(ByteFunction.class);
        MutableByteCollection targetCollection = new ByteArrayList();
        CollectByteProcedure<String> procedure = new CollectByteProcedure<>(mockByteFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getByteCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        ByteFunction<String> mockByteFunction = mock(ByteFunction.class);
        MutableByteCollection targetCollection = new ByteArrayList();
        CollectByteProcedure<String> procedure = new CollectByteProcedure<>(mockByteFunction, targetCollection);

        // Expect
        when(mockByteFunction.byteValueOf("test1")).thenReturn((byte) 10);
        when(mockByteFunction.byteValueOf("test2")).thenReturn((byte) 20);
        when(mockByteFunction.byteValueOf("test3")).thenReturn((byte) 30);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains((byte) 10));
        assertTrue(targetCollection.contains((byte) 20));
        assertTrue(targetCollection.contains((byte) 30));
    }
    
    @Test
    public void testMinMaxValues()
    {
        // Arrange
        ByteFunction<String> mockByteFunction = mock(ByteFunction.class);
        MutableByteCollection targetCollection = new ByteArrayList();
        CollectByteProcedure<String> procedure = new CollectByteProcedure<>(mockByteFunction, targetCollection);

        // Expect
        when(mockByteFunction.byteValueOf("min")).thenReturn(Byte.MIN_VALUE);
        when(mockByteFunction.byteValueOf("max")).thenReturn(Byte.MAX_VALUE);

        // Act
        procedure.value("min");
        procedure.value("max");

        // Assert
        assertEquals(2, targetCollection.size());
        assertTrue(targetCollection.contains(Byte.MIN_VALUE));
        assertTrue(targetCollection.contains(Byte.MAX_VALUE));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        ByteFunction<String> mockByteFunction = mock(ByteFunction.class);
        MutableByteCollection targetCollection = new ByteArrayList();
        CollectByteProcedure<String> procedure = new CollectByteProcedure<>(mockByteFunction, targetCollection);

        // Expect
        when(mockByteFunction.byteValueOf(null)).thenReturn((byte) 0);

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains((byte) 0));
        verify(mockByteFunction).byteValueOf(null);
    }
}
