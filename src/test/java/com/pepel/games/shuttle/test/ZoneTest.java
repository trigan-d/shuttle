package com.pepel.games.shuttle.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.pepel.games.shuttle.model.geography.AbstractLocation;
import com.pepel.games.shuttle.model.geography.Location;
import com.pepel.games.shuttle.model.geography.NamedLocation;
import com.pepel.games.shuttle.model.geography.Zone;

public class ZoneTest {

	@Test
	public void testZoneCapacity() {
		assertEquals(1, Zone.zeroZone().getCapacity());
		assertEquals(8, zone(1, 1).getCapacity());
		assertEquals(4 * 14, zone(3, 4).getCapacity());
		assertEquals(4 * 18, zone(2, 4).getCapacity());
	}

	@Test
	public void testZonePreconditions() {
		assertWrongZoneNotCreated(0l, 1, 3);
		assertWrongZoneNotCreated(2l, 6, 4);
		assertWrongZoneNotCreated(2l, 0, 2);
	}

	@Test
	public void testZoneLocations() {
		//printLocations(zone(3, 7));
		printLocations(zone(200, 250), 300, 500);
	}

	private void printLocations(Zone zone) {
		int[][] points = new int[zone.getEnd() * 2 + 1][zone.getEnd() * 2 + 1];
		ArrayList<NamedLocation> locations = zone.getLocations();
		for (int i = 0; i < zone.getCapacity(); i++) {
			Location location = locations.get(i);
			points[location.getY() + zone.getEnd()][location.getX() + zone.getEnd()] = i;
		}

		for (int y = 0; y < points.length; y++) {
			for (int x = 0; x < points.length; x++) {
				String label = String.valueOf(points[y][x]);
				while (label.length() < 4) {
					label = " " + label;
				}
				System.out.print(label);
			}
			System.out.println();
		}
	}

	private void printLocations(Zone zone, int fromIndex, int toIndex) {
		boolean[][] points = new boolean[zone.getEnd() * 2 + 1][zone.getEnd() * 2 + 1];

		ArrayList<NamedLocation> locations = zone.getLocations();
		for (int i = Math.max(fromIndex, 0); i < Math.min(toIndex, zone.getCapacity()); i++) {
			Location location = locations.get(i);
			points[location.getY() + zone.getEnd()][location.getX() + zone.getEnd()] = true;
		}

		for (int y = 0; y < points.length; y++) {
			for (int x = 0; x < points.length; x++) {
				System.out.print(zone.contains(new AbstractLocation(y - zone.getEnd(), x
						- zone.getEnd())) ? (points[y][x] ? "X" : "_") : ".");
			}
			System.out.println("<br/>");
		}
	}

	private void assertWrongZoneNotCreated(long id, int x, int y) {
		try {
			new Zone(id, x, y);
			fail("exception was expected");
		} catch (RuntimeException e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	private Zone zone(int start, int end) {
		return new Zone(1l, start, end);
	}
}
