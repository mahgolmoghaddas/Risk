package com.riskgame.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.riskgame.model.Board;
import com.riskgame.model.Card;
import com.riskgame.model.GameLogs;
import com.riskgame.model.Player;
import com.riskgame.model.TournamentModel;
import com.riskgame.model.World;
import com.riskgame.utility.GameUtility;
import com.riskgame.view.TournamentPhaseView;
import com.riskgame.view.TournamentView;

public class TournamentController {

	private GameLogs gameLogs = GameLogs.getInstance();
	private GameController gameController = GameController.getInstance();
	private GameUtility gameUtility = new GameUtility();
	private HashMap<String, HashMap<String, String>> tournamentResult = new HashMap<>();

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

						gameLogs.log("****STARTED GAME " + j + " FOR MAP "+ (i+1));

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
		try {
			gameLogs.log(" [Pre SETUP START]  ");
			executePreSetup(playerList, world);
			gameLogs.log(" [Pre SETUP END]  ");

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
		}
	}
	
}
