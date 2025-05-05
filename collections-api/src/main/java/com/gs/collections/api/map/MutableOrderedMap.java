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
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.list.primitive.MutableBooleanList;
import com.gs.collections.api.list.primitive.MutableByteList;
import com.gs.collections.api.list.primitive.MutableCharList;
import com.gs.collections.api.list.primitive.MutableDoubleList;
import com.gs.collections.api.list.primitive.MutableFloatList;
import com.gs.collections.api.list.primitive.MutableIntList;
import com.gs.collections.api.list.primitive.MutableLongList;
import com.gs.collections.api.list.primitive.MutableShortList;
import com.gs.collections.api.multimap.list.MutableListMultimap;
import com.gs.collections.api.partition.list.PartitionMutableList;
import com.gs.collections.api.tuple.Pair;

/**
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface MutableOrderedMap<K, V> extends MutableMapIterable<K, V>
{
    MutableOrderedMap<K, V> tap(Procedure<? super V> procedure);

    MutableOrderedMap<V, K> flipUniqueValues();

    MutableListMultimap<V, K> flip();

    MutableOrderedMap<K, V> select(Predicate2<? super K, ? super V> predicate);

    MutableOrderedMap<K, V> reject(Predicate2<? super K, ? super V> predicate);

    <K2, V2> MutableOrderedMap<K2, V2> collect(Function2<? super K, ? super V, Pair<K2, V2>> function);

    <R> MutableOrderedMap<K, R> collectValues(Function2<? super K, ? super V, ? extends R> function);

    MutableOrderedMap<K, V> toReversed();

    MutableOrderedMap<K, V> take(int count);

    MutableOrderedMap<K, V> takeWhile(Predicate<? super V> predicate);

    MutableOrderedMap<K, V> drop(int count);

    MutableOrderedMap<K, V> dropWhile(Predicate<? super V> predicate);

    PartitionMutableList<V> partitionWhile(Predicate<? super V> predicate);

    /**
     * Returns a mutable list of distinct values.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableList<V> distinctValues();
    
    /**
     * @deprecated As of 7.0.4, use {@link #distinctValues()} instead.
     */
    @Deprecated
    MutableList<V> distinct();

    /**
     * Returns a mutable list of values that match the given predicate.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableList<V> selectValues(Predicate<? super V> predicate);
    
    /**
     * @deprecated As of 7.0.4, use {@link #selectValues(Predicate)} instead.
     */
    @Deprecated
    MutableList<V> select(Predicate<? super V> predicate);

    /**
     * Returns a mutable list of values that match the given predicate and parameter.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <P> MutableList<V> selectValuesWith(Predicate2<? super V, ? super P> predicate, P parameter);
    
    /**
     * @deprecated As of 7.0.4, use {@link #selectValuesWith(Predicate2, Object)} instead.
     */
    @Deprecated
    <P> MutableList<V> selectWith(Predicate2<? super V, ? super P> predicate, P parameter);

    /**
     * Returns a mutable list of values that do not match the given predicate.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableList<V> rejectValues(Predicate<? super V> predicate);
    
    /**
     * @deprecated As of 7.0.4, use {@link #rejectValues(Predicate)} instead.
     */
    @Deprecated
    MutableList<V> reject(Predicate<? super V> predicate);

    /**
     * Returns a mutable list of values that do not match the given predicate and parameter.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <P> MutableList<V> rejectValuesWith(Predicate2<? super V, ? super P> predicate, P parameter);
    
    /**
     * @deprecated As of 7.0.4, use {@link #rejectValuesWith(Predicate2, Object)} instead.
     */
    @Deprecated
    <P> MutableList<V> rejectWith(Predicate2<? super V, ? super P> predicate, P parameter);

    PartitionMutableList<V> partition(Predicate<? super V> predicate);

    <P> PartitionMutableList<V> partitionWith(Predicate2<? super V, ? super P> predicate, P parameter);

    MutableBooleanList collectBoolean(BooleanFunction<? super V> booleanFunction);

    MutableByteList collectByte(ByteFunction<? super V> byteFunction);

    MutableCharList collectChar(CharFunction<? super V> charFunction);

    MutableDoubleList collectDouble(DoubleFunction<? super V> doubleFunction);

    MutableFloatList collectFloat(FloatFunction<? super V> floatFunction);

    MutableIntList collectInt(IntFunction<? super V> intFunction);

    MutableLongList collectLong(LongFunction<? super V> longFunction);

    MutableShortList collectShort(ShortFunction<? super V> shortFunction);

    /**
     * Returns a mutable list of pairs of values and elements from the provided iterable.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <S> MutableList<Pair<V, S>> zipValues(Iterable<S> that);
    
    /**
     * @deprecated As of 7.0.4, use {@link #zipValues(Iterable)} instead.
     */
    @Deprecated
    <S> MutableList<Pair<V, S>> zip(Iterable<S> that);

    /**
     * Returns a mutable list of pairs of values and their indices.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableList<Pair<V, Integer>> zipValuesWithIndex();
    
    /**
     * @deprecated As of 7.0.4, use {@link #zipValuesWithIndex()} instead.
     */
    @Deprecated
    MutableList<Pair<V, Integer>> zipWithIndex();

    /**
     * Returns a mutable list of transformed values using the function and parameter.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <P, V1> MutableList<V1> collectValuesWith(Function2<? super V, ? super P, ? extends V1> function, P parameter);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectValuesWith(Function2, Object)} instead.
     */
    @Deprecated
    <P, V1> MutableList<V1> collectWith(Function2<? super V, ? super P, ? extends V1> function, P parameter);

    /**
     * Returns a mutable list of transformed values that match the predicate.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <V1> MutableList<V1> collectValuesIf(Predicate<? super V> predicate, Function<? super V, ? extends V1> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectValuesIf(Predicate, Function)} instead.
     */
    @Deprecated
    <V1> MutableList<V1> collectIf(Predicate<? super V> predicate, Function<? super V, ? extends V1> function);

    /**
     * Returns a mutable list of values that are instances of the specified class.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <S> MutableList<S> selectValuesInstancesOf(Class<S> clazz);
    
    /**
     * @deprecated As of 7.0.4, use {@link #selectValuesInstancesOf(Class)} instead.
     */
    @Deprecated
    <S> MutableList<S> selectInstancesOf(Class<S> clazz);

    /**
     * Returns a mutable list of all elements from the nested iterables.
     * This method has a different name than the one in OrderedMap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <V1> MutableList<V1> flatCollectValues(Function<? super V, ? extends Iterable<V1>> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #flatCollectValues(Function)} instead.
     */
    @Deprecated
    <V1> MutableList<V1> flatCollect(Function<? super V, ? extends Iterable<V1>> function);

    <V1> MutableListMultimap<V1, V> groupBy(Function<? super V, ? extends V1> function);

    <V1> MutableListMultimap<V1, V> groupByEach(Function<? super V, ? extends Iterable<V1>> function);

    <V1> MutableOrderedMap<V1, V> groupByUniqueKey(Function<? super V, ? extends V1> function);

    <KK, VV> MutableOrderedMap<KK, VV> aggregateInPlaceBy(Function<? super V, ? extends KK> groupBy, Function0<? extends VV> zeroValueFactory, Procedure2<? super VV, ? super V> mutatingAggregator);

    <KK, VV> MutableOrderedMap<KK, VV> aggregateBy(Function<? super V, ? extends KK> groupBy, Function0<? extends VV> zeroValueFactory, Function2<? super VV, ? super V, ? extends VV> nonMutatingAggregator);
}
