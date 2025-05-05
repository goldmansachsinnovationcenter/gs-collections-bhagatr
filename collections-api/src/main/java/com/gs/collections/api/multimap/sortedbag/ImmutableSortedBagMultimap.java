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

package com.gs.collections.api.multimap.sortedbag;

import com.gs.collections.api.bag.sorted.ImmutableSortedBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.multimap.bag.ImmutableBagIterableMultimap;
import com.gs.collections.api.multimap.bag.ImmutableBagMultimap;
import com.gs.collections.api.multimap.list.ImmutableListMultimap;
import com.gs.collections.api.tuple.Pair;

/**
 * @since 4.2
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface ImmutableSortedBagMultimap<K, V>
        extends ImmutableBagIterableMultimap<K, V>
{
    /**
     * Returns an immutable sorted bag of values associated with the specified key.
     * This method has a different name than the one in SortedBagMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    ImmutableSortedBag<V> getValues(K key);
    
    /**
     * @deprecated As of 7.0.4, use {@link #getValues(Object)} instead.
     */
    @Deprecated
    ImmutableSortedBag<V> get(K key);

    ImmutableSortedBagMultimap<K, V> newEmpty();

    ImmutableSortedBagMultimap<K, V> newWith(K key, V value);

    ImmutableSortedBagMultimap<K, V> newWithout(Object key, Object value);

    ImmutableSortedBagMultimap<K, V> newWithAll(K key, Iterable<? extends V> values);

    ImmutableSortedBagMultimap<K, V> newWithoutAll(Object key);

    /**
     * Returns an immutable bag multimap with the keys and values flipped.
     * This method has a different name than the one in SortedBagMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    ImmutableBagMultimap<V, K> flipKeysValues();
    
    /**
     * @deprecated As of 7.0.4, use {@link #flipKeysValues()} instead.
     */
    @Deprecated
    ImmutableBagMultimap<V, K> flip();

    ImmutableSortedBagMultimap<K, V> selectKeysValues(Predicate2<? super K, ? super V> predicate);

    ImmutableSortedBagMultimap<K, V> rejectKeysValues(Predicate2<? super K, ? super V> predicate);

    ImmutableSortedBagMultimap<K, V> selectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate);

    ImmutableSortedBagMultimap<K, V> rejectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate);

    /**
     * Returns an immutable bag multimap of transformed keys and values.
     * This method has a different name than the one in SortedBagMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <K2, V2> ImmutableBagMultimap<K2, V2> collectToKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectToKeysValues(Function2)} instead.
     */
    @Deprecated
    <K2, V2> ImmutableBagMultimap<K2, V2> collectKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function);

    /**
     * Returns an immutable list multimap with transformed values.
     * This method has a different name than the one in SortedBagMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <V2> ImmutableListMultimap<K, V2> collectToValues(Function<? super V, ? extends V2> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectToValues(Function)} instead.
     */
    @Deprecated
    <V2> ImmutableListMultimap<K, V2> collectValues(Function<? super V, ? extends V2> function);
}
