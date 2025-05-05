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
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.set.sorted.MutableSortedSet;
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
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default MutableList<ObjectIntPair<T>> topOccurrences(int count)
    {
        return (MutableList<ObjectIntPair<T>>) SortedBag.super.topOccurrences(count);
    }

    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default MutableList<ObjectIntPair<T>> bottomOccurrences(int count)
    {
        return (MutableList<ObjectIntPair<T>>) SortedBag.super.bottomOccurrences(count);
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default <V> MutableList<V> collect(Function<? super T, ? extends V> function)
    {
        return (MutableList<V>) SortedBag.super.collect(function);
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default <P, V> MutableList<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter)
    {
        return (MutableList<V>) SortedBag.super.collectWith(function, parameter);
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default <V> MutableList<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function)
    {
        return (MutableList<V>) SortedBag.super.collectIf(predicate, function);
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default <V> MutableList<V> flatCollect(Function<? super T, ? extends Iterable<V>> function)
    {
        return (MutableList<V>) SortedBag.super.flatCollect(function);
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default MutableSortedSet<T> distinct()
    {
        return (MutableSortedSet<T>) SortedBag.super.distinct();
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default <S> MutableSortedSet<Pair<T, S>> zip(Iterable<S> that)
    {
        return (MutableSortedSet<Pair<T, S>>) SortedBag.super.zip(that);
    }
    
    /**
     * Bridge method to resolve clash between different bag interfaces
     */
    @Override
    default MutableSortedSet<Pair<T, Integer>> zipWithIndex()
    {
        return (MutableSortedSet<Pair<T, Integer>>) SortedBag.super.zipWithIndex();
    }
}
