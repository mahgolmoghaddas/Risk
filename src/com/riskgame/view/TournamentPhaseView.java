package com.riskgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.riskgame.enums.GameScreen;
import com.riskgame.model.GameLogs;

/**
 * This class provides the console view for the selected tournament mode. It provides information related to the game phase, current player, battle results etc. 
 * @author pushpa
 *
 */
public class TournamentPhaseView implements Observer {

	private static final long serialVersionUID = 1L;
	JTextArea tournamentConsole = new JTextArea();
	private GameLogs gameLogs;

	public TournamentPhaseView(GameLogs gameLogs) {
		this.gameLogs = gameLogs;
		gameLogs.addObserver(this);
		createAndShowFrame();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof GameLogs) {
			System.out.println(gameLogs.getLogs());
			tournamentConsole.append(gameLogs.getLogs());
			tournamentConsole.repaint();
		}
	}

	/**
	 * This method creates and displays the frame with scroll pane for the tournament console view
	 */
	public void createAndShowFrame() {
		JFrame tournamentPhaseFrame = new JFrame("Tournament Phase View");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		double width = GameScreen.WIDTH.getValue();
		double height = GameScreen.HEIGHT.getValue();
		dim.width = (int) (dim.width * width);
		dim.height = (int) (dim.height * height);
		tournamentPhaseFrame.getContentPane().add(createTournamentScrollPanel(),BorderLayout.CENTER); // need to modify this line
		tournamentPhaseFrame.setSize(dim);
		tournamentPhaseFrame.setVisible(true);
		tournamentPhaseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method creates a JPanel with the text area which appends the logs for the tournament activities
	 * @return JPanel Panel with Text area
	 */
	private JPanel createTournamentScrollPanel() {
		JPanel tournamentScrollPanel = new JPanel();
		tournamentConsole = new JTextArea();
		Font font = new Font("Calibri", Font.PLAIN, 15);
		tournamentConsole.setFont(font);
		tournamentScrollPanel.setLayout(new BorderLayout());
		tournamentScrollPanel.add(new JScrollPane(tournamentConsole, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		return tournamentScrollPanel;
	}
}
