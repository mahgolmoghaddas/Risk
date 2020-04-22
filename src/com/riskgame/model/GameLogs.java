package com.riskgame.model;

import java.util.Observable;

/**
 * This class gives information about the Logs of the game using Singleton Pattern
 *
 */
public class GameLogs extends Observable {

	private String logs;

	private static GameLogs gameLogs;

	private GameLogs() {

	}

	public static GameLogs getInstance() {
		if (gameLogs == null) {
			gameLogs = new GameLogs();
		}
		return gameLogs;
	}

	public String getLogs() {
		return logs;
	}

	public void log(String logs) {
		this.logs = "\n" + logs;
		logsChanged();
	}

	private void logsChanged() {
		setChanged();
		notifyObservers();
	}

}
