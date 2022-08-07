/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess;

import cz.radimkolacny.chess.gamefield.CapturedFiguresBlack;
import cz.radimkolacny.chess.gamefield.Player;
import cz.radimkolacny.chess.gamefield.CapturedFiguresWhite;
import cz.radimkolacny.chess.gamefield.CapturedFigures;
import cz.radimkolacny.chess.gamefield.Board;
import cz.radimkolacny.chess.forms.ChessJFrame;
import cz.radimkolacny.chess.managers.GameManager;
import cz.radimkolacny.chess.managers.FileManager;

/**
 *
 * @author radimkolacny
 */
public class ChessGame{
    
    private ChessJFrame chessJFrame;
    
    private GameManager gameManager;
    
    private FileManager fileManager;

    private Board board = null;
    
    private CapturedFigures captureFiguresBlack = null;
    private CapturedFigures captureFiguresWhite = null;
    //  players for game
    private Player player1;
    private Player player2;
    
    public ChessGame(ChessJFrame chessJFrame,String player1, String player2) {
        
        this.chessJFrame = chessJFrame;
        
        board = new Board();
        
        captureFiguresBlack = new CapturedFiguresBlack();
        captureFiguresWhite = new CapturedFiguresWhite();

        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        
        gameManager = new GameManager(chessJFrame, this.player1, this.player2, board, captureFiguresBlack, captureFiguresWhite);
        
        fileManager = new FileManager();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    /**
     * Method manipulating coordinates of clicking on gamefield
     * 
     * @param coordinateX
     * @param coordinateY 
     */
    public void click(int coordinateX, int coordinateY) {
        //  we need to divide coordinates by SIDE of cell
        //  for getting position of cell inside gamefield
        int positionX = coordinateX / Board.getSIDE();
        int positionY = coordinateY / Board.getSIDE();

        gameManager.processClick(positionX, positionY);
    }
}