package com.funnyboyroks.mapify.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Cache<K, V> {

    private final Map<K, CacheItem> map = new HashMap<>();
    private final Function<K, V>    getter;
    private       long              cacheDuration;

    public Cache(long cacheDurationMs, Function<K, V> getter) {
        this.cacheDuration = cacheDurationMs;
        this.getter = getter;

    }

    public V get(K key) {
        if (map.containsKey(key)) {
            CacheItem item = map.get(key);
            if (System.currentTimeMillis() - item.insertedTime < this.cacheDuration) {
                return item.value;
            }
        }
        V newValue = this.getter.apply(key);
        this.map.put(key, new CacheItem(newValue));
        return newValue;
    }

    public long getCacheDuration() {
        return cacheDuration;
    }

    public void setCacheDuration(long cacheDuration) {
        this.cacheDuration = cacheDuration;
    }

    private class CacheItem {

        private final long insertedTime;
        private final V    value;

        public CacheItem(V value) {
            this.insertedTime = System.currentTimeMillis();
            this.value = value;
        }

    }
}
