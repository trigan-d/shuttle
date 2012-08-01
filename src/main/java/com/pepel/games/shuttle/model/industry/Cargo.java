package com.pepel.games.shuttle.model.industry;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

public enum Cargo {
	Passengers,
	Mail,
	Food,
	Goods,
	Logs,
	Pulpwood,
	Paper,
	Lumber,
	Iron,
	Coal,
	Steel,
	Cattle,
	Wool,
	Grain,
	Produce,
	Cotton,
	Sugar,
	Fertilizer;

	public static final List<Cargo> freight;

	static {
		EnumSet<Cargo> freightSet = EnumSet.allOf(Cargo.class);
		freightSet.remove(Passengers);
		freightSet.remove(Mail);
		freight = ImmutableList.copyOf(freightSet);
	}
	
	public static Cargo randomFreight(Random rand, EnumSet<Cargo> except) {
		Cargo res = null;
		do {
			res = freight.get(rand.nextInt(freight.size()));
		} while (except != null && except.contains(res));
		return res;
	}
}
