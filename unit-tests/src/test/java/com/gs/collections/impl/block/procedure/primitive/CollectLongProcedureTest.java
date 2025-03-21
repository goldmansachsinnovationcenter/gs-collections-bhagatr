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

import com.gs.collections.api.block.function.primitive.LongFunction;
import com.gs.collections.api.collection.primitive.MutableLongCollection;
import com.gs.collections.impl.list.mutable.primitive.LongArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectLongProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        LongFunction<String> mockLongFunction = mock(LongFunction.class);
        MutableLongCollection targetCollection = new LongArrayList();
        CollectLongProcedure<String> procedure = new CollectLongProcedure<>(mockLongFunction, targetCollection);

        // Expect
        when(mockLongFunction.longValueOf("test")).thenReturn(42L);

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(42L));
        verify(mockLongFunction).longValueOf("test");
    }

    @Test
    public void testGetLongCollection()
    {
        // Arrange
        LongFunction<String> mockLongFunction = mock(LongFunction.class);
        MutableLongCollection targetCollection = new LongArrayList();
        CollectLongProcedure<String> procedure = new CollectLongProcedure<>(mockLongFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getLongCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        LongFunction<String> mockLongFunction = mock(LongFunction.class);
        MutableLongCollection targetCollection = new LongArrayList();
        CollectLongProcedure<String> procedure = new CollectLongProcedure<>(mockLongFunction, targetCollection);

        // Expect
        when(mockLongFunction.longValueOf("test1")).thenReturn(10L);
        when(mockLongFunction.longValueOf("test2")).thenReturn(20L);
        when(mockLongFunction.longValueOf("test3")).thenReturn(30L);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains(10L));
        assertTrue(targetCollection.contains(20L));
        assertTrue(targetCollection.contains(30L));
    }
    
    @Test
    public void testMinMaxValues()
    {
        // Arrange
        LongFunction<String> mockLongFunction = mock(LongFunction.class);
        MutableLongCollection targetCollection = new LongArrayList();
        CollectLongProcedure<String> procedure = new CollectLongProcedure<>(mockLongFunction, targetCollection);

        // Expect
        when(mockLongFunction.longValueOf("min")).thenReturn(Long.MIN_VALUE);
        when(mockLongFunction.longValueOf("max")).thenReturn(Long.MAX_VALUE);

        // Act
        procedure.value("min");
        procedure.value("max");

        // Assert
        assertEquals(2, targetCollection.size());
        assertTrue(targetCollection.contains(Long.MIN_VALUE));
        assertTrue(targetCollection.contains(Long.MAX_VALUE));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        LongFunction<String> mockLongFunction = mock(LongFunction.class);
        MutableLongCollection targetCollection = new LongArrayList();
        CollectLongProcedure<String> procedure = new CollectLongProcedure<>(mockLongFunction, targetCollection);

        // Expect
        when(mockLongFunction.longValueOf(null)).thenReturn(0L);

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(0L));
        verify(mockLongFunction).longValueOf(null);
    }
}
