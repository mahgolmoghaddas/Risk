package com.riskgame.strategy;

import com.riskgame.model.Player;

public interface PlayerStrategy {

	public void runSetupPhase(Player player);
	
	public void runReinforcePhase(Player player);

	public void runAttackPhase(Player attacker);

	public void runFortifyPhase(Player player);

}
