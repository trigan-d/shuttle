package com.pepel.games.shuttle.model.geography;

import com.pepel.games.shuttle.model.industry.Cargo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-08-01T22:34:12.543+0700")
@StaticMetamodel(Province.class)
public class Province_ {
	public static volatile SingularAttribute<Province, Long> id;
	public static volatile SingularAttribute<Province, String> name;
	public static volatile SingularAttribute<Province, Integer> x;
	public static volatile SingularAttribute<Province, Integer> y;
	public static volatile SingularAttribute<Province, Long> zone;
	public static volatile ListAttribute<Province, Planet> planets;
	public static volatile SingularAttribute<Province, Boolean> occupied;
	public static volatile SingularAttribute<Province, Cargo> deficit;
	public static volatile SingularAttribute<Province, Cargo> proficit;
}
