package com.pepel.games.shuttle.web.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.web.Authenticated;
import com.pepel.games.shuttle.web.security.annotations.NotLoggedIn;
import com.pepel.games.shuttle.web.security.annotations.NotSaved;

public class Restrictions {
	public @Secures @NotLoggedIn boolean isNotLoggedIn(Identity identity) {
		return ! identity.isLoggedIn();
	}

	public @Secures @NotSaved boolean isNotSaved(@Authenticated Player player) {
		return ! (player == null || player.isSaved());
	}
}
