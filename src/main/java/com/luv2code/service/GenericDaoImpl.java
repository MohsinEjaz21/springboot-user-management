package com.luv2code.service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.luv2code.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	protected Class<T> entityClass;

  @SuppressWarnings("unchecked")
  public GenericDaoImpl() {
      this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

	@Override
	public Optional<T> findById(Object id) {
	  T foundObj	= (T) entityManager.find(entityClass, id);
		if(foundObj == null) {
			throw new RuntimeException("ENTITY `"+ entityClass.getSimpleName()+"` IS NOT FOUND WITH ID :: "+id);
		}
		return Optional.ofNullable(foundObj);
	}

	@Override
	public List<T> findAll() {
		log.info("Entity findAll :: {}", entityClass.getName());
		Query query = entityManager.createQuery("FROM " + entityClass.getName());
		return query.getResultList();
	}

	@Override
	public T create(T t) {
		entityManager.persist(t);
		entityManager.flush();
		return t;
	}

	@Override
	public T update(T t) {
		return entityManager.merge(t);
	}

	@Override
	public boolean deleteById(Object id) {
		Optional<T> obj = findById(id);
		if (!obj.isPresent()) {
			throw new RuntimeException("ENTITY `"+ entityClass.getSimpleName()+"` IS NOT FOUND WITH ID :: "+id);
		}
		entityManager.remove(obj);
		return true;
	}

	@Override
	public List<T> findWithPaging(int page, int size) {
		Query query = entityManager.createQuery("FROM " + entityClass.getName());
		if(page >-1) {
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findWithPagingAndSort(int page, int size, String sortBy, String sortOrder) {
		Query query = entityManager.createQuery("FROM " + entityClass.getName() + " ORDER BY " + sortBy + " " + sortOrder);
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query.getResultList();
	}

	@Override
	public List<T> findWithPagingAndFilter(int page, int size, Consumer<Query> filter) {
		Query query = entityManager.createQuery("FROM " + entityClass.getName());
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		filter.accept(query);
		return query.getResultList();
	}

	@Override
	public List<T> findByAttribute(String attribute, Object value) {
		Query query = entityManager.createQuery("FROM " + entityClass.getName() + " WHERE " + attribute + " = :value");
		query.setParameter("value", value);
		return query.getResultList();
	}

	@Override
	public List<T> createMany(List<T> list) {
		list.forEach(entityManager::persist);
		entityManager.flush();
		return list;
	}

	@Override
	public List<T> updateMany(List<T> list) {
		list.forEach(entityManager::merge);
		entityManager.flush();
		return list;
	}

		@Override
	public List<T> filter(HashMap<String, Object> queryMap, int page, int size) {
		// queryMap has query and queryParams
		HashMap<String, Object> queryParams = (HashMap<String, Object>) queryMap.get(Constants.QUERY_PARAMS);
		Query query = entityManager.createQuery(queryMap.get(Constants.QUERY).toString());

		if (queryParams != null && !queryParams.isEmpty()) {
			queryParams.forEach((key, value) -> query.setParameter(key, value));
		}

		if(page >-1) {
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
		}
		return query.getResultList();
	}


}
