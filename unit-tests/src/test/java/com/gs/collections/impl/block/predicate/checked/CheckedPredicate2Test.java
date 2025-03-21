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

public class CheckedPredicate2Test
{
    @Test
    public void acceptWithoutException()
    {
        CheckedPredicate2<String, Integer> predicate = new CheckedPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                return item != null && item.length() > param;
            }
        };
        
        Assert.assertTrue(predicate.accept("test", 3));
        Assert.assertFalse(predicate.accept("abc", 3));
        Assert.assertFalse(predicate.accept(null, 3));
    }
    
    @Test
    public void acceptWithRuntimeException()
    {
        CheckedPredicate2<String, Integer> predicate = new CheckedPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                if (item == null)
                {
                    throw new NullPointerException("Item cannot be null");
                }
                if (param == null)
                {
                    throw new NullPointerException("Param cannot be null");
                }
                return item.length() > param;
            }
        };
        
        Assert.assertTrue(predicate.accept("test", 3));
        Assert.assertFalse(predicate.accept("abc", 3));
        
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept(null, 3));
        Verify.assertThrows(NullPointerException.class, () -> predicate.accept("test", null));
    }
    
    @Test
    public void acceptWithCheckedException()
    {
        CheckedPredicate2<String, Integer> predicate = new CheckedPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                if (item == null)
                {
                    throw new Exception("Item cannot be null");
                }
                if (param == null)
                {
                    throw new Exception("Param cannot be null");
                }
                return item.length() > param;
            }
        };
        
        Assert.assertTrue(predicate.accept("test", 3));
        Assert.assertFalse(predicate.accept("abc", 3));
        
        RuntimeException exception1 = Verify.assertThrows(RuntimeException.class, () -> predicate.accept(null, 3));
        Assert.assertEquals("Checked exception caught in Predicate", exception1.getMessage());
        Assert.assertTrue(exception1.getCause() instanceof Exception);
        Assert.assertEquals("Item cannot be null", exception1.getCause().getMessage());
        
        RuntimeException exception2 = Verify.assertThrows(RuntimeException.class, () -> predicate.accept("test", null));
        Assert.assertEquals("Checked exception caught in Predicate", exception2.getMessage());
        Assert.assertTrue(exception2.getCause() instanceof Exception);
        Assert.assertEquals("Param cannot be null", exception2.getCause().getMessage());
    }
    
    @Test
    public void serialization()
    {
        CheckedPredicate2<String, Integer> predicate = new CheckedPredicate2<String, Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean safeAccept(String item, Integer param) throws Exception
            {
                return item != null && item.length() > param;
            }
        };
        
        Verify.assertSerializedForm(predicate);
    }
}
