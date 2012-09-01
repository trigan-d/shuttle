package com.pepel.games.shuttle.model.industry;

import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.industry.PlanetCargoAmount.Direction;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-11T20:43:08.399+0700")
@StaticMetamodel(PlanetCargoAmount.class)
public class PlanetCargoAmount_ {
	public static volatile SingularAttribute<PlanetCargoAmount, Long> id;
	public static volatile SingularAttribute<PlanetCargoAmount, Planet> planet;
	public static volatile SingularAttribute<PlanetCargoAmount, Direction> direction;
	public static volatile SingularAttribute<PlanetCargoAmount, Cargo> cargo;
	public static volatile SingularAttribute<PlanetCargoAmount, Long> lastChangeTime;
	public static volatile SingularAttribute<PlanetCargoAmount, Integer> lastAmount;
}
