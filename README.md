## Implementing Risk Game Mission Mode for Concordia University's SOEN6441 Project

**Version 1.0.0**
---
**Members**

-Pushpa Gautam

-Jasmeet Walia

-Himani Gandhi

-Mahgol Moghaddas

---
Risk is a turn-based game for two to six players. The standard version is played on a board depicting a political map of the Earth, divided into forty-two territories. The objective of the game is to eliminate other players by occupying every territory on the board. Players use armies to attack and defend territories from other players. A random number generator determines the outcomes of battles. 

MVC Architecture has been followed in this Project. The main menu of the game has three options: New Game, Map, Editor Menu. 

Editor Menu lets Create a new Map or edit Existing map. Creating a new map has functionalities to add or delete a continent and/or a country and thereafter saving the changes. Once you click on New Game, you have two options: Selecting number of Players and Loading Map.

After selecting the number of players and loading a valid map, game begins. The game has 2 phases:

###### Game Setup Phase:

The Game SetUp Phase involves placement of armies depending on the order in which player gets a chance. Initially armies are placed randomly till all the territories are captured. Then Player with highest dice number initiates the game and places three armies as per his wish. This continues unil all armies are placed.

###### Game Play Phase:

This Phase involves three major steps: Reinforcement, Attack, Fortification

*Reinforcement:* Additional armies can be placed if the player has dominance over any continent.

*Attack:* Player with highest dice number gets to decide the defender and attacking territory. In case of unequal armies at war, the best of total turns are chosen and compared with the numbers on defender's dice.

*Fortify:* Once the player decides to not attack further, he gets a free move to reshuffle armies at the end of his turn.

---
# License & copyright

© Himani Gandhi, Concordia University

---


