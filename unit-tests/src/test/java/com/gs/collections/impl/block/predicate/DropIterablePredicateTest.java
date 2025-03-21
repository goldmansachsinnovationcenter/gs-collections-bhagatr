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

package com.gs.collections.impl.block.predicate;

import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class DropIterablePredicateTest
{
    @Test
    public void dropZero()
    {
        DropIterablePredicate<String> predicate = new DropIterablePredicate<String>(0);
        
        Assert.assertTrue(predicate.accept("first"));
        Assert.assertTrue(predicate.accept("second"));
        Assert.assertTrue(predicate.accept("third"));
    }
    
    @Test
    public void dropOne()
    {
        DropIterablePredicate<String> predicate = new DropIterablePredicate<String>(1);
        
        Assert.assertFalse(predicate.accept("first"));
        Assert.assertTrue(predicate.accept("second"));
        Assert.assertTrue(predicate.accept("third"));
    }
    
    @Test
    public void dropMultiple()
    {
        DropIterablePredicate<String> predicate = new DropIterablePredicate<String>(3);
        
        Assert.assertFalse(predicate.accept("first"));
        Assert.assertFalse(predicate.accept("second"));
        Assert.assertFalse(predicate.accept("third"));
        Assert.assertTrue(predicate.accept("fourth"));
        Assert.assertTrue(predicate.accept("fifth"));
    }
    
    @Test
    public void dropWithNegativeCount()
    {
        DropIterablePredicate<String> predicate = new DropIterablePredicate<String>(-1);
        
        Assert.assertTrue(predicate.accept("first"));
        Assert.assertTrue(predicate.accept("second"));
        Assert.assertTrue(predicate.accept("third"));
    }
    
    @Test
    public void serialization()
    {
        DropIterablePredicate<String> predicate = new DropIterablePredicate<String>(2);
        Verify.assertSerializedForm(predicate);
    }
    
    @Test
    public void stateIsPreservedBetweenCalls()
    {
        DropIterablePredicate<String> predicate = new DropIterablePredicate<String>(2);
        
        Assert.assertFalse(predicate.accept("first"));
        Assert.assertFalse(predicate.accept("second"));
        Assert.assertTrue(predicate.accept("third"));
        Assert.assertTrue(predicate.accept("fourth"));
        
        // Create a new predicate to reset state
        DropIterablePredicate<String> newPredicate = new DropIterablePredicate<String>(2);
        
        Assert.assertFalse(newPredicate.accept("first"));
        Assert.assertFalse(newPredicate.accept("second"));
        Assert.assertTrue(newPredicate.accept("third"));
    }
}
