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

import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.collection.primitive.MutableBooleanCollection;
import com.gs.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectBooleanProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        BooleanFunction<String> mockBooleanFunction = mock(BooleanFunction.class);
        MutableBooleanCollection targetCollection = new BooleanArrayList();
        CollectBooleanProcedure<String> procedure = new CollectBooleanProcedure<>(mockBooleanFunction, targetCollection);

        // Expect
        when(mockBooleanFunction.booleanValueOf("test")).thenReturn(true);

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(true));
        verify(mockBooleanFunction).booleanValueOf("test");
    }

    @Test
    public void testGetBooleanCollection()
    {
        // Arrange
        BooleanFunction<String> mockBooleanFunction = mock(BooleanFunction.class);
        MutableBooleanCollection targetCollection = new BooleanArrayList();
        CollectBooleanProcedure<String> procedure = new CollectBooleanProcedure<>(mockBooleanFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getBooleanCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        BooleanFunction<String> mockBooleanFunction = mock(BooleanFunction.class);
        MutableBooleanCollection targetCollection = new BooleanArrayList();
        CollectBooleanProcedure<String> procedure = new CollectBooleanProcedure<>(mockBooleanFunction, targetCollection);

        // Expect
        when(mockBooleanFunction.booleanValueOf("true")).thenReturn(true);
        when(mockBooleanFunction.booleanValueOf("false")).thenReturn(false);
        when(mockBooleanFunction.booleanValueOf("true2")).thenReturn(true);

        // Act
        procedure.value("true");
        procedure.value("false");
        procedure.value("true2");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains(true));
        assertTrue(targetCollection.contains(false));
        assertEquals(2, targetCollection.occurrencesOf(true));
        assertEquals(1, targetCollection.occurrencesOf(false));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        BooleanFunction<String> mockBooleanFunction = mock(BooleanFunction.class);
        MutableBooleanCollection targetCollection = new BooleanArrayList();
        CollectBooleanProcedure<String> procedure = new CollectBooleanProcedure<>(mockBooleanFunction, targetCollection);

        // Expect
        when(mockBooleanFunction.booleanValueOf(null)).thenReturn(false);

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains(false));
        verify(mockBooleanFunction).booleanValueOf(null);
    }
}
