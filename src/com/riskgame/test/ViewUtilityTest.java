
package com.riskgame.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.riskgame.model.World;
import com.riskgame.utility.ViewUtility;

public class ViewUtilityTest {
	ViewUtility vu = new ViewUtility();
	World world = null;

	@Test
	public void createWorldMapTableTest() {

		boolean val = false;
		try {
			assertNull(vu.createWorldMapTable(world));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (world == null)
			val = true;
		assertTrue("world is null", val);
	}

}