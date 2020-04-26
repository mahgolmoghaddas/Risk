package com.riskgame.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.controller.GameController;
import com.riskgame.model.Board;
import com.riskgame.view.BoardView;
import com.riskgame.view.MainWindowView;

/**
 * This class acts as Concrete builder for constructing Board object to save
 * game and loading the saved game and returning the Board object for the Risk
 * Game
 * 
 * @author pushpa
 *
 */
public class RiskGameBuilder implements GameBuilder {

	Board board;

	public RiskGameBuilder() {
		this.board = new Board();
	}

	/**
	 * This method allows user to select the file path where user wants to save game
	 * and it saves the board data using Serialization in the respective file
	 */
	@Override
	public void saveGame() {
		FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Serialization  (*.ser)", "*.ser");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(extFilter);
		fileChooser.setDialogTitle("Specify a file to save");
		int userSelection = fileChooser.showSaveDialog(BoardView.mainBoardFrame.getContentPane());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			saveFile(GameController.getInstance().getBoard(), fileToSave);
			System.out.println("File saved successfully in " + fileToSave.getAbsolutePath());
		}
	}

	private void saveFile(Board board, File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(board);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * This method allows user to load the file where user saved game .It load the
	 * serialized file and return Board object
	 */

	@Override
	public Board loadGame() {
		try {
			JFileChooser chooser = new JFileChooser();

			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Serialization  (*.ser)", "*.ser");
			chooser.setFileFilter(extFilter);

			int selection = chooser.showOpenDialog(MainWindowView.mainWindowView.getContentPane());

			if (selection != JFileChooser.CANCEL_OPTION) {
				File mapFile = chooser.getSelectedFile();
				this.board = loadSavedFile(mapFile);
				System.out.println("LOADED FILE " + board);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this.board;
	}

	/**
	 * @param file file
	 * @return Board saved board data
	 */
	private Board loadSavedFile(File file) {
		Board board = null;
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			board = (Board) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception i) {
			i.printStackTrace();
		}
		return board;
	}

}
