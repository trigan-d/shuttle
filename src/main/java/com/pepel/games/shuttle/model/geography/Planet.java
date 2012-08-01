package com.pepel.games.shuttle.model.geography;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pepel.games.shuttle.model.industry.Cargo;
import com.pepel.games.shuttle.model.industry.PlanetCargoAmount;

@Entity
@Table(name = "planets")
@org.hibernate.annotations.Table(appliesTo = "planets", indexes = @Index(name = "idx_planets_xy", columnNames = {
		"x", "y" }))
public class Planet implements Serializable, Location {
	private static final long serialVersionUID = 7474122973140247977L;

	private static final Gson gson = new Gson();
	private static final Type cargoAmountsType = new TypeToken<HashMap<Cargo, Integer>>() {
	}.getType();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(unique = true)
	@Index(name = "idx_planets_name")
	private String name;

	@NotNull
	private int x;

	@NotNull
	private int y;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "province_id")
	private Province province;

	@NotNull
	@Column(name = "is_province_capital")
	private boolean isProvinceCapital;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "planet", cascade = CascadeType.PERSIST)
	private List<PlanetCargoAmount> cargoAmounts;

	@Transient
	private EnumMap<Cargo, Integer> supply;

	@Transient
	private EnumMap<Cargo, Integer> demand;

	public Planet() {
	}

	public Planet(Province province, int x, int y, String name) {
		this.province = province;
		this.x = x;
		this.y = y;
		this.name = name;
		cargoAmounts = Lists.newArrayList();
		supply = Maps.newEnumMap(Cargo.class);
		demand = Maps.newEnumMap(Cargo.class);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public List<PlanetCargoAmount> getCargoAmounts() {
		return cargoAmounts;
	}

	public void setCargoAmounts(List<PlanetCargoAmount> cargoAmounts) {
		this.cargoAmounts = cargoAmounts;
	}

	public boolean isProvinceCapital() {
		return isProvinceCapital;
	}

	public void setProvinceCapital(boolean isProvinceCapital) {
		this.isProvinceCapital = isProvinceCapital;
	}

	public EnumMap<Cargo, Integer> getSupply() {
		return supply;
	}

	public EnumMap<Cargo, Integer> getDemand() {
		return demand;
	}

	@Column(name = "supply")
	@Access(AccessType.PROPERTY)
	@SuppressWarnings("unused")
	private String getSupplyAsString() {
		return gson.toJson(supply);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void setSupplyAsString(String supplyAsString) {
		supply = Maps.newEnumMap((HashMap<Cargo, Integer>) gson.fromJson(
				supplyAsString, cargoAmountsType));
	}

	@Column(name = "demand")
	@Access(AccessType.PROPERTY)
	@SuppressWarnings("unused")
	private String getDemandAsString() {
		return gson.toJson(demand);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void setDemandAsString(String demandAsString) {
		demand = Maps.newEnumMap((HashMap<Cargo, Integer>) gson.fromJson(
				demandAsString, cargoAmountsType));
	}
}
