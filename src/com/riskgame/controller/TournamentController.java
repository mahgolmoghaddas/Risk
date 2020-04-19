package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.riskgame.view.TournamentPhaseView;
import com.riskgame.view.TournamentView;

public class TournamentController implements ActionListener{

	TournamentView view ;
	TournamentPhaseView phaseView;
	private boolean isPlayTournament;
	
	public TournamentController(boolean isPlayTournament) {
		this.isPlayTournament  =isPlayTournament;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(isPlayTournament) {
			phaseView = new TournamentPhaseView();
		}else {
			view =new TournamentView();
		}
	}

}
