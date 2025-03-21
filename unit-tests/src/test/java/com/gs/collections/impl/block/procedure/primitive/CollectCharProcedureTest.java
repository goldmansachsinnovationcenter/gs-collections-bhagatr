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

import com.gs.collections.api.block.function.primitive.CharFunction;
import com.gs.collections.api.collection.primitive.MutableCharCollection;
import com.gs.collections.impl.list.mutable.primitive.CharArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectCharProcedureTest
{
    @Test
    public void testValueMethod()
    {
        // Arrange
        CharFunction<String> mockCharFunction = mock(CharFunction.class);
        MutableCharCollection targetCollection = new CharArrayList();
        CollectCharProcedure<String> procedure = new CollectCharProcedure<>(mockCharFunction, targetCollection);

        // Expect
        when(mockCharFunction.charValueOf("test")).thenReturn('A');

        // Act
        procedure.value("test");

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains('A'));
        verify(mockCharFunction).charValueOf("test");
    }

    @Test
    public void testGetCharCollection()
    {
        // Arrange
        CharFunction<String> mockCharFunction = mock(CharFunction.class);
        MutableCharCollection targetCollection = new CharArrayList();
        CollectCharProcedure<String> procedure = new CollectCharProcedure<>(mockCharFunction, targetCollection);

        // Act & Assert
        assertSame(targetCollection, procedure.getCharCollection());
    }

    @Test
    public void testMultipleValues()
    {
        // Arrange
        CharFunction<String> mockCharFunction = mock(CharFunction.class);
        MutableCharCollection targetCollection = new CharArrayList();
        CollectCharProcedure<String> procedure = new CollectCharProcedure<>(mockCharFunction, targetCollection);

        // Expect
        when(mockCharFunction.charValueOf("A")).thenReturn('A');
        when(mockCharFunction.charValueOf("B")).thenReturn('B');
        when(mockCharFunction.charValueOf("C")).thenReturn('C');

        // Act
        procedure.value("A");
        procedure.value("B");
        procedure.value("C");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains('A'));
        assertTrue(targetCollection.contains('B'));
        assertTrue(targetCollection.contains('C'));
    }
    
    @Test
    public void testSpecialCharacters()
    {
        // Arrange
        CharFunction<String> mockCharFunction = mock(CharFunction.class);
        MutableCharCollection targetCollection = new CharArrayList();
        CollectCharProcedure<String> procedure = new CollectCharProcedure<>(mockCharFunction, targetCollection);

        // Expect
        when(mockCharFunction.charValueOf("space")).thenReturn(' ');
        when(mockCharFunction.charValueOf("tab")).thenReturn('\t');
        when(mockCharFunction.charValueOf("newline")).thenReturn('\n');

        // Act
        procedure.value("space");
        procedure.value("tab");
        procedure.value("newline");

        // Assert
        assertEquals(3, targetCollection.size());
        assertTrue(targetCollection.contains(' '));
        assertTrue(targetCollection.contains('\t'));
        assertTrue(targetCollection.contains('\n'));
    }
    
    @Test
    public void testWithNullInput()
    {
        // Arrange
        CharFunction<String> mockCharFunction = mock(CharFunction.class);
        MutableCharCollection targetCollection = new CharArrayList();
        CollectCharProcedure<String> procedure = new CollectCharProcedure<>(mockCharFunction, targetCollection);

        // Expect
        when(mockCharFunction.charValueOf(null)).thenReturn('\0');

        // Act
        procedure.value(null);

        // Assert
        assertEquals(1, targetCollection.size());
        assertTrue(targetCollection.contains('\0'));
        verify(mockCharFunction).charValueOf(null);
    }
}
