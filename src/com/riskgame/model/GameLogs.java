package com.riskgame.model;

import java.util.Observable;

public class GameLogs extends Observable {

	private String logs;


	public String getLogs() {
		return logs;
	}

	public void log(String logs) {
		this.logs = "\n"+ logs;
		logsChanged();
	}

	private void logsChanged() {
		setChanged();
		notifyObservers();
	}

}
