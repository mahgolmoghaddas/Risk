package com.riskgame.test;
import com.riskgame.model.Game;
import com.riskgame.model.Player;

import java.util.ArrayList;

public class GameTest {
    public Game g;
        @Test
        public void duplicatePlayerTest(){
            ArrayList<Player> str=new ArrayList<>(6);
            g.setPlayerList(str);
            assertTrue(g.addPlayer("mahgol"));
        }


}
