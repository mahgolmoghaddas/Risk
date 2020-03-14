package com.riskgame.controller;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;
import com.riskgame.view.MainWindowView;

public class EditMapController implements ActionListener {

	MainWindowView mainWindowView;
	JFrame editMapFrame;
	JPanel editMapPanel;
	ViewUtility viewUtility = new ViewUtility();

	public EditMapController() {
		mainWindowView = MainWindowView.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		JFileChooser chooser = new JFileChooser();
		try {
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
			chooser.setFileFilter(mapFileFilter);

			int selection = chooser.showOpenDialog(mainWindowView.getContentPane());

			if (selection != JFileChooser.CANCEL_OPTION) {
				File mapFile = chooser.getSelectedFile();

				// Show panel to edit the map
				MapReader mapReader = new MapReader();
//
				if (mapReader.isValidMap(mapFile)) {
					
					World world = mapReader.createWorldMap();
					createEditMapPanel(world);

//
//					JOptionPane.showMessageDialog(editMapFrame.getContentPane(),
//							"Please click save button to update the map", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(editMapFrame.getContentPane(), "Unsupported Map File", "MESSAGE",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createEditMapPanel(World world) throws Exception{
		editMapFrame = viewUtility.createMainFrame("Edit Map", false);

		editMapPanel = new JPanel();
		editMapPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		editMapPanel.setBorder(new EmptyBorder(20, 10, 10, 10));
		
		editMapPanel.add(viewUtility.createWorldMapTable(world));
		
		editMapFrame.add(editMapPanel);
		editMapFrame.setVisible(true);
		editMapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
