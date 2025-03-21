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

public class IntIntProcedureTest
{
    @Test
    public void valueMethod()
    {
        // Arrange
        List<Integer> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        
        IntIntProcedure procedure = new IntIntProcedure()
        {
            @Override
            public void value(int key, int value)
            {
                keys.add(key);
                values.add(value);
            }
        };

        // Act
        procedure.value(1, 10);
        procedure.value(2, 20);
        procedure.value(3, 30);

        // Assert
        Assert.assertEquals(3, keys.size());
        Assert.assertEquals(Integer.valueOf(1), keys.get(0));
        Assert.assertEquals(Integer.valueOf(2), keys.get(1));
        Assert.assertEquals(Integer.valueOf(3), keys.get(2));
        
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Integer.valueOf(10), values.get(0));
        Assert.assertEquals(Integer.valueOf(20), values.get(1));
        Assert.assertEquals(Integer.valueOf(30), values.get(2));
    }

    @Test
    public void valueMethodWithMinMaxValues()
    {
        // Arrange
        List<Integer> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        
        IntIntProcedure procedure = new IntIntProcedure()
        {
            @Override
            public void value(int key, int value)
            {
                keys.add(key);
                values.add(value);
            }
        };

        // Act
        procedure.value(Integer.MIN_VALUE, Integer.MAX_VALUE);
        procedure.value(Integer.MAX_VALUE, Integer.MIN_VALUE);
        procedure.value(0, 0);

        // Assert
        Assert.assertEquals(3, keys.size());
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), keys.get(0));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), keys.get(1));
        Assert.assertEquals(Integer.valueOf(0), keys.get(2));
        
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), values.get(0));
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), values.get(1));
        Assert.assertEquals(Integer.valueOf(0), values.get(2));
    }
    
    @Test
    public void anonymousClassImplementation()
    {
        // Arrange
        final int[] keySum = {0};
        final int[] valueSum = {0};
        
        // Act
        IntIntProcedure sumProcedure = new IntIntProcedure()
        {
            @Override
            public void value(int key, int value)
            {
                keySum[0] += key;
                valueSum[0] += value;
            }
        };
        
        sumProcedure.value(10, 1);
        sumProcedure.value(20, 2);
        sumProcedure.value(30, 3);
        
        // Assert
        Assert.assertEquals(60, keySum[0]);
        Assert.assertEquals(6, valueSum[0]);
    }
}
