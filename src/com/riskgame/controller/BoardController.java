package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.riskgame.model.Board;

public class BoardController implements ActionListener{
	
	private Board board ;
	public  BoardController() {
		this.board = Board.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("GAME IN BOARD CONTROLLER");
	}
	
}
