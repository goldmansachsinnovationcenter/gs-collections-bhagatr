package com.gs.collections.impl.map.immutable;

import com.gs.collections.api.map.ImmutableMap;
import com.gs.collections.impl.factory.Maps;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ImmutableMapTest {

    @Test
    public void testRetrieve() {
        ImmutableMap<String, Integer> map = Maps.immutable.of("X", 10, "Y", 20);

        assertEquals(Optional.of(10), map.retrieve("X"));
        assertEquals(Optional.empty(), map.retrieve("Z"));
    }

    @Test
    public void testHasKey() {
        ImmutableMap<String, Integer> map = Maps.immutable.of("X", 10);

        assertTrue(map.hasKey("X"));
        assertFalse(map.hasKey("Z"));
    }
}
