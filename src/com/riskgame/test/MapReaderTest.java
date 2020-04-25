package com.riskgame.test;
import com.riskgame.model.World;
import com.riskgame.utility.MapReader;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * This class provides test cases for MapReader module.
 * 
 * @author Mahgol
 *
 */
public class MapReaderTest {
	
	World world;
	private String worldName1;
	private String worldName2;
	static MapReader map=new MapReader();
	File mapFile1;
	File mapFile2;
	
	@Before
	
	public void beforeCLass() {
		worldName1="resources//map//World.map";
		worldName2="resources//map//InvalidMap.map";
		mapFile1=new File(worldName1);
		mapFile2=new File(worldName2);
	}
    @Test
    public void validMapTest() {
        
       try {
		assertTrue(map.isValidMap(mapFile1));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    @Test
    public void InvalidMapTest(){
        
      try {
		assertFalse(map.isValidMap(mapFile2));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }


}
