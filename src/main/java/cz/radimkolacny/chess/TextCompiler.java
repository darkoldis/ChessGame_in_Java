/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess;

/**
 *
 * @author radimkolacny
 */
public class TextCompiler {

    private static String endGameOutput;

    public static String getEndGameOutput() {
        return endGameOutput;
    }

    public static String createTextForOutput(Player winner, Player loser, String typeOfEndGame){
        if(typeOfEndGame.equals("CHECKMATE")){
            endGameOutput = winner + " WON over the " + loser;
        }
        else{
            endGameOutput = "DRAW - no one gained points\n but everybody got experience...";
        }
        return endGameOutput;
    }
}
