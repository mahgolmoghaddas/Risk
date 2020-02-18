package com.riskgame.model;

import java.util.List;

public class Player {
	
	private Score playerScore;
    private List<Card> cardsHeld;
    private Integer startDiceNo;
    private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStartDiceNo() {
		return startDiceNo;
	}
	public void setStartDiceNo(Integer startUpDiceNo) {
		this.startDiceNo = startUpDiceNo;
	}
    
    
}
