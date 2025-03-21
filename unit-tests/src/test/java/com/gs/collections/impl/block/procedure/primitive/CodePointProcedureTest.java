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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CodePointProcedureTest
{
    @Test
    public void valueMethod()
    {
        // Arrange
        List<Integer> values = new ArrayList<>();
        CodePointProcedure procedure = new CodePointProcedure()
        {
            @Override
            public void value(int each)
            {
                values.add(each);
            }
        };

        // Act
        procedure.value(65);    // 'A'
        procedure.value(66);    // 'B'
        procedure.value(67);    // 'C'

        // Assert
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Integer.valueOf(65), values.get(0));
        Assert.assertEquals(Integer.valueOf(66), values.get(1));
        Assert.assertEquals(Integer.valueOf(67), values.get(2));
    }

    @Test
    public void valueMethodWithUnicodeCodePoints()
    {
        // Arrange
        List<Integer> values = new ArrayList<>();
        CodePointProcedure procedure = new CodePointProcedure()
        {
            @Override
            public void value(int each)
            {
                values.add(each);
            }
        };

        // Act
        // Basic Latin, Supplementary Multilingual Plane, and Supplementary Ideographic Plane code points
        procedure.value(0x0041);    // LATIN CAPITAL LETTER A
        procedure.value(0x1F600);   // GRINNING FACE (emoji)
        procedure.value(0x20000);   // CJK UNIFIED IDEOGRAPH-20000

        // Assert
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Integer.valueOf(0x0041), values.get(0));
        Assert.assertEquals(Integer.valueOf(0x1F600), values.get(1));
        Assert.assertEquals(Integer.valueOf(0x20000), values.get(2));
    }
    
    @Test
    public void anonymousClassImplementation()
    {
        // Arrange
        final StringBuilder sb = new StringBuilder();
        
        // Act
        CodePointProcedure appendProcedure = new CodePointProcedure()
        {
            @Override
            public void value(int each)
            {
                sb.appendCodePoint(each);
            }
        };
        
        // "Hello" in code points
        appendProcedure.value(72);  // H
        appendProcedure.value(101); // e
        appendProcedure.value(108); // l
        appendProcedure.value(108); // l
        appendProcedure.value(111); // o
        
        // Assert
        Assert.assertEquals("Hello", sb.toString());
    }
}
