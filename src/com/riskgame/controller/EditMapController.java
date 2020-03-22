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
			try {
				deleteCountry();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
        for (Continent name : world.getContinents()) {
            continents[count++] = name.getContinentName();
        }
        JComboBox<Object> continentBox = new JComboBox<Object>(continents);
        continentBox.setSelectedIndex(-1);
        JOptionPane.showConfirmDialog(null, continentBox, "Select continent name : ", JOptionPane.OK_CANCEL_OPTION);
        if (continentBox.getSelectedIndex() > -1) {
            int result = JOptionPane.showConfirmDialog(null, "Deleting Continent will delete all the countries! \n Do you want to Continue ?");
            if (result == JOptionPane.YES_OPTION) {
            	String continentName=(String) continentBox.getItemAt(continentBox.getSelectedIndex());
            	HashSet<String>  removedCountries=new HashSet<String>();
                Continent tempContinent = null;
                for (Continent continent : world.getContinents()) {
                    if (continent.getContinentName().equals(continentName)) {
                        tempContinent = continent;
                        for (Territory c : tempContinent.getTerritoryList()) {
                        	removedCountries.add(c.getCountryName());
                        }
                    }
                }
                world.removeContinent(tempContinent);
                for (Continent continent : world.getContinents()) {
                    for (Territory territory : continent.getTerritoryList()) {
                        for (String neighbours : removedCountries) {
                            if (territory.getNeighborsTerritory().contains(neighbours)) {
                                territory.getNeighborsTerritory().remove(neighbours);
                            }
                        }
                    }
                }
              
            	try {
					viewUtility.createWorldMapTable(world);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
            }
        



	private void deleteCountry() throws Exception {
		// TODO Auto-generated method stub
		HashSet<String> continentsList=new HashSet<String>();
		int count = 0;
        for (Continent continentName : world.getContinents()) {
            if (continentName.getTerritoryList().size() > 0) {
                continentsList.add(continentName.getContinentName());
                
            }
        }
        String[] continents = new String[continentsList.size()];
        for (String value : continentsList) {
            continents[count++] = value;
        }
		
        
        JComboBox<Object> continentBox = new JComboBox<Object>(continents);
        continentBox.setSelectedIndex(-1);
        int result = JOptionPane.showConfirmDialog(null, continentBox, "Continent Name ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (continentBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Select The Continent!");
            } else {
                String continentNameString = (String) continentBox.getSelectedItem();
                Continent continentName = null;
                for (Continent name : world.getContinents()) {
                    if (name.getContinentName().equals(continentNameString)) {
                        continentName = name;
                    }
                }
                if (continentName.getTerritoryList().size() == 0) {
                    JOptionPane.showMessageDialog(null,
                            continentName.getContinentName() + " has no country to remove");
                } else {
                    String countries[] = new String[continentName.getTerritoryList().size()];
                    int val = 0;
                    for (Territory countryName : continentName.getTerritoryList()) {
                        countries[val++] = countryName.getCountryName();
                    }
                    JComboBox<Object> countryBox = new JComboBox<Object>(countries);
                    countryBox.setSelectedIndex(-1);
                    int CountryResult = JOptionPane.showConfirmDialog(null, countryBox, "Select Country name : ", JOptionPane.OK_CANCEL_OPTION);
                    if (CountryResult == JOptionPane.OK_OPTION) {
                        if (countryBox.getSelectedItem() == null) {
                            JOptionPane.showMessageDialog(null, "Please select country");
                        } else {
                        	String countryName=(String) countryBox.getItemAt(countryBox.getSelectedIndex());
                        	Territory tempCountry = continentName.findTerritory(countryName);	
                            String removedCountry = tempCountry.getCountryName();
                            continentName.deleteTerritory(tempCountry);
                            for (Continent continent : world.getContinents()) {
                                for (Territory territory : continent.getTerritoryList()) {
                                    if (territory.getNeighborsTerritory().contains(removedCountry)) {
                                  	  territory.getNeighborsTerritory().remove(removedCountry);
                                    }
                                }
                            }
                            }
                           
                        viewUtility.createWorldMapTable(world);
                        }}}}
                    
		
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