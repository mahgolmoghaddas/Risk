package com.riskgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

import com.riskgame.model.Board;
import com.riskgame.model.Player;
import com.riskgame.utility.DiceType;
import com.riskgame.utility.DiceUtility;

/**
 * This is the panel which provides a user GUI to roll a dice
 * 
 * @author pushpa
 *
 */
public class DicePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	DiceUtility diceUtility = new DiceUtility();
	private JButton rollButton;
	private JLabel displayLabel;
	private JLabel diceCountLabel = new JLabel();
	private int diceCount;
	private DiceType diceType;
	private int maxAllowedRoll;
	private Board board;
	private boolean isSetUpPhase;
	private int rolledCount;
	private ArrayList tempDiceList = new ArrayList<>();

	public DicePanel(DiceType diceType, Board board, boolean isSetUpPhase) {
		createDiceRollButton();
		diceCount = 0;
		this.diceType = diceType;
		this.board = board;
		this.isSetUpPhase = isSetUpPhase;
		this.maxAllowedRoll = calculateMaxAllowedRoll();
		rolledCount = 1;
	}

	/**
	 * This method returns the maximum number of Roll allowed for a player defending
	 * in the Game Phase.
	 *
	 * @return 3 for attack, 2 for defend and for setup phase, max roll is equal to
	 *         the number of players in the game
	 */
	public int calculateMaxAllowedRoll() {
		int value = 0;
		if (isSetUpPhase) {
			value = board.getPlayerList().size();
		} else {
			if (DiceType.Attack.equals(this.diceType)) {
				value = DiceType.Attack.getMaxAllowedRoll();
			} else if (DiceType.Defend.equals(diceType)) {
				value = DiceType.Defend.getMaxAllowedRoll();
			}
		}
		return value;
	}

	/**
	 * This method creates a dice roll botton, which when clicked displays 1-6
	 * numbered faces die
	 */
	public void createDiceRollButton() {

		rollButton = new JButton("Roll");
		Dimension prefSize = rollButton.getPreferredSize();
		prefSize.setSize(prefSize.getWidth(), 40);
		rollButton.setPreferredSize(prefSize);
		rollButton.setUI(new BasicButtonUI());
		rollButton.setForeground(Color.WHITE);
		rollButton.setBackground(Color.BLUE);
		displayLabel = new JLabel();
		rollButton.addActionListener(e -> {
			try {
				System.out.println("RolledCount " + rolledCount + "MaxALlowedRoll " + maxAllowedRoll);
				ImageIcon imageIcon = getDiceIcon();
				imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
				displayLabel.setIcon(imageIcon);
				if (isSetUpPhase) {
					updatePlayer(diceCount);
				}else {
					String text = "";
					if (diceCountLabel.getText() != null && !diceCountLabel.getText().isEmpty()) {
						text = diceCountLabel.getText().concat(" ") + diceCount;
					}else {
						text = diceCount+"";
					}
					diceCountLabel.setText(text);
					diceCountLabel.setVisible(true);
				}
				

				rolledCount++;

				if (rolledCount > maxAllowedRoll) {
					tempDiceList.clear();
					rollButton.setEnabled(false);
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		add(rollButton);
		add(displayLabel);
		add(diceCountLabel);
	}

	/**
	 * Updates the start dice number for a particular player
	 * 
	 * @param diceCount
	 */
	private void updatePlayer(int diceCount) {
		List<Player> playerList = this.board.getPlayerList();

		for (int i = 0; i < playerList.size(); i++) {
			Player player = playerList.get(i);
			if (player.getId() == this.rolledCount) {
				System.out.println("Updating player DiceCount");
				player.setStartDiceNo(diceCount);
				break;
			}
		}

	}

	/**
	 * This returns the dice image as per the phase whether Attack/Defend with the
	 * randomly generated dice number from 1 to 6.
	 * 
	 * @return ImageIcon
	 * @throws Exception
	 */
	public ImageIcon getDiceIcon() throws Exception {

		ImageIcon imageIcon = null;
		diceCount = getUniqueDice();

		switch (diceCount) {
		case 1:
			if (DiceType.Attack.equals(diceType)) {

				imageIcon = new ImageIcon("resources/images/RedDice1.jpg");
			} else {
				imageIcon = new ImageIcon("resources/images/BlackDice1.png");
			}
			break;
		case 2:
			if (DiceType.Attack.equals(diceType)) {

				imageIcon = new ImageIcon("resources/images/RedDice2.jpg");
			} else {
				imageIcon = new ImageIcon("resources/images/BlackDice2.png");
			}
			break;
		case 3:
			if (DiceType.Attack.equals(diceType)) {

				imageIcon = new ImageIcon("resources/images/RedDice3.jpg");
			} else {
				imageIcon = new ImageIcon("resources/images/BlackDice3.png");
			}
			break;
		case 4:
			if (DiceType.Attack.equals(diceType)) {

				imageIcon = new ImageIcon("resources/images/RedDice4.jpg");
			} else {
				imageIcon = new ImageIcon("resources/images/BlackDice4.png");
			}
			break;
		case 5:
			if (DiceType.Attack.equals(diceType)) {

				imageIcon = new ImageIcon("resources/images/RedDice5.jpg");
			} else {
				imageIcon = new ImageIcon("resources/images/BlackDice5.png");
			}
			break;
		case 6:
			if (DiceType.Attack.equals(diceType)) {

				imageIcon = new ImageIcon("resources/images/RedDice6.jpg");
			} else {
				imageIcon = new ImageIcon("resources/images/BlackDice6.png");
			}
			break;

		default:
			break;
		}

		return imageIcon;
	}

	/**
	 * Returns the diceCount on a particular role
	 * 
	 * @return
	 */
	public int getDiceCount() {
		return this.diceCount;
	}

	/**
	 * This method returns the unique Dice number in a particular rolling phase to
	 * ensure no players have the same dice number
	 * 
	 * @return int
	 */
	public int getUniqueDice() {

		int diceNum = 0;
		try {
			diceNum = diceUtility.rollDice();
			System.out.println("Dice num :: " + diceNum);
			while (this.tempDiceList.contains(diceNum)) {

				diceNum = diceUtility.rollDice();
			}
			tempDiceList.add(diceNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diceNum;
	}

}
