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

package com.gs.collections.api.list;

import java.util.List;

import com.gs.collections.api.collection.FixedSizeCollection;

/**
 * A bridge interface to resolve method clashes between FixedSizeCollection and ListBridge.
 * This interface is used to avoid method clashes in Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface FixedSizeListBridge<T> extends MutableList<T>, FixedSizeCollection<T>
{
    /**
     * Bridge method to resolve clash between List and RichIterable
     */
    @Override
    default List<T> castToList()
    {
        return this;
    }
}
