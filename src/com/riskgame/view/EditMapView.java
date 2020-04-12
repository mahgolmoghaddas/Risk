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
/**
 * This class providing the view of the edit map window
 * 
 * @author Mahgol
 *
 */
public class EditMapView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame editMapFrame;
	JPanel editMapPanel;
	JPanel toolBar ;
	JPanel toolBar2;
	JPanel countryPanel ;
	JLabel continentLabel;
	
	JButton showMapButton;
	JButton addContinentButton;
	JButton addCountryButton;
	
	JButton deleteContinentButton;
	JButton deleteCountryButton;
	JButton save;
	JButton reload;
	JButton addAdjacencyButton;
	JButton deleteAdjacencyButton;
	
	
	ViewUtility viewUtility = new ViewUtility();
	
	public World world;
	private EditMapController editMapControllerr;
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
				toolBar .setBorder(new EmptyBorder(5,5,5,5));
				toolBar.setBounds(0, 0, editMapFrame.getWidth(), 0);
				
				toolBar2 = new JPanel();
				toolBar2 .setLayout(new FlowLayout(FlowLayout.CENTER));
				toolBar2 .setBorder(new EmptyBorder(20, 5, 10, 2));
				toolBar2.setBounds(0, 0, editMapFrame.getWidth(), 0);
				
				addContinentButton=new JButton("Add Continent");
				
				
				addContinentButton.addActionListener(editMapControllerr);
				addCountryButton=new JButton("Add Country");
				addCountryButton.addActionListener(editMapControllerr);
				reload = new JButton("Reload Map");
				reload.addActionListener(editMapControllerr);
				deleteContinentButton=new JButton("Delete Continent");
				deleteContinentButton.addActionListener(editMapControllerr);
				deleteCountryButton=new JButton("Delete Country");
				deleteCountryButton.addActionListener(editMapControllerr);
				addAdjacencyButton=new JButton("Add Adjacency");
				addAdjacencyButton.addActionListener(editMapControllerr);
				deleteAdjacencyButton=new JButton("Delete Adjacency");
				deleteAdjacencyButton.addActionListener(editMapControllerr);
				save=new JButton("Save The Changes");
				save.addActionListener(editMapControllerr);
				
				toolBar.add(addContinentButton);
				
				toolBar.add(addCountryButton);
				toolBar.add(deleteContinentButton);
				toolBar.add(deleteCountryButton);
				toolBar2.add(save);
				toolBar2.add(reload);
				toolBar.add(addAdjacencyButton);
				toolBar.add(deleteAdjacencyButton);
				
				editMapPanel = new JPanel();
				editMapPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
				editMapPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				
				editMapPanel.add(viewUtility.createWorldMapTable(world));
				editMapFrame.add(toolBar);
				editMapFrame.add(toolBar2);
				editMapFrame.add(editMapPanel);
				
				
				editMapFrame.setVisible(true);
				editMapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
}
