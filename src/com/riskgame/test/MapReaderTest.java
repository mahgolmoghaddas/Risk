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
 * @author Jasmeet
 *
 */
public class MapReaderTest {
    public String path1="map\\World.map";
    public String path2="map\\WrongMap.map";
    @Test
    public void validMapTest() {
        MapReader map = new MapReader();
//        assertTrue(map.mapValidity(new World(), path1));
    }
    @Test
    public void mapg(){
        MapReader map = new MapReader();
//        assertFalse(map.mapValidity(new World(), path2));
    }


}
