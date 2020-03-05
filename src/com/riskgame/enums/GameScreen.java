package com.riskgame.enums;

public enum GameScreen {

	WIDTH(0.8), HEIGHT(0.9);

	private double value;

	GameScreen(double d) {
		this.value = d;
	}

	public double getValue() {
		return value;
	}
}
