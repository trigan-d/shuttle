package com.pepel.games.shuttle.web.security;

import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.AuthorizationCheckEvent;
import org.jboss.seam.security.events.PostLoggedOutEvent;
import org.jboss.solder.servlet.http.CookieParam;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import com.ocpsoft.pretty.faces.util.StringUtils;
import com.pepel.games.shuttle.controller.CommonEntityManager;
import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.Player_;
import com.pepel.games.shuttle.util.ServletUtils;
import com.pepel.games.shuttle.web.PlayerHolder;
import com.pepel.games.shuttle.web.config.Bundles;

@RequestScoped
@Named
public class PlayerAuthenticator extends BaseAuthenticator implements Authenticator {
	public static final String REMEMBER_PLAYER_COOKIE = "com.pepel.games.shuttle.player.remembered";
	public static final int REMEMBER_PLAYER_COOKIE_MAX_AGE = 60 * 60 * 24 * 30;

	@Inject
	private Credentials credentials;

	@Inject
	private Identity identity;

	@Inject
	private ExternalContext ctx;

	@Inject
	private Messages messages;

	@Inject
	private PlayerHolder playerHolder;

	@Inject
	private CommonEntityManager cem;

	@Inject
	@CookieParam(REMEMBER_PLAYER_COOKIE)
	private String rememberedPlayerCookie;

	@Override
	public void authenticate() {
		Player authenticatedPlayer = null;

		if (playerHolder.getCurrentPlayer() != null) {
			authenticatedPlayer = playerHolder.getCurrentPlayer();
		}
		if (authenticatedPlayer == null && !StringUtils.isBlank(rememberedPlayerCookie)) {
			authenticatedPlayer = cem.findByAttribute(Player_.rememberedLoginCookie,
					rememberedPlayerCookie);
		}
		if (authenticatedPlayer == null && !StringUtils.isBlank(credentials.getUsername())
				&& credentials.getCredential() instanceof PasswordCredential) {
			Player found = cem.findByAttribute(Player_.name, credentials.getUsername());
			if (found != null
					&& found.getPassword().equals(
							((PasswordCredential) credentials.getCredential()).getValue())) {
				authenticatedPlayer = found;
			} else {
				messages.error(Bundles.Common.key("auth.wrongCredentials"));
			}
		}

		if (authenticatedPlayer != null) {
			if (playerHolder.getCurrentPlayer() == null) {
				playerHolder.setCurrentPlayer(authenticatedPlayer);
			}
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(new SimpleUser(authenticatedPlayer.getName()));
		} else {
			setStatus(AuthenticationStatus.FAILURE);
		}
	}

	private String generatePlayerCookie(Player player) {
		return DigestUtils.md5Hex(player.getName() + ThreadLocalRandom.current().nextInt(10000)
				+ player.getPassword());
	}

	@Override
	public void postAuthenticate() {
		Player currentPlayer = playerHolder.getCurrentPlayer();

		if (currentPlayer.getRememberedLoginCookie() == null) {
			currentPlayer.setRememberedLoginCookie(generatePlayerCookie(currentPlayer));
			cem.save(currentPlayer, false);
		}

		ctx.addResponseCookie(REMEMBER_PLAYER_COOKIE, currentPlayer.getRememberedLoginCookie(),
				ServletUtils.cookiePropertiesWithMaxAge(REMEMBER_PLAYER_COOKIE_MAX_AGE));
	}

	public void findPlayerByCookie(@Observes AuthorizationCheckEvent event) {
		if (!identity.isLoggedIn() && !StringUtils.isBlank(rememberedPlayerCookie)) {
			identity.login();
		}
	}

	public void onLogout(@Observes PostLoggedOutEvent event) {
		ServletUtils.removeCookie(ctx, REMEMBER_PLAYER_COOKIE);
	}
}
