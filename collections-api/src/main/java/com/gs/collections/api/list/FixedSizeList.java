/*
 * Copyright 2014 Goldman Sachs.
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

import com.gs.collections.api.collection.FixedSizeCollection;

/**
 * A FixedSizeList is a list that may be mutated, but cannot grow or shrink in size.  The typical
 * mutation allowed for a FixedSizeList implementation is a working implementation for set(int, T).
 * This will allow the FixedSizeList to be sorted.
 *
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface FixedSizeList<T>
        extends MutableList<T>, FixedSizeCollection<T>
{
    FixedSizeList<T> toReversed();
}
