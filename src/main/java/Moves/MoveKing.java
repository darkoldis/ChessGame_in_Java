/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Moves;

/**
 *
 * @author radimkolacny
 */
public class MoveKing extends Move{
    
    private final int[][] directionsKing = {{1,1,-1,-1,1,-1,0,0},
                                             {-1,1,1,-1,0,0,1,-1}};
    
    public MoveKing() {
        super();
        directions = directionsKing;
    }
}
