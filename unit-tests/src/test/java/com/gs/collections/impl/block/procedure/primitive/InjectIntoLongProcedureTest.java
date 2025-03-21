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

import java.util.concurrent.atomic.AtomicLong;

import com.gs.collections.api.block.function.primitive.LongObjectToLongFunction;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class InjectIntoLongProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        AtomicLong result = new AtomicLong(0);
        LongObjectToLongFunction<String> mockFunction = mock(LongObjectToLongFunction.class);
        InjectIntoLongProcedure<String> procedure = new InjectIntoLongProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.longValueOf(0L, "test")).thenReturn(42L);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(42L, result.get());
        verify(mockFunction).longValueOf(0L, "test");
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        AtomicLong result = new AtomicLong(0);
        LongObjectToLongFunction<String> mockFunction = mock(LongObjectToLongFunction.class);
        InjectIntoLongProcedure<String> procedure = new InjectIntoLongProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.longValueOf(0L, "test1")).thenReturn(10L);
        when(mockFunction.longValueOf(10L, "test2")).thenReturn(30L);
        when(mockFunction.longValueOf(30L, "test3")).thenReturn(60L);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        Assert.assertEquals(60L, result.get());
        verify(mockFunction).longValueOf(0L, "test1");
        verify(mockFunction).longValueOf(10L, "test2");
        verify(mockFunction).longValueOf(30L, "test3");
    }
    
    @Test
    public void testWithNonZeroInitialValue()
    {
        // Arrange
        AtomicLong result = new AtomicLong(100L);
        LongObjectToLongFunction<String> mockFunction = mock(LongObjectToLongFunction.class);
        InjectIntoLongProcedure<String> procedure = new InjectIntoLongProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.longValueOf(100L, "test")).thenReturn(142L);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(142L, result.get());
        verify(mockFunction).longValueOf(100L, "test");
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        AtomicLong result = new AtomicLong(10L);
        LongObjectToLongFunction<String> mockFunction = mock(LongObjectToLongFunction.class);
        InjectIntoLongProcedure<String> procedure = new InjectIntoLongProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.longValueOf(10L, null)).thenReturn(20L);

        // Act
        procedure.value(null);

        // Assert
        Assert.assertEquals(20L, result.get());
        verify(mockFunction).longValueOf(10L, null);
    }
}
