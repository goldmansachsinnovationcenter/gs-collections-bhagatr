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

package com.gs.collections.api.partition.set.sorted;

import com.gs.collections.api.partition.ordered.PartitionOrderedIterable;
import com.gs.collections.api.set.sorted.SortedSetIterable;

/**
 * A bridge interface to ensure compatibility between PartitionSortedSet and PartitionOrderedIterable interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface PartitionSortedSetBridge<T> extends PartitionSortedSet<T>, PartitionOrderedIterable<T>
{
    /**
     * Bridge method to resolve clash between PartitionSortedSet and PartitionOrderedIterable
     */
    @Override
    default SortedSetIterable<T> getSelected()
    {
        return PartitionSortedSet.super.getSelected();
    }

    /**
     * Bridge method to resolve clash between PartitionSortedSet and PartitionOrderedIterable
     */
    @Override
    default SortedSetIterable<T> getRejected()
    {
        return PartitionSortedSet.super.getRejected();
    }
}
