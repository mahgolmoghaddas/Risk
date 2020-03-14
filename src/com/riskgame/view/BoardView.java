package com.riskgame.view;

import java.util.Observable;
import java.util.Observer;

import com.riskgame.model.Board;

public class BoardView implements Observer {
	
	private Board board;
	
	
	public BoardView(Board board) {
		this.board =board;
		board.addObserver(this);
	} 
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Updated..");
		if(o instanceof Board) {
			
		}
		board = (Board) o;
		//Yet to decide
		showGameBoard(board);
	}

	
	public void showGameBoard(Board board) {
		try {
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
