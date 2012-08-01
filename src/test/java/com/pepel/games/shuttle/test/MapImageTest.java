package com.pepel.games.shuttle.test;

import static org.junit.Assert.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
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
import com.pepel.games.shuttle.model.geography.AbstractLocation;
import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.geography.Province;
import com.pepel.games.shuttle.model.geography.Province_;
import com.pepel.games.shuttle.model.geography.Zone;
import com.pepel.games.shuttle.model.geography.Zone_;

@RunWith(Arquillian.class)
public class MapImageTest {
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
	private PlanetsManager planetsManager;

	@Test
	public void testInitialMapCreation() {
		drawMap(0, 0, 1000);
		drawMap(250, 550, 100);
	}

	private void drawMap(int x, int y, int radius) {
		System.out.println("x=" + x + ", y=" + y + ", radius= " + radius);
		BufferedImage image = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setBackground(Color.green);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(Color.gray);
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
				new float[] { 10.0f }, 0.0f));
/*
		for (int i = ((x - radius) / 100 + 1) * 100; i < x + radius; i += 100) {
			if (i > x - radius) {
				g2.draw(new Line2D.Double(i - x + radius, 0, i - x + radius, radius*2));
			}
		}

		for (int i = ((y - radius) / 100 + 1) * 100; i < y + radius; i += 100) {
			if (i > y - radius) {
				g2.draw(new Line2D.Double(0, i - y + radius, radius*2, i - y + radius));
			}
		}
*/
		for (Planet planet : planetsManager.getPlanetsInSquare(new AbstractLocation(x, y), radius)) {
			// System.out.println("planet.x=" + planet.getX() + ", planet.y=" +
			// planet.getY());
			g2.fill(new Ellipse2D.Double(planet.getX() - x + radius - 3,
					planet.getY() - y + radius - 3, 6, 6));
		}

		try {
			ImageIO.write(image, "png", new File("/home/trigan-d/temp/map_" + x + "_" + y + "-"
					+ radius + "_" + new Random().nextInt(1000) + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
