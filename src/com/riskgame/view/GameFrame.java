package com.riskgame.view;
import com.riskgame.utility.MapReader;
import com.riskgame.controller.*;
import com.riskgame.view.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameFrame implements ActionListener {





        private static final long serialVersionUID = 1L;

    private JFrame gameWindow;
        final static String GAMELAUNCHERPANEL = "Card with Game Launching View";
        private CardLayout cardLayout;
        private JPanel cardsContainerPanel;

        private JPanel panel;
        private JButton startButton;
        private MapReader reader;




        /**
         * Instantiates a new game launcher view.
         */
        public GameFrame() {
            JFrame.setDefaultLookAndFeelDecorated(true);
            gameWindow=new JFrame("Welcome To The Risk Game");

            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            reader=new MapReader();
            panel = new JPanel();
            panel.setLayout(null);
            panel.setVisible(true);
            startButton=new JButton("Start The Game");
            startButton.setVisible(true);
            startButton.setBounds(230, 220, 155, 31);
            panel.add(startButton);
            startButton.addActionListener(this);
            cardsContainerPanel=new JPanel(new CardLayout());
            cardsContainerPanel.add(panel,GAMELAUNCHERPANEL);
            gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
            cardLayout=(CardLayout) cardsContainerPanel.getLayout();
            cardLayout.show(cardsContainerPanel, GAMELAUNCHERPANEL);
            gameWindow.setSize(700, 700);
            gameWindow.setVisible(true);



        }









        /**
         * create menu bar method
         */


        /**
         * The main method.
         *
         * @param args the arguments
         */
        public static void main(String args[]) {
            new GameFrame();

        }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton)
        {
            new BoardController(new BoardView(gameWindow,cardsContainerPanel));
        }
    }
}


