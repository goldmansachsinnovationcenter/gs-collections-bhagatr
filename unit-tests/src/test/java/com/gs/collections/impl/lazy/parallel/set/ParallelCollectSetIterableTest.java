/*
 * Copyright 2014 Goldman Sachs.
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

package com.gs.collections.impl.lazy.parallel.set;

import com.gs.collections.api.ParallelIterable;
import com.gs.collections.api.bag.MutableBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.impl.bag.mutable.HashBag;
import com.gs.collections.impl.block.factory.IntegerPredicates;
import com.gs.collections.impl.block.function.NegativeIntervalFunction;
import com.gs.collections.impl.lazy.parallel.ParallelIterableTestCase;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import org.junit.Assert;
import org.junit.Test;

public class ParallelCollectSetIterableTest extends ParallelIterableTestCase
{
    @Override
    protected ParallelIterable<Integer> classUnderTest()
    {
        return UnifiedSet.newSetWith(1.1, 2.1, 2.2, 3.1, 3.2, 3.3, 4.1, 4.2, 4.3, 4.4)
                .asParallel(this.executorService, 2)
                .collect(Double::intValue);
    }

    @Override
    protected MutableBag<Integer> getExpected()
    {
        return HashBag.newBagWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
    }

    @Override
    protected boolean isOrdered()
    {
        return false;
    }

    @Override
    protected boolean isUnique()
    {
        return false;
    }

    @Test
    @Override
    public void groupBy()
    {
        Function<Integer, Boolean> isOddFunction = object -> IntegerPredicates.isOdd().accept(object);

        Assert.assertEquals(
                this.getExpected().toBag().groupBy(isOddFunction),
                this.classUnderTest().groupBy(isOddFunction));
    }

    @Test
    @Override
    public void groupByEach()
    {
        Assert.assertEquals(
                this.getExpected().toBag().groupByEach(new NegativeIntervalFunction()),
                this.classUnderTest().groupByEach(new NegativeIntervalFunction()));
    }
}
