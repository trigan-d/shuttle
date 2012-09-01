package com.pepel.games.shuttle.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Identity;

import com.pepel.games.shuttle.controller.AvailableProvinceProvider;
import com.pepel.games.shuttle.controller.CommonEntityManager;
import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.geography.Province;
import com.pepel.games.shuttle.web.config.ActionOutcome;
import com.pepel.games.shuttle.web.config.Bundles;
import com.pepel.games.shuttle.web.security.annotations.NotLoggedIn;

@RequestScoped
@Named
public class PlayerRegistrator {
	@Inject
	private Messages messages;

	@Inject
	private Identity identity;

	@Inject
	private MessageFactory messageFactory;

	@Inject
	private AvailableProvinceProvider availableProvinceProvider;

	@Inject
	private CommonEntityManager cem;

	@Inject
	private PlayerHolder playerHolder;

	@NotLoggedIn
	public ActionOutcome registerNewbie() {
		//messages.warn(Bundles.Common.key("reg.no.free.province"));
		//return ActionOutcome.failure;/*
		Province province = availableProvinceProvider.getProvinceForRegistration();
		if (province == null) {
			messages.warn(Bundles.Common.key("reg.no.free.province"));
			return ActionOutcome.failure;
		} else {
			Player player = new Player(messageFactory
					.info(Bundles.Common.key("reg.newbie.login"), province.getName()).build()
					.getText());
			cem.save(player, true);
			playerHolder.setCurrentPlayer(player);
			identity.login();
			return ActionOutcome.success;
		}
		//*/
	}
}
