package com.concordia.riskgame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import RiskyGame.Model.Gameplay;
import RiskyGame.Model.World;
import RiskyGame.Utility.MapReader;
import RiskyGame.View.BoardView;

/**
 * This class initializes the StartUpPhase View and invoke corresponding gameplay functions as per user-view interaction.
 */

public class BoardController implements ActionListener{
    private List<String> countries;
    private Gameplay gameplay;
    private BoardView sView;
    private MapReader reader=new MapReader();
    //private MapEditorController mapEditor=new MapEditorController(new MapEditorView(new Map()));

    /**
     * Instantiates a new startup phase controller.
     *
     * @param startupView : The startup view object
     */
    public BoardController(BoardView startupView) {
        this.countries=new ArrayList<String>();
        this.gameplay = new Gameplay();
        this.sView=startupView;
        initView();
    }


    /**
     * Getter method to get counties list
     * @return counties
     */
    public List<String> getCountries() {
        return countries;
    }


    /**
     * Setter method to set countries.
     * @param countries Countries
     */
    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    /**
     * Initializes the Startup phase view.
     */
    public void initView() {
        sView.initaliseUI();
        initController();

    }


    /**
     * Initialise the controller.
     */
    public void initController() {
        sView.getAddPlayerButton().addActionListener(this);
        sView.getRemovePlayerButton().addActionListener(this);
        sView.getPopulateCountriesButton().addActionListener(this);
        sView.getMapSelectorButton().addActionListener(this);
        sView.getShowMapButton().addActionListener(this);

    }


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource()==sView.getMapSelectorButton())
        {

            World existingMap=new World();
            gameplay.setSelectedMap(existingMap);
        }



        else if(event.getSource()==sView.getAddPlayerButton())
        {	if(gameplay.getPlayerCount()==0)
            gameplay.setPlayerCount(Integer.parseInt(sView.getPlayerCount().getSelectedItem().toString()));

            if(sView.getPlayerName().getText().contentEquals(""))
                JOptionPane.showMessageDialog(null,
                        "Please enter a player name", "Error Message",
                        JOptionPane.ERROR_MESSAGE);

                String message=gameplay.addPlayer(sView.getPlayerName().getText());
                JOptionPane.showMessageDialog(null,
                        message, "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                sView.getModel().clear();
                for(int i=0;i<gameplay.getPlayers().size();i++)
                    sView.getModel().add(i, gameplay.getPlayers().get(i).getPlayerName());
        }



        else if (event.getSource()==sView.getShowMapButton())
        {
            if(gameplay.getSelectedMap()==null)
                JOptionPane.showMessageDialog(null, "No Map Selected!");
            else
                mapEditor.showMapService(gameplay.getSelectedMap());
        }

    }






}
