package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.riskgame.model.World;
import com.riskgame.model.Continent;
import com.riskgame.model.Coordinates;
import com.riskgame.model.Territory;
import com.riskgame.utility.MapReader;
import com.riskgame.utility.ViewUtility;
import com.riskgame.view.*;
public class EditMapController implements ActionListener {

	MainWindowView mainWindowView;
	EditMapView editMapView;
	
	JFrame editMapFrame;
	JPanel editMapPanel;
	JPanel toolBar;
	JPanel countryPanel ;
	JLabel continentLabel;
	ViewUtility viewUtility = new ViewUtility();
	JButton showMapButton;
	JButton addContinentButton;
	JButton addCountryButton;
	JButton save;
	private World world;







	public EditMapController(EditMapView editMapView) {
		this.world=editMapView.world;
	}
	
	




	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String st=actionEvent.getActionCommand();

		if(st=="Add Continent") {
			addContinent();
		}
		else if(st=="Add Country") {
			
			addCountry();
		}
		
		else if(st=="Save The Changes") {
			saveChanges();
		}
		
		else if(st=="Delete Continent") {
			deleteContinent();
		}
		else if(st=="Delete Country") {
			//deleteCountry();
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
			int count=1;
			System.out.println(count++);
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
		ViewUtility createTable=new ViewUtility();
		JTextField inputCountry = new JTextField();
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
            boolean loop = true;
            while (loop) {
                int countryName = JOptionPane.showConfirmDialog(null, message, "Country Name",
                        JOptionPane.OK_CANCEL_OPTION);

                if (countryName == JOptionPane.OK_OPTION) {
                    for (Continent continentName : world.getContinents()) {
                    	
                        for (Territory name : continentName.getTerritoryList()) {
                        	
                            if (name.getCountryName().equals(inputCountry.getText())) {
                                flag = 1;
                                
                                break;
                            }
                            
                        }
                        
                    }

                if (inputCountry.getText() == null || inputCountry.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please specify the name!");
                } else if (world.getContinents().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter continent first");
                } else if (flag == 1) {
                    flag = 0;
                    JOptionPane.showMessageDialog(null, "Country already exists!");
                } else {
                	createCountry((String)continentBox.getItemAt(continentBox.getSelectedIndex()),inputCountry.getText());
                    
                	try {
						createTable.createWorldMapTable(getWorld());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    loop = false;
                }
                }
                else {
                loop = false;
            }
            }}

}
	public void createCountry(String continentName, String countryName){
		Continent tempContinent = null;
        
        for (Continent name : world.getContinents()) {
            if (name.getContinentName().equals(continentName)) {
                tempContinent = name;
            }
        }
        
        HashSet<Territory> countries = new HashSet<Territory>();
        countries = world.getTerritories();
        HashSet<String> countryNames = new HashSet<String>();
        for(Territory territory: countries) {
        	countryNames.add(territory.getCountryName());
        }
        Territory newCountry = new Territory();
        Coordinates territoryPosition=new Coordinates(Math.random(),Math.random());
        newCountry.setTerritoryPosition(territoryPosition);
        newCountry.setCountryName(countryName);
        tempContinent.addTerritory(newCountry);
	}
	
	
	
	private void saveChanges() {
		MapReader mapReader=new MapReader();
		boolean flag=false;
		while (!flag) {
            String mapName = JOptionPane.showInputDialog(null, "Please enter the map name to save");
            if (mapName != null) {
                if (mapName.trim().isEmpty()) {
                    JOptionPane.showConfirmDialog(null, "Please enter some name!");
                } else {
                    world.setMapName(mapName);
                    if (mapReader.saveAndUpdateFile(world, mapName)) {
                        JOptionPane.showMessageDialog(null, "Map has been saved");
                    } else {
                        JOptionPane.showMessageDialog(null, "Not able to save map file, enter different map name.");
                    }
                    flag = true;
                }
            } else {
                flag = true;
            }
        }
		
	}
	
    public void deleteContinent() {
        
            String continents[] = new String[world.getContinents().size()];
            int count = 0;
            for (Continent continent : world.getContinents()) {
                continents[count++] = continent.getContinentName();
            }
            JComboBox<Object> continentBox = new JComboBox<Object>(continents);
            continentBox.setSelectedIndex(0);
            JOptionPane.showConfirmDialog(null, continentBox, "Select Continent ", JOptionPane.OK_CANCEL_OPTION);
            if (continentBox.getSelectedIndex() > 0) {
                	removeContinentService((String) continentBox.getItemAt(continentBox.getSelectedIndex()),true);
                   
                	viewUtility.createWorldMapTable(getWorld());
                }
            }
        
    
	
	
	
	
    /**
     * Setter method to setup World
     * @param world World
     */
    public void setWorld(World world) {
		this.world = world;
		
	}

    /**
     * Getter method to get World
     * @return World
     */
    public World getWorld() {
		return world;
				
    }

}