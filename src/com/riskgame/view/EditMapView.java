package com.riskgame.view;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.riskgame.controller.EditMapController;
import com.riskgame.model.*;
import com.riskgame.utility.ViewUtility;
import com.riskgame.view.*;

public class EditMapView extends JFrame {
	JFrame editMapFrame;
	JPanel editMapPanel;
	JPanel toolBar ;
	JPanel countryPanel ;
	JLabel continentLabel;
	
	JButton showMapButton;
	JButton addContinentButton;
	JButton addCountryButton;
	JButton save;
	
	ViewUtility viewUtility = new ViewUtility();
	
	public World world;
	private EditMapController editMapControllerr;
	private EditMapController edit;
			public EditMapView(World world) {
				
				this.world=world;
				editMapControllerr = new EditMapController(this);
				try {
					createEditMapPanel(world);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			public void createEditMapPanel(World world) throws Exception{
				
				editMapFrame = viewUtility.editFrame("Edit Map");
				toolBar = new JPanel();
				toolBar .setLayout(new FlowLayout(FlowLayout.CENTER));
				toolBar .setBorder(new EmptyBorder(20, 10, 10, 10));
				toolBar.setBounds(0, 0, editMapFrame.getWidth(), 40);
				
				addContinentButton=new JButton("Add Continent");
				addContinentButton.addActionListener(editMapControllerr);
				addCountryButton=new JButton("Add Country");
				addCountryButton.addActionListener(editMapControllerr);
				save=new JButton("Save The Changes");
				save.addActionListener(editMapControllerr);
				toolBar.add(addContinentButton);
				toolBar.add(addCountryButton);
				toolBar.add(save);
				editMapPanel = new JPanel();
				editMapPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				editMapPanel.setBorder(new EmptyBorder(20, 10, 10, 10));
				

				
				editMapPanel.add(viewUtility.createWorldMapTable(world));
				editMapFrame.add(toolBar);
				editMapFrame.add(editMapPanel);
				
				
				editMapFrame.setVisible(true);
				editMapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
}
