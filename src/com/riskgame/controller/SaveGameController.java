package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.builder.GameBuilder;
import com.riskgame.model.Board;
import com.riskgame.view.BoardView;

/**
 * This class handles the actions that saves the Game and do the corresponding
 * actions
 * 
 * @author Pushpa
 *
 */
public class SaveGameController implements ActionListener {

	private GameBuilder gameBuilder;

	public SaveGameController(GameBuilder gameBuilder) {
		this.gameBuilder = gameBuilder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.gameBuilder.saveGame();
	}
}
