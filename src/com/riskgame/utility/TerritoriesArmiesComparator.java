package com.riskgame.utility;

import java.util.Comparator;
import com.riskgame.model.Territory;

public class TerritoriesArmiesComparator implements Comparator<Territory>{

	@Override
	public int compare(Territory territory1, Territory territory2) {
		Integer terr1ArmyCnt = territory1.getArmyCount();
		Integer terr2ArmyCnt = territory2.getArmyCount();
		return terr1ArmyCnt.compareTo(terr2ArmyCnt);
	}

}
