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

package com.gs.collections.api.set.sorted;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

/**
 * A bridge interface to ensure compatibility between SortedSet and MutableSortedSet interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface MutableSortedSetBridge<E> extends SortedSet<E>
{
    Comparator<? super E> comparator();
    
    SortedSet<E> subSet(E fromElement, E toElement);
    
    SortedSet<E> headSet(E toElement);
    
    SortedSet<E> tailSet(E fromElement);
    
    E first();
    
    E last();
    
    boolean add(E e);
    
    boolean addAll(Collection<? extends E> c);
    
    void clear();
    
    boolean contains(Object o);
    
    boolean containsAll(Collection<?> c);
    
    boolean equals(Object o);
    
    int hashCode();
    
    boolean isEmpty();
    
    boolean remove(Object o);
    
    boolean removeAll(Collection<?> c);
    
    boolean retainAll(Collection<?> c);
    
    int size();
    
    Object[] toArray();
    
    <T> T[] toArray(T[] a);
}
