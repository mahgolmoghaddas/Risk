package com.riskgame.utility;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;

import com.riskgame.enums.GameScreen;

public class ViewUtility {

	public JFrame createMainFrame(String name, boolean showBackGround) {
		JFrame frame = new JFrame(name);
		try {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			double width = GameScreen.WIDTH.getValue();
			double height = GameScreen.HEIGHT.getValue();
			dim.width = (int) (dim.width * width);
			dim.height = (int) (dim.height * height);

			System.out.println(dim.getHeight() + " " + dim.getWidth());
			frame.setSize(dim);

			frame.getContentPane().setLayout(new FlowLayout());
			if (showBackGround) {
				final JLabel backGround = new JLabel(new ImageIcon(((new ImageIcon("resources/images/RiskGame.jpg")
						.getImage().getScaledInstance(frame.getSize().width, (int) ((int) frame.getSize().height - 30),
								java.awt.Image.SCALE_SMOOTH)))));

				frame.add(backGround);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return frame;
	}

	public JMenu createMenu(String name) throws Exception {
		JMenu menu = new JMenu(name);
		menu.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 13));

		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		menu.setCursor(cursor);
		return menu;
	}
}
