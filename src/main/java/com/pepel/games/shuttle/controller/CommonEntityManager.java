package com.pepel.games.shuttle.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.pepel.games.shuttle.util.QueryUtils;

@Stateless
public class CommonEntityManager {
	@Inject
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> T find(Class<T> clazz, long id) {
		return em.find(clazz, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> T save(T entity, boolean inCreationMode) {
		if (inCreationMode) {
			em.persist(entity);
			return entity;
		} else {
			return em.merge(entity);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> void remove(T entity) {
		if (!em.contains(entity)) {
			entity = em.merge(entity);
		}
		em.remove(entity);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> getAll(Class<T> clazz) {
		return getOrdered(clazz, null, true, 0);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> T getFirst(Class<T> clazz, SingularAttribute<? super T, V> order) {
		return QueryUtils.firstResult(getOrdered(clazz, order, true, 0));
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> T getLast(Class<T> clazz, SingularAttribute<? super T, V> order) {
		return QueryUtils.firstResult(getOrdered(clazz, order, false, 0));
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> List<T> getOrdered(Class<T> clazz, SingularAttribute<? super T, V> order,
			boolean ascending, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(clazz);
		Root<T> entity = criteria.from(clazz);
		criteria.select(entity);
		if (order != null) {
			criteria.orderBy(ascending ? cb.asc(entity.get(order)) : cb.desc(entity.get(order)));
		}
		TypedQuery<T> query = em.createQuery(criteria);
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> long countAll(Class<T> clazz) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		criteria.select(cb.count(criteria.from(clazz)));
		return QueryUtils.firstResult(em.createQuery(criteria));
	}

	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> T findByAttribute(SingularAttribute<T, V> attribute, V value,
			Attribute... attributesToLoad) {
		return findByAttribute(attribute.getDeclaringType().getJavaType(), attribute, value,
				attributesToLoad);
	}

	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> T findByAttribute(Class<T> clazz, SingularAttribute<? super T, V> attribute,
			V value, Attribute... attributesToLoad) {
		return QueryUtils.firstResult(listByAttribute(clazz, attribute, value, attributesToLoad));
	}

	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> List<T> listByAttribute(SingularAttribute<T, V> attribute, V value,
			Attribute... attributesToLoad) {
		return listByAttribute(attribute.getDeclaringType().getJavaType(), attribute, value,
				attributesToLoad);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T, V> List<T> listByAttribute(Class<T> clazz,
			SingularAttribute<? super T, V> attribute, V value, Attribute... attributesToLoad) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(clazz);
		Root<T> entity = criteria.from(clazz);
		criteria.select(entity).where(cb.equal(entity.get(attribute), value));
		for (Attribute attr : attributesToLoad) {
			if (attr instanceof SingularAttribute) {
				entity.fetch((SingularAttribute) attr, JoinType.LEFT);
			} else {
				entity.fetch((PluralAttribute) attr, JoinType.LEFT);
			}
		}
		return em.createQuery(criteria).getResultList();
	}

	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> listByAttributePattern(SingularAttribute<T, String> attribute,
			String pattern, Attribute... attributesToLoad) {
		return listByAttributePattern(attribute.getDeclaringType().getJavaType(), attribute,
				pattern, attributesToLoad);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <T> List<T> listByAttributePattern(Class<T> clazz,
			SingularAttribute<? super T, String> attribute, String pattern,
			Attribute... attributesToLoad) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(clazz);
		Root<T> entity = criteria.from(clazz);
		criteria.select(entity).where(
				cb.like(cb.lower(entity.get(attribute)), pattern.toLowerCase()));
		for (Attribute attr : attributesToLoad) {
			if (attr instanceof SingularAttribute) {
				entity.fetch((SingularAttribute) attr, JoinType.LEFT);
			} else {
				entity.fetch((PluralAttribute) attr, JoinType.LEFT);
			}
		}
		return em.createQuery(criteria).getResultList();
	}
}
