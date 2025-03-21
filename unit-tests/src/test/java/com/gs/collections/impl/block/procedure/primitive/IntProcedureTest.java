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

public class IntProcedureTest
{
    @Test
    public void valueMethod()
    {
        // Arrange
        List<Integer> values = new ArrayList<>();
        IntProcedure procedure = new IntProcedure()
        {
            @Override
            public void value(int each)
            {
                values.add(each);
            }
        };

        // Act
        procedure.value(42);
        procedure.value(99);
        procedure.value(-5);

        // Assert
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Integer.valueOf(42), values.get(0));
        Assert.assertEquals(Integer.valueOf(99), values.get(1));
        Assert.assertEquals(Integer.valueOf(-5), values.get(2));
    }

    @Test
    public void valueMethodWithMinMaxValues()
    {
        // Arrange
        List<Integer> values = new ArrayList<>();
        IntProcedure procedure = new IntProcedure()
        {
            @Override
            public void value(int each)
            {
                values.add(each);
            }
        };

        // Act
        procedure.value(Integer.MIN_VALUE);
        procedure.value(Integer.MAX_VALUE);
        procedure.value(0);

        // Assert
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), values.get(0));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), values.get(1));
        Assert.assertEquals(Integer.valueOf(0), values.get(2));
    }
    
    @Test
    public void anonymousClassImplementation()
    {
        // Arrange
        final int[] sum = {0};
        
        // Act
        IntProcedure sumProcedure = new IntProcedure()
        {
            @Override
            public void value(int each)
            {
                sum[0] += each;
            }
        };
        
        sumProcedure.value(10);
        sumProcedure.value(20);
        sumProcedure.value(30);
        
        // Assert
        Assert.assertEquals(60, sum[0]);
    }
}
