package com.riskgame.strategy;

import java.util.Random;

import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
/**
 * This class gives concrete implementation of Random Player's Strategy
 *
 * @author Himani
 * 
 */
public class RandomPlayerStrategy extends PlayerStrategy {

	private static final long serialVersionUID = -8411243203693203201L;
	transient GameLogs gameLogs = GameLogs.getInstance();

	@Override
	public void runReinforcePhase(Player player,Board board) {
		PlayerStrategy playerStrategy = generateRandomPlayerStrategy(player);
		playerStrategy.runReinforcePhase(player,board);
	}

	@Override
	public void runAttackPhase(Player player,Board board) {
		PlayerStrategy playerStrategy = generateRandomPlayerStrategy(player);
		playerStrategy.runAttackPhase(player,board);
	}

	/**
	 * This method generates random behaviour for the player. He/she may play
	 * aggressive or conservative player for the single game
	 * 
	 * @return
	 * @param player
	 */
	public PlayerStrategy generateRandomPlayerStrategy(Player player) {
		PlayerStrategy playerStrategy = null;
		try {

			if (GameController.getInstance().isSavedGame()) {
				gameLogs = GameLogs.getInstance();
			}
			Random rd = new Random();
			boolean isGreedy = rd.nextBoolean();
			if (isGreedy) {
				System.out.println(player.getName() + "*** Playing as Aggressive Player ******");
				gameLogs.log("****" + player.getName() + ":::Playing as Aggressive Player ******");
				playerStrategy = new AggressivePlayerStrategy();
			} else {
				System.out.println(player.getName() + "*** Playing as Conservative Player ******");
				gameLogs.log("****" + player.getName() + ":::Playing as Conservative Player ******");
				playerStrategy = new ConservativePlayerStrategy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playerStrategy;
	}

}
