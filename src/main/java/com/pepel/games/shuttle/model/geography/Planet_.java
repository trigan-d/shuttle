package com.pepel.games.shuttle.model.geography;

import com.pepel.games.shuttle.model.industry.PlanetCargoAmount;
import com.pepel.games.shuttle.model.ports.Port;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-09T23:28:31.876+0700")
@StaticMetamodel(Planet.class)
public class Planet_ {
	public static volatile SingularAttribute<Planet, Long> id;
	public static volatile SingularAttribute<Planet, String> name;
	public static volatile SingularAttribute<Planet, Integer> x;
	public static volatile SingularAttribute<Planet, Integer> y;
	public static volatile SingularAttribute<Planet, Province> province;
	public static volatile SingularAttribute<Planet, Boolean> isProvinceCapital;
	public static volatile ListAttribute<Planet, PlanetCargoAmount> cargoAmounts;
	public static volatile ListAttribute<Planet, Port> ports;
}
