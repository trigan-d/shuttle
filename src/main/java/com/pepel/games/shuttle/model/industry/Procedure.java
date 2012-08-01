package com.pepel.games.shuttle.model.industry;

import java.util.Collections;
import java.util.Set;

public class Procedure {
	private final Set<CargoAmount> raw;
	private final Set<CargoAmount> product;

	public Procedure(Set<CargoAmount> raw, Set<CargoAmount> product) {
		this.raw = raw;
		this.product = product;
	}

	public static Procedure simple(Cargo from, Cargo to) {
		return new Procedure(Collections.singleton(CargoAmount.one(from)),
				Collections.singleton(CargoAmount.one(to)));
	}

	public static Procedure combine(Set<CargoAmount> raw, CargoAmount product) {
		return new Procedure(raw, product.asSet());
	}

	public Set<CargoAmount> getFrom() {
		return raw;
	}

	public Set<CargoAmount> getTo() {
		return product;
	}
}
