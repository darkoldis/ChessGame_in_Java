/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.gamefield;

import java.awt.Image;

/**
 *
 * @author radimkolacny
 */
public class Player{
    
    private static int nextId = 0;
    private final int id;
    private String name;
    private Image imageOfPlayer = null;
    private int gamePoints = 0;

    public Player(String name) {
        id = nextId;
        nextId++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(int gamePoints) {
        this.gamePoints = gamePoints;
    }

    public void addGamePoints(int points){
        gamePoints += points;
    }
    
    @Override
    public String toString(){
        return name; 
    }  
}
