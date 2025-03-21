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

import com.gs.collections.api.block.function.primitive.FloatFunction;
import com.gs.collections.api.collection.primitive.MutableFloatCollection;
import com.gs.collections.impl.list.mutable.primitive.FloatArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectFloatProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        FloatFunction<String> mockFloatFunction = mock(FloatFunction.class);
        MutableFloatCollection targetCollection = new FloatArrayList();
        CollectFloatProcedure<String> procedure = new CollectFloatProcedure<>(mockFloatFunction, targetCollection);

        // Expect
        when(mockFloatFunction.floatValueOf("test")).thenReturn(42.5f);

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(42.5f));
        verify(mockFloatFunction).floatValueOf("test");
    }

    @Test
    public void testGetFloatCollection()
    {
        // Arrange
        FloatFunction<String> mockFloatFunction = mock(FloatFunction.class);
        MutableFloatCollection targetCollection = new FloatArrayList();
        CollectFloatProcedure<String> procedure = new CollectFloatProcedure<>(mockFloatFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getFloatCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        FloatFunction<String> mockFloatFunction = mock(FloatFunction.class);
        MutableFloatCollection targetCollection = new FloatArrayList();
        CollectFloatProcedure<String> procedure = new CollectFloatProcedure<>(mockFloatFunction, targetCollection);

        // Expect
        when(mockFloatFunction.floatValueOf("test1")).thenReturn(10.5f);
        when(mockFloatFunction.floatValueOf("test2")).thenReturn(20.5f);
        when(mockFloatFunction.floatValueOf("test3")).thenReturn(30.5f);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains(10.5f));
        assertTrue(targetCollection.contains(20.5f));
        assertTrue(targetCollection.contains(30.5f));
    }
    
    @Test
    public void testSpecialValues()
    {
        // Arrange
        FloatFunction<String> mockFloatFunction = mock(FloatFunction.class);
        MutableFloatCollection targetCollection = new FloatArrayList();
        CollectFloatProcedure<String> procedure = new CollectFloatProcedure<>(mockFloatFunction, targetCollection);

        // Expect
        when(mockFloatFunction.floatValueOf("NaN")).thenReturn(Float.NaN);
        when(mockFloatFunction.floatValueOf("POSITIVE_INFINITY")).thenReturn(Float.POSITIVE_INFINITY);
        when(mockFloatFunction.floatValueOf("NEGATIVE_INFINITY")).thenReturn(Float.NEGATIVE_INFINITY);
        when(mockFloatFunction.floatValueOf("MIN_VALUE")).thenReturn(Float.MIN_VALUE);
        when(mockFloatFunction.floatValueOf("MAX_VALUE")).thenReturn(Float.MAX_VALUE);

        // Act
        procedure.value("NaN");
        procedure.value("POSITIVE_INFINITY");
        procedure.value("NEGATIVE_INFINITY");
        procedure.value("MIN_VALUE");
        procedure.value("MAX_VALUE");

        // Assert
        assertEquals(5, targetCollection.size());
        // Note: Cannot use contains() for NaN as it uses equals which always returns false for NaN
        assertTrue(targetCollection.anySatisfy(f -> Float.isNaN(f)));
        assertTrue(targetCollection.contains(Float.POSITIVE_INFINITY));
        assertTrue(targetCollection.contains(Float.NEGATIVE_INFINITY));
        assertTrue(targetCollection.contains(Float.MIN_VALUE));
        assertTrue(targetCollection.contains(Float.MAX_VALUE));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        FloatFunction<String> mockFloatFunction = mock(FloatFunction.class);
        MutableFloatCollection targetCollection = new FloatArrayList();
        CollectFloatProcedure<String> procedure = new CollectFloatProcedure<>(mockFloatFunction, targetCollection);

        // Expect
        when(mockFloatFunction.floatValueOf(null)).thenReturn(0.0f);

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(0.0f));
        verify(mockFloatFunction).floatValueOf(null);
    }
}
