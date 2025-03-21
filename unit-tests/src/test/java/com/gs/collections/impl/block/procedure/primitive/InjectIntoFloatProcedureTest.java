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

import com.gs.collections.api.block.function.primitive.FloatObjectToFloatFunction;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class InjectIntoFloatProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        AtomicReference<Float> result = new AtomicReference<>(0.0f);
        FloatObjectToFloatFunction<String> mockFunction = mock(FloatObjectToFloatFunction.class);
        InjectIntoFloatProcedure<String> procedure = new InjectIntoFloatProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.floatValueOf(0.0f, "test")).thenReturn(42.5f);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(42.5f, result.get(), 0.0f);
        verify(mockFunction).floatValueOf(0.0f, "test");
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        AtomicReference<Float> result = new AtomicReference<>(0.0f);
        FloatObjectToFloatFunction<String> mockFunction = mock(FloatObjectToFloatFunction.class);
        InjectIntoFloatProcedure<String> procedure = new InjectIntoFloatProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.floatValueOf(0.0f, "test1")).thenReturn(10.5f);
        when(mockFunction.floatValueOf(10.5f, "test2")).thenReturn(30.5f);
        when(mockFunction.floatValueOf(30.5f, "test3")).thenReturn(60.5f);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        Assert.assertEquals(60.5f, result.get(), 0.0f);
        verify(mockFunction).floatValueOf(0.0f, "test1");
        verify(mockFunction).floatValueOf(10.5f, "test2");
        verify(mockFunction).floatValueOf(30.5f, "test3");
    }
    
    @Test
    public void testWithNonZeroInitialValue()
    {
        // Arrange
        AtomicReference<Float> result = new AtomicReference<>(100.0f);
        FloatObjectToFloatFunction<String> mockFunction = mock(FloatObjectToFloatFunction.class);
        InjectIntoFloatProcedure<String> procedure = new InjectIntoFloatProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.floatValueOf(100.0f, "test")).thenReturn(142.5f);

        // Act
        procedure.value("test");

        // Assert
        Assert.assertEquals(142.5f, result.get(), 0.0f);
        verify(mockFunction).floatValueOf(100.0f, "test");
    }
    
    @Test
    public void testWithSpecialValues()
    {
        // Arrange
        AtomicReference<Float> result = new AtomicReference<>(0.0f);
        FloatObjectToFloatFunction<String> mockFunction = mock(FloatObjectToFloatFunction.class);
        InjectIntoFloatProcedure<String> procedure = new InjectIntoFloatProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.floatValueOf(0.0f, "NaN")).thenReturn(Float.NaN);

        // Act
        procedure.value("NaN");

        // Assert
        Assert.assertTrue(Float.isNaN(result.get()));
        verify(mockFunction).floatValueOf(0.0f, "NaN");
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        AtomicReference<Float> result = new AtomicReference<>(10.0f);
        FloatObjectToFloatFunction<String> mockFunction = mock(FloatObjectToFloatFunction.class);
        InjectIntoFloatProcedure<String> procedure = new InjectIntoFloatProcedure<>(result, mockFunction);

        // Expect
        when(mockFunction.floatValueOf(10.0f, null)).thenReturn(20.0f);

        // Act
        procedure.value(null);

        // Assert
        Assert.assertEquals(20.0f, result.get(), 0.0f);
        verify(mockFunction).floatValueOf(10.0f, null);
    }
}
