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

package com.gs.collections.api.map;

import com.gs.collections.api.bag.MutableBag;
import com.gs.collections.api.bag.primitive.MutableBooleanBag;
import com.gs.collections.api.bag.primitive.MutableByteBag;
import com.gs.collections.api.bag.primitive.MutableCharBag;
import com.gs.collections.api.bag.primitive.MutableDoubleBag;
import com.gs.collections.api.bag.primitive.MutableFloatBag;
import com.gs.collections.api.bag.primitive.MutableIntBag;
import com.gs.collections.api.bag.primitive.MutableLongBag;
import com.gs.collections.api.bag.primitive.MutableShortBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.block.function.primitive.ByteFunction;
import com.gs.collections.api.block.function.primitive.CharFunction;
import com.gs.collections.api.block.function.primitive.DoubleFunction;
import com.gs.collections.api.block.function.primitive.FloatFunction;
import com.gs.collections.api.block.function.primitive.IntFunction;
import com.gs.collections.api.block.function.primitive.LongFunction;
import com.gs.collections.api.block.function.primitive.ShortFunction;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.Procedure2;
import com.gs.collections.api.multimap.bag.MutableBagMultimap;
import com.gs.collections.api.multimap.set.MutableSetMultimap;
import com.gs.collections.api.ordered.OrderedIterable;
import com.gs.collections.api.partition.bag.PartitionMutableBag;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.Pair;

