package com.riskgame.view;
/**
 * This is the main class to launch the Risk Game application
 * @author pushpa
 *
 */
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
