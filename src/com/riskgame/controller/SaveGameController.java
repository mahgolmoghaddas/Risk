package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.model.Board;
import com.riskgame.view.BoardView;

public class SaveGameController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Serialization  (*.ser)", "*.ser");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(extFilter);
		fileChooser.setDialogTitle("Specify a file to save");
		int userSelection = fileChooser.showSaveDialog(BoardView.mainBoardFrame.getContentPane());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			saveFile(GameController.getInstance().getBoard(), fileToSave);
			System.out.println("File saved successfully in "+fileToSave.getAbsolutePath());
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
}
