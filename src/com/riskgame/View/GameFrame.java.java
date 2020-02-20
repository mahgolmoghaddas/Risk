package RiskyGame.View;
import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.controller.StartupPhaseController;
import com.concordia.riskgame.model.Modules.Map;
import RiskyGame.Utility.MapReader;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class GameFrame{







    /**
     * ===============================================
     * THIS IS THE DRIVER CLASS FOR PROJECT
     * ================================================
     * THIS CLASS IS THE INITAL VIEW OF THE GAME.
     */


        private static final long serialVersionUID = 1L;
        private JFrame gameWindow;
        final static String GAMELAUNCHERPANEL = "Card with Game Launching View";
        private CardLayout cardLayout;
        private JPanel cardsContainerPanel;

        private JPanel gameLaunchPanel;
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
            gameLaunchPanel = new JPanel();
            gameLaunchPanel.setLayout(null);
            gameLaunchPanel.setVisible(true);


            startButton=new JButton("Start The Game");
            startButton.setVisible(true);
            startButton.setBounds(280, 210, 155, 21);
            gameLaunchPanel.add(startButton);

            //startButton.addActionListener(this);

            cardsContainerPanel=new JPanel(new CardLayout());
            cardsContainerPanel.add(gameLaunchPanel,GAMELAUNCHERPANEL);

            gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
            cardLayout=(CardLayout) cardsContainerPanel.getLayout();
            cardLayout.show(cardsContainerPanel, GAMELAUNCHERPANEL);

            gameWindow.pack();
            //gameWindow.setFont(Font.CE);
            gameWindow.setSize(700, 700);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setVisible(true);



        }






        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent event) {

            if(event.getSource()==startButton)
            {
                new com.concordia.riskgame.controller.BoardController(new BoardView(gameWindow,cardsContainerPanel));
            }

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


    }


