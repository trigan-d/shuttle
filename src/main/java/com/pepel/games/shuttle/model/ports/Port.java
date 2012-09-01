package com.pepel.games.shuttle.model.ports;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.jboss.solder.core.Veto;

import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.geography.Planet;

@Entity
@Table(name = "ports")
@Veto
public class Port implements Serializable {
	private static final long serialVersionUID = 7474122973140247977L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "planet_id")
	@Index(name = "idx_ports_planet")
	private Planet planet;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "player_id")
	@Index(name = "idx_ports_player")
	private Player player;

	public Port() {
	}

	public Port(Planet planet, Player player) {
		this.planet = planet;
		this.player = player;
	}

	public long getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public Planet getPlanet() {
		return planet;
	}
}
