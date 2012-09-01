package com.pepel.games.shuttle.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.jboss.seam.security.events.PostLoggedOutEvent;

import com.pepel.games.shuttle.model.Player;

@SessionScoped
public class PlayerHolder implements Serializable {
	private static final long serialVersionUID = 838936685499329395L;

	@Produces
	@Authenticated
	@Named
	private Player currentPlayer;

	public PlayerHolder() {

	}

	public void onPlayerChanged(@Observes @Authenticated Player player) {
		setCurrentPlayer(player);
	}

	public void onLogout(@Observes PostLoggedOutEvent event) {
		currentPlayer = null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
