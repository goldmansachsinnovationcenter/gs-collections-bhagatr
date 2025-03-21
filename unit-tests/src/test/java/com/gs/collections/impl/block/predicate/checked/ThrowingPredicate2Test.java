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

public class ThrowingPredicate2Test
{
    @Test
    public void implementationWithoutException() throws Exception
    {
        ThrowingPredicate2<String, Integer> predicate = new ThrowingPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                return item != null && item.length() > param;
            }
        };
        
        Assert.assertTrue(predicate.safeAccept("test", 3));
        Assert.assertFalse(predicate.safeAccept("abc", 3));
        Assert.assertFalse(predicate.safeAccept(null, 3));
    }
    
    @Test(expected = NullPointerException.class)
    public void implementationWithRuntimeException() throws Exception
    {
        ThrowingPredicate2<String, Integer> predicate = new ThrowingPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                if (item == null)
                {
                    throw new NullPointerException("Item cannot be null");
                }
                return item.length() > param;
            }
        };
        
        Assert.assertTrue(predicate.safeAccept("test", 3));
        Assert.assertFalse(predicate.safeAccept("abc", 3));
        predicate.safeAccept(null, 3); // Should throw NullPointerException
    }
    
    @Test(expected = Exception.class)
    public void implementationWithCheckedException() throws Exception
    {
        ThrowingPredicate2<String, Integer> predicate = new ThrowingPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                if (item == null)
                {
                    throw new Exception("Item cannot be null");
                }
                return item.length() > param;
            }
        };
        
        Assert.assertTrue(predicate.safeAccept("test", 3));
        Assert.assertFalse(predicate.safeAccept("abc", 3));
        predicate.safeAccept(null, 3); // Should throw Exception
    }
}
