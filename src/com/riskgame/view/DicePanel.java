package com.riskgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import com.riskgame.utility.DiceType;
import com.riskgame.utility.DiceUtility;
import com.riskgame.utility.TurnPhase;

public class DicePanel extends JPanel {

	DiceUtility diceUtility = new DiceUtility();
	private JButton rollButton;
	private JLabel displayLabel;
	private int diceCount;
	private DiceType diceType;

	public DicePanel(DiceType diceType) {
		createDiceRollButton();
		diceCount = 0;
		this.diceType = diceType;
	}

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
				ImageIcon imageIcon = getDiceIcon();
				imageIcon =new ImageIcon(imageIcon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
				displayLabel.setIcon(imageIcon);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		add(rollButton);
		add(displayLabel);
	}

	public ImageIcon getDiceIcon() throws Exception {

		ImageIcon imageIcon = null;
		diceCount = diceUtility.rollDice();

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
	
	public int getDiceCount() {
		return this.diceCount;
	}

}
