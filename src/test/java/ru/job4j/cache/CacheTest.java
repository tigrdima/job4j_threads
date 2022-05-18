package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CacheTest {

    @Test
    public void add() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("");
        assertTrue(cache.add(base));
        assertFalse(cache.add(base));
    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("");
        cache.delete(base);
        assertTrue(cache.add(base));
    }

    @Test
    public void update() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("");
        cache.add(base);

        base.setName("rrrr");
        assertTrue(cache.update(base));

        int ver = base.getId();
        assertThat(ver, is(1));

    }
}