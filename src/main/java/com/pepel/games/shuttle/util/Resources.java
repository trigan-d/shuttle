package com.pepel.games.shuttle.util;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {
	@SuppressWarnings("unused")
	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	@Produces
	@Config
	public String getStringConfig(InjectionPoint injectionPoint) {
		return ResourceBundle.getBundle("com.pepel.games.shuttle.system").getString(
				injectionPoint.getMember().getDeclaringClass().getSimpleName() + "."
						+ injectionPoint.getMember().getName());
	}

	@Produces
	@Config
	public int getIntConfig(InjectionPoint injectionPoint) {
		return Integer.parseInt(getStringConfig(injectionPoint));
	}

	@Produces
	@Config
	public float getFloatConfig(InjectionPoint injectionPoint) {
		return Float.parseFloat(getStringConfig(injectionPoint));
	}
}
