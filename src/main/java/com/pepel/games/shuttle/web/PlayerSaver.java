package com.pepel.games.shuttle.web;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;

import com.pepel.games.shuttle.controller.CommonEntityManager;
import com.pepel.games.shuttle.model.Player;
import com.pepel.games.shuttle.model.Player_;
import com.pepel.games.shuttle.web.config.ActionOutcome;
import com.pepel.games.shuttle.web.config.Bundles;
import com.pepel.games.shuttle.web.security.annotations.NotSaved;

@ViewScoped
@Named
public class PlayerSaver implements Serializable {
	private static final long serialVersionUID = 669166891264583481L;

	@Inject
	private CommonEntityManager cem;

	@Inject
	@Authenticated
	private Player currentPlayer;

	@Inject
	@Authenticated
	private Event<Player> playerEventSrc;

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private String phoneOrEmail;

	@NotSaved
	public ActionOutcome save() {
		currentPlayer.setName(username);
		currentPlayer.setPassword(password);
		if (phoneOrEmail != null) {
			String phone = parsePhone(phoneOrEmail);
			if (phone != null) {
				currentPlayer.setPhone(phone);
			} else {
				currentPlayer.setEmail(parseEmail(phoneOrEmail));
			}
		}
		currentPlayer.setSaved(true);
		playerEventSrc.fire(cem.save(currentPlayer, false));
		return ActionOutcome.success;
	}

	public void validateUsername(FacesContext ctx, UIComponent toValidate, Object value) {
		if (!currentPlayer.getName().equals(value)) {
			String username = value.toString();
			try {
				new MessageFormat(Bundles.Common.message("reg.newbie.login")).parse(username);
				((UIInput) toValidate).setValid(false);
				ctx.addMessage(toValidate.getClientId(ctx), Bundles.Common.message(
						FacesMessage.SEVERITY_ERROR, "error.username.reserved"));
			} catch (ParseException e) {
				if (cem.findByAttribute(Player_.name, username) != null) {
					((UIInput) toValidate).setValid(false);
					ctx.addMessage(toValidate.getClientId(ctx), Bundles.Common.message(
							FacesMessage.SEVERITY_ERROR, "error.username.occupied"));
				}
			}
		}
	}

	public void validatePhoneOrEmail(FacesContext ctx, UIComponent toValidate, Object value) {
		if (value != null && parsePhone(value.toString()) == null
				&& parseEmail(value.toString()) == null) {
			((UIInput) toValidate).setValid(false);
			ctx.addMessage(toValidate.getClientId(ctx), Bundles.Common.message(
					FacesMessage.SEVERITY_ERROR, "error.phoneOrEmail.malformed"));
		}
	}

	private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	private Pattern emailPattern = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN
			+ "|" + IP_DOMAIN + ")$", Pattern.CASE_INSENSITIVE);

	public String parseEmail(String value) {
		if (value == null) {
			return null;
		} else {
			value = value.trim();
			return emailPattern.matcher(value).matches() ? value : null;
		}
	}

	private Pattern phonePattern = Pattern.compile("\\d{10,}");
	private Pattern notDigitsPattern = Pattern.compile("[^\\d]");

	public String parsePhone(String value) {
		if (value == null) {
			return null;
		} else {
			value = notDigitsPattern.matcher(value).replaceAll("");
			return phonePattern.matcher(value).matches() ? value : null;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneOrEmail() {
		return phoneOrEmail;
	}

	public void setPhoneOrEmail(String phoneOrEmail) {
		this.phoneOrEmail = phoneOrEmail;
	}
}
