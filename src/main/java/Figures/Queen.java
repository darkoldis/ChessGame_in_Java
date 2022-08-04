/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Figures;

import java.awt.Color;

/**
 *
 * @author radimkolacny
 */
public class Queen extends Figure{

    public Queen(Color colorOfFigure, String nameOfFigure, int x, int y) {
        super(colorOfFigure, nameOfFigure, x, y);
        figurePoints = 9;
    } 
}
