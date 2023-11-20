package com.bolun.hotel.dao;

import java.util.Optional;

public interface Dao<K, E> {

    E save(E entity);

    void update(E entity);

    Optional<E> findById(K id);

    boolean delete(K id);
}
