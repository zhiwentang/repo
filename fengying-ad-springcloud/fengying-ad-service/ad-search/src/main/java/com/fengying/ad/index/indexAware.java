package com.fengying.ad.index;

public interface indexAware<K,V> {
    V get(K key);

    void add(K key,V value);

    void update(K key,V value);

    void delete(K key,V value);
}
