package com.riskgame.view;

public class StartApplication {

	public static void main(String[] args) {
		try {
			MainWindowView mainWindowView = MainWindowView.getInstatnce();

			mainWindowView.launchMainWindow();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
