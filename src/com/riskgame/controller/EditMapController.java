package com.riskgame.controller;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.riskgame.model.World;
import com.riskgame.model.Continent;
import com.riskgame.model.Territory;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;
import com.riskgame.view.*;
public class EditMapController implements ActionListener {

	private HashSet<Continent> continents;
	EditMapView editMapView;
	MainWindowView mainWindowView;
	JFrame editMapFrame;
	JPanel editMapPanel;
	JPanel toolBar ;
	JPanel countryPanel ;
	JLabel continentLabel;
	ViewUtility viewUtility = new ViewUtility();
	JButton showMapButton;
	JButton addContinentButton;
	JButton addCountryButton;
	JButton save;
	World world;







	public EditMapController(EditMapView editMapView) {
		// TODO Auto-generated constructor stub
		this.editMapView=editMapView;
		this.world=editMapView.world;
	}





	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String st=actionEvent.getActionCommand();

		if(st=="Add Continent") {
			addContinent();
		}
		else if(st==" Add Country") {
			addCountry();
		}
		else if(st=="Save The Changes") {
			//saveChanges();
		}
	}




	/**
	 * method for adding the continent to the map file
	 */
	public void addContinent() {
		boolean loop=true;
		while(loop==true) {
			String continentName = JOptionPane.showInputDialog(null, "Enter the Continent name: ", "Add Continent", JOptionPane.OK_CANCEL_OPTION | JOptionPane.QUESTION_MESSAGE);
			double controlValue=Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Continent Control Value: ", "Add Continent Control Value", JOptionPane.OK_CANCEL_OPTION | JOptionPane.QUESTION_MESSAGE));
			if(continentName.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please specify the name!");
			}
			loop=false;
			createContinet(continentName, controlValue);
		}



	}
	public void createContinet(String continentName, double controlValue) {
		Continent continent=new Continent();
		continent.setContinentName(continentName);
		continent.setBonusPoint(controlValue);
		world.addContinent(continent);
	}


	/**
	 * method for adding the country to the designated continent
	 */
	public void addCountry() {
		JTextField inputCountry = new JTextField();
		String lastContinent = "";
		int flag = 0;
		if (world.getContinents().size() == 0) {
			JOptionPane.showMessageDialog(null, "Please add the continent First");
		} else {
			String continents[] = new String[world.getContinents().size()];
			int count = 0;
			for (Continent name : world.getContinents()) {
				continents[count++] = name.getContinentName();
			}
			JComboBox<Object> continentBox = new JComboBox<Object>(continents);
			Object[] message = {"Select continent name : ", continentBox, "Enter the Country name : ", inputCountry};
			continentBox.setSelectedIndex(0);
		}}




}