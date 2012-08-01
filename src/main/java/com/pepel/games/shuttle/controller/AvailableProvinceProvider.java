package com.pepel.games.shuttle.controller;

import java.util.logging.Logger;

import javax.ejb.DependsOn;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.pepel.games.shuttle.model.geography.Province;

@Startup
@ApplicationScoped
@Singleton
@DependsOn("WorldMapGenerator")
public class AvailableProvinceProvider {
	@Inject
	private Logger log;

	@Inject
	private PlanetsManager planetsManager;

	@Inject
	private WorldMapGenerator worldMapGenerator;

	@Lock(LockType.WRITE)
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Province getProvinceForRegistration() {
		Province availableProvince = planetsManager.getProvinceForRegistration();

		if (availableProvince == null) {
			log.warning("no available province found for new player");
			return null;
		} else {
			worldMapGenerator.expandIfNeeded(availableProvince);
			return availableProvince;
		}
	}
}
