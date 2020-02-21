package com.riskgame.controller;

import com.riskgame.view.BoardView;

/**
 * This class collaborating to the GameFrame class to invoke the BoardView class
 */

public class BoardController{

    private BoardView boardView;
    public BoardController(BoardView board){
            this.boardView=board;
            boardView.initaliseUI();
    }



    }







