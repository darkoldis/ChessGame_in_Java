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
public class MoveKnight extends Move{

    private final int[][] directionsKnight = {{-2,-2,2,2,-1,-1,1,1},
                                              {-1,1,1,-1,-2,2,-2,2}};
    
    public MoveKnight() {
        super();
        directions = directionsKnight;
    }
}
