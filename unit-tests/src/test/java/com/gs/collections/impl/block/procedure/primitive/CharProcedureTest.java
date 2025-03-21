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

public class CharProcedureTest
{
    @Test
    public void valueMethod()
    {
        // Arrange
        List<Character> values = new ArrayList<>();
        CharProcedure procedure = new CharProcedure()
        {
            @Override
            public void value(char each)
            {
                values.add(each);
            }
        };

        // Act
        procedure.value('A');
        procedure.value('B');
        procedure.value('C');

        // Assert
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Character.valueOf('A'), values.get(0));
        Assert.assertEquals(Character.valueOf('B'), values.get(1));
        Assert.assertEquals(Character.valueOf('C'), values.get(2));
    }

    @Test
    public void valueMethodWithSpecialCharacters()
    {
        // Arrange
        List<Character> values = new ArrayList<>();
        CharProcedure procedure = new CharProcedure()
        {
            @Override
            public void value(char each)
            {
                values.add(each);
            }
        };

        // Act
        procedure.value(' ');
        procedure.value('\t');
        procedure.value('\n');
        procedure.value('\0');

        // Assert
        Assert.assertEquals(4, values.size());
        Assert.assertEquals(Character.valueOf(' '), values.get(0));
        Assert.assertEquals(Character.valueOf('\t'), values.get(1));
        Assert.assertEquals(Character.valueOf('\n'), values.get(2));
        Assert.assertEquals(Character.valueOf('\0'), values.get(3));
    }
    
    @Test
    public void anonymousClassImplementation()
    {
        // Arrange
        final StringBuilder sb = new StringBuilder();
        
        // Act
        CharProcedure appendProcedure = new CharProcedure()
        {
            @Override
            public void value(char each)
            {
                sb.append(each);
            }
        };
        
        appendProcedure.value('H');
        appendProcedure.value('e');
        appendProcedure.value('l');
        appendProcedure.value('l');
        appendProcedure.value('o');
        
        // Assert
        Assert.assertEquals("Hello", sb.toString());
    }
}
