package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.builder.GameBuilder;
import com.riskgame.model.Board;
import com.riskgame.utility.GamePhase;
import com.riskgame.view.MainWindowView;

/**
 * This class handles the actions that Load the Game and do the corresponding
 * actions
 * 
 * @author Pushpa
 *
 */
public class LoadGameController implements ActionListener {

	private GameBuilder gameBuilder;

	public LoadGameController(GameBuilder gameBuilder) {
		this.gameBuilder = gameBuilder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Board board = gameBuilder.loadGame();
			GameController controller = GameController.getInstance();
			controller.resumeGame(board);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
