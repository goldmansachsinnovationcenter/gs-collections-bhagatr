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

package com.gs.collections.api.multimap.bag;

import com.gs.collections.api.bag.MutableBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.tuple.Pair;

/**
 * @since 1.0
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface MutableBagMultimap<K, V>
        extends MutableBagIterableMultimap<K, V>
{
    MutableBag<V> replaceValues(K key, Iterable<? extends V> values);

    MutableBag<V> removeAll(Object key);

    MutableBagMultimap<K, V> newEmpty();

    /**
     * Returns a mutable bag of values associated with the specified key.
     * This method has a different name than the one in UnsortedBagMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableBag<V> getValues(K key);
    
    /**
     * @deprecated As of 7.0.4, use {@link #getValues(Object)} instead.
     */
    @Deprecated
    MutableBag<V> get(K key);

    void putOccurrences(K key, V value, int occurrences);

    MutableBagMultimap<V, K> flip();

    MutableBagMultimap<K, V> selectKeysValues(Predicate2<? super K, ? super V> predicate);

    MutableBagMultimap<K, V> rejectKeysValues(Predicate2<? super K, ? super V> predicate);

    MutableBagMultimap<K, V> selectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate);

    MutableBagMultimap<K, V> rejectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate);

    <K2, V2> MutableBagMultimap<K2, V2> collectKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function);

    <V2> MutableBagMultimap<K, V2> collectValues(Function<? super V, ? extends V2> function);
}
