package com.riskgame.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.TournamentModel;
import com.riskgame.model.World;
import com.riskgame.strategy.PlayerStrategy;
import com.riskgame.utility.DiceUtility;
import com.riskgame.utility.GamePhase;
import com.riskgame.utility.GameUtility;
import com.riskgame.view.TournamentPhaseView;
import com.riskgame.view.TournamentView;

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
			executePreSetup(playerList, world);
			gameLogs.log(" [Pre SETUP END]  ");

			rollDiceForTurnManagement(playerList);
			
			//run setupPhase here
			
			while(turnCount <= maxAllowedTurn) {//Need to add 1 more case here
				Player activePlayer = Board.getInstance().getActivePlayer();
				gameLogs.log("############CURRENT PLAYER IS "+activePlayer.getName()+"############");
				autoRunReinforceToFortify(activePlayer);
				turnCount++;
			}
			
			if(turnCount > maxAllowedTurn) {
				gameResult="DRAW";
			}else {
				gameResult = "WINNER SOMEBODY";//TODO modify this
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameResult;
	}

	private void executePreSetup(ArrayList<Player> playerList, World world) {
		Board board = Board.getInstance();
		try {
			ArrayList<Card> cardDeck = gameUtility.buildCardDeck(world);
			board.initializeGame(world, playerList, cardDeck);
			gameController.assignArmiesToPlayer(playerList);
			gameController.distributeTerritories(playerList, world);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method assigns unique start Dice number to each player
	 * to decide the turn to be followed throughout the game
	 */
	private void rollDiceForTurnManagement(ArrayList<Player> playerList) {
		ArrayList<Integer> tempDiceList = new ArrayList<>();
		try {
			gameLogs.log("*****AUTO ROLLING DICE TO DECIDE THE PLAYER'S TURN****");
			if (playerList != null && !playerList.isEmpty()) {
				for (Player player : playerList) {
					int diceNum = diceUtility.rollDice();
					while (tempDiceList.contains(diceNum)) {
						diceNum = diceUtility.rollDice();
					}
					tempDiceList.add(diceNum);
					player.setStartDiceNo(diceNum);
					gameLogs.log(player.getName() +" gets start dice ::"+ diceNum);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public void autoRunReinforceToFortify(Player activePlayer) {
		try {
			PlayerStrategy playerStrategy = activePlayer.getPlayerStrategy();
			
			playerStrategy.runReinforcePhase(activePlayer);
			playerStrategy.runAttackPhase(activePlayer);
			playerStrategy.runFortifyPhase(activePlayer);
			Board.getInstance().getNextPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
