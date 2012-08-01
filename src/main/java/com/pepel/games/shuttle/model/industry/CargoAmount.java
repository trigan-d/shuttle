package com.pepel.games.shuttle.model.industry;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class CargoAmount implements Serializable {
	private final Cargo cargo;
	private final double amount;

	public CargoAmount(Cargo cargo, double amount) {
		this.cargo = cargo;
		this.amount = amount;
	}

	public static CargoAmount one(Cargo cargo) {
		return new CargoAmount(cargo, 1);
	}

	public static CargoAmount of(Cargo cargo, double amount) {
		return of(cargo, amount);
	}

	public Set<CargoAmount> asSet() {
		return Collections.singleton(this);
	}

	public Cargo getCargo() {
		return cargo;
	}

	public double getAmount() {
		return amount;
	}
}
