package RiskyGame.Utility;
import RiskyGame.Model.Country;
import RiskyGame.Model.Continent;
import RiskyGame.Model.World;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

/**
 *this class read and parse the .map file and set the entities for the game
 */
public class MapReader {

    /**
     * method for choosing the map
     * @param map
     * @return  name of the map have been read
     */
    public String filePicker(World map){
        String fileName="";
        String importedFile;
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle("CHOOSE THE MAP");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.map", "map"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            importedFile = chooser.getSelectedFile().getAbsolutePath();

            if (importedFile.trim().substring(importedFile.length() - 4).equals(".map")){
                    File file = new File(importedFile);
                    map.setMapName(file.getName());
                    map.setMapPath(importedFile.substring(0, importedFile.lastIndexOf("\\")));
                    fileName=map.getMapPath();
                }

        }

    return fileName;

    }

    /**
     * method for parsing and initializing the game entities
     * @param map
     * @throws Exception
     */

    public void pareseMap(World map) throws Exception{


            FileReader mapFile=new FileReader(map.getMapPath() + "\\" + map.getMapName());
            String line;
            BufferedReader reader = new BufferedReader(mapFile);
            String text="";
            while ((line = reader.readLine()) != null) {
                if (line != "\n") {
                    text += line + "\n";
                }
            }

            String continents = text.substring(text.indexOf("[Continents]"), text.indexOf("[Territories]"));
            String countries = text.substring(text.indexOf("[Territories]"));
            String[] countriesSplit = countries.split("\n");
            String[] continentsSplit = continents.split("\n");
            for (String data : continentsSplit) {
                if (data.equalsIgnoreCase("[Continents]")) {
                    Continent continent = new Continent();
                    continent.setContinentName(data.substring(0, data.indexOf("=")));
                    continent.setControlValue(Integer.parseInt(data.substring(data.indexOf("=") + 1)));
                    map.getContinents().add(continent);
                }
            }
            for (String data : countriesSplit) {
                if ((!data.equalsIgnoreCase("[Territories]"))) {

                    Country country = new Country();
                    String[] countryAttributes = data.split(",");
                    country.setCountryName(countryAttributes[0]);
                    country.setCoordinateX(Double.parseDouble(countryAttributes[1]));
                    country.setCoordinateY(Double.parseDouble(countryAttributes[2]));
                    for (int i = 4; i < countryAttributes.length; i++) {
                        country.getNeighbors().add(countryAttributes[i]);
                    }
                    for (Continent countryContinent : map.getContinents()) {
                        if (countryContinent.getContinentName().toLowerCase().indexOf(countryAttributes[3].trim().toLowerCase()) >= 0) {
                            countryContinent.getContainedCountries().add(country);
                        }
                    }
                }
                }

        }

        }

