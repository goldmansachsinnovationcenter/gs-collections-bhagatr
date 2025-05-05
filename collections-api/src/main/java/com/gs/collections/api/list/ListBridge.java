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
import java.util.List;

/**
 * A bridge interface to ensure compatibility between List and MutableList interfaces.
 * This is needed for Java 21's stricter type checking.
 *
 * @since 7.0.4
 */
public interface ListBridge<E> extends List<E>
{
    boolean add(E e);
    
    boolean addAll(Collection<? extends E> c);
    
    void clear();
    
    boolean contains(Object o);
    
    boolean containsAll(Collection<?> c);
    
    boolean equals(Object o);
    
    E get(int index);
    
    int hashCode();
    
    int indexOf(Object o);
    
    boolean isEmpty();
    
    int lastIndexOf(Object o);
    
    boolean remove(Object o);
    
    boolean removeAll(Collection<?> c);
    
    boolean retainAll(Collection<?> c);
    
    E set(int index, E element);
    
    int size();
    
    List<E> subList(int fromIndex, int toIndex);
    
    Object[] toArray();
    
    <T> T[] toArray(T[] a);
}
