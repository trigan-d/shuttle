package com.pepel.games.shuttle.controller;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.geography.PlanetCargo;
import com.pepel.games.shuttle.model.geography.NamedLocation;
import com.pepel.games.shuttle.model.geography.Province;
import com.pepel.games.shuttle.model.geography.Zone;
import com.pepel.games.shuttle.model.geography.PlanetCargo.Direction;
import com.pepel.games.shuttle.model.industry.Cargo;

@Stateless
public class ProvinceGenerator {
	private static final int CITIES_IN_PROVINCE = 9;
	private static final int MIN_DISTANCE = 20;

	private static final int PASSENGERS_AND_MAIL_PERDAY = 2;
	private static final int PASSENGERS_AND_MAIL_PERDAY_CAPITAL = 4;

	private static final int MAX_CARGO_TYPES = 2;
	private static final int MIN_CARGO_TYPES_CAPITAL = 3;

	private static final int MAX_CARGO_AMOUNT = 3;
	private static final int MIN_CARGO_AMOUNT_CAPITAL = 2;

	@Inject
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Province generateProvince(Zone zone, NamedLocation location) {
		Province province = new Province(zone, location);
		Random rand = new Random();

		province.setDeficit(Cargo.randomFreight(rand, null));
		province.setProficit(Cargo.randomFreight(rand, EnumSet.of(province.getDeficit())));

		province.getPlanets().add(generateCapital(province, rand));
		for (int i = 0; i < CITIES_IN_PROVINCE; i++) {
			province.getPlanets().add(generatePlanet(province, rand, i));
		}

		em.persist(province);
		return province;
	}

	private Planet generateCapital(Province province, Random rand) {
		int x = province.getX() * Province.PROVINCE_WIDTH + Province.PROVINCE_WIDTH / 4
				+ rand.nextInt(Province.PROVINCE_WIDTH / 2);
		int y = province.getY() * Province.PROVINCE_WIDTH + Province.PROVINCE_WIDTH / 4
				+ rand.nextInt(Province.PROVINCE_WIDTH / 2);

		Planet capital = new Planet(province, x, y, province.getName());
		capital.setProvinceCapital(true);

		generatePlanetCargos(capital, rand);
		return capital;
	}

	private Planet generatePlanet(Province province, Random rand, int i) {
		int x, y;
		do {
			x = province.getX() * Province.PROVINCE_WIDTH + MIN_DISTANCE / 4
					+ rand.nextInt(Province.PROVINCE_WIDTH - MIN_DISTANCE / 2);
			y = province.getY() * Province.PROVINCE_WIDTH + MIN_DISTANCE / 4
					+ rand.nextInt(Province.PROVINCE_WIDTH - MIN_DISTANCE / 2);
		} while (getMinDistance(x, y, province.getPlanets()) <= MIN_DISTANCE);

		Planet planet = new Planet(province, x, y, province.getName() + "-" + i);

		generatePlanetCargos(planet, rand);
		return planet;
	}

	private void generatePlanetCargos(Planet planet, Random rand) {
		int passengersAndMailPerDay = planet.isProvinceCapital() ? PASSENGERS_AND_MAIL_PERDAY_CAPITAL
				: PASSENGERS_AND_MAIL_PERDAY;
		planet.getCargos().add(
				new PlanetCargo(planet, Direction.Supply, Cargo.Passengers, passengersAndMailPerDay));
		planet.getCargos().add(
				new PlanetCargo(planet, Direction.Demand, Cargo.Passengers, passengersAndMailPerDay));
		planet.getCargos().add(
				new PlanetCargo(planet, Direction.Supply, Cargo.Mail, passengersAndMailPerDay));
		planet.getCargos().add(
				new PlanetCargo(planet, Direction.Demand, Cargo.Mail, passengersAndMailPerDay));

		generatePlanetFreights(planet, rand, Direction.Supply);
		generatePlanetFreights(planet, rand, Direction.Demand);
	}

	private void generatePlanetFreights(Planet planet, Random rand, Direction direction) {
		int count = rand.nextInt(MAX_CARGO_TYPES)
				+ (planet.isProvinceCapital() ? MIN_CARGO_TYPES_CAPITAL : 0);
		
		EnumSet<Cargo> cargos = EnumSet.of(planet.getProvince().getDeficit(), planet.getProvince()
				.getProficit());
		
		for (int i = 0; i < count; i++) {
			Cargo cargo = Cargo.randomFreight(rand, cargos);
			planet.getCargos().add(
					new PlanetCargo(planet, direction, cargo, rand.nextInt(MAX_CARGO_AMOUNT)
							+ (planet.isProvinceCapital() ? MIN_CARGO_AMOUNT_CAPITAL : 1)));
			cargos.add(cargo);
		}
		
		Cargo additional = rand.nextBoolean() ? Cargo.randomFreight(rand, cargos)
				: (direction == Direction.Supply ? planet.getProvince().getProficit() : planet
						.getProvince().getDeficit());
		planet.getCargos().add(
				new PlanetCargo(planet, direction, additional, rand.nextInt(MAX_CARGO_AMOUNT)
						+ (planet.isProvinceCapital() ? MIN_CARGO_AMOUNT_CAPITAL : 1)));
	}

	private double getMinDistance(int x, int y, List<Planet> planets) {
		double min = Province.PROVINCE_WIDTH + 2;
		for (Planet planet : planets) {
			int xSide = planet.getX() - x;
			int ySide = planet.getY() - y;
			double distance = Math.sqrt(xSide * xSide + ySide * ySide);
			if (distance < min) {
				min = distance;
			}
		}
		return min;
	}
}
