package com.pepel.games.shuttle.util;

import com.google.common.collect.ImmutableBiMap;
import com.pepel.games.shuttle.model.geography.AbstractLocation;
import com.pepel.games.shuttle.model.geography.Location;

public class NamingUtils {
	/*BY IDS*/
	/*
	public static final ImmutableBiMap<Character, Character> replacements = new ImmutableBiMap.Builder<Character, Character>()
			.put('0', 'B').put('1', 'S').put('2', 'N').put('3', 'P').put('4', 'H').put('5', 'R')
			.put('6', 'K').put('7', 'Q').put('8', 'U').put('9', 'W').put('a', 'O').put('b', 'C')
			.put('c', 'V').put('d', 'A').put('e', 'L').put('f', 'G').put('g', 'X').put('h', 'T')
			.put('i', 'Y').put('j', 'M').put('k', 'F').put('l', 'D').put('m', 'E').put('n', 'J')
			.put('o', 'I').build();
	private static final ImmutableBiMap<Character, Character> inverseReplacements = replacements
			.inverse();

	public static String encode(long id) {
		char[] chars = Long.toString(id, replacements.size()).toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = replacements.get(chars[i]);
		}
		return new String(chars);
	}

	public static long decode(String s) {
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = inverseReplacements.get(chars[i]);
		}
		return Long.parseLong(new String(chars), replacements.size());
	}
	*/
	
	/*BY LOCATIONS*/
	
	private static final char minusChar = 'I';
	private static final char delimChar = 'A';

	private static final ImmutableBiMap<Character, Character> replacements = new ImmutableBiMap.Builder<Character, Character>()
			.put('0', 'H').put('1', 'T').put('2', 'D').put('3', 'R').put('4', 'N').put('5', 'B')
			.put('6', 'Y').put('7', 'L').put('8', 'M').put('9', 'S').put('a', 'W').put('b', 'V')
			.put('c', 'K').put('d', 'X').put('e', 'E').put('f', 'G').put('g', 'J').put('h', 'U')
			.put('i', 'F').put('j', 'Q').put('k', 'O').put('l', 'C').put('m', 'P').build();
	
	private static final ImmutableBiMap<Character, Character> inverseReplacements = replacements
			.inverse();
	
	
	public static String encode(Location loc) {
		StringBuilder sb = new StringBuilder();

		if (loc.getX() < 0) {
			sb.append(minusChar);
		}
		for (char digit : Integer.toString(Math.abs(loc.getX()), replacements.size()).toCharArray()) {
			sb.append(replacements.get(digit));
		}

		sb.append(loc.getY() < 0 ? minusChar : delimChar);
		for (char digit : Integer.toString(Math.abs(loc.getY()), replacements.size()).toCharArray()) {
			sb.append(replacements.get(digit));
		}

		return sb.toString();
	}
	
	
	
	public static Location decode(String str) {
		StringBuilder xSb = new StringBuilder();
		StringBuilder ySb = new StringBuilder();

		StringBuilder sb = null;
		for (char digit : str.toCharArray()) {
			if (digit == minusChar) {
				sb = (sb == null ? xSb : ySb);
				sb.append("-");
			} else if (digit == delimChar) {
				sb = ySb;
			} else {
				sb = (sb == null ? xSb : sb);
				sb.append(inverseReplacements.get(digit));
			}
		}

		return new AbstractLocation(Integer.parseInt(xSb.toString(), replacements.size()),
				Integer.parseInt(ySb.toString(), replacements.size()));
	}
	 
}
