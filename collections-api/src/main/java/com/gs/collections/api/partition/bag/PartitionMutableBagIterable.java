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

package com.gs.collections.api.partition.bag;

import com.gs.collections.api.bag.MutableBagIterable;
import com.gs.collections.api.partition.PartitionMutableCollection;

/**
 * A PartitionMutableBagIterable is the result of splitting a mutable bag into two MutableBagIterables based on a Predicate.
 * The results that answer true for the Predicate will be returned from the getSelected() method and the results that answer
 * false for the predicate will be returned from the getRejected() method.
 *
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface PartitionMutableBagIterable<T> extends PartitionMutableCollection<T>, PartitionBridge<T>
{
    /**
     * Returns a mutable bag iterable of elements that satisfy the predicate.
     * This method has a different name than the one in PartitionBag to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableBagIterable<T> getSelectedBagIterable();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getSelectedBagIterable()} instead.
     */
    @Deprecated
    MutableBagIterable<T> getSelected();

    /**
     * Returns a mutable bag iterable of elements that do not satisfy the predicate.
     * This method has a different name than the one in PartitionBag to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableBagIterable<T> getRejectedBagIterable();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getRejectedBagIterable()} instead.
     */
    @Deprecated
    MutableBagIterable<T> getRejected();

    PartitionImmutableBagIterable<T> toImmutable();
}
