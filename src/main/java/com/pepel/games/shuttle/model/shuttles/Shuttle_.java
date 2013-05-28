package com.pepel.games.shuttle.model.shuttles;

import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.geography.Planet;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-18T00:06:03.132+0700")
@StaticMetamodel(Shuttle.class)
public class Shuttle_ {
	public static volatile SingularAttribute<Shuttle, Long> id;
	public static volatile SingularAttribute<Shuttle, Player> player;
	public static volatile SingularAttribute<Shuttle, String> name;
	public static volatile SingularAttribute<Shuttle, Planet> departurePlanet;
	public static volatile SingularAttribute<Shuttle, Planet> destinationPlanet;
	public static volatile SingularAttribute<Shuttle, Long> departureTime;
	public static volatile ListAttribute<Shuttle, Container> containers;
}
