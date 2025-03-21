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

import java.util.concurrent.atomic.AtomicReference;

import com.gs.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class InjectIntoDoubleProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        AtomicReference<Double> result = new AtomicReference<>(0.0);
        DoubleObjectToDoubleFunction<String> mockFunction = mock(DoubleObjectToDoubleFunction.class);
        InjectIntoDoubleProcedure<String> procedure = new InjectIntoDoubleProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.doubleValueOf(0.0, "test")).thenReturn(42.5);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(42.5, result.get(), 0.0);
        verify(mockFunction).doubleValueOf(0.0, "test");
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        AtomicReference<Double> result = new AtomicReference<>(0.0);
        DoubleObjectToDoubleFunction<String> mockFunction = mock(DoubleObjectToDoubleFunction.class);
        InjectIntoDoubleProcedure<String> procedure = new InjectIntoDoubleProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.doubleValueOf(0.0, "test1")).thenReturn(10.5);
        when(mockFunction.doubleValueOf(10.5, "test2")).thenReturn(30.5);
        when(mockFunction.doubleValueOf(30.5, "test3")).thenReturn(60.5);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        Assert.assertEquals(60.5, result.get(), 0.0);
        verify(mockFunction).doubleValueOf(0.0, "test1");
        verify(mockFunction).doubleValueOf(10.5, "test2");
        verify(mockFunction).doubleValueOf(30.5, "test3");
    }
    
    @Test
    public void testWithNonZeroInitialValue()
    {
        // Arrange
        AtomicReference<Double> result = new AtomicReference<>(100.0);
        DoubleObjectToDoubleFunction<String> mockFunction = mock(DoubleObjectToDoubleFunction.class);
        InjectIntoDoubleProcedure<String> procedure = new InjectIntoDoubleProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.doubleValueOf(100.0, "test")).thenReturn(142.5);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(142.5, result.get(), 0.0);
        verify(mockFunction).doubleValueOf(100.0, "test");
    }
    
    @Test
    public void testWithSpecialValues()
    {
        // Arrange
        AtomicReference<Double> result = new AtomicReference<>(0.0);
        DoubleObjectToDoubleFunction<String> mockFunction = mock(DoubleObjectToDoubleFunction.class);
        InjectIntoDoubleProcedure<String> procedure = new InjectIntoDoubleProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.doubleValueOf(0.0, "NaN")).thenReturn(Double.NaN);

        // Act
        procedure.value("NaN");

        // Assert
        Assert.assertTrue(Double.isNaN(result.get()));
        verify(mockFunction).doubleValueOf(0.0, "NaN");
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        AtomicReference<Double> result = new AtomicReference<>(10.0);
        DoubleObjectToDoubleFunction<String> mockFunction = mock(DoubleObjectToDoubleFunction.class);
        InjectIntoDoubleProcedure<String> procedure = new InjectIntoDoubleProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.doubleValueOf(10.0, null)).thenReturn(20.0);

        // Act
        procedure.value(null);

        // Assert
        Assert.assertEquals(20.0, result.get(), 0.0);
        verify(mockFunction).doubleValueOf(10.0, null);
    }
}
