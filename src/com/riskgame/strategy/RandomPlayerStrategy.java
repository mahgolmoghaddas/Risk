package com.riskgame.strategy;

import java.util.Random;
import com.riskgame.model.Player;

public class RandomPlayerStrategy extends PlayerStrategy {

	@Override
	public void runReinforcePhase(Player player) {
		PlayerStrategy playerStrategy = generateRandomPlayerStrategy();
		playerStrategy.runReinforcePhase(player);
	}

	@Override
	public void runAttackPhase(Player player) {
		PlayerStrategy playerStrategy = generateRandomPlayerStrategy();
		playerStrategy.runAttackPhase(player);
	}

	/**
	 * This method generates random behaviour for the player. He/she may play aggressive or conservative 
	 * player for the single game
	 * @return
	 * @throws Exception @param
	 */
	public PlayerStrategy generateRandomPlayerStrategy(){
		PlayerStrategy playerStrategy = null;
		try {
			Random rd = new Random();
			boolean isGreedy = rd.nextBoolean();
			if (isGreedy) {
				System.out.println("*** Playing as Aggressive Player ******");
				playerStrategy = new AggressivePlayerStrategy();
			} else {
				System.out.println("*** Playing as Conservative Player ******");
				playerStrategy = new ConservativePlayerStrategy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playerStrategy;
	}

}
