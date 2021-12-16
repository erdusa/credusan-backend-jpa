package com.credusan.domain.ports.input;

import java.util.List;

public interface ICRUD<T, ID> {
    T create(T t) throws Exception;

    T update(ID id, T t) throws Exception;

    List<T> getAll() throws Exception;

    T getById(ID id) throws Exception;

    void delete(ID id) throws Exception;
}
