package com.riskgame.utility;
import com.riskgame.model.Country;
import com.riskgame.model.Continent;
import com.riskgame.model.World;

import java.sql.SQLOutput;
import java.util.*;
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
     * This method pass the parameters to the method validity to check the validity of the map files passed and return the boolean value if the map is in the correct format or no
     * @param map
     * @param fileName
     * @return valid
     */
    public boolean mapValidity(World map,String fileName)  {

        boolean valid=false;

                try{
                valid=pareseMap(map, fileName);}
                catch (Exception e) {
                    e.printStackTrace();
                }


        return valid;
    }

    /**
     * checks if the defined continent is empty
     * @param map
     * @return boolean
     */

    public boolean checkEmptyContinent(World map){
        if(map.getContinents().isEmpty()){
            return true;
        }
        else{
            for(Continent c:map.getContinents()){
                if(c.getContainedCountries().isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if the current continent has any country neighbors to the other continent or not
     * @param map
     * @return
     */
    public boolean checkIfNeigbourExist(World map) {
        List<String> list =  map.continentNamesList();
        List<String> listOfCountries = new ArrayList<String>();
        for(String name : list) {
            listOfCountries.add(name.toLowerCase());
        }
        for (Continent c : map.getContinents()) {
            for (Country country : c.getContainedCountries()) {
                for (String neighbour : country.getNeighbors()) {
                    if (!listOfCountries.contains(neighbour.toLowerCase())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * the method which calls and run and checks the validity of the map
     * @param map
     * @return valid
     */
    public boolean checkValid(World map){
        boolean valid=false;

            if (!checkEmptyContinent(map)) {
                if (!checkIfNeigbourExist(map)) {
                    valid=true;

                    return valid;
                }
                else{; return valid;}

            }
            else { return valid;}


    }

    /**
     * method for parsing and initializing the game entities
     * @param map
     * @throws Exception
     */

    public boolean pareseMap(World map, String fileName) throws Exception{

            boolean valid=true;
            FileReader mapFile=new FileReader(fileName);
            String line;
            BufferedReader reader = new BufferedReader(mapFile);
            String text="";
            while ((line = reader.readLine()) != null) {
                if (line != "\n") {
                    text += line + "\n";
                }
            }

        if(text.contains("[Continents]")&& text.contains("[Territories]")){
            String continents = text.substring(text.indexOf("[Continents]"), text.indexOf("[Territories]"));
            String countries = text.substring(text.indexOf("[Territories]"));
            String[] countriesSplit = countries.split("\n");
            String[] continentsSplit = continents.split("\n");
            for (String data : continentsSplit) {
                if (!data.equalsIgnoreCase("[Continents]")) {
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

                }}

                valid=checkValid(map);
                return valid;


        }}

