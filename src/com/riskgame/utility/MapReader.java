package com.riskgame.utility;

import com.riskgame.model.Territory;
import com.riskgame.model.Continent;
import com.riskgame.model.Coordinates;
import com.riskgame.model.World;

import java.sql.SQLOutput;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.filechooser.*;

import static org.junit.Assert.assertNotEquals;

import java.io.*;

/**
 * this class read and parse the .map file and set the entities for the game
 */
public class MapReader {

	private static final String TERRITORY_FORMAT = "[Territories]";
	private static final String CONTINENT_FORMAT = "[Continents]";
	private static final String MAP_FORMAT = "[Map]";

	HashMap<String, Double> continentInfo = new HashMap<>();
	HashMap<String, ArrayList<Territory>> continentTerritoryMap = new HashMap<>();
	HashMap<String, String> mapInfo = new HashMap<>();

	public boolean isValidMap(File mapFile) throws Exception {

		boolean isValidMap = true;

		if (mapFile != null) {
			BufferedReader br = new BufferedReader(new FileReader(mapFile));
			String line;

			while ((line = br.readLine()) != null) {
				if (line.contains(CONTINENT_FORMAT)) {
					isValidMap = checkAndCreateContinentsInfo(br);

					if (!isValidMap) {
						continentInfo.clear();
						return isValidMap;
					}

				} else if (line.contains(TERRITORY_FORMAT)) {

					isValidMap = checkAndCreateTerritoryInfo(br);
					if (!isValidMap) {
						continentTerritoryMap.clear();
						return isValidMap;
					}
				} else if (line.contains(MAP_FORMAT)) {
					createMapInfo(br);
				}
			}

		}

		return isValidMap;
	}

	public boolean checkAndCreateContinentsInfo(BufferedReader br) throws IOException {
		boolean isValidContinent = true;
		String line;
		int continentCounter = 0;
		while ((line = br.readLine()) != null && !"".equals(line)) {

			String[] continentArr = line.split("=");

			if (continentCounter > 6 || continentArr == null || continentArr.length != 2
					|| !isNumeric(continentArr[1])) {
				isValidContinent = false;
				return isValidContinent;
			}

			continentInfo.put(continentArr[0], Double.valueOf(continentArr[1]));

			continentCounter++;
		}
		return isValidContinent;
	}

	public boolean checkAndCreateTerritoryInfo(BufferedReader br) throws Exception {
		boolean isValidTerritory = true;
		String line;

		while ((line = br.readLine()) != null) {

			if (!"".equals(line)) {
				String[] territoriesArr = line.split(",");

				if (territoriesArr.length < 5 || !isNumeric(territoriesArr[1]) || !isNumeric(territoriesArr[2])
						|| !checkIfContinent(territoriesArr[3])) {
					isValidTerritory = false;
					return isValidTerritory;
				}
				createTerritory(territoriesArr);
			}
			
		}
		return isValidTerritory;
	}

	public void createMapInfo(BufferedReader br) throws Exception {
		String line;

		while ((line = br.readLine()) != null && !"".equals(line)) {

			String[] mapArr = line.split("=");

			if (mapArr != null && mapArr.length == 2) {
				String keyName = mapArr[0];
				String valueName = mapArr[1];

				mapInfo.put(keyName, valueName);
			}
		}
		System.out.println(line);
	}

	public boolean isNumeric(String value) {

		if (value == null) {
			return false;
		}

		try {
			Double.valueOf(value);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public boolean checkIfContinent(String continent) {

		if (continentInfo != null && !continentInfo.isEmpty() && continentInfo.containsKey(continent)) {
			return true;
		}
		return false;
	}

	public void createTerritory(String[] territoryArray) throws Exception {

		if (territoryArray != null && territoryArray.length >= 5) {

			String countryName = territoryArray[0];
			double xPosition = Double.valueOf(territoryArray[1]);
			double yPosition = Double.valueOf(territoryArray[2]);
			String continentName = territoryArray[3];
			Coordinates territoryPosition = new Coordinates(xPosition, yPosition);

			ArrayList<String> neighbourTerritories = new ArrayList<>();

			for (int i = 4; i < territoryArray.length; i++) {
				neighbourTerritories.add(territoryArray[i]);
			}
			Territory territory = new Territory(countryName, territoryPosition, neighbourTerritories);

			ArrayList<Territory> territoryList = new ArrayList<>();
			if (!continentTerritoryMap.isEmpty() && continentTerritoryMap.containsKey(continentName)) {

				territoryList = continentTerritoryMap.get(continentName);

				if (territoryList == null) {
					territoryList = new ArrayList<>();
				}
				territoryList.add(territory);

			} else {
				territoryList.add(territory);
				continentTerritoryMap.put(continentName, territoryList);
			}

		}
	}

	public World createWorldMap() {
		World world = null;
		ArrayList<Continent> continentList = new ArrayList<Continent>();
		if (continentInfo != null && !continentInfo.isEmpty()) {
			for (Entry<String, Double> continentEntry : continentInfo.entrySet()) {

				String continentName = continentEntry.getKey();
				Double bonusPoint = continentEntry.getValue();
				ArrayList<Territory> territoryList = continentTerritoryMap.get(continentName);

				Continent continent = new Continent(continentName, territoryList, bonusPoint);
				continentList.add(continent);
			}

			world = new World(continentList);

			if (mapInfo != null && !mapInfo.isEmpty()) {

				for (Entry<String, String> mapEntry : mapInfo.entrySet()) {

					String key = mapEntry.getKey();
					String value = mapEntry.getValue();

					switch (key) {
					case "author":
						world.setAuthor(value);
						break;
					case "image":
						world.setImage(value);
						break;
					case "wrap":
						world.setWrap(value);
						break;
					case "scroll":
						world.setScroll(value);
						break;
					case "warn":
						world.setWarn(value);
						break;
					default:
						break;
					}
				}
			}
		}
		System.out.println("Successfully parsed Map");
		return world;
	}
}
