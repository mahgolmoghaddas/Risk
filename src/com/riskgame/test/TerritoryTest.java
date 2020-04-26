package com.riskgame.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.riskgame.model.Continent;
import com.riskgame.model.Coordinates;
import com.riskgame.model.Player;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;

public class TerritoryTest {
	//HashSet<Territory> neighbors=
	static String worldName;	
	static World world;
	ArrayList<String> temp=new ArrayList<String>();
	HashSet<String> listOfNeighbors;
	static MapReader map=new MapReader();
	Coordinates coordiantes;
	static Territory country;
	@BeforeClass
	public static void beforeCLass()
	{
		country= new Territory( );
		worldName="resources//map//World.map";
		world=map.fileMap(worldName);
	}

    
    
    @Test
    public void setCountryNameTest() {
    	String name="China";
    	country.setCountryName(name);
    	assertEquals(country.getCountryName(), name);
    }
    
    @Test
    public void setNeighborsTerritories() {
    	Territory[] countries= {world.getTerritoryByName("India"),world.getTerritoryByName("Alberta")};
		for(Territory c:countries) {
			temp.add(c.getCountryName());
		}
		listOfNeighbors=new HashSet<String>(temp);
		
		country.setNeighborsTerritory("Alberta");
		country.setNeighborsTerritory("India");
		assertEquals(country.getNeighborsTerritory().toString(), listOfNeighbors.toString());
    }
    @Test
    public void checkOwner() {
    	Player player=new Player(1, "Mahgol");
    	country.setOwner(player);
    	assertEquals(country.getOwner(), player);
    }
    
}
