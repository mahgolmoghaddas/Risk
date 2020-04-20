package com.riskgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.riskgame.enums.GameScreen;
import com.riskgame.model.GameLogs;

public class TournamentPhaseView implements Observer{

	JTextArea tournamentConsole = new JTextArea();
	private JFrame tournamentPhaseFrame;
	private GameLogs gameLogs;

	public TournamentPhaseView(GameLogs gameLogs) {
		this.gameLogs = gameLogs;
		gameLogs.addObserver(this);
		tournamentPhaseFrame = new JFrame("Tournament Phase View");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		double width = GameScreen.WIDTH.getValue();
		double height = GameScreen.HEIGHT.getValue();
		dim.width = (int) (dim.width * width);
		dim.height = (int) (dim.height * height);
		tournamentPhaseFrame.setSize(dim);
		tournamentPhaseFrame.setVisible(true);
		tournamentPhaseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = createTournamentConsole();
		tournamentPhaseFrame.getContentPane().add(scrollPane, BorderLayout.EAST);
		tournamentPhaseFrame.revalidate();
	}

	private JScrollPane createTournamentConsole() {
		tournamentConsole.setSize(new Dimension(500, 300));
		tournamentConsole.setBorder(new LineBorder(Color.decode("#42f5e3"), 1));
		tournamentConsole.setBackground(Color.WHITE);
		tournamentConsole.setOpaque(false);
		tournamentConsole.setWrapStyleWord(true);
		tournamentConsole.setEditable(false);
		tournamentConsole.setLineWrap(true);
		tournamentConsole.setText("HELLO THERE");
		JScrollPane textScroller = new JScrollPane(tournamentConsole);
		textScroller.setViewportBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return textScroller;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof GameLogs) {
			System.out.println(tournamentConsole);
			System.out.println(gameLogs);
			tournamentConsole.append(gameLogs.getLogs());
			this.tournamentPhaseFrame.revalidate();
		}
	}
}
