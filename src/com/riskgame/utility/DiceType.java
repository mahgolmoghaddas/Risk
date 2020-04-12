package com.riskgame.utility;

/**
 * This enum specifies the types of dice used in the Risk Game
 * @author pushpa
 *
 */
public enum DiceType {
	Attack(3),Defend(2);
	
	private int maxRollAllowed ;
	
	DiceType(int maxRollAllowed){
		this.maxRollAllowed =maxRollAllowed;
	}
	public int getMaxAllowedRoll() {
		return this.maxRollAllowed;
	}
}