/**
 * A MutableMap is similar to a JCF Map but adds additional useful internal iterator methods. The MutableMap interface
 * additionally implements some of the methods in the Smalltalk Dictionary protocol.
 *
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface MutableMap<K, V>
        extends MutableMapIterable<K, V>, Cloneable
{
    /**
     * Adds all the entries derived from {@code iterable} to {@code this}. The key and value for each entry
     * is determined by applying the {@code keyFunction} and {@code valueFunction} to each item in
     * {@code collection}. Any entry in {@code map} that has the same key as an entry in {@code this}
     * will have its value replaced by that in {@code map}.
     */
    <E> MutableMap<K, V> collectKeysAndValues(
            Iterable<E> iterable,
            Function<? super E, ? extends K> keyFunction,
            Function<? super E, ? extends V> valueFunction);

    MutableMap<K, V> newEmpty();

    MutableMap<K, V> clone();

    MutableMap<K, V> asUnmodifiable();

    MutableMap<K, V> asSynchronized();

    /**
     * Returns a mutable set multimap with the keys and values flipped.
     * This method has a different name than the one in UnsortedMapIterable to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSetMultimap<V, K> flipKeysValues();
    
    /**
     * @deprecated As of 7.0.4, use {@link #flipKeysValues()} instead.
     */
    @Deprecated
    MutableSetMultimap<V, K> flip();

    MutableMap<K, V> select(Predicate2<? super K, ? super V> predicate);

    MutableMap<K, V> reject(Predicate2<? super K, ? super V> predicate);

    <R> MutableMap<K, R> collectValues(Function2<? super K, ? super V, ? extends R> function);

    <K2, V2> MutableMap<K2, V2> collect(Function2<? super K, ? super V, Pair<K2, V2>> function);

    MutableMap<K, V> tap(Procedure<? super V> procedure);

    /**
     * Returns a mutable bag of elements that satisfy the predicate.
     * This method has a different name than the one in UnsortedMapIterable to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableBag<V> selectValues(Predicate<? super V> predicate);
    
    /**
     * @deprecated As of 7.0.4, use {@link #selectValues(Predicate)} instead.
     */
    @Deprecated
    MutableBag<V> select(Predicate<? super V> predicate);

    /**
     * Returns a mutable bag of elements that satisfy the predicate.
     * This method has a different name than the one in UnsortedMapIterable to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <P> MutableBag<V> selectValuesWith(Predicate2<? super V, ? super P> predicate, P parameter);
    
    /**
     * @deprecated As of 7.0.4, use {@link #selectValuesWith(Predicate2, Object)} instead.
     */
    @Deprecated
    <P> MutableBag<V> selectWith(Predicate2<? super V, ? super P> predicate, P parameter);

    /**
     * Returns a mutable bag of elements that do not satisfy the predicate.
     * This method has a different name than the one in UnsortedMapIterable to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableBag<V> rejectValues(Predicate<? super V> predicate);
    
    /**
     * @deprecated As of 7.0.4, use {@link #rejectValues(Predicate)} instead.
     */
    @Deprecated
    MutableBag<V> reject(Predicate<? super V> predicate);

    /**
     * Returns a mutable bag of elements that do not satisfy the predicate.
     * This method has a different name than the one in UnsortedMapIterable to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <P> MutableBag<V> rejectValuesWith(Predicate2<? super V, ? super P> predicate, P parameter);
    
    /**
     * @deprecated As of 7.0.4, use {@link #rejectValuesWith(Predicate2, Object)} instead.
     */
    @Deprecated
    <P> MutableBag<V> rejectWith(Predicate2<? super V, ? super P> predicate, P parameter);

    PartitionMutableBag<V> partition(Predicate<? super V> predicate);

    <P> PartitionMutableBag<V> partitionWith(Predicate2<? super V, ? super P> predicate, P parameter);

    <S> MutableBag<S> selectInstancesOf(Class<S> clazz);

    /**
     * Returns a mutable bag of transformed values.
     * This method has a different name than the one in UnsortedMapIterable to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <R> MutableBag<R> collectValues(Function<? super V, ? extends R> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectValues(Function)} instead.
     */
    @Deprecated
    <R> MutableBag<R> collect(Function<? super V, ? extends R> function);

    <P, V1> MutableBag<V1> collectWith(Function2<? super V, ? super P, ? extends V1> function, P parameter);

    MutableBooleanBag collectBoolean(BooleanFunction<? super V> booleanFunction);

    MutableByteBag collectByte(ByteFunction<? super V> byteFunction);

    MutableCharBag collectChar(CharFunction<? super V> charFunction);

    MutableDoubleBag collectDouble(DoubleFunction<? super V> doubleFunction);

    MutableFloatBag collectFloat(FloatFunction<? super V> floatFunction);

    MutableIntBag collectInt(IntFunction<? super V> intFunction);

    MutableLongBag collectLong(LongFunction<? super V> longFunction);

    MutableShortBag collectShort(ShortFunction<? super V> shortFunction);

    <R> MutableBag<R> collectIf(Predicate<? super V> predicate, Function<? super V, ? extends R> function);

    <R> MutableBag<R> flatCollect(Function<? super V, ? extends Iterable<R>> function);

    /**
     * @deprecated in 6.0. Use {@link OrderedIterable#zip(Iterable)} instead.
     */
    @Deprecated
    <S> MutableBag<Pair<V, S>> zip(Iterable<S> that);

    /**
     * @deprecated in 6.0. Use {@link OrderedIterable#zipWithIndex()} instead.
     */
    @Deprecated
    MutableSet<Pair<V, Integer>> zipWithIndex();

    <VV> MutableBagMultimap<VV, V> groupBy(Function<? super V, ? extends VV> function);

    <VV> MutableBagMultimap<VV, V> groupByEach(Function<? super V, ? extends Iterable<VV>> function);

    <V1> MutableMap<V1, V> groupByUniqueKey(Function<? super V, ? extends V1> function);

    <K2, V2> MutableMap<K2, V2> aggregateInPlaceBy(
            Function<? super V, ? extends K2> groupBy,
            Function0<? extends V2> zeroValueFactory,
            Procedure2<? super V2, ? super V> mutatingAggregator);

    <K2, V2> MutableMap<K2, V2> aggregateBy(
            Function<? super V, ? extends K2> groupBy,
            Function0<? extends V2> zeroValueFactory,
            Function2<? super V2, ? super V, ? extends V2> nonMutatingAggregator);

    MutableMap<V, K> flipUniqueValues();

    MutableMap<K, V> withKeyValue(K key, V value);

    MutableMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues);

    MutableMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs);

    MutableMap<K, V> withoutKey(K key);

    MutableMap<K, V> withoutAllKeys(Iterable<? extends K> keys);
}
