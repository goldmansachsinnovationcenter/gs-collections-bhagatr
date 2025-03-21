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

import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class CheckedPredicateTest
{
    @Test
    public void acceptWithoutException()
    {
        CheckedPredicate<String> predicate = new CheckedPredicate<String>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String object) throws Exception
            {
                return object != null && object.length() > 3;
            }
        };
        
        Assert.assertTrue(predicate.accept("test"));
        Assert.assertFalse(predicate.accept("abc"));
        Assert.assertFalse(predicate.accept(null));
    }
    
    @Test
    public void acceptWithRuntimeException()
    {
        CheckedPredicate<String> predicate = new CheckedPredicate<String>()
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
        
        Assert.assertTrue(predicate.accept("test"));
        Assert.assertFalse(predicate.accept("abc"));
        
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept(null));
    }
    
    @Test
    public void acceptWithCheckedException()
    {
        CheckedPredicate<String> predicate = new CheckedPredicate<String>()
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
        
        Assert.assertTrue(predicate.accept("test"));
        Assert.assertFalse(predicate.accept("abc"));
        
        RuntimeException exception = Verify.assertThrows(RuntimeException.class, () -> predicate.accept(null));
        Assert.assertEquals("Checked exception caught in Predicate", exception.getMessage());
        Assert.assertTrue(exception.getCause() instanceof Exception);
        Assert.assertEquals("Object cannot be null", exception.getCause().getMessage());
    }
    
    @Test
    public void serialization()
    {
        CheckedPredicate<String> predicate = new CheckedPredicate<String>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String object) throws Exception
            {
                return object != null && object.length() > 3;
            }
        };
        
        Verify.assertSerializedForm(predicate);
    }
}
