package com.pepel.games.shuttle.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.solder.core.Veto;

import com.pepel.games.shuttle.model.ports.Port;
import com.pepel.games.shuttle.model.shuttles.Shuttle;

@Entity
@Table(name = "players")
@Veto
public class Player implements Serializable {
	private static final long serialVersionUID = 7474122973140247977L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(unique = true)
	@Index(name = "idx_players_name")
	private String name;

	@NotBlank
	private String password;

	@Column(name = "remembered_login_cookie")
	@Index(name = "idx_players_remembered_login_cookie")
	private String rememberedLoginCookie;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
	private List<Port> ports;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
	private List<Shuttle> shuttles;

	@NotNull
	private boolean saved = false;

	@Email
	private String email;

	private String phone;

	public Player() {
	}

	public Player(String name) {
		this.name = name;
		this.password = "initial password for " + name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public List<Shuttle> getShuttles() {
		return shuttles;
	}

	public String getRememberedLoginCookie() {
		return rememberedLoginCookie;
	}

	public void setRememberedLoginCookie(String rememberedLoginCookie) {
		this.rememberedLoginCookie = rememberedLoginCookie;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
