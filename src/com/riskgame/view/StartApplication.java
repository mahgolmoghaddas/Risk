package com.riskgame.view;

public class StartApplication {

	public static void main(String[] args) {
		try {
			MainWindowView mainWindowView = MainWindowView.getInstance();

			mainWindowView.launchMainWindow();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
