package com.gs.collections.impl.map.mutable;

import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.test.Verify;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class MutableMapTest {

    @Test
    public void testRetrieve() {
        MutableMap<String,Integer> map=new UnifiedMap<>();
        map.put("A",1);
        map.put("B",2);

        assertEquals(Optional.of(1), map.retrieve("A"));       //Verifying type safety
        assertEquals(Optional.empty(), map.retrieve("C"));
    }

    @Test
    public void testHasKey() {
        MutableMap<String, Integer> map=new UnifiedMap<>();
        map.put("A",1);

        assertTrue(map.hasKey("A"));      // type safety verification
        assertFalse(map.hasKey("B"));
    }
}