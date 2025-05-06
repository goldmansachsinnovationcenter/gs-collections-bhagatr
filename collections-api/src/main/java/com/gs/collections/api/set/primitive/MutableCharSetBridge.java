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

package com.gs.collections.api.set.primitive;

import com.gs.collections.api.block.function.primitive.CharToObjectFunction;
import com.gs.collections.api.collection.primitive.MutableCharCollection;
import com.gs.collections.api.set.MutableSet;

/**
 * A bridge interface to resolve method clashes between CharSet and MutableCharCollection interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface MutableCharSetBridge extends CharSet, MutableCharCollection
{
    /**
     * Bridge method to resolve clash between different set interfaces
     */
    @Override
    default <V> MutableSet<V> collect(CharToObjectFunction<? extends V> function)
    {
        return (MutableSet<V>) CharSet.super.collect(function);
    }
}
