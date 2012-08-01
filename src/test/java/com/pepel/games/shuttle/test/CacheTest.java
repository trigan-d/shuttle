package com.pepel.games.shuttle.test;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pepel.games.shuttle.controller.PlanetsManager;
import com.pepel.games.shuttle.controller.CommonEntityManager;
import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.geography.Province;

@RunWith(Arquillian.class)
public class CacheTest {
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive res = ShrinkWrap
				.create(WebArchive.class)
				.addPackages(true, "com.pepel.games.shuttle.controller",
						"com.pepel.games.shuttle.model", "com.pepel.games.shuttle.util")
				.addAsLibraries(
						DependencyResolvers.use(MavenDependencyResolver.class)
								.artifacts("com.google.guava:guava:12.0").resolveAsFiles())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("test-system.properties",
						"com/pepel/games/shuttle/system.properties")
				.addAsWebInfResource("jbossas-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return res;
	}

	@Inject
	private CommonEntityManager cem;

	@Inject
	private PlanetsManager planetsManager;

	/*
	@Test
	public void testFindPlanets() {
		Planet planet = cem.find(Planet.class, 47);
		System.out.println(planet.getName());
		planet.setName("tr.tr.mitya");
		cem.save(planet, false);
		planet = cem.find(Planet.class, 47);
		System.out.println(planet.getName());
	}
	
	@Test
	public void testFindProvinces() {
		Province province = cem.find(Province.class, 47);
		System.out.println(province.getName());
		province.setName("tr.tr.mitya");
		cem.save(province, false);
		province = cem.find(Province.class, 47);
		System.out.println(province.getName());
	}
	*/
	@Test
	public void testFetching() {
		Planet planet = cem.find(Planet.class, 47);
		System.out.println(planet.getName());
		//System.out.println(planet.getProvince());
		planetsManager.printProvince(planet);
		//System.out.println(planet.getProvince().getId());		
		//System.out.println(planet.getProvince().getName());		
	}	
}
