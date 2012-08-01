package com.pepel.games.shuttle.model.industry;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Range;

import com.pepel.games.shuttle.model.geography.Planet;

@Entity
@Table(name = "planet_cargo", uniqueConstraints = @UniqueConstraint(columnNames = { "planet_id",
		"direction", "cargo" }))
@org.hibernate.annotations.Table(appliesTo = "planet_cargo", indexes = @Index(name = "idx_planet_cargo_direction", columnNames = {
		"planet_id", "direction", "cargo" }))
public class PlanetCargoAmount {
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_change")
	private Date lastChange;

	@NotNull
	@Range(min = 0, max = MAX_AMOUNT)
	@Column(name = "last_amount")
	private int lastAmount;

	public PlanetCargoAmount() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}

	public int getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(int lastAmount) {
		this.lastAmount = lastAmount;
	}
}
