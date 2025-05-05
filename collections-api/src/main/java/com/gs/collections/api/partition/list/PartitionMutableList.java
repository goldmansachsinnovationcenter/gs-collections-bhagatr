/*
 * Copyright 2011 Goldman Sachs.
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

package com.gs.collections.api.partition.list;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.partition.PartitionMutableCollection;

/**
 * A PartitionMutableList is the result of splitting a mutable list into two mutable lists based on a Predicate.
 * The results that answer true for the Predicate will be returned from the getSelected() method and the results that answer
 * false for the predicate will be returned from the getRejected() method.
 *
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface PartitionMutableList<T> extends PartitionMutableCollection<T>
{
    /**
     * Returns a mutable list of elements that satisfy the predicate.
     * This method has a different name than the one in PartitionList to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableList<T> getSelectedList();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getSelectedList()} instead.
     */
    @Deprecated
    MutableList<T> getSelected();

    /**
     * Returns a mutable list of elements that do not satisfy the predicate.
     * This method has a different name than the one in PartitionList to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableList<T> getRejectedList();
    
    /**
     * @deprecated As of 7.0.4, use {@link #getRejectedList()} instead.
     */
    @Deprecated
    MutableList<T> getRejected();

    PartitionImmutableList<T> toImmutable();
}
