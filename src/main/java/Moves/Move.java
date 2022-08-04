/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Moves;

/**
 * @author radimkolacny
 */
public abstract class Move{

    protected int shiftInRow = 0;
    protected int shiftInColumn = 0;

    protected boolean verticalMove = false;
    protected boolean horizontalMove = false;
    protected boolean diagonalMove = false; 
    
    protected int[][] directions = null;
}