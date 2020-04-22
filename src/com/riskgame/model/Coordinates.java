package com.riskgame.model;

import java.io.Serializable;

/**
 * This specifies the x and y coordinates for the territory
 * @author gauta
 *
 */
public class Coordinates implements Serializable{

	private static final long serialVersionUID = -6231586306352989967L;
	private double x;
	private double y;

	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
