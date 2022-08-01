package com.luv2code.service;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.Query;

public interface GenericDao<T> {

	Optional<T> findById(Object id);

	List<T> findAll();

	T create(T t);

	boolean deleteById(Object id);

	T update(T t);

	List<T> findWithPaging(int page, int size);

  List<T> findWithPagingAndSort(int page, int size, String sortBy, String sortOrder);

	List<T> findWithPagingAndFilter(int page, int size, Consumer<Query> filter);

	List<T> findByAttribute(String attribute, Object value);

	List<T> createMany(List<T> list);

	List<T> updateMany(List<T> list);

	List<T> filter(HashMap<String, Object> queryMap, int page, int size);

}
