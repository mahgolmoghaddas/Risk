package com.riskgame.enums;

/**
 * This provides the width and height provied for the frame of the Game play window
 * @author gauta
 *
 */
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
