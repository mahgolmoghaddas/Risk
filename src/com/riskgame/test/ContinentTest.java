  package com.riskgame.test;
    import static org.hamcrest.CoreMatchers.hasItems; import static
  org.junit.jupiter.api.Assertions.*;
    import org.junit.Test;
  import java.util.Arrays; import java.util.List;
  
 // import org.junit.jupiter.api.Test;
  import com.riskgame.model.Continent;
import com.riskgame.utility.CardType; import
  com.riskgame.utility.RiskUtility;
  
 public class ContinentTest {
  
  @Test 
  public void ContinentNameTest() {
	  boolean condition=false;
	  List<String> continentList = Arrays.asList("Europe","North America","Asia","Africa","South America"
			  ,"Antarctica","Australia");
	  Continent continent = new Continent();
	  continent.setContinentName("Asia");
	  if(continentList.contains(continent.getContinentName())) {
		  condition = true;
	  }
	  assert(condition);
  }
  
  @Test
  public void NoContinentTest() {
	  boolean condition=true;
	  List<String> continentList = Arrays.asList("Europe","North America","Asia","Africa","South America"
			  ,"Antarctica","Australia");
	  Continent continent = new Continent();
	  continent.setContinentName("Italy");
	  if(!continentList.contains(continent.getContinentName())) {
		  condition = false;
	  }
	  assertFalse(condition);
  }
  }
 