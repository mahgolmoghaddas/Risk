package RiskyGame.Utility;
import RiskyGame.Model.Country;
import RiskyGame.Model.Continent;
import RiskyGame.Model.World;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.*;

/**
 *this class read and parse the .map file and set the entities for the game
 */
public class MapReader {
    public String filePicker(World map){
        String fileName="";
        String importedFile;
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle("Choose Map file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.map", "map"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            importedFile = chooser.getSelectedFile().getAbsolutePath();
            if (importedFile.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "File name invalid");
            }
            else {
                if (importedFile.trim().substring(importedFile.length() - 4).equals(".map") /*||
							importFileName.trim().substring(importFileName.length() - 4).equals(".bin"))*/ ){
                    File f = new File(importedFile);
                    map.setMapName(f.getName());
                    map.setMapPath(importedFile.substring(0, importedFile.lastIndexOf("\\")));
                    JOptionPane.showMessageDialog(null, "File in Correct format");
                    fileName=map.getMapPath();
                }
                else {
                    JOptionPane.showMessageDialog(null, "File is not in the correct format");
                }
            }
        }

    return fileName;

    }



    public void parseAndValidateMap(World map) throws Exception{


            FileReader mapFile=new FileReader(map.getMapPath() + "\\" + map.getMapName());
            String line;
            BufferedReader reader = new BufferedReader(mapFile);
            String text="";
            while ((line = reader.readLine()) != null) {
                if (line != "\n") {
                    text += line + "\n";
                }
            }

            String continentData = text.substring(text.indexOf("[Continents]"), text.indexOf("[Territories]"));
            String countryData = text.substring(text.indexOf("[Territories]"));
            String[] countryDataArray = countryData.split("\n");
            String[] continentDataArray = continentData.split("\n");
            for (String data : continentDataArray) {
                if (data.equalsIgnoreCase("[Continents]")) {
                    Continent continent = new Continent();
                    continent.setContinentName(data.substring(0, data.indexOf("=")));
                    continent.setControlValue(Integer.parseInt(data.substring(data.indexOf("=") + 1)));
                    map.getContinents().add(continent);
                }
            }
            for (String data : countryDataArray) {
                if ((!data.equalsIgnoreCase("[Territories]"))) {

                    Country country = new Country();
                    String[] countryAttributes = data.split(",");
                    country.setCountryName(countryAttributes[0]);
                    country.setCoordinateX(Double.parseDouble(countryAttributes[1]));
                    country.setCoordinateY(Double.parseDouble(countryAttributes[2]));
                    for (int i = 4; i < countryAttributes.length; i++) {
                        country.getNeighbors().add(countryAttributes[i]);
                    }
                    for (Continent currentContinent : map.getContinents()) {
                        if (currentContinent.getContinentName().toLowerCase().indexOf(countryAttributes[3].trim().toLowerCase()) >= 0) {
                            currentContinent.getContainedCountries().add(country);
                        }
                    }
                }
                }

        }

        }

