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

import java.util.concurrent.atomic.AtomicInteger;

import com.gs.collections.api.block.function.primitive.IntObjectToIntFunction;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class InjectIntoIntProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        AtomicInteger result = new AtomicInteger(0);
        IntObjectToIntFunction<String> mockFunction = mock(IntObjectToIntFunction.class);
        InjectIntoIntProcedure<String> procedure = new InjectIntoIntProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.intValueOf(0, "test")).thenReturn(42);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(42, result.get());
        verify(mockFunction).intValueOf(0, "test");
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        AtomicInteger result = new AtomicInteger(0);
        IntObjectToIntFunction<String> mockFunction = mock(IntObjectToIntFunction.class);
        InjectIntoIntProcedure<String> procedure = new InjectIntoIntProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.intValueOf(0, "test1")).thenReturn(10);
        when(mockFunction.intValueOf(10, "test2")).thenReturn(30);
        when(mockFunction.intValueOf(30, "test3")).thenReturn(60);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        Assert.assertEquals(60, result.get());
        verify(mockFunction).intValueOf(0, "test1");
        verify(mockFunction).intValueOf(10, "test2");
        verify(mockFunction).intValueOf(30, "test3");
    }
    
    @Test
    public void testWithNonZeroInitialValue()
    {
        // Arrange
        AtomicInteger result = new AtomicInteger(100);
        IntObjectToIntFunction<String> mockFunction = mock(IntObjectToIntFunction.class);
        InjectIntoIntProcedure<String> procedure = new InjectIntoIntProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.intValueOf(100, "test")).thenReturn(142);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(142, result.get());
        verify(mockFunction).intValueOf(100, "test");
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        AtomicInteger result = new AtomicInteger(10);
        IntObjectToIntFunction<String> mockFunction = mock(IntObjectToIntFunction.class);
        InjectIntoIntProcedure<String> procedure = new InjectIntoIntProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.intValueOf(10, null)).thenReturn(20);

        // Act
        procedure.value(null);

        // Assert
        Assert.assertEquals(20, result.get());
        verify(mockFunction).intValueOf(10, null);
    }
}
