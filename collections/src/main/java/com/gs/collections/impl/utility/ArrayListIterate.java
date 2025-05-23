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

package com.gs.collections.impl.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.gs.collections.api.block.HashingStrategy;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.function.Function3;
import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.block.function.primitive.ByteFunction;
import com.gs.collections.api.block.function.primitive.CharFunction;
import com.gs.collections.api.block.function.primitive.DoubleFunction;
import com.gs.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
import com.gs.collections.api.block.function.primitive.FloatFunction;
import com.gs.collections.api.block.function.primitive.FloatObjectToFloatFunction;
import com.gs.collections.api.block.function.primitive.IntFunction;
import com.gs.collections.api.block.function.primitive.IntObjectToIntFunction;
import com.gs.collections.api.block.function.primitive.LongFunction;
import com.gs.collections.api.block.function.primitive.LongObjectToLongFunction;
import com.gs.collections.api.block.function.primitive.ShortFunction;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.Procedure2;
import com.gs.collections.api.block.procedure.primitive.ObjectIntProcedure;
import com.gs.collections.api.collection.primitive.MutableBooleanCollection;
import com.gs.collections.api.collection.primitive.MutableByteCollection;
import com.gs.collections.api.collection.primitive.MutableCharCollection;
import com.gs.collections.api.collection.primitive.MutableDoubleCollection;
import com.gs.collections.api.collection.primitive.MutableFloatCollection;
import com.gs.collections.api.collection.primitive.MutableIntCollection;
import com.gs.collections.api.collection.primitive.MutableLongCollection;
import com.gs.collections.api.collection.primitive.MutableShortCollection;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.list.primitive.MutableBooleanList;
import com.gs.collections.api.list.primitive.MutableByteList;
import com.gs.collections.api.list.primitive.MutableCharList;
import com.gs.collections.api.list.primitive.MutableDoubleList;
import com.gs.collections.api.list.primitive.MutableFloatList;
import com.gs.collections.api.list.primitive.MutableIntList;
import com.gs.collections.api.list.primitive.MutableLongList;
import com.gs.collections.api.list.primitive.MutableShortList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.MutableMultimap;
import com.gs.collections.api.partition.list.PartitionMutableList;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.api.tuple.Twin;
import com.gs.collections.impl.block.factory.Comparators;
import com.gs.collections.impl.block.procedure.MutatingAggregationProcedure;
import com.gs.collections.impl.block.procedure.NonMutatingAggregationProcedure;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.list.mutable.primitive.BooleanArrayList;
import com.gs.collections.impl.list.mutable.primitive.ByteArrayList;
import com.gs.collections.impl.list.mutable.primitive.CharArrayList;
import com.gs.collections.impl.list.mutable.primitive.DoubleArrayList;
import com.gs.collections.impl.list.mutable.primitive.FloatArrayList;
import com.gs.collections.impl.list.mutable.primitive.IntArrayList;
import com.gs.collections.impl.list.mutable.primitive.LongArrayList;
import com.gs.collections.impl.list.mutable.primitive.ShortArrayList;
import com.gs.collections.impl.map.mutable.UnifiedMap;
import com.gs.collections.impl.multimap.list.FastListMultimap;
import com.gs.collections.impl.partition.list.PartitionFastList;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import com.gs.collections.impl.set.strategy.mutable.UnifiedSetWithHashingStrategy;
import com.gs.collections.impl.tuple.Tuples;
import com.gs.collections.impl.utility.internal.InternalArrayIterate;
import com.gs.collections.impl.utility.internal.RandomAccessListIterate;

/**
 * This utility class provides optimized iteration pattern implementations that work with java.util.ArrayList.
 */
public final class ArrayListIterate
{
    private static final Field ELEMENT_DATA_FIELD;
    private static final Field SIZE_FIELD;
    private static final int MIN_DIRECT_ARRAY_ACCESS_SIZE = 100;

    static
    {
        Field data = null;
        Field size = null;
        try
        {
            data = ArrayList.class.getDeclaredField("elementData");
            size = ArrayList.class.getDeclaredField("size");
            try
            {
                // In Java 17+, reflection access to JDK internals is restricted by default
                // We'll try to set accessible, but if it fails, we'll fall back to non-optimized path
                try {
                    data.setAccessible(true);
                    size.setAccessible(true);
                } catch (Exception e) {
                    // Java 17+ module restrictions - fall back to non-optimized path
                    data = null;
                    size = null;
                }
            }
            catch (SecurityException ignored)
            {
                data = null;
                size = null;
            }
        }
        catch (NoSuchFieldException ignored)
        {
        }
        ELEMENT_DATA_FIELD = data;
        SIZE_FIELD = size;
    }

    private ArrayListIterate()
    {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    /**
     * @see Iterate#select(Iterable, Predicate)
     */
    public static <T> ArrayList<T> select(ArrayList<T> list, Predicate<? super T> predicate)
    {
        return ArrayListIterate.select(list, predicate, new ArrayList<T>());
    }

    /**
     * @see Iterate#selectWith(Iterable, Predicate2, Object)
     */
    public static <T, IV> ArrayList<T> selectWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super IV> predicate,
            IV injectedValue)
    {
        return ArrayListIterate.selectWith(list, predicate, injectedValue, new ArrayList<T>());
    }

