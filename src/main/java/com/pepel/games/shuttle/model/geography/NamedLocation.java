package com.pepel.games.shuttle.model.geography;

public class NamedLocation extends AbstractLocation {
	private String name;
	
	public NamedLocation(int x, int y) {
		super(x, y);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
