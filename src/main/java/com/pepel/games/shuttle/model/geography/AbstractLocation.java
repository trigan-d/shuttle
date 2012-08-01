package com.pepel.games.shuttle.model.geography;

public class AbstractLocation implements Location {
	private int x;
	private int y;

	public AbstractLocation(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void incrementX() {
		x++;
	}

	public void incrementY() {
		y++;
	}
}