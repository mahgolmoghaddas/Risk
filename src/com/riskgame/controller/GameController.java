package com.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.riskgame.model.Card;
import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.model.World;
import com.riskgame.utility.GameUtility;
import com.riskgame.view.BoardView;
import com.riskgame.view.NewGameView;

/**
 * This class provides the
 * 
 * @author gauta
 *
 */
public class GameController implements ActionListener {

	private boolean isGamePlay;
	private World world;
	private int numberOfPlayers;
	private BoardView boardView;
	private Board game ;
	private NewGameView newGameView;
	private GameUtility gameUtility = new GameUtility();
	
	public GameController(boolean isGamePlay) {
		this.isGamePlay = isGamePlay;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		try {
			if (isGamePlay) {
				initiateBoardAndPlayGame();
			} else {
				newGameView = new NewGameView(this);
				newGameView.launchNewGameFrame();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setGameParameters(World world, int numberOfPlayers, boolean isGamePlay) {
		this.isGamePlay = isGamePlay;
		this.world = world;
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void initiateBoardAndPlayGame() {
		try {
			game = Board.getInstance();
			
			ArrayList<Player> playerList = gameUtility.createPlayers(numberOfPlayers);
			ArrayList<Card> cardDeck = gameUtility.buildCardDeck(world);
			game.initializeGame(world, playerList,cardDeck);
			
			boardView = new BoardView(game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
