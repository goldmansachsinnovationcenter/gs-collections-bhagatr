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

import com.gs.collections.api.block.function.primitive.DoubleFunction;
import com.gs.collections.api.collection.primitive.MutableDoubleCollection;
import com.gs.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectDoubleProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        DoubleFunction<String> mockDoubleFunction = mock(DoubleFunction.class);
        MutableDoubleCollection targetCollection = new DoubleArrayList();
        CollectDoubleProcedure<String> procedure = new CollectDoubleProcedure<>(mockDoubleFunction, targetCollection);

        // Expect
        when(mockDoubleFunction.doubleValueOf("test")).thenReturn(42.5);

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(42.5));
        verify(mockDoubleFunction).doubleValueOf("test");
    }

    @Test
    public void testGetDoubleCollection()
    {
        // Arrange
        DoubleFunction<String> mockDoubleFunction = mock(DoubleFunction.class);
        MutableDoubleCollection targetCollection = new DoubleArrayList();
        CollectDoubleProcedure<String> procedure = new CollectDoubleProcedure<>(mockDoubleFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getDoubleCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        DoubleFunction<String> mockDoubleFunction = mock(DoubleFunction.class);
        MutableDoubleCollection targetCollection = new DoubleArrayList();
        CollectDoubleProcedure<String> procedure = new CollectDoubleProcedure<>(mockDoubleFunction, targetCollection);

        // Expect
        when(mockDoubleFunction.doubleValueOf("test1")).thenReturn(10.5);
        when(mockDoubleFunction.doubleValueOf("test2")).thenReturn(20.5);
        when(mockDoubleFunction.doubleValueOf("test3")).thenReturn(30.5);

        // Act
        procedure.value("test1");
        procedure.value("test2");
        procedure.value("test3");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains(10.5));
        assertTrue(targetCollection.contains(20.5));
        assertTrue(targetCollection.contains(30.5));
    }
    
    @Test
    public void testSpecialValues()
    {
        // Arrange
        DoubleFunction<String> mockDoubleFunction = mock(DoubleFunction.class);
        MutableDoubleCollection targetCollection = new DoubleArrayList();
        CollectDoubleProcedure<String> procedure = new CollectDoubleProcedure<>(mockDoubleFunction, targetCollection);

        // Expect
        when(mockDoubleFunction.doubleValueOf("NaN")).thenReturn(Double.NaN);
        when(mockDoubleFunction.doubleValueOf("POSITIVE_INFINITY")).thenReturn(Double.POSITIVE_INFINITY);
        when(mockDoubleFunction.doubleValueOf("NEGATIVE_INFINITY")).thenReturn(Double.NEGATIVE_INFINITY);
        when(mockDoubleFunction.doubleValueOf("MIN_VALUE")).thenReturn(Double.MIN_VALUE);
        when(mockDoubleFunction.doubleValueOf("MAX_VALUE")).thenReturn(Double.MAX_VALUE);

        // Act
        procedure.value("NaN");
        procedure.value("POSITIVE_INFINITY");
        procedure.value("NEGATIVE_INFINITY");
        procedure.value("MIN_VALUE");
        procedure.value("MAX_VALUE");

        // Assert
        assertEquals(5, targetCollection.size());
        // Note: Cannot use contains() for NaN as it uses equals which always returns false for NaN
        assertTrue(targetCollection.anySatisfy(d -> Double.isNaN(d)));
        assertTrue(targetCollection.contains(Double.POSITIVE_INFINITY));
        assertTrue(targetCollection.contains(Double.NEGATIVE_INFINITY));
        assertTrue(targetCollection.contains(Double.MIN_VALUE));
        assertTrue(targetCollection.contains(Double.MAX_VALUE));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        DoubleFunction<String> mockDoubleFunction = mock(DoubleFunction.class);
        MutableDoubleCollection targetCollection = new DoubleArrayList();
        CollectDoubleProcedure<String> procedure = new CollectDoubleProcedure<>(mockDoubleFunction, targetCollection);

        // Expect
        when(mockDoubleFunction.doubleValueOf(null)).thenReturn(0.0);

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(0.0));
        verify(mockDoubleFunction).doubleValueOf(null);
    }
}