    /**
     * @see Iterate#select(Iterable, Predicate, Collection)
     */
    public static <T, R extends Collection<T>> R select(
            ArrayList<T> list,
            Predicate<? super T> predicate,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i]))
                {
                    targetCollection.add(elements[i]);
                }
            }
            return targetCollection;
        }
        return RandomAccessListIterate.select(list, predicate, targetCollection);
    }

    /**
     * @see Iterate#selectWith(Iterable, Predicate2, Object, Collection)
     */
    public static <T, P, R extends Collection<T>> R selectWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i], parameter))
                {
                    targetCollection.add(elements[i]);
                }
            }
            return targetCollection;
        }
        return RandomAccessListIterate.selectWith(list, predicate, parameter, targetCollection);
    }

    /**
     * @see Iterate#selectInstancesOf(Iterable, Class)
     */
    public static <T> MutableList<T> selectInstancesOf(
            ArrayList<?> list,
            Class<T> clazz)
    {
        int size = list.size();
        FastList<T> result = FastList.newList(size);
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            Object[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                Object element = elements[i];
                if (clazz.isInstance(element))
                {
                    result.add((T) element);
                }
            }
            result.trimToSize();
            return result;
        }
        return RandomAccessListIterate.selectInstancesOf(list, clazz);
    }

    /**
     * @see Iterate#count(Iterable, Predicate)
     */
    public static <T> int count(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            int count = 0;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i]))
                {
                    count++;
                }
            }
            return count;
        }
        return RandomAccessListIterate.count(list, predicate);
    }

    /**
     * @see Iterate#countWith(Iterable, Predicate2, Object)
     */
    public static <T, P> int countWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            int count = 0;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i], parameter))
                {
                    count++;
                }
            }
            return count;
        }
        return RandomAccessListIterate.countWith(list, predicate, parameter);
    }

    /**
     * @see Iterate#collectIf(Iterable, Predicate, Function)
     */
    public static <T, A> ArrayList<A> collectIf(
            ArrayList<T> list,
            Predicate<? super T> predicate,
            Function<? super T, ? extends A> function)
    {
        return ArrayListIterate.collectIf(list, predicate, function, new ArrayList<A>());
    }

    /**
     * @see Iterate#collectIf(Iterable, Predicate, Function, Collection)
     */
    public static <T, A, R extends Collection<A>> R collectIf(
            ArrayList<T> list,
            Predicate<? super T> predicate,
            Function<? super T, ? extends A> function,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i]))
                {
                    targetCollection.add(function.valueOf(elements[i]));
                }
            }
            return targetCollection;
        }
        return RandomAccessListIterate.collectIf(list, predicate, function, targetCollection);
    }

    /**
     * @see Iterate#reject(Iterable, Predicate)
     */
    public static <T> ArrayList<T> reject(ArrayList<T> list, Predicate<? super T> predicate)
    {
        return ArrayListIterate.reject(list, predicate, new ArrayList<T>());
    }

    /**
     * @see Iterate#rejectWith(Iterable, Predicate2, Object)
     */
    public static <T, IV> ArrayList<T> rejectWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super IV> predicate,
            IV injectedValue)
    {
        return ArrayListIterate.rejectWith(list, predicate, injectedValue, new ArrayList<T>());
    }

    /**
     * @see Iterate#reject(Iterable, Predicate, Collection)
     */
    public static <T, R extends Collection<T>> R reject(
            ArrayList<T> list,
            Predicate<? super T> predicate,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (!predicate.accept(elements[i]))
                {
                    targetCollection.add(elements[i]);
                }
            }
            return targetCollection;
        }
        return RandomAccessListIterate.reject(list, predicate, targetCollection);
    }

    /**
     * @see Iterate#reject(Iterable, Predicate, Collection)
     */
    public static <T, P, R extends Collection<T>> R rejectWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P injectedValue,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (!predicate.accept(elements[i], injectedValue))
                {
                    targetCollection.add(elements[i]);
                }
            }
            return targetCollection;
        }
        return RandomAccessListIterate.rejectWith(list, predicate, injectedValue, targetCollection);
    }

    /**
     * @see Iterate#collect(Iterable, Function)
     */
    public static <T, A> ArrayList<A> collect(
            ArrayList<T> list,
            Function<? super T, ? extends A> function)
    {
        return ArrayListIterate.collect(list, function, new ArrayList<A>(list.size()));
    }

    /**
     * @see Iterate#collectBoolean(Iterable, BooleanFunction)
     */
    public static <T> MutableBooleanList collectBoolean(
            ArrayList<T> list,
            BooleanFunction<? super T> booleanFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableBooleanList result = new BooleanArrayList(size);
            return ArrayListIterate.collectBooleanFromInternalArray(list, booleanFunction, size, result);
        }
        return RandomAccessListIterate.collectBoolean(list, booleanFunction);
    }

    /**
     * @see Iterate#collectBoolean(Iterable, BooleanFunction, MutableBooleanCollection)
     */
    public static <T, R extends MutableBooleanCollection> R collectBoolean(ArrayList<T> list, BooleanFunction<? super T> booleanFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectBooleanFromInternalArray(list, booleanFunction, size, target);
        }
        return RandomAccessListIterate.collectBoolean(list, booleanFunction, target);
    }

    private static <T, R extends MutableBooleanCollection> R collectBooleanFromInternalArray(ArrayList<T> source, BooleanFunction<? super T> booleanFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(booleanFunction.booleanValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectByte(Iterable, ByteFunction)
     */
    public static <T> MutableByteList collectByte(
            ArrayList<T> list,
            ByteFunction<? super T> byteFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableByteList result = new ByteArrayList(size);
            return ArrayListIterate.collectByteFromInternalArray(list, byteFunction, size, result);
        }
        return RandomAccessListIterate.collectByte(list, byteFunction);
    }

    /**
     * @see Iterate#collectByte(Iterable, ByteFunction, MutableByteCollection)
     */
    public static <T, R extends MutableByteCollection> R collectByte(ArrayList<T> list, ByteFunction<? super T> byteFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectByteFromInternalArray(list, byteFunction, size, target);
        }
        return RandomAccessListIterate.collectByte(list, byteFunction, target);
    }

    private static <T, R extends MutableByteCollection> R collectByteFromInternalArray(ArrayList<T> source, ByteFunction<?
            super T> byteFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(byteFunction.byteValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectChar(Iterable, CharFunction)
     */
    public static <T> MutableCharList collectChar(
            ArrayList<T> list,
            CharFunction<? super T> charFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableCharList result = new CharArrayList(size);
            return ArrayListIterate.collectCharFromInternalArray(list, charFunction, size, result);
        }
        return RandomAccessListIterate.collectChar(list, charFunction);
    }

    /**
     * @see Iterate#collectChar(Iterable, CharFunction, MutableCharCollection)
     */
    public static <T, R extends MutableCharCollection> R collectChar(ArrayList<T> list, CharFunction<? super T> charFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectCharFromInternalArray(list, charFunction, size, target);
        }
        return RandomAccessListIterate.collectChar(list, charFunction, target);
    }

    private static <T, R extends MutableCharCollection> R collectCharFromInternalArray(ArrayList<T> source, CharFunction<?
            super T> charFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(charFunction.charValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectDouble(Iterable, DoubleFunction)
     */
    public static <T> MutableDoubleList collectDouble(
            ArrayList<T> list,
            DoubleFunction<? super T> doubleFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableDoubleList result = new DoubleArrayList(size);
            return ArrayListIterate.collectDoubleFromInternalArray(list, doubleFunction, size, result);
        }
        return RandomAccessListIterate.collectDouble(list, doubleFunction);
    }

    /**
     * @see Iterate#collectDouble(Iterable, DoubleFunction, MutableDoubleCollection)
     */
    public static <T, R extends MutableDoubleCollection> R collectDouble(ArrayList<T> list, DoubleFunction<? super T> doubleFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectDoubleFromInternalArray(list, doubleFunction, size, target);
        }
        return RandomAccessListIterate.collectDouble(list, doubleFunction, target);
    }

    private static <T, R extends MutableDoubleCollection> R collectDoubleFromInternalArray(ArrayList<T> source, DoubleFunction<?
            super T> doubleFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(doubleFunction.doubleValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectFloat(Iterable, FloatFunction)
     */
    public static <T> MutableFloatList collectFloat(
            ArrayList<T> list,
            FloatFunction<? super T> floatFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableFloatList result = new FloatArrayList(size);
            return ArrayListIterate.collectFloatFromInternalArray(list, floatFunction, size, result);
        }
        return RandomAccessListIterate.collectFloat(list, floatFunction);
    }

    /**
     * @see Iterate#collectFloat(Iterable, FloatFunction, MutableFloatCollection)
     */
    public static <T, R extends MutableFloatCollection> R collectFloat(ArrayList<T> list, FloatFunction<? super T> floatFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectFloatFromInternalArray(list, floatFunction, size, target);
        }
        return RandomAccessListIterate.collectFloat(list, floatFunction, target);
    }

    private static <T, R extends MutableFloatCollection> R collectFloatFromInternalArray(ArrayList<T> source, FloatFunction<?
            super T> floatFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(floatFunction.floatValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectInt(Iterable, IntFunction)
     */
    public static <T> MutableIntList collectInt(
            ArrayList<T> list,
            IntFunction<? super T> intFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableIntList result = new IntArrayList(size);
            return ArrayListIterate.collectIntFromInternalArray(list, intFunction, size, result);
        }
        return RandomAccessListIterate.collectInt(list, intFunction);
    }

    /**
     * @see Iterate#collectInt(Iterable, IntFunction, MutableIntCollection)
     */
    public static <T, R extends MutableIntCollection> R collectInt(ArrayList<T> list, IntFunction<? super T> intFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectIntFromInternalArray(list, intFunction, size, target);
        }
        return RandomAccessListIterate.collectInt(list, intFunction, target);
    }

    private static <T, R extends MutableIntCollection> R collectIntFromInternalArray(ArrayList<T> source, IntFunction<? super T> intFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(intFunction.intValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectLong(Iterable, LongFunction)
     */
    public static <T> MutableLongList collectLong(
            ArrayList<T> list,
            LongFunction<? super T> longFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableLongList result = new LongArrayList(size);
            return ArrayListIterate.collectLongFromInternalArray(list, longFunction, size, result);
        }
        return RandomAccessListIterate.collectLong(list, longFunction);
    }

    /**
     * @see Iterate#collectLong(Iterable, LongFunction, MutableLongCollection)
     */
    public static <T, R extends MutableLongCollection> R collectLong(ArrayList<T> list, LongFunction<? super T> longFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectLongFromInternalArray(list, longFunction, size, target);
        }
        return RandomAccessListIterate.collectLong(list, longFunction, target);
    }

    private static <T, R extends MutableLongCollection> R collectLongFromInternalArray(ArrayList<T> source, LongFunction<?
            super T> longFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(longFunction.longValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collectShort(Iterable, ShortFunction)
     */
    public static <T> MutableShortList collectShort(
            ArrayList<T> list,
            ShortFunction<? super T> shortFunction)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableShortList result = new ShortArrayList(size);
            return ArrayListIterate.collectShortFromInternalArray(list, shortFunction, size, result);
        }
        return RandomAccessListIterate.collectShort(list, shortFunction);
    }

    /**
     * @see Iterate#collectShort(Iterable, ShortFunction, MutableShortCollection)
     */
    public static <T, R extends MutableShortCollection> R collectShort(ArrayList<T> list, ShortFunction<? super T> shortFunction, R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            return ArrayListIterate.collectShortFromInternalArray(list, shortFunction, size, target);
        }
        return RandomAccessListIterate.collectShort(list, shortFunction, target);
    }

    private static <T, R extends MutableShortCollection> R collectShortFromInternalArray(ArrayList<T> source, ShortFunction<?
            super T> shortFunction, int elementsToCollect, R target)
    {
        T[] elements = ArrayListIterate.getInternalArray(source);
        for (int i = 0; i < elementsToCollect; i++)
        {
            target.add(shortFunction.shortValueOf(elements[i]));
        }
        return target;
    }

    /**
     * @see Iterate#collect(Iterable, Function, Collection)
     */
    public static <T, A, R extends Collection<A>> R collect(
            ArrayList<T> list,
            Function<? super T, ? extends A> function,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                targetCollection.add(function.valueOf(elements[i]));
            }
            return targetCollection;
        }
        return RandomAccessListIterate.collect(list, function, targetCollection);
    }

    /**
     * @see Iterate#flatCollect(Iterable, Function)
     */
    public static <T, A> ArrayList<A> flatCollect(
            ArrayList<T> list,
            Function<? super T, ? extends Iterable<A>> function)
    {
        return ArrayListIterate.flatCollect(list, function, new ArrayList<A>(list.size()));
    }

    /**
     * @see Iterate#flatCollect(Iterable, Function, Collection)
     */
    public static <T, A, R extends Collection<A>> R flatCollect(
            ArrayList<T> list,
            Function<? super T, ? extends Iterable<A>> function,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                Iterate.addAllTo(function.valueOf(elements[i]), targetCollection);
            }
            return targetCollection;
        }
        return RandomAccessListIterate.flatCollect(list, function, targetCollection);
    }

    /**
     * @see Iterate#forEach(Iterable, Procedure)
     */
    public static <T> void forEach(ArrayList<T> list, Procedure<? super T> procedure)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                procedure.value(elements[i]);
            }
        }
        else
        {
            RandomAccessListIterate.forEach(list, procedure);
        }
    }

    /**
     * Reverses over the List in reverse order executing the Procedure for each element
     */
    public static <T> void reverseForEach(ArrayList<T> list, Procedure<? super T> procedure)
    {
        if (!list.isEmpty())
        {
            ArrayListIterate.forEach(list, list.size() - 1, 0, procedure);
        }
    }

    /**
     * Iterates over the section of the list covered by the specified indexes.  The indexes are both inclusive.  If the
     * from is less than the to, the list is iterated in forward order. If the from is greater than the to, then the
     * list is iterated in the reverse order.
     * <p>
     * <pre>e.g.
     * ArrayList<People> people = new ArrayList<People>(FastList.newListWith(ted, mary, bob, sally));
     * ArrayListIterate.forEach(people, 0, 1, new Procedure<Person>()
     * {
     *     public void value(Person person)
     *     {
     *          LOGGER.info(person.getName());
     *     }
     * });
     * </pre>
     * <p>
     * This code would output ted and mary's names.
     */
    public static <T> void forEach(ArrayList<T> list, int from, int to, Procedure<? super T> procedure)
    {
        ListIterate.rangeCheck(from, to, list.size());
        if (ArrayListIterate.isOptimizableArrayList(list, to - from + 1))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);

            InternalArrayIterate.forEachWithoutChecks(elements, from, to, procedure);
        }
        else
        {
            RandomAccessListIterate.forEach(list, from, to, procedure);
        }
    }

    /**
     * Iterates over the section of the list covered by the specified indexes.  The indexes are both inclusive.  If the
     * from is less than the to, the list is iterated in forward order. If the from is greater than the to, then the
     * list is iterated in the reverse order. The index passed into the ObjectIntProcedure is the actual index of the
     * range.
     * <p>
     * <pre>e.g.
     * ArrayList<People> people = new ArrayList<People>(FastList.newListWith(ted, mary, bob, sally));
     * ArrayListIterate.forEachWithIndex(people, 0, 1, new ObjectIntProcedure<Person>()
     * {
     *     public void value(Person person, int index)
     *     {
     *          LOGGER.info(person.getName() + " at index: " + index);
     *     }
     * });
     * </pre>
     * <p>
     * This code would output ted and mary's names.
     */
    public static <T> void forEachWithIndex(
            ArrayList<T> list,
            int from,
            int to,
            ObjectIntProcedure<? super T> objectIntProcedure)
    {
        ListIterate.rangeCheck(from, to, list.size());
        if (ArrayListIterate.isOptimizableArrayList(list, to - from + 1))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);

            InternalArrayIterate.forEachWithIndexWithoutChecks(elements, from, to, objectIntProcedure);
        }
        else
        {
            RandomAccessListIterate.forEachWithIndex(list, from, to, objectIntProcedure);
        }
    }

    /**
     * @see ListIterate#forEachInBoth(List, List, Procedure2)
     */
    public static <T1, T2> void forEachInBoth(
            ArrayList<T1> list1,
            ArrayList<T2> list2,
            Procedure2<? super T1, ? super T2> procedure)
    {
        RandomAccessListIterate.forEachInBoth(list1, list2, procedure);
    }

    /**
     * @see Iterate#forEachWithIndex(Iterable, ObjectIntProcedure)
     */
    public static <T> void forEachWithIndex(ArrayList<T> list, ObjectIntProcedure<? super T> objectIntProcedure)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                objectIntProcedure.value(elements[i], i);
            }
        }
        else
        {
            RandomAccessListIterate.forEachWithIndex(list, objectIntProcedure);
        }
    }

    /**
     * @see Iterate#detect(Iterable, Predicate)
     */
    public static <T> T detect(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T item = elements[i];
                if (predicate.accept(item))
                {
                    return item;
                }
            }
            return null;
        }
        return RandomAccessListIterate.detect(list, predicate);
    }

    /**
     * @see Iterate#detectWith(Iterable, Predicate2, Object)
     */
    public static <T, P> T detectWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T item = elements[i];
                if (predicate.accept(item, parameter))
                {
                    return item;
                }
            }
            return null;
        }
        return RandomAccessListIterate.detectWith(list, predicate, parameter);
    }

    /**
     * @see Iterate#detectIfNone(Iterable, Predicate, Object)
     */
    public static <T> T detectIfNone(ArrayList<T> list, Predicate<? super T> predicate, T ifNone)
    {
        T result = ArrayListIterate.detect(list, predicate);
        return result == null ? ifNone : result;
    }

    /**
     * @see Iterate#detectWithIfNone(Iterable, Predicate2, Object, Object)
     */
    public static <T, IV> T detectWithIfNone(
            ArrayList<T> list,
            Predicate2<? super T, ? super IV> predicate,
            IV injectedValue,
            T ifNone)
    {
        T result = ArrayListIterate.detectWith(list, predicate, injectedValue);
        return result == null ? ifNone : result;
    }

    /**
     * @see Iterate#injectInto(Object, Iterable, Function2)
     */
    public static <T, IV> IV injectInto(
            IV injectValue,
            ArrayList<T> list,
            Function2<? super IV, ? super T, ? extends IV> function)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            IV result = injectValue;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                result = function.value(result, elements[i]);
            }
            return result;
        }
        return RandomAccessListIterate.injectInto(injectValue, list, function);
    }

    /**
     * @see Iterate#injectInto(int, Iterable, IntObjectToIntFunction)
     */
    public static <T> int injectInto(
            int injectValue,
            ArrayList<T> list,
            IntObjectToIntFunction<? super T> function)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            int result = injectValue;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                result = function.intValueOf(result, elements[i]);
            }
            return result;
        }
        return RandomAccessListIterate.injectInto(injectValue, list, function);
    }

    /**
     * @see Iterate#injectInto(long, Iterable, LongObjectToLongFunction)
     */
    public static <T> long injectInto(
            long injectValue,
            ArrayList<T> list,
            LongObjectToLongFunction<? super T> function)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            long result = injectValue;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                result = function.longValueOf(result, elements[i]);
            }
            return result;
        }
        return RandomAccessListIterate.injectInto(injectValue, list, function);
    }

    /**
     * @see Iterate#injectInto(double, Iterable, DoubleObjectToDoubleFunction)
     */
    public static <T> double injectInto(
            double injectValue,
            ArrayList<T> list,
            DoubleObjectToDoubleFunction<? super T> function)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            double result = injectValue;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                result = function.doubleValueOf(result, elements[i]);
            }
            return result;
        }
        return RandomAccessListIterate.injectInto(injectValue, list, function);
    }

    /**
     * @see Iterate#injectInto(float, Iterable, FloatObjectToFloatFunction)
     */
    public static <T> float injectInto(
            float injectValue,
            ArrayList<T> list,
            FloatObjectToFloatFunction<? super T> function)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            float result = injectValue;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                result = function.floatValueOf(result, elements[i]);
            }
            return result;
        }
        return RandomAccessListIterate.injectInto(injectValue, list, function);
    }

    /**
     * @see Iterate#anySatisfy(Iterable, Predicate)
     */
    public static <T> boolean anySatisfy(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i]))
                {
                    return true;
                }
            }
            return false;
        }
        return RandomAccessListIterate.anySatisfy(list, predicate);
    }

    /**
     * @see Iterate#anySatisfyWith(Iterable, Predicate2, Object)
     */
    public static <T, P> boolean anySatisfyWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i], parameter))
                {
                    return true;
                }
            }
            return false;
        }
        return RandomAccessListIterate.anySatisfyWith(list, predicate, parameter);
    }

    /**
     * @see Iterate#allSatisfy(Iterable, Predicate)
     */
    public static <T> boolean allSatisfy(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (!predicate.accept(elements[i]))
                {
                    return false;
                }
            }
            return true;
        }
        return RandomAccessListIterate.allSatisfy(list, predicate);
    }

    /**
     * @see Iterate#allSatisfyWith(Iterable, Predicate2, Object)
     */
    public static <T, IV> boolean allSatisfyWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super IV> predicate,
            IV injectedValue)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (!predicate.accept(elements[i], injectedValue))
                {
                    return false;
                }
            }
            return true;
        }
        return RandomAccessListIterate.allSatisfyWith(list, predicate, injectedValue);
    }

    /**
     * @see Iterate#noneSatisfy(Iterable, Predicate)
     */
    public static <T> boolean noneSatisfy(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i]))
                {
                    return false;
                }
            }
            return true;
        }
        return RandomAccessListIterate.noneSatisfy(list, predicate);
    }

    /**
     * @see Iterate#noneSatisfyWith(Iterable, Predicate2, Object)
     */
    public static <T, IV> boolean noneSatisfyWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super IV> predicate,
            IV injectedValue)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i], injectedValue))
                {
                    return false;
                }
            }
            return true;
        }
        return RandomAccessListIterate.noneSatisfyWith(list, predicate, injectedValue);
    }

    /**
     * @see Iterate#selectAndRejectWith(Iterable, Predicate2, Object)
     */
    public static <T, P> Twin<MutableList<T>> selectAndRejectWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableList<T> positiveResult = Lists.mutable.empty();
            MutableList<T> negativeResult = Lists.mutable.empty();
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                (predicate.accept(elements[i], parameter) ? positiveResult : negativeResult).add(elements[i]);
            }
            return Tuples.twin(positiveResult, negativeResult);
        }
        return RandomAccessListIterate.selectAndRejectWith(list, predicate, parameter);
    }

    /**
     * @see Iterate#partition(Iterable, Predicate)
     */
    public static <T> PartitionMutableList<T> partition(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            PartitionFastList<T> partitionFastList = new PartitionFastList<T>();
            MutableList<T> selected = partitionFastList.getSelected();
            MutableList<T> rejected = partitionFastList.getRejected();

            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T each = elements[i];
                MutableList<T> bucket = predicate.accept(each) ? selected : rejected;
                bucket.add(each);
            }
            return partitionFastList;
        }
        return RandomAccessListIterate.partition(list, predicate);
    }

    public static <T, P> PartitionMutableList<T> partitionWith(ArrayList<T> list, Predicate2<? super T, ? super P> predicate, P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            PartitionFastList<T> partitionFastList = new PartitionFastList<T>();
            MutableList<T> selected = partitionFastList.getSelected();
            MutableList<T> rejected = partitionFastList.getRejected();

            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T each = elements[i];
                MutableList<T> bucket = predicate.accept(each, parameter) ? selected : rejected;
                bucket.add(each);
            }
            return partitionFastList;
        }
        return RandomAccessListIterate.partitionWith(list, predicate, parameter);
    }

    /**
     * @see Iterate#detectIndex(Iterable, Predicate)
     */
    public static <T> int detectIndex(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i]))
                {
                    return i;
                }
            }
            return -1;
        }
        return RandomAccessListIterate.detectIndex(list, predicate);
    }

    /**
     * @see Iterate#detectIndexWith(Iterable, Predicate2, Object)
     */
    public static <T, P> int detectIndexWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (predicate.accept(elements[i], parameter))
                {
                    return i;
                }
            }
            return -1;
        }
        return RandomAccessListIterate.detectIndexWith(list, predicate, parameter);
    }

    /**
     * @see ListIterate#detectLastIndex(List, Predicate)
     */
    public static <T> int detectLastIndex(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = size - 1; i >= 0; i--)
            {
                if (predicate.accept(elements[i]))
                {
                    return i;
                }
            }
            return -1;
        }
        return RandomAccessListIterate.detectLastIndex(list, predicate);
    }

    /**
     * @see Iterate#injectIntoWith(Object, Iterable, Function3, Object)
     */
    public static <T, IV, P> IV injectIntoWith(
            IV injectedValue,
            ArrayList<T> list,
            Function3<? super IV, ? super T, ? super P, ? extends IV> function,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            IV result = injectedValue;
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                result = function.value(result, elements[i], parameter);
            }
            return result;
        }
        return RandomAccessListIterate.injectIntoWith(injectedValue, list, function, parameter);
    }

    /**
     * @see Iterate#forEachWith(Iterable, Procedure2, Object)
     */
    public static <T, P> void forEachWith(
            ArrayList<T> list,
            Procedure2<? super T, ? super P> procedure,
            P parameter)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                procedure.value(elements[i], parameter);
            }
        }
        else
        {
            RandomAccessListIterate.forEachWith(list, procedure, parameter);
        }
    }

    /**
     * @see Iterate#collectWith(Iterable, Function2, Object)
     */
    public static <T, P, A> ArrayList<A> collectWith(
            ArrayList<T> list,
            Function2<? super T, ? super P, ? extends A> function,
            P parameter)
    {
        return ArrayListIterate.collectWith(list, function, parameter, new ArrayList<A>(list.size()));
    }

    /**
     * @see Iterate#collectWith(Iterable, Function2, Object, Collection)
     */
    public static <T, P, A, R extends Collection<A>> R collectWith(
            ArrayList<T> list,
            Function2<? super T, ? super P, ? extends A> function,
            P parameter,
            R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                targetCollection.add(function.value(elements[i], parameter));
            }
            return targetCollection;
        }
        return RandomAccessListIterate.collectWith(list, function, parameter, targetCollection);
    }

    /**
     * @see Iterate#removeIf(Iterable, Predicate)
     */
    public static <T> boolean removeIf(ArrayList<T> list, Predicate<? super T> predicate)
    {
        boolean changed;
        if (list.getClass() == ArrayList.class && ArrayListIterate.SIZE_FIELD != null)
        {
            int currentFilledIndex = 0;
            int size = list.size();
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (!predicate.accept(elements[i]))
                {
                    // keep it
                    if (currentFilledIndex != i)
                    {
                        elements[currentFilledIndex] = elements[i];
                    }
                    currentFilledIndex++;
                }
            }
            changed = currentFilledIndex < size;
            ArrayListIterate.wipeAndResetTheEnd(currentFilledIndex, size, elements, list);
        }
        else
        {
            return RandomAccessListIterate.removeIf(list, predicate);
        }
        return changed;
    }

    /**
     * @see Iterate#removeIfWith(Iterable, Predicate2, Object)
     */
    public static <T, P> boolean removeIfWith(
            ArrayList<T> list,
            Predicate2<? super T, ? super P> predicate,
            P parameter)
    {
        boolean changed;
        if (list.getClass() == ArrayList.class && ArrayListIterate.SIZE_FIELD != null)
        {
            int currentFilledIndex = 0;
            int size = list.size();
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (!predicate.accept(elements[i], parameter))
                {
                    // keep it
                    if (currentFilledIndex != i)
                    {
                        elements[currentFilledIndex] = elements[i];
                    }
                    currentFilledIndex++;
                }
            }
            changed = currentFilledIndex < size;
            ArrayListIterate.wipeAndResetTheEnd(currentFilledIndex, size, elements, list);
        }
        else
        {
            return RandomAccessListIterate.removeIfWith(list, predicate, parameter);
        }
        return changed;
    }

    public static <T> ArrayList<T> distinct(ArrayList<T> list)
    {
        return ArrayListIterate.distinct(list, new ArrayList<T>());
    }

    /**
     * @deprecated in 7.0.
     */
    @Deprecated
    public static <T, R extends List<T>> R distinct(ArrayList<T> list, R targetList)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableSet<T> seenSoFar = UnifiedSet.newSet();
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (seenSoFar.add(elements[i]))
                {
                    targetList.add(elements[i]);
                }
            }
            return targetList;
        }
        return RandomAccessListIterate.distinct(list, targetList);
    }

    /**
     * @since 7.0.
     */
    public static <T> ArrayList<T> distinct(ArrayList<T> list, HashingStrategy<? super T> hashingStrategy)
    {
        int size = list.size();
        MutableSet<T> seenSoFar = UnifiedSetWithHashingStrategy.newSet(hashingStrategy);
        ArrayList<T> result = new ArrayList<T>();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                if (seenSoFar.add(elements[i]))
                {
                    result.add(elements[i]);
                }
            }
        }
        else
        {
            for (int i = 0; i < size; i++)
            {
                T item = list.get(i);
                if (seenSoFar.add(item))
                {
                    result.add(item);
                }
            }
        }
        return result;
    }

    private static <T> void wipeAndResetTheEnd(
            int newCurrentFilledIndex,
            int newSize,
            T[] newElements,
            ArrayList<T> list)
    {
        for (int i = newCurrentFilledIndex; i < newSize; i++)
        {
            newElements[i] = null;
        }
        try
        {
            ArrayListIterate.SIZE_FIELD.setInt(list, newCurrentFilledIndex);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(
                    "Something really bad happened on the way to pounding size into the ArrayList reflectively",
                    e);
        }
    }

    /**
     * Mutates the internal array of the ArrayList by sorting it and then returns the same ArrayList.
     */
    public static <T extends Comparable<? super T>> ArrayList<T> sortThis(ArrayList<T> list)
    {
        return ArrayListIterate.sortThis(list, Comparators.naturalOrder());
    }

    /**
     * Mutates the internal array of the ArrayList by sorting it and then returns the same ArrayList.
     */
    public static <T> ArrayList<T> sortThis(ArrayList<T> list, Comparator<? super T> comparator)
    {
        int size = list.size();
        if (ArrayListIterate.canAccessInternalArray(list))
        {
            Arrays.sort(ArrayListIterate.getInternalArray(list), 0, size, comparator);
        }
        else
        {
            Collections.sort(list, comparator);
        }
        return list;
    }

    public static <T> void toArray(ArrayList<T> list, T[] target, int startIndex, int sourceSize)
    {
        if (ArrayListIterate.canAccessInternalArray(list))
        {
            System.arraycopy(ArrayListIterate.getInternalArray(list), 0, target, startIndex, sourceSize);
        }
        else
        {
            RandomAccessListIterate.toArray(list, target, startIndex, sourceSize);
        }
    }

    /**
     * @see Iterate#take(Iterable, int)
     */
    public static <T> ArrayList<T> take(ArrayList<T> list, int count)
    {
        if (count < 0)
        {
            throw new IllegalArgumentException("Count must be greater than zero, but was: " + count);
        }
        return ArrayListIterate.take(list, count, new ArrayList<T>(Math.min(list.size(), count)));
    }

    /**
     * @see Iterate#take(Iterable, int)
     */
    public static <T, R extends Collection<T>> R take(ArrayList<T> list, int count, R targetList)
    {
        if (count < 0)
        {
            throw new IllegalArgumentException("Count must be greater than zero, but was: " + count);
        }

        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);

            int end = Math.min(size, count);
            for (int i = 0; i < end; i++)
            {
                targetList.add(elements[i]);
            }
            return targetList;
        }
        return RandomAccessListIterate.take(list, count, targetList);
    }

    /**
     * @see Iterate#drop(Iterable, int)
     */
    public static <T> ArrayList<T> drop(ArrayList<T> list, int count)
    {
        if (count < 0)
        {
            throw new IllegalArgumentException("Count must be greater than zero, but was: " + count);
        }
        return ArrayListIterate.drop(list, count, new ArrayList<T>(list.size() - Math.min(list.size(), count)));
    }

    /**
     * @see Iterate#drop(Iterable, int)
     */
    public static <T, R extends Collection<T>> R drop(ArrayList<T> list, int count, R targetList)
    {
        if (count < 0)
        {
            throw new IllegalArgumentException("Count must be greater than zero, but was: " + count);
        }
        int size = list.size();
        if (count >= size)
        {
            return targetList;
        }
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);

            for (int i = count; i < size; i++)
            {
                targetList.add(elements[i]);
            }
            return targetList;
        }
        return RandomAccessListIterate.drop(list, count, targetList);
    }

    /**
     * @see Iterate#groupBy(Iterable, Function)
     */
    public static <T, V> FastListMultimap<V, T> groupBy(
            ArrayList<T> list,
            Function<? super T, ? extends V> function)
    {
        return ArrayListIterate.groupBy(list, function, FastListMultimap.<V, T>newMultimap());
    }

    /**
     * @see Iterate#groupBy(Iterable, Function, MutableMultimap)
     */
    public static <T, V, R extends MutableMultimap<V, T>> R groupBy(
            ArrayList<T> list,
            Function<? super T, ? extends V> function,
            R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                target.put(function.valueOf(elements[i]), elements[i]);
            }
            return target;
        }
        return RandomAccessListIterate.groupBy(list, function, target);
    }

    /**
     * @see Iterate#groupByEach(Iterable, Function)
     */
    public static <T, V> FastListMultimap<V, T> groupByEach(
            ArrayList<T> list,
            Function<? super T, ? extends Iterable<V>> function)
    {
        return ArrayListIterate.groupByEach(list, function, FastListMultimap.<V, T>newMultimap());
    }

    /**
     * @see Iterate#groupByEach(Iterable, Function, MutableMultimap)
     */
    public static <T, V, R extends MutableMultimap<V, T>> R groupByEach(
            ArrayList<T> list,
            Function<? super T, ? extends Iterable<V>> function,
            R target)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                Iterable<V> iterable = function.valueOf(elements[i]);
                for (V key : iterable)
                {
                    target.put(key, elements[i]);
                }
            }
            return target;
        }
        return RandomAccessListIterate.groupByEach(list, function, target);
    }

    /**
     * @see Iterate#groupByUniqueKey(Iterable, Function)
     */
    public static <T, V> MutableMap<V, T> groupByUniqueKey(
            ArrayList<T> list,
            Function<? super T, ? extends V> function)
    {
        return ArrayListIterate.groupByUniqueKey(list, function, UnifiedMap.<V, T>newMap());
    }

    /**
     * @see Iterate#groupByUniqueKey(Iterable, Function, MutableMap)
     */
    public static <T, V, R extends MutableMap<V, T>> R groupByUniqueKey(
            ArrayList<T> list,
            Function<? super T, ? extends V> function,
            R target)
    {
        if (list == null)
        {
            throw new IllegalArgumentException("Cannot perform a groupByUniqueKey on null");
        }

        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                V key = function.valueOf(elements[i]);
                if (target.put(key, elements[i]) != null)
                {
                    throw new IllegalStateException("Key " + key + " already exists in map!");
                }
            }
            return target;
        }
        return RandomAccessListIterate.groupByUniqueKey(list, function, target);
    }

    /**
     * @see Iterate#zip(Iterable, Iterable)
     */
    public static <X, Y> MutableList<Pair<X, Y>> zip(ArrayList<X> xs, Iterable<Y> ys)
    {
        return ArrayListIterate.zip(xs, ys, FastList.<Pair<X, Y>>newList());
    }

    /**
     * @see Iterate#zip(Iterable, Iterable, Collection)
     */
    public static <X, Y, R extends Collection<Pair<X, Y>>> R zip(ArrayList<X> xs, Iterable<Y> ys, R targetCollection)
    {
        int size = xs.size();
        if (ArrayListIterate.isOptimizableArrayList(xs, size))
        {
            Iterator<Y> yIterator = ys.iterator();
            X[] elements = ArrayListIterate.getInternalArray(xs);
            for (int i = 0; i < size && yIterator.hasNext(); i++)
            {
                targetCollection.add(Tuples.pair(elements[i], yIterator.next()));
            }
            return targetCollection;
        }
        return RandomAccessListIterate.zip(xs, ys, targetCollection);
    }

    /**
     * @see Iterate#zipWithIndex(Iterable)
     */
    public static <T> MutableList<Pair<T, Integer>> zipWithIndex(ArrayList<T> list)
    {
        return ArrayListIterate.zipWithIndex(list, FastList.<Pair<T, Integer>>newList());
    }

    /**
     * @see Iterate#zipWithIndex(Iterable, Collection)
     */
    public static <T, R extends Collection<Pair<T, Integer>>> R zipWithIndex(ArrayList<T> list, R targetCollection)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                targetCollection.add(Tuples.pair(elements[i], i));
            }
            return targetCollection;
        }
        return RandomAccessListIterate.zipWithIndex(list, targetCollection);
    }

    public static <T> MutableList<T> takeWhile(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableList<T> result = FastList.newList();
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T element = elements[i];
                if (predicate.accept(element))
                {
                    result.add(element);
                }
                else
                {
                    return result;
                }
            }
            return result;
        }
        return RandomAccessListIterate.takeWhile(list, predicate);
    }

    public static <T> MutableList<T> dropWhile(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            MutableList<T> result = FastList.newList();
            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T element = elements[i];
                if (!predicate.accept(element))
                {
                    result.add(element);
                    for (int j = i + 1; j < size; j++)
                    {
                        T eachNotDropped = elements[j];
                        result.add(eachNotDropped);
                    }
                    return result;
                }
            }
            return result;
        }
        return RandomAccessListIterate.dropWhile(list, predicate);
    }

    public static <T> PartitionMutableList<T> partitionWhile(ArrayList<T> list, Predicate<? super T> predicate)
    {
        int size = list.size();
        if (ArrayListIterate.isOptimizableArrayList(list, size))
        {
            PartitionMutableList<T> result = new PartitionFastList<T>();
            MutableList<T> selected = result.getSelected();

            T[] elements = ArrayListIterate.getInternalArray(list);
            for (int i = 0; i < size; i++)
            {
                T each = elements[i];
                if (predicate.accept(each))
                {
                    selected.add(each);
                }
                else
                {
                    MutableList<T> rejected = result.getRejected();
                    rejected.add(each);
                    for (int j = i + 1; j < size; j++)
                    {
                        rejected.add(elements[j]);
                    }
                    return result;
                }
            }
            return result;
        }
        return RandomAccessListIterate.partitionWhile(list, predicate);
    }

    private static boolean canAccessInternalArray(ArrayList<?> list)
    {
        return ArrayListIterate.ELEMENT_DATA_FIELD != null && list.getClass() == ArrayList.class;
    }

    private static boolean isOptimizableArrayList(ArrayList<?> list, int newSize)
    {
        return newSize > MIN_DIRECT_ARRAY_ACCESS_SIZE
                && ArrayListIterate.ELEMENT_DATA_FIELD != null
                && list.getClass() == ArrayList.class;
    }

    private static <T> T[] getInternalArray(ArrayList<T> list)
    {
        try
        {
            return (T[]) ArrayListIterate.ELEMENT_DATA_FIELD.get(list);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T, K, V> MutableMap<K, V> aggregateInPlaceBy(
            ArrayList<T> list,
            Function<? super T, ? extends K> groupBy,
            Function0<? extends V> zeroValueFactory,
            Procedure2<? super V, ? super T> mutatingAggregator)
    {
        MutableMap<K, V> map = UnifiedMap.newMap();
        ArrayListIterate.forEach(list, new MutatingAggregationProcedure<T, K, V>(map, groupBy, zeroValueFactory, mutatingAggregator));
        return map;
    }

    public static <T, K, V> MutableMap<K, V> aggregateBy(
            ArrayList<T> list,
            Function<? super T, ? extends K> groupBy,
            Function0<? extends V> zeroValueFactory,
            Function2<? super V, ? super T, ? extends V> nonMutatingAggregator)
    {
        MutableMap<K, V> map = UnifiedMap.newMap();
        ArrayListIterate.forEach(list, new NonMutatingAggregationProcedure<T, K, V>(map, groupBy, zeroValueFactory, nonMutatingAggregator));
        return map;
    }
}
