package com.bolun.hotel.dao;

import java.util.Optional;

public interface BaseDao<K, E> {

    E save(E entity);

    Boolean update(E entity);

    Optional<E> findById(K id);

    Boolean delete(K id);
}
