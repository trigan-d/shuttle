package com.pepel.games.shuttle.web.config;

import java.io.IOException;

import javax.faces.context.ExternalContext;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;

@HandlesExceptions
public class GlobalExceptionHandler {

	public void accessDeniedRedirect(@Handles CaughtException<AuthorizationException> evt,
			ExternalContext ctx, Messages messages) {
		try {
			ctx.redirect("errors/denied.jsf");
		} catch (IOException e) {
			messages.error(Bundles.Common.key("error.denied"));
		}
		evt.handled();
	}
}
