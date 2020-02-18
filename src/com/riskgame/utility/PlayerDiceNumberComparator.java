package com.riskgame.utility;

import java.util.Comparator;

import com.riskgame.model.Player;

public class PlayerDiceNumberComparator  implements Comparator<Player>{

	@Override
	public int compare(Player player1, Player player2) {
		return player1.getStartDiceNo().compareTo(player2.getStartDiceNo());
	}
}
