package com.pepel.games.shuttle.web.config;

import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import org.jboss.seam.security.annotations.LoggedIn;

import com.pepel.games.shuttle.web.security.annotations.NotLoggedIn;
import com.pepel.games.shuttle.web.security.annotations.NotSaved;

@ViewConfig
public interface PagesConfig {
	static enum Pages {
		@UrlMapping(pattern = "/login")
		@ViewPattern("/login.xhtml")
		@NotLoggedIn
		LOGIN,

		@UrlMapping(pattern = "/about")
		@ViewPattern("/about.xhtml")
		ABOUT,

		@UrlMapping(pattern = "/")
		@ViewPattern("/home.xhtml")
		@LoggedIn
		HOME,

		@UrlMapping(pattern = "/save")
		@ViewPattern("/save.xhtml")
		@LoggedIn
		@NotSaved
		SAVE,

		@UrlMapping(pattern = "/private")
		@ViewPattern("/private.xhtml")
		@LoggedIn
		PRIVATE,

		@ViewPattern("/*")
		@AccessDeniedView("/errors/denied.xhtml")
		@LoginView("/login.xhtml")
		@RestrictAtPhase(PhaseIdType.RESTORE_VIEW)
		ALL;
	}
}