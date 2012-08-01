package com.pepel.games.shuttle.util;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

public class QueryUtils {
	public static <T> Expression<Boolean> equalsOrIn(CriteriaBuilder cb, Path<T> path,
			Collection<T> values) {
		if (values.size() == 1) {
			return cb.equal(path, values.iterator().next());
		} else {
			return path.in(values);
		}
	}

	public static <T> T firstResult(TypedQuery<T> query) {
		return firstResult(query.getResultList());
	}

	public static <T> T firstResult(List<T> resultList) {
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	public static void groupByDay(CriteriaQuery<?> criteria, CriteriaBuilder cb, Path<Date> date) {
		criteria.groupBy(cb.function("year", Date.class, date),
				cb.function("month", Date.class, date), cb.function("day", Date.class, date));
	}
}