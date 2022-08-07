/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.figures;

import java.awt.Color;

/**
 *
 * @author radimkolacny
 */
public class Bishop extends Figure{

    public Bishop(Color colorOfFigure, String nameOfFigure, int x, int y){
        super(colorOfFigure, nameOfFigure, x, y);
        figurePoints = 3;
    }

}
