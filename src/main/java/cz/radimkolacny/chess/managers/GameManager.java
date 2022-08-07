/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.managers;

import cz.radimkolacny.chess.figures.Figure;
import cz.radimkolacny.chess.figures.King;
import cz.radimkolacny.chess.figures.Rook;
import cz.radimkolacny.chess.moves.MoveControler;
import cz.radimkolacny.chess.gamefield.Board;
import cz.radimkolacny.chess.gamefield.CapturedFigures;
import cz.radimkolacny.chess.gamefield.Cell;
import cz.radimkolacny.chess.forms.ChessJFrame;
import cz.radimkolacny.chess.gamefield.Player;
import cz.radimkolacny.chess.gamefield.SelectedCell;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Game manager is like judge
 *  He work with rules of game (take care of figures, moves and desides which player is on turn)
 * @author radimkolacny
 */
public class GameManager{
    
    private ChessJFrame chessJFrame = null;
    
    private Board gameBoard = null;

    private CapturedFigures panelBlackFigures = null;
    private CapturedFigures panelWhiteFigures = null;
    
    private Player whitePlayer = null;
    private Player blackPlayer = null;

    private Player playerOnTurn = null;
    
    private Player winner = null;
    private Player looser = null;
    
    //  generator for choose white player
    private final Random randomGenerator;

    private final int SIDES_OF_DICE = 6;
    
    private Cell selectedCell = null;
    private Figure selectedFigure = null;
    
    private boolean isKingSelect = false;
    private Cell cellWithKing = null;
    private Figure selectedKing = null;
    
    private MoveControler moveControler = null;

    private boolean endGame = false;

    public GameManager(ChessJFrame jFrame, Player player1, Player player2, Board gameBoard, CapturedFigures panelBlackFigures, CapturedFigures panelWhiteFigures) {
        
        this.chessJFrame = jFrame;
        
        randomGenerator = new Random();
        
        this.gameBoard = gameBoard;
        
        this.panelBlackFigures = panelBlackFigures;
        
        this.panelWhiteFigures = panelWhiteFigures;
        
        randomPlayerStart(player1, player2);
        
        setStartingPlayer(whitePlayer);
        
        connectPlayersToFigures();
        
        moveControler = new MoveControler(this);
        
        moveControler.createMovesForFigures(whitePlayer, endGame);
    }

    public ChessJFrame getChessJFrame() {
        return chessJFrame;
    }
    
    public Board getBoard() {
        return gameBoard;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    public void setEndGame(boolean endGame){
        this.endGame = endGame;
    }
    public boolean isEndGame() {
        return endGame;
    }
    
    public CapturedFigures getCapturedBlack() {
        return panelBlackFigures;
    }
    
    public CapturedFigures getCapturedWhite() {
        return panelWhiteFigures;
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getLooser() {
        return looser;
    }

    public void setLooser(Player looser) {
        this.looser = looser;
    }
    
    public Figure getSelectedFigure() {
        return selectedFigure;
    }
    
    public void setSelectedFigure(Figure figure) {
        selectedFigure = figure;
    }

    public MoveControler getMoveControler() {
        return moveControler;
    }
  
    private void randomPlayerStart(Player player1, Player player2){
        //  number which tell us which player will start the game
        int numberForChoosePlayer = randomGenerator.nextInt(SIDES_OF_DICE + 1);
        //  check randomly generate number for connecting figures and players
        //  and set actualPlayerOnTurn for start turn
        if(numberForChoosePlayer < 4){
            whitePlayer = player1;
            blackPlayer = player2;
        }
        else{
            whitePlayer = player2;
            blackPlayer = player1;
        }
    }

    private void setStartingPlayer(Player startingPlayer){
        playerOnTurn = startingPlayer;
    }

    private void connectPlayersToFigures(){
        gameBoard.getWhiteFigures().forEach(figure -> {
            figure.setPlayer(whitePlayer);
        });
        gameBoard.getBlackFigures().forEach(figure -> {
            figure.setPlayer(blackPlayer);
        });
    }
    /**
     * Method deal with click
     * Check click on board, and control everything from choose click to move figure
     * @param positionX
     * @param positionY 
     */
    public void processClick(int positionX, int positionY){
        
        System.out.println("Player " + playerOnTurn + " is on turn");
        boolean clickOnBoard = isClickOnBoard(positionX, positionY);
        Cell cellForMove = null;
        if(clickOnBoard){
            Cell clickedCell = gameBoard.getGameField()[positionX][positionY];
            
            if((clickedCell.getFigureOnTheField() != null) 
                    && playerOnTurn.equals(clickedCell.getFigureOnTheField().getPlayer())){

                selectedCell = clickedCell;
                //  save reference on figure
                selectedFigure = findFigure(selectedCell.getFigureOnTheField());
                //  if player select King we want to know king is select for possible future castling
                if(selectedFigure instanceof King){
                    cellWithKing = selectedCell;
                    selectedKing = selectedFigure;
                    isKingSelect = true;
                }
                else if(isKingSelect && (selectedFigure instanceof Rook)){
                    moveControler.tryCastling(selectedCell, selectedFigure, cellWithKing, selectedKing);
                    cellWithKing = null;
                    selectedKing = null;
                    isKingSelect = false;
                }
                //  after proper choose of figure set first all cells to no_select state
                moveControler.setNoSelectCells();
                //  when we choose right figure we set cell ass selected
                clickedCell.setSelectedCell(SelectedCell.SELECT);
            }
            //  if we clicked on empty or cell with opponent figure then we want to try move
            else if(selectedCell != null){
                cellForMove = gameBoard.getGameField()[positionX][positionY];
                moveControler.tryMove(selectedCell, selectedFigure, cellForMove); 
            }
        }
    }

    private boolean isClickOnBoard(int positionX, int positionY){
        return (positionX < gameBoard.getGameField().length) && (positionY < gameBoard.getGameField()[0].length)
                && (positionX >= 0) && (positionY >= 0);
    }
    /**
     * Method look into colection of figures in game and return finded figure
     * @param figureForFind
     * @return 
     */
    public Figure findFigure(Figure figureForFind){
        Figure findedFigure = null;
        for(Figure figure : gameBoard.getFiguresFromBoard()){
            if(figure.equals(figureForFind)){
                findedFigure = figure;
                return findedFigure;
            }
        }
        return findedFigure;
    }
    /**
     * Method check whole board and take the figures which are player's
     * @param player
     * @return 
     */
    public List<Figure> getPlayerFigures(Player player){
        
        List<Figure> findedFigures = new ArrayList<>(); 
        Cell[][] board = gameBoard.getGameField();
        
        for(int row = 0; row < board[0].length; row++){
            for(int column = 0; column < board.length; column++){
                Figure figureOnField = board[column][row].getFigureOnTheField();
                if((figureOnField != null) && player.equals(figureOnField.getPlayer())){
                    findedFigures.add(figureOnField);
                }
            }
        }
        return findedFigures;
    }
    
    private void putFigureToCapturedList(Figure capturedFigure){
        moveControler.figureToCaptureList(capturedFigure);
    }
    
    public void changePlayers(){
        if(playerOnTurn.equals(whitePlayer)){
            //  if players which finish the move is white then give turn to back player
            playerOnTurn = blackPlayer;
        }
        else{
            //  if players which finish the move is black then give turn to white player
            playerOnTurn = whitePlayer;
        }
    }
    
    public Player getSecondPlayer(Player firstPlayer){
        if(firstPlayer.equals(whitePlayer)){
            return blackPlayer;
        }
        else{
            return whitePlayer;
        }
    } 
}
