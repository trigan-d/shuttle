package com.pepel.games.shuttle.model.geography;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.pepel.games.shuttle.model.industry.Cargo;

@Entity
@Table(name = "provinces")
@org.hibernate.annotations.Table(appliesTo = "provinces", indexes = {
		@Index(name = "idx_provinces_xy", columnNames = { "x", "y" }),
		@Index(name = "idx_provinces_occupied", columnNames = { "occupied", "id" }) })
public class Province implements Serializable, Location {
	private static final long serialVersionUID = 7474122973140247977L;
	public static final int PROVINCE_WIDTH = 100;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(unique = true)
	@Index(name = "idx_provinces_name")
	private String name;

	@NotNull
	private int x;

	@NotNull
	private int y;

	@NotNull
	@Index(name = "idx_provinces_zone")
	private long zone;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "province", cascade = CascadeType.PERSIST)
	private List<Planet> planets;

	@NotNull
	private boolean occupied;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Cargo deficit;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Cargo proficit;

	public Province() {
	}
	
	public Province(Zone zone, NamedLocation location) {
		this.zone = zone.getId();
		x = location.getX();
		y = location.getY();
		name = location.getName();
		occupied = false;
		planets = Lists.newArrayList();
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

	public List<Planet> getPlanets() {
		return planets;
	}

	public void setPlanets(List<Planet> planets) {
		this.planets = planets;
	}

	public long getZone() {
		return zone;
	}

	public void setZone(long zone) {
		this.zone = zone;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Cargo getProficit() {
		return proficit;
	}

	public void setProficit(Cargo proficit) {
		this.proficit = proficit;
	}

	public Cargo getDeficit() {
		return deficit;
	}

	public void setDeficit(Cargo deficit) {
		this.deficit = deficit;
	}
}
