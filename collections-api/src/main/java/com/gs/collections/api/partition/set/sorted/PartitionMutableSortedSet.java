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

import com.gs.collections.api.partition.set.PartitionMutableSetIterable;
import com.gs.collections.api.set.sorted.MutableSortedSet;

/**
 * A PartitionMutableSortedSet is the result of splitting a mutable sorted set into two mutable sorted sets based on a Predicate.
 * The results that answer true for the Predicate will be returned from the getSelected() method and the results that answer
 * false for the predicate will be returned from the getRejected() method.
 */
public interface PartitionMutableSortedSet<T> extends PartitionSortedSetBridge<T>, PartitionMutableSetIterable<T>, PartitionBridge<T>
{
    /**
     * Returns a mutable sorted set of elements that satisfy the predicate.
     * This method has a different name than the one in PartitionSortedSet to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSortedSet<T> getSelectedSet();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getSelectedSet()} instead.
     */
    @Deprecated
    MutableSortedSet<T> getSelected();

    /**
     * Returns a mutable sorted set of elements that do not satisfy the predicate.
     * This method has a different name than the one in PartitionSortedSet to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSortedSet<T> getRejectedSet();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getRejectedSet()} instead.
     */
    @Deprecated
    MutableSortedSet<T> getRejected();

    PartitionImmutableSortedSet<T> toImmutable();
}
