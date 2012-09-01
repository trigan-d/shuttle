package com.pepel.games.shuttle.model;

import com.pepel.games.shuttle.model.ports.Port;
import com.pepel.games.shuttle.model.shuttles.Shuttle;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-27T22:56:30.088+0700")
@StaticMetamodel(Player.class)
public class Player_ {
	public static volatile SingularAttribute<Player, Long> id;
	public static volatile SingularAttribute<Player, String> name;
	public static volatile SingularAttribute<Player, String> password;
	public static volatile SingularAttribute<Player, String> rememberedLoginCookie;
	public static volatile ListAttribute<Player, Port> ports;
	public static volatile ListAttribute<Player, Shuttle> shuttles;
	public static volatile SingularAttribute<Player, Boolean> saved;
	public static volatile SingularAttribute<Player, String> email;
	public static volatile SingularAttribute<Player, String> phone;
}
