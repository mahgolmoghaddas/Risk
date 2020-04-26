
package com.riskgame.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.TournamentModel;
import com.riskgame.model.World;
import com.riskgame.strategy.PlayerStrategy;
import com.riskgame.utility.DiceUtility;
import com.riskgame.utility.GameUtility;
import com.riskgame.view.TournamentPhaseView;
import com.riskgame.view.TournamentView;

/**
 * This class handles the Tournament mode Implementation for the Game
 * 
 * @author Himani
 *
 */
public class TournamentController {

	private GameLogs gameLogs = GameLogs.getInstance();
	private GameController gameController = GameController.getInstance();
	private GameUtility gameUtility = new GameUtility();
	private HashMap<String, HashMap<String, String>> tournamentResult = new HashMap<>();
	DiceUtility diceUtility = new DiceUtility();

	public void displayTournamentOptions() {
		new TournamentView();
	}

	public void startTournament(TournamentModel tournamentModel) {
		HashMap<String, String> gameResultMap = null;
		try {
			new TournamentPhaseView(gameLogs);
			if (tournamentModel != null && tournamentModel.getWorldList() != null
					&& !tournamentModel.getWorldList().isEmpty() && tournamentModel.getNoOfGame() > 0
					&& tournamentModel.getPlayerList() != null && !tournamentModel.getPlayerList().isEmpty()
					&& tournamentModel.getTotalTurns() >= 10) {

				for (int i = 0; i < tournamentModel.getWorldList().size(); i++) {
					gameResultMap = new HashMap<>();
					World world = tournamentModel.getWorldList().get(i);
					for (int j = 1; j <= tournamentModel.getNoOfGame(); j++) {

						gameLogs.log("****STARTED GAME " + j + " FOR MAP " + (i + 1));

						String gameResult = startGame(tournamentModel.getPlayerList(), world,
								tournamentModel.getTotalTurns());
						gameResultMap.put("GAME" + j, gameResult);
					}
					tournamentResult.put("MAP" + i, gameResultMap);
				}
				gameLogs.log("Tournament Result " + tournamentResult);
			} else {
				gameLogs.log("Required data for the tournament not provided.....");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String startGame(ArrayList<Player> playerList, World world, int maxAllowedTurn) {
		String gameResult = "";
		int turnCount = 1;
		try {
			gameLogs.log(" [Pre SETUP START]  ");
			Board board = executePreSetup(playerList, world);
			gameLogs.log(" [Pre SETUP END]  ");

			gameLogs.log(" [DICE ROLL FOR TURN MANAGEMENT START]  ");
			rollDiceForTurnManagement(playerList);
			gameLogs.log(" [DICE ROLL FOR TURN MANAGEMENT END]  ");

			// run setupPhase here
			gameLogs.log(" [SETUP PHASE START] ");
			autoRunSetupPhase(board);
			gameLogs.log(" [SETUP PHASE END]");

			gameLogs.log(" [ACTUAL GAME PLAY PHASE START] ");
			while (turnCount <= maxAllowedTurn && gameUtility.hasPlayerWon(board)) {// Need to add 1 more case here
				Player activePlayer = board.getActivePlayer();
				gameLogs.log("############CURRENT PLAYER IS " + activePlayer.getName() + "############");
				autoRunReinforceToFortify(activePlayer,board);
				turnCount++;
			}
			gameLogs.log(" [ACTUAL GAME PLAY PHASE START] ");
			System.out.println("Turn COUNT...." + turnCount);

			if (turnCount > maxAllowedTurn) {
				gameResult = "DRAW";
			} else {
				gameResult = board.getActivePlayer().getPlayerType().toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameResult;
	}

	private Board executePreSetup(ArrayList<Player> playerList, World world) {
		Board board = new Board();
		try {
			List<Card> cardDeck = gameUtility.buildCardDeck(world);
			board.initializeGame(world, playerList, cardDeck);
			gameController.assignArmiesToPlayer(playerList);
			gameController.distributeTerritories(playerList, world);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return board;
	}

	/**
	 * This method assigns unique start Dice number to each player to decide the
	 * turn to be followed throughout the game
	 */
	private void rollDiceForTurnManagement(ArrayList<Player> playerList) {
		ArrayList<Integer> tempDiceList = new ArrayList<>();
		try {
			if (playerList != null && !playerList.isEmpty()) {
				for (Player player : playerList) {
					int diceNum = diceUtility.rollDice();
					while (tempDiceList.contains(diceNum)) {
						diceNum = diceUtility.rollDice();
					}
					tempDiceList.add(diceNum);
					player.setStartDiceNo(diceNum);
					gameLogs.log(player.getName() + " gets start dice ::" + diceNum);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void autoRunSetupPhase(Board board) {
		while (gameUtility.playersHaveArmies(board)) {
			Player activePlayer = board.getActivePlayer();
			PlayerStrategy playerStrategy = activePlayer.getPlayerStrategy();
			playerStrategy.runSetupPhase(activePlayer, board);
		}
	}

	private void autoRunReinforceToFortify(Player activePlayer,Board board) {
		try {
			PlayerStrategy playerStrategy = activePlayer.getPlayerStrategy();

			playerStrategy.runReinforcePhase(activePlayer, board);
			playerStrategy.runAttackPhase(activePlayer, board);
			playerStrategy.runFortifyPhase(activePlayer, board);
			board.getNextPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
