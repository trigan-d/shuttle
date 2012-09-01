package com.pepel.games.shuttle.model.shuttles;

import java.io.Serializable;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.jboss.solder.core.Veto;

import com.pepel.games.shuttle.model.geography.Planet;
import com.pepel.games.shuttle.model.industry.Cargo;

@Entity
@Table(name = "containers", uniqueConstraints = @UniqueConstraint(columnNames = {
		"shuttle_id", "position" }))
@org.hibernate.annotations.Table(appliesTo = "containers", indexes = @Index(name = "idx_container_shuttle_position", columnNames = {
		"shuttle_id", "position" }))
@Veto
public class Container implements Serializable {
	private static final long serialVersionUID = 8876097875823411007L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "departure_planet_id")
	private Planet departurePlanet;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Cargo cargo;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "shuttle_id")
	private Shuttle shuttle;

	@NotNull
	@Min(0)
	private int position;

	public Container() {
	}

	public Container(Planet departurePlanet, Cargo cargo, Shuttle shuttle) {
		this.departurePlanet = departurePlanet;
		this.cargo = cargo;
		this.shuttle = shuttle;
	}

	public long getId() {
		return id;
	}

	public Planet getDeparturePlanet() {
		return departurePlanet;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public Shuttle getShuttle() {
		return shuttle;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
