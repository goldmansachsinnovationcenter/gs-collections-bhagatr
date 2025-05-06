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

package com.gs.collections.api.bag.sorted;

import com.gs.collections.api.bag.MutableBagIterable;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.ListIterable;
import com.gs.collections.api.list.primitive.BooleanList;
import com.gs.collections.api.set.sorted.SortedSetIterable;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.api.tuple.primitive.ObjectIntPair;

/**
 * A bridge interface to resolve method clashes between SortedBag and MutableBagIterable interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface MutableSortedBagBridge<T> extends SortedBag<T>, MutableBagIterable<T>
{
    /**
     * Bridge method to resolve clash between different bag interfaces.
     * Returns the specified number of items with the highest occurrence counts.
     *
     * @param count The maximum number of items to return.
     * @return The top occurrence items.
     */
    ListIterable<ObjectIntPair<T>> topOccurrences(int count);

    /**
     * Bridge method to resolve clash between different bag interfaces.
     * Returns the specified number of items with the lowest occurrence counts.
     *
     * @param count The maximum number of items to return.
     * @return The bottom occurrence items.
     */
    ListIterable<ObjectIntPair<T>> bottomOccurrences(int count);
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    <V> ListIterable<V> collect(Function<? super T, ? extends V> function);
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    <P, V> ListIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    <V> ListIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    <V> ListIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    SortedSetIterable<T> distinct();
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    <S> SortedSetIterable<Pair<T, S>> zip(Iterable<S> that);
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    SortedSetIterable<Pair<T, Integer>> zipWithIndex();
    
    /**
     * Bridge method to resolve clash between different bag interfaces.
     */
    BooleanList collectBoolean(BooleanFunction<? super T> booleanFunction);
}
