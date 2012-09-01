package com.pepel.games.shuttle.web.config;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.jboss.seam.international.status.builder.BundleKey;

public enum Bundles {
	Common("com.pepel.games.shuttle.web.messages.common.Messages");

	private final String name;

	private Bundles(String name) {
		this.name = name;
	}

	public BundleKey key(String key) {
		return new BundleKey(name, key);
	}

	public String message(String key) {
		return ResourceBundle.getBundle(name).getString(key);
	}

	public FacesMessage message(Severity severity, String key) {
		return new FacesMessage(severity, message(key), null);
	}
}
