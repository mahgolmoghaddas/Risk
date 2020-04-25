package com.riskgame.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;
import com.riskgame.controller.EditMapController;
import com.riskgame.model.Continent;
import com.riskgame.model.Territory;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import com.riskgame.view.EditMapView;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class MapEditorTest{
	private static World world;
	private static  String worldName;
	 static EditMapView  editMapView;
	 //static EditMapController editMap;
	 static MapReader map=new MapReader();
	
	@BeforeClass
	public static void classSetup() {
		
		worldName="resources//map//World.map";
		world=map.fileMap(worldName);
		
		
	}
	
	
	@Test
	public void neighborhoodTest() {
		
		String countryName="India";
		String neighborName="Afghanistan";
		Territory[] resultCountries=new Territory[2];
		for(Continent continent:world.getContinents()) {
			Territory returnVal1=continent.findTerritory(countryName);
			Territory returnVal2=continent.findTerritory(neighborName);
			if(returnVal1!=null)
					{resultCountries[0]=returnVal1;}
			if(returnVal2!=null)
					{resultCountries[1]=returnVal2;}
			if((returnVal1!=null) && (returnVal2!=null) )
				break;
			}
		HashSet<String> f=new HashSet<String>();
		f=(resultCountries[0].getNeighborsTerritory());
		boolean flag=f.contains(neighborName);
		Assert.assertTrue(flag);
		
	}
	
	@Test
	public void addContinentTest() {
		Continent continent=new Continent();
		continent.setContinentName("Middle");
		continent.setBonusPoint(8);
		world.addContinent(continent);
		
		boolean temp=world.getContinents().contains(continent);
		Assert.assertTrue(temp);
	}
	
	
}
