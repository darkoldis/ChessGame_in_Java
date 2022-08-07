/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.moves;

/**
 *
 * @author radimkolacny
 */
public class MoveQueen extends Move{

    private final int[][] directionsQueen = {{1,1,-1,-1,1,-1,0,0},
                                             {-1,1,1,-1,0,0,1,-1}};
    
    public MoveQueen() {
        super();
        directions = directionsQueen;
    }
}
