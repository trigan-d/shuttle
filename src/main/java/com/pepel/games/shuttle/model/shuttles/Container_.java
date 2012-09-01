package com.pepel.games.shuttle.model.shuttles;

import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.industry.Cargo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-11T20:43:13.435+0700")
@StaticMetamodel(Container.class)
public class Container_ {
	public static volatile SingularAttribute<Container, Long> id;
	public static volatile SingularAttribute<Container, Planet> departurePlanet;
	public static volatile SingularAttribute<Container, Cargo> cargo;
	public static volatile SingularAttribute<Container, Shuttle> shuttle;
	public static volatile SingularAttribute<Container, Integer> position;
}
