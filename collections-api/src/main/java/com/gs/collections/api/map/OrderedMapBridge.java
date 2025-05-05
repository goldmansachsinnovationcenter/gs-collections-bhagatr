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
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.list.ListIterable;
import com.gs.collections.api.ordered.OrderedIterable;
import com.gs.collections.api.tuple.Pair;

/**
 * A bridge interface to ensure compatibility between OrderedMap and OrderedIterable interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface OrderedMapBridge<K, V> extends OrderedMap<K, V>, OrderedIterable<V>
{
    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default ListIterable<V> select(Predicate<? super V> predicate)
    {
        return OrderedMap.super.select(predicate);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <P> ListIterable<V> selectWith(Predicate2<? super V, ? super P> predicate, P parameter)
    {
        return OrderedMap.super.selectWith(predicate, parameter);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default ListIterable<V> reject(Predicate<? super V> predicate)
    {
        return OrderedMap.super.reject(predicate);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <P> ListIterable<V> rejectWith(Predicate2<? super V, ? super P> predicate, P parameter)
    {
        return OrderedMap.super.rejectWith(predicate, parameter);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <S> ListIterable<Pair<V, S>> zip(Iterable<S> that)
    {
        return OrderedMap.super.zip(that);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default ListIterable<Pair<V, Integer>> zipWithIndex()
    {
        return OrderedMap.super.zipWithIndex();
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <P, A> ListIterable<A> collectWith(Function2<? super V, ? super P, ? extends A> function, P parameter)
    {
        return OrderedMap.super.collectWith(function, parameter);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <A> ListIterable<A> collectIf(Predicate<? super V> predicate, Function<? super V, ? extends A> function)
    {
        return OrderedMap.super.collectIf(predicate, function);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <S> ListIterable<S> selectInstancesOf(Class<S> clazz)
    {
        return OrderedMap.super.selectInstancesOf(clazz);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default <V1> ListIterable<V1> flatCollect(Function<? super V, ? extends Iterable<V1>> function)
    {
        return OrderedMap.super.flatCollect(function);
    }

    /**
     * Bridge method to resolve clash between OrderedMap and OrderedIterable
     */
    @Override
    default ListIterable<V> distinct()
    {
        return OrderedMap.super.distinct();
    }
}
