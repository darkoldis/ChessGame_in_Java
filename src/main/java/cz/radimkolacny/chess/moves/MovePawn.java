/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.moves;

import cz.radimkolacny.chess.figures.Figure;
import java.awt.Color;

/**
 *
 * @author radimkolacny
 */
public class MovePawn extends Move{
    
    private final int[][] directionsBlackPawn = {{0,-1,1},
                                                 {1,1,1}};
    private final int[][] directionsWhitePawn = {{0,-1,1},
                                                 {-1,-1,-1}};
    
    public MovePawn(Figure figure) {
        super();
        if(figure.getColorOfFigure().equals(Color.WHITE)){
            directions = directionsWhitePawn;
        }
        else{
            directions = directionsBlackPawn;
        }
    }
}
