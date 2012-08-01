package com.pepel.games.shuttle.test;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

import com.pepel.games.shuttle.controller.AvailableProvinceProvider;
import com.pepel.games.shuttle.controller.PlanetsManager;
import com.pepel.games.shuttle.controller.CommonEntityManager;
import com.pepel.games.shuttle.model.geography.Province;
import com.pepel.games.shuttle.model.geography.Province_;
import com.pepel.games.shuttle.model.geography.Zone;
import com.pepel.games.shuttle.model.geography.Zone_;

@RunWith(Arquillian.class)
public class WorldMapGenerationTest {
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
	private AvailableProvinceProvider availableProvinceProvider;

	@Inject
	private CommonEntityManager cem;

	@Inject
	private PlanetsManager planetsManager;

	/*
	 * @Test public void testMapExpansionCompleted() { Zone lastZone =
	 * cem.getLast(Zone.class, Zone_.id); assertEquals(2, lastZone.getId());
	 * assertEquals(2, lastZone.getEnd()); assertEquals(0,
	 * planetsManager.countProvinces(lastZone));
	 * 
	 * Zone previousZone = cem.find(Zone.class, lastZone.getId()-1);
	 * assertEquals(previousZone.getCapacity(),
	 * planetsManager.countProvinces(previousZone)); }
	 */

	//@Test
	public void testInitialMapCreation() {
		Zone lastZone = cem.getLast(Zone.class, Zone_.id);
		assertEquals(1, lastZone.getId());
		assertEquals(10, lastZone.getEnd());
		assertEquals(0, planetsManager.countProvinces(lastZone));

		Province lastProvince = cem.getLast(Province.class, Province_.id);
		assertEquals(0, lastProvince.getX());
		assertEquals(0, lastProvince.getY());
		assertEquals(0, lastProvince.getZone());
	}

	@Test
	public void testMassiveRegistrations() throws InterruptedException, ExecutionException {
		Province province = availableProvinceProvider.getProvinceForRegistration();
		System.out.println("admin registered");

		Thread.sleep(20000);
/*
		Future<?> submit = null;
		ExecutorService executor = Executors.newFixedThreadPool(20);
		Thread.sleep(10000);
		System.out.println("registration started");

		for (int i = 0; i < 600; i++) {
			Thread.sleep(50);
			submit = executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					availableProvinceProvider.getProvinceForRegistration();
				}
			});
		}

		submit.get();
		System.out.println("everybody registered");
		Thread.sleep(40000);
*/
	}
}
