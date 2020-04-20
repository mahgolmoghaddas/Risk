package com.riskgame.strategy;

import java.util.Random;

import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;

public class RandomPlayerStrategy extends PlayerStrategy {
	GameLogs gameLogs = GameLogs.getInstance();

	@Override
	public void runReinforcePhase(Player player) {
		PlayerStrategy playerStrategy = generateRandomPlayerStrategy(player);
		playerStrategy.runReinforcePhase(player);
	}

	@Override
	public void runAttackPhase(Player player) {
		PlayerStrategy playerStrategy = generateRandomPlayerStrategy(player);
		playerStrategy.runAttackPhase(player);
	}

	/**
	 * This method generates random behaviour for the player. He/she may play aggressive or conservative 
	 * player for the single game
	 * @return
	 * @throws Exception @param
	 */
	public PlayerStrategy generateRandomPlayerStrategy(Player player){
		PlayerStrategy playerStrategy = null;
		try {
			Random rd = new Random();
			boolean isGreedy = rd.nextBoolean();
			if (isGreedy) {
				System.out.println(player.getName()+"*** Playing as Aggressive Player ******");
				gameLogs.log("****"+player.getName()+":::Playing as Aggressive Player ******");
				playerStrategy = new AggressivePlayerStrategy();
			} else {
				System.out.println(player.getName()+"*** Playing as Conservative Player ******");
				gameLogs.log("****"+player.getName()+":::Playing as Conservative Player ******");
				playerStrategy = new ConservativePlayerStrategy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playerStrategy;
	}

}
