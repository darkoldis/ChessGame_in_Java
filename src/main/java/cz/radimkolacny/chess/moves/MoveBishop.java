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
public class MoveBishop extends Move{
    
    private final int[][] directionsBishop = {{1,1,-1,-1},
                                              {-1,1,1,-1}};
    
    public MoveBishop() {
        super();
        directions = directionsBishop;
    }
}
