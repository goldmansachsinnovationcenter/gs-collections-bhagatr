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

package com.gs.collections.impl.block.predicate.checked;

import org.junit.Assert;
import org.junit.Test;

public class ThrowingPredicateTest
{
    @Test
    public void implementationWithoutException() throws Exception
    {
        ThrowingPredicate<String> predicate = new ThrowingPredicate<String>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String object) throws Exception
            {
                return object != null && object.length() > 3;
            }
        };
        
        Assert.assertTrue(predicate.safeAccept("test"));
        Assert.assertFalse(predicate.safeAccept("abc"));
        Assert.assertFalse(predicate.safeAccept(null));
    }
    
    @Test(expected = NullPointerException.class)
    public void implementationWithRuntimeException() throws Exception
    {
        ThrowingPredicate<String> predicate = new ThrowingPredicate<String>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String object) throws Exception
            {
                if (object == null)
                {
                    throw new NullPointerException("Object cannot be null");
                }
                return object.length() > 3;
            }
        };
        
        Assert.assertTrue(predicate.safeAccept("test"));
        Assert.assertFalse(predicate.safeAccept("abc"));
        predicate.safeAccept(null); // Should throw NullPointerException
    }
    
    @Test(expected = Exception.class)
    public void implementationWithCheckedException() throws Exception
    {
        ThrowingPredicate<String> predicate = new ThrowingPredicate<String>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String object) throws Exception
            {
                if (object == null)
                {
                    throw new Exception("Object cannot be null");
                }
                return object.length() > 3;
            }
        };
        
        Assert.assertTrue(predicate.safeAccept("test"));
        Assert.assertFalse(predicate.safeAccept("abc"));
        predicate.safeAccept(null); // Should throw Exception
    }
}
