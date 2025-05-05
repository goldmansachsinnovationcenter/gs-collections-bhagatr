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

package com.gs.collections.api.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.tuple.Pair;

/**
 * A bridge interface to resolve method clashes between List and RichIterable.
 * This interface is used to avoid method clashes in Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface ListBridge<T> extends List<T>, RichIterable<T>
{
    /**
     * Returns a new collection with the elements that evaluate to true for the specified predicate.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default RichIterable<T> selectInBridge(Predicate<? super T> predicate)
    {
        return this.select(predicate);
    }
    
    /**
     * Returns a new collection with the elements that evaluate to false for the specified predicate.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default RichIterable<T> rejectInBridge(Predicate<? super T> predicate)
    {
        return this.reject(predicate);
    }
    
    /**
     * Returns a new collection with the results of applying the specified function to each element.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <V> RichIterable<V> collectInBridge(Function<? super T, ? extends V> function)
    {
        return this.collect(function);
    }
    
    /**
     * Returns a new collection with the results of applying the specified function to each element that
     * satisfies the predicate. This method has a different name than the one in RichIterable to avoid
     * method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <V> RichIterable<V> collectIfInBridge(Predicate<? super T> predicate, Function<? super T, ? extends V> function)
    {
        return this.collectIf(predicate, function);
    }
    
    /**
     * Returns a new collection with the results of applying the specified function to each element and the
     * specified parameter. This method has a different name than the one in RichIterable to avoid method
     * clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <P, V> RichIterable<V> collectWithInBridge(Function2<? super T, ? super P, ? extends V> function, P parameter)
    {
        return this.collectWith(function, parameter);
    }
    
    /**
     * Returns a new collection with the elements that satisfy the predicate when evaluated with the specified
     * parameter. This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <P> RichIterable<T> selectWithInBridge(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.selectWith(predicate, parameter);
    }
    
    /**
     * Returns a new collection with the elements that do not satisfy the predicate when evaluated with the
     * specified parameter. This method has a different name than the one in RichIterable to avoid method
     * clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <P> RichIterable<T> rejectWithInBridge(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.rejectWith(predicate, parameter);
    }
    
    /**
     * Returns a new collection with only the instances of the specified class.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <S> RichIterable<S> selectInstancesOfInBridge(Class<S> clazz)
    {
        return this.selectInstancesOf(clazz);
    }
    
    /**
     * Returns a new collection with distinct elements.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default RichIterable<T> distinctInBridge()
    {
        return this.distinct();
    }
    
    /**
     * Returns a new collection by zipping the elements of this collection with the specified iterable.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default <S> RichIterable<Pair<T, S>> zipInBridge(Iterable<S> that)
    {
        return this.zip(that);
    }
    
    /**
     * Returns a new collection by zipping the elements of this collection with their indices.
     * This method has a different name than the one in RichIterable to avoid method clash in Java 21.
     * 
     * @since 7.0.4
     */
    default RichIterable<Pair<T, Integer>> zipWithIndexInBridge()
    {
        return this.zipWithIndex();
    }
}
