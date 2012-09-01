package com.pepel.games.shuttle.model.industry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Range;
import org.jboss.solder.core.Veto;

import com.pepel.games.shuttle.model.geography.Planet;

@Entity
@Table(name = "planet_cargo", uniqueConstraints = @UniqueConstraint(columnNames = {
		"planet_id", "direction", "cargo" }))
@org.hibernate.annotations.Table(appliesTo = "planet_cargo", indexes = @Index(name = "idx_planet_cargo_direction", columnNames = {
		"planet_id", "direction", "cargo" }))
@Veto
public class PlanetCargoAmount implements Serializable {
	private static final long serialVersionUID = 2297942204801661722L;

	public static final int MAX_AMOUNT = 9;

	public enum Direction {
		Supply, Demand
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "planet_id")
	private Planet planet;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Direction direction;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Cargo cargo;

	@NotNull
	@Column(name = "last_change_time")
	private long lastChangeTime;

	@NotNull
	@Range(min = 0, max = MAX_AMOUNT)
	@Column(name = "last_amount")
	private int lastAmount;

	public PlanetCargoAmount() {
	}

	public PlanetCargoAmount(Planet planet, Direction direction, Cargo cargo) {
		this.planet = planet;
		this.direction = direction;
		this.cargo = cargo;
	}

	public long getId() {
		return id;
	}

	public Planet getPlanet() {
		return planet;
	}

	public Direction getDirection() {
		return direction;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public int getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(int lastAmount) {
		this.lastAmount = lastAmount;
	}

	public long getLastChangeTime() {
		return lastChangeTime;
	}

	public void setLastChangeTime(long lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
}
