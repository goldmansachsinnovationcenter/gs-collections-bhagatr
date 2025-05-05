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

package com.gs.collections.api.list.primitive;

import com.gs.collections.api.block.function.primitive.ByteToObjectFunction;
import com.gs.collections.api.collection.primitive.MutableByteCollection;
import com.gs.collections.api.list.MutableList;

/**
 * A bridge interface to resolve method clashes between ByteList and MutableByteCollection interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface MutableByteListBridge extends ByteList, MutableByteCollection
{
    /**
     * Bridge method to resolve clash between different list interfaces
     */
    @Override
    default <V> MutableList<V> collect(ByteToObjectFunction<? extends V> function)
    {
        return (MutableList<V>) ByteList.super.collect(function);
    }
}
