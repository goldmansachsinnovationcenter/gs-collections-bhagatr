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

package com.gs.collections.api.partition.bag.sorted;

import com.gs.collections.api.bag.sorted.MutableSortedBag;
import com.gs.collections.api.partition.bag.PartitionMutableBagIterable;

/**
 * @since 4.2
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface PartitionMutableSortedBag<T> extends PartitionMutableBagIterable<T>
{
    /**
     * Returns a mutable sorted bag of elements that satisfy the predicate.
     * This method has a different name than the one in PartitionSortedBag to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSortedBag<T> getSelectedSortedBag();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getSelectedSortedBag()} instead.
     */
    @Deprecated
    MutableSortedBag<T> getSelected();

    /**
     * Returns a mutable sorted bag of elements that do not satisfy the predicate.
     * This method has a different name than the one in PartitionSortedBag to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSortedBag<T> getRejectedSortedBag();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getRejectedSortedBag()} instead.
     */
    @Deprecated
    MutableSortedBag<T> getRejected();

    PartitionImmutableSortedBag<T> toImmutable();
}
