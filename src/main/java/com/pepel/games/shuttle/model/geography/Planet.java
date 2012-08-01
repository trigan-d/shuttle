package com.pepel.games.shuttle.model.geography;

import java.io.Serializable;
import java.util.List;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;

@Entity
@Table(name = "planets")
@org.hibernate.annotations.Table(appliesTo = "planets", indexes = @Index(name = "idx_planets_xy", columnNames = {
		"x", "y" }))
public class Planet implements Serializable, Location {
	private static final long serialVersionUID = 7474122973140247977L;

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
	private List<PlanetCargo> cargos;

	public Planet() {
	}

	public Planet(Province province, int x, int y, String name) {
		this.province = province;
		this.x = x;
		this.y = y;
		this.name = name;
		cargos = Lists.newArrayList();
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

	public List<PlanetCargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<PlanetCargo> cargos) {
		this.cargos = cargos;
	}

	public boolean isProvinceCapital() {
		return isProvinceCapital;
	}

	public void setProvinceCapital(boolean isProvinceCapital) {
		this.isProvinceCapital = isProvinceCapital;
	}
}
