package com.pepel.games.shuttle.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.convert.IntegerConverter;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.pepel.games.shuttle.model.geography.AbstractLocation;
import com.pepel.games.shuttle.model.geography.Location;
import com.pepel.games.shuttle.model.geography.NamedLocation;
import com.pepel.games.shuttle.model.geography.Zone;
import com.pepel.games.shuttle.util.NamingUtils;

public class LocationsTest {

	// @Test
	public void generateReplacements() {
		/*
		 * List<Character> chars = Lists.newArrayList();
		 * 
		 * for (char c = 'A'; c < 'Z'; c++) { if (c != 'T' && c != 'R') {
		 * chars.add(c); } }
		 * 
		 * Collections.shuffle(chars);
		 * 
		 * System.out.println(chars.size()); for (int i = 0; i < chars.size();
		 * i++) { System.out.print(".put('" + Integer.toString(i, chars.size())
		 * + "', '" + chars.get(i) + "')");
		 * 
		 * }
		 */

		List<Character> chars = Lists.newArrayList();

		for (char c = 'A'; c < 'Z'; c++) {
			// if (c != 'T' && c != 'R') {
			chars.add(c);
			// }
		}

		Collections.shuffle(chars);

		System.out.println(chars.size());
		for (int i = 0; i < chars.size(); i++) {
			System.out.print(".put('" + Integer.toString(i, chars.size()) + "', '" + chars.get(i)
					+ "')");

		}

	}

	//@Test
	public void testSwearWords() {
		System.out.println(NamingUtils.decode("ANAL"));
	}

	
	@Test
	public void testByLocations() {
		// System.out.println(NamingUtils.encode(new AbstractLocation(0, 0)));
		int i = 0;
		ArrayList<NamedLocation> locations = new Zone(1l, 1, 150).getLocations();

		Collections.sort(locations, new Comparator<NamedLocation>() {
			@Override
			public int compare(NamedLocation o2, NamedLocation o1) {
				return Integer.valueOf(o1.getY()).compareTo(o2.getY()) * 10000 
						+ Integer.valueOf(o1.getX()).compareTo(o2.getX());
			}
		});

		for (NamedLocation loc : locations) {
			if (i++ > 100) {
				break;
			}
			// String str = NamingUtils.encode(loc);
			System.out.println(loc.toString() + " : " + loc.getName());
			/*
			 * assertTrue(NamingUtils.decode(str).getX() == loc.getX() &&
			 * NamingUtils.decode(str).getY() == loc.getY());
			 */
		}
	}

	/*
	 * @Test public void testByIds() {
	 * System.out.println(NamingUtils.decode("S"));
	 * System.out.println(NamingUtils.decode("SB"));
	 * System.out.println(NamingUtils.decode("SBB"));
	 * System.out.println(NamingUtils.decode("SBBB"));
	 * System.out.println(NamingUtils.decode("SBBBB")); for(long i=100;i<20;i++)
	 * { long id = i + 15625; String str = NamingUtils.encode(id);
	 * System.out.println(id + " : " + str); assertEquals(id,
	 * NamingUtils.decode(str)); } }
	 */
}
