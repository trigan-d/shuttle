package com.pepel.games.shuttle.model.ports;

import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.geography.Planet;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-09T23:11:30.344+0700")
@StaticMetamodel(Port.class)
public class Port_ {
	public static volatile SingularAttribute<Port, Long> id;
	public static volatile SingularAttribute<Port, Planet> planet;
	public static volatile SingularAttribute<Port, Player> player;
}
