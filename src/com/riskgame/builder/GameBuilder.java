package com.riskgame.builder;

import com.riskgame.model.Board;
/**
 * This is the interface which acts as abstract builder for saving and loading the game
 * @author gauta
 *
 */
public interface GameBuilder {

	public void saveGame();
	public Board loadGame();
}
