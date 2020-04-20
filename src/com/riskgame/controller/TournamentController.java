package com.riskgame.controller;

import com.riskgame.model.GameLogs;
import com.riskgame.model.TournamentModel;
import com.riskgame.view.TournamentPhaseView;
import com.riskgame.view.TournamentView;

public class TournamentController {

	TournamentView view;
	TournamentPhaseView phaseView;
	private GameLogs gameLogs = new GameLogs();

	public void displayTournamentOptions() {
		view = new TournamentView();
	}

	public void startTournament(TournamentModel tournamentModel) {

		phaseView = new TournamentPhaseView(gameLogs);
		
		System.out.println(tournamentModel.getNoOfGame());
		System.out.println(tournamentModel.getTotalTurns());
		System.out.println(tournamentModel.getWorldList().size());
		System.out.println(tournamentModel.getPlayerList().size());
	}
}
