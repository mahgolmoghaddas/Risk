package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.model.Board;
import com.riskgame.utility.GamePhase;
import com.riskgame.view.MainWindowView;

public class LoadGameController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JFileChooser chooser = new JFileChooser();

			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Serialization  (*.ser)", "*.ser");
			chooser.setFileFilter(extFilter);

			int selection = chooser.showOpenDialog(MainWindowView.mainWindowView.getContentPane());

			if (selection != JFileChooser.CANCEL_OPTION) {
				File mapFile = chooser.getSelectedFile();
				Board board = loadSavedFile(mapFile);
				System.out.println("LOADED FILE " + board);
				GameController controller = GameController.getInstance();
				controller.resumeGame(board);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @param file file
	 * @return GamePlayController GamePlayController
	 */
	public Board loadSavedFile(File file) {
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
