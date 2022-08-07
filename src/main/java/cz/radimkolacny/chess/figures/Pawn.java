/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.figures;

import java.awt.Color;

/**
 *  Pěšec
 * @author radimkolacny
 */
public class Pawn extends Figure{
    
    private int positionOnBoardY;
    
    public Pawn(Color colorOfFigure, String nameOfFigure, int x, int y) {
        super(colorOfFigure, nameOfFigure, x, y);
        setFirstMove(true);
        figurePoints = 1;
        positionOnBoardY = 1;
    }
    public int getPositionOnBoardY() {
        return positionOnBoardY;
    }
    public void setPositionOnBoardY(int numberOfSteps) {
        this.positionOnBoardY = numberOfSteps;
    } 
}
