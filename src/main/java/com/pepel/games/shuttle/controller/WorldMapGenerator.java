package com.pepel.games.shuttle.controller;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.pepel.games.shuttle.model.geography.NamedLocation;
import com.pepel.games.shuttle.model.geography.Province;
import com.pepel.games.shuttle.model.geography.Province_;
import com.pepel.games.shuttle.model.geography.Zone;
import com.pepel.games.shuttle.model.geography.Zone_;
import com.pepel.games.shuttle.util.Config;

@Startup
@ApplicationScoped
@Singleton
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Lock(LockType.READ)
public class WorldMapGenerator {
	@Inject
	@Config
	private int initialZoneWidth;

	@Inject
	@Config
	private int minAvailableProvinces;

	@Inject
	private Logger log;

	@Inject
	private CommonEntityManager cem;

	@Inject
	private PlanetsManager planetsManager;

	@Inject
	private ProvinceGenerator provinceGenerator;

	private long lastProvinceId;

	private AtomicBoolean expansionRunning = new AtomicBoolean(false);

	@PostConstruct
	public void init() {
		Zone lastZone = cem.getLast(Zone.class, Zone_.id);
		if (lastZone == null) {
			lastZone = new Zone(0l, 0, 0);
			cem.save(lastZone, true);
			expand(lastZone, true);
		} else {
			long provincesInLastZone = planetsManager.countProvinces(lastZone);
			if (provincesInLastZone > 0 && provincesInLastZone < lastZone.getCapacity()) {
				expand(lastZone, false);
			} else {
				lastProvinceId = cem.getLast(Province.class, Province_.id).getId();
			}
		}
	}

	@Asynchronous
	public void expandIfNeeded(Province provinceRegistered) {
		if ((lastProvinceId - provinceRegistered.getId() < minAvailableProvinces)
				&& expansionRunning.compareAndSet(false, true)) {
			expand(cem.getLast(Zone.class, Zone_.id), true);
			expansionRunning.set(false);
		}
	}

	private void expand(Zone zone, boolean emptyZone) {
		log.info("Start map expansion at zone " + zone.getId());

		for (NamedLocation location : zone.getLocations()) {
			if (emptyZone || planetsManager.getProvince(location) == null) {
				lastProvinceId = provinceGenerator.generateProvince(zone, location).getId();
			}
		}

		long nextZoneId = zone.getId() + 1;
		Zone nextZone = cem.find(Zone.class, nextZoneId);
		if (nextZone == null) {
			generateNextZone(zone);
		}

		log.info("End map expansion");
	}

	private Zone generateNextZone(Zone previosZone) {
		long id = previosZone.getId() + 1;
		int start = previosZone.getEnd() + 1;
		int end = previosZone.getId() == 0 ? initialZoneWidth
				: (start + previosZone.getEnd() - previosZone.getStart());
		Zone zone = new Zone(id, start, end);
		cem.save(zone, true);
		return zone;
	}
}
