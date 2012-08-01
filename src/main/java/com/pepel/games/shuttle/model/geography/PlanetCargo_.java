package com.pepel.games.shuttle.model.geography;

import com.pepel.games.shuttle.model.geography.PlanetCargo.Direction;
import com.pepel.games.shuttle.model.industry.Cargo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-01T21:52:37.298+0700")
@StaticMetamodel(PlanetCargo.class)
public class PlanetCargo_ {
	public static volatile SingularAttribute<PlanetCargo, Long> id;
	public static volatile SingularAttribute<PlanetCargo, Direction> direction;
	public static volatile SingularAttribute<PlanetCargo, Cargo> cargo;
	public static volatile SingularAttribute<PlanetCargo, Integer> perDay;
	public static volatile SingularAttribute<PlanetCargo, Date> lastChange;
	public static volatile SingularAttribute<PlanetCargo, Integer> lastAmount;
	public static volatile SingularAttribute<PlanetCargo, Planet> planet;
}
