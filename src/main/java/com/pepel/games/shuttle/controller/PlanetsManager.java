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
import javax.persistence.criteria.Root;

import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.geography.Planet_;
import com.pepel.games.shuttle.model.geography.Location;
import com.pepel.games.shuttle.model.geography.Province;
import com.pepel.games.shuttle.model.geography.Province_;
import com.pepel.games.shuttle.model.geography.Zone;
import com.pepel.games.shuttle.util.QueryUtils;

@Stateless
public class PlanetsManager {
	@Inject
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Province getProvinceForRegistration() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Province> criteria = cb.createQuery(Province.class);
		Root<Province> province = criteria.from(Province.class);

		TypedQuery<Province> query = em.createQuery(criteria.select(province).orderBy(
				cb.asc(province.get(Province_.occupied)), cb.asc(province.get(Province_.id))));
		query.setMaxResults(1);
		Province availableProvince = QueryUtils.firstResult(query);

		if (availableProvince == null || availableProvince.isOccupied()) {
			return null;
		} else {
			availableProvince.setOccupied(true);
			return availableProvince;
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public long countProvinces(Zone zone) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<Province> province = criteria.from(Province.class);
		return QueryUtils.firstResult(em.createQuery(criteria.select(cb.count(province)).where(
				cb.equal(province.get(Province_.zone), zone.getId()))));
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Province getProvince(Location location) {
		return getProvince(location.getX(), location.getY());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void printProvince(Planet planet) {
		planet = em.merge(planet);
		// System.out.println(planet.getProvince());
		System.out.println(planet.getProvince().getId());
		System.out.println(planet.getProvince().getName());
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Province getProvince(int x, int y) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Province> criteria = cb.createQuery(Province.class);
		Root<Province> province = criteria.from(Province.class);
		return QueryUtils.firstResult(em.createQuery(criteria.select(province).where(
				cb.and(cb.equal(province.get(Province_.x), x),
						cb.equal(province.get(Province_.y), y)))));
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Planet> getPlanetsInSquare(Location squareCenter, int radius) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Planet> criteria = cb.createQuery(Planet.class);
		Root<Planet> planet = criteria.from(Planet.class);
		criteria.select(planet).where(
				cb.and(cb.between(planet.get(Planet_.x), squareCenter.getX() - radius,
						squareCenter.getX() + radius), cb.between(planet.get(Planet_.y),
						squareCenter.getY() - radius, squareCenter.getY() + radius)));
		return em.createQuery(criteria).getResultList();
	}
}
