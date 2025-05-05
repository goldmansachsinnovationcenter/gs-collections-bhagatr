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

package com.gs.collections.api.bag;

import com.gs.collections.api.collection.MutableCollection;
import com.gs.collections.api.list.ListIterable;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.tuple.primitive.ObjectIntPair;

/**
 * A bridge interface to resolve method clashes between Bag and MutableCollection interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface MutableBagBridge<T> extends Bag<T>, MutableCollection<T>
{
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default MutableList<ObjectIntPair<T>> topOccurrences(int count)
    {
        return (MutableList<ObjectIntPair<T>>) Bag.super.topOccurrences(count);
    }

    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default MutableList<ObjectIntPair<T>> bottomOccurrences(int count)
    {
        return (MutableList<ObjectIntPair<T>>) Bag.super.bottomOccurrences(count);
    }
}
