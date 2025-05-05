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

package com.gs.collections.api.multimap.set;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.multimap.bag.MutableBagMultimap;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.Pair;

/**
 * @since 1.0
 * @since 7.0.4 - Updated for Java 21 compatibility
 */
public interface MutableSetMultimap<K, V>
        extends MutableSetIterableMultimap<K, V>
{
    MutableSet<V> replaceValues(K key, Iterable<? extends V> values);

    MutableSet<V> removeAll(Object key);

    MutableSetMultimap<K, V> newEmpty();

    /**
     * Returns a mutable set of values associated with the specified key.
     * This method has a different name than the one in UnsortedSetMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSet<V> getValues(K key);
    
    /**
     * @deprecated As of 7.0.4, use {@link #getValues(Object)} instead.
     */
    @Deprecated
    MutableSet<V> get(K key);

    /**
     * Returns a mutable set multimap with the keys and values flipped.
     * This method has a different name than the one in UnsortedSetMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    MutableSetMultimap<V, K> flipKeysValues();
    
    /**
     * @deprecated As of 7.0.4, use {@link #flipKeysValues()} instead.
     */
    @Deprecated
    MutableSetMultimap<V, K> flip();

    MutableSetMultimap<K, V> selectKeysValues(Predicate2<? super K, ? super V> predicate);

    MutableSetMultimap<K, V> rejectKeysValues(Predicate2<? super K, ? super V> predicate);

    MutableSetMultimap<K, V> selectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate);

    MutableSetMultimap<K, V> rejectKeysMultiValues(Predicate2<? super K, ? super Iterable<V>> predicate);

    /**
     * Returns a mutable bag multimap of transformed keys and values.
     * This method has a different name than the one in UnsortedSetMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <K2, V2> MutableBagMultimap<K2, V2> collectToKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectToKeysValues(Function2)} instead.
     */
    @Deprecated
    <K2, V2> MutableBagMultimap<K2, V2> collectKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function);

    /**
     * Returns a mutable bag multimap with transformed values.
     * This method has a different name than the one in UnsortedSetMultimap to avoid method clash in Java 21.
     * @since 7.0.4
     */
    <V2> MutableBagMultimap<K, V2> collectToValues(Function<? super V, ? extends V2> function);
    
    /**
     * @deprecated As of 7.0.4, use {@link #collectToValues(Function)} instead.
     */
    @Deprecated
    <V2> MutableBagMultimap<K, V2> collectValues(Function<? super V, ? extends V2> function);
}
