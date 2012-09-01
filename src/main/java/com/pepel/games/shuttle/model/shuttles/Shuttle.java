package com.pepel.games.shuttle.model.shuttles;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.solder.core.Veto;

import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.geography.Planet;

@Entity
@Table(name = "shuttles", uniqueConstraints = @UniqueConstraint(columnNames = {
		"player_id", "name" }))
@Veto
public class Shuttle implements Serializable {
	private static final long serialVersionUID = 7474122973140247977L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "player_id")
	@Index(name = "idx_shuttles_player")
	private Player player;

	@NotBlank
	private String name;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "departure_planet_id")
	private Planet departurePlanet;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "destination_planet_id")
	private Planet destinationPlanet;

	@Column(name = "departure_time")
	private long departureTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shuttle")
	//@orde
	private List<Container> containers;

	public Shuttle() {
	}

	public Shuttle(Player player, String name) {
		this.player = player;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public Planet getDeparturePlanet() {
		return departurePlanet;
	}

	public void setDeparturePlanet(Planet departurePlanet) {
		this.departurePlanet = departurePlanet;
	}

	public Planet getDestinationPlanet() {
		return destinationPlanet;
	}

	public void setDestinationPlanet(Planet destinationPlanet) {
		this.destinationPlanet = destinationPlanet;
	}

	public long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(long departureTime) {
		this.departureTime = departureTime;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
