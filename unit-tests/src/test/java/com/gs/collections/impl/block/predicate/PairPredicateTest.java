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

import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.test.Verify;
import com.gs.collections.impl.tuple.Tuples;
import org.junit.Assert;
import org.junit.Test;

public class PairPredicateTest
{
    @Test
    public void acceptWithPair()
    {
        PairPredicate<String, Integer> predicate = new PairPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String one, Integer two)
            {
                return one.length() > 3 && two > 10;
            }
        };
        
        Pair<String, Integer> pair1 = Tuples.pair("test", 20);
        Pair<String, Integer> pair2 = Tuples.pair("abc", 20);
        Pair<String, Integer> pair3 = Tuples.pair("test", 5);
        Pair<String, Integer> pair4 = Tuples.pair("abc", 5);
        
        Assert.assertTrue(predicate.accept(pair1));
        Assert.assertFalse(predicate.accept(pair2));
        Assert.assertFalse(predicate.accept(pair3));
        Assert.assertFalse(predicate.accept(pair4));
    }
    
    @Test
    public void acceptWithOneTwo()
    {
        PairPredicate<String, Integer> predicate = new PairPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String one, Integer two)
            {
                return one.length() > 3 && two > 10;
            }
        };
        
        Assert.assertTrue(predicate.accept("test", 20));
        Assert.assertFalse(predicate.accept("abc", 20));
        Assert.assertFalse(predicate.accept("test", 5));
        Assert.assertFalse(predicate.accept("abc", 5));
    }
    
    @Test
    public void acceptWithNullOne()
    {
        PairPredicate<String, Integer> predicate = new PairPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String one, Integer two)
            {
                return one != null && one.length() > 3 && two > 10;
            }
        };
        
        Pair<String, Integer> pair = Tuples.pair(null, 20);
        Assert.assertFalse(predicate.accept(pair));
        Assert.assertFalse(predicate.accept(null, 20));
    }
    
    @Test
    public void acceptWithNullTwo()
    {
        PairPredicate<String, Integer> predicate = new PairPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String one, Integer two)
            {
                return one.length() > 3 && two != null && two > 10;
            }
        };
        
        Pair<String, Integer> pair = Tuples.pair("test", null);
        
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept(pair));
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept("test", null));
    }
    
    @Test
    public void serialization()
    {
        PairPredicate<String, Integer> predicate = new PairPredicate<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(String one, Integer two)
            {
                return one.length() > 3 && two > 10;
            }
        };
        
        Verify.assertSerializedForm(predicate);
    }
}
