package com.pepel.games.shuttle.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.industry.Cargo;
import com.pepel.games.shuttle.model.industry.PlanetCargoAmount;
import com.pepel.games.shuttle.model.industry.PlanetCargoAmount.Direction;
import com.pepel.games.shuttle.util.TimeUtils;

public class CargoAmountTest {

	@Test
	public void checkCargoAmount() {
		Planet planet = new Planet(null, 0, 0, null);
		planet.getSupply().put(Cargo.Coal, 2);

		PlanetCargoAmount amount = new PlanetCargoAmount(planet,
				Direction.Supply, Cargo.Coal);
		amount.setLastAmount(1);
		amount.setLastChangeTime(System.currentTimeMillis()
				- (long) (TimeUtils.DAY_MILLIS * 1.5));
		planet.getCargoAmounts().add(amount);

		assertEquals(4, planet.getRealAmount(Direction.Supply, Cargo.Coal));
		assertEquals(0, planet.getRealAmount(Direction.Demand, Cargo.Coal));
		assertEquals(0, planet.getRealAmount(Direction.Supply, Cargo.Cattle));

		planet.getCargoAmounts().clear();
		assertEquals(2, planet.getRealAmount(Direction.Supply, Cargo.Coal));

		amount.setLastAmount(6);
		planet.getCargoAmounts().add(amount);
		planet.getSupply().clear();
		assertEquals(6, planet.getRealAmount(Direction.Supply, Cargo.Coal));
	}
}
