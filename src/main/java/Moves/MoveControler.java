/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Moves;

import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;
import Managers.GameManager;
import cz.radimkolacny.chess.Board;
import cz.radimkolacny.chess.CapturedFigures;
import cz.radimkolacny.chess.Cell;
import cz.radimkolacny.chess.Player;
import cz.radimkolacny.chess.SelectedCell;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author radimkolacny
 */
public class MoveControler{
    
    private GameManager gameManager = null;
    private Player actualPlayerOnTurn = null;
    private Move moveOfFigure = null;
    
    protected Board gameBoard = null;
    protected Cell[][] gameField = null;
    
    protected int shiftColumn = 0;
    protected int shiftRow = 0;
    
    protected MoveType typeOfMove = null;
    protected String typeOfEndGame = null;

    protected static final int START_POSITION_GAME_FIELD = 0;
    protected static final int END_POSITION_GAME_FIELD = Board.getCELLS_ON_SIDE() - 1;

    public MoveControler(GameManager gameManager) {
        
        this.gameManager = gameManager;
        
        gameBoard = gameManager.getBoard();
        
        gameField = gameBoard.getGameField();
        
        actualPlayerOnTurn = gameManager.getPlayerOnTurn();
    }

    public String getTypeOfEndGame() {
        return typeOfEndGame;
    }
    
    /**
     *  Method add possible moves for all figures of wanted player
     * @param wantedPlayer 
     * @return player figures on board
     */
    public List<Figure> createMovesForFigures(Player wantedPlayer, boolean test){
        
        List<Figure> playerFiguresOnBoard = gameManager.getPlayerFigures(wantedPlayer);
        //  loop for iterate figures for adding possible moves
        for(Figure actualFigure : playerFiguresOnBoard){
            //  setup empty list of poss. moves for actual figure
            setEmptyNewMoves(actualFigure);
            //  create instance of move by type of figure
            createMoveByFigure(actualFigure);
            //  fill the moves by possible moves of figure
            addPossibleMoves(actualFigure, test);
        }
        return playerFiguresOnBoard;
    }
  
    protected final void setEmptyNewMoves(Figure actualFigure){
        actualFigure.setPossibleMoves(new boolean[Board.getCELLS_ON_SIDE()][Board.getCELLS_ON_SIDE()]);
    }
   
    private void createMoveByFigure(Figure actualFigure){
        //  if on choosen cell is Bishop, we use bishop move - diagonal
        if(actualFigure instanceof Bishop){
            moveOfFigure = new MoveBishop();
        }
        //  if on choosen cell is Rook, we use rook move - vertical, horizontal
        else if(actualFigure instanceof Rook){
            moveOfFigure = new MoveRook();
        }
        //  if on choosen cell is Queen, we use queen move - vertical, horizontal and diagonal
        else if(actualFigure instanceof Queen){
            moveOfFigure = new MoveQueen();
        }
        //  if on choosen cell is King, we use king move - vertical, horizontal and diagonal in 1 position
        else if(actualFigure instanceof King){
            moveOfFigure = new MoveKing();
        }
        //  if on choosen cell is King, we use king move - vertical, horizontal and diagonal in 1 position
        else if(actualFigure instanceof Knight){
            moveOfFigure = new MoveKnight();
        }
        else if(actualFigure instanceof Pawn){
            moveOfFigure = new MovePawn(actualFigure);
        }
    }
    /**
     * Method will add possible moves for figure on given cell
     * Also control if in direction of possible move is figure and then stop adding possible move behind them
     * @param actualFigure
     * @param test
     */
    public void addPossibleMoves(Figure actualFigure, boolean test){

        boolean[][] possibleMoves = actualFigure.getPossibleMoves();
        Player actualPlayer = actualFigure.getPlayer();
        
        for(int direction = 0; direction < moveOfFigure.directions[0].length; direction++){
            //  0 in column is for column shift
            shiftColumn = moveOfFigure.directions[0][direction];
            //  1 in column is for row shift
            shiftRow = moveOfFigure.directions[1][direction];
            
            typeOfMove = controlTypeOfMove(shiftColumn, shiftRow);
            
            int stepsForIteration = setupStepsForIteration(actualFigure);
            //  actual column for check possible move
            int nextColumn = actualFigure.getX();
            //  actual row for check possible move
            int nextRow = actualFigure.getY();
            
            for(int step = 0; step < stepsForIteration; step++){
                
                boolean overStepBoard = overstepRangeOfBoard(shiftColumn, shiftRow, nextColumn, nextRow);
                
                if(!overStepBoard){
                    //  we want increase with actual shifts in column and row, because we want to move with iteration
                    //  in given direction
                    nextColumn += shiftColumn;
                    nextRow += shiftRow;
                    if(actualFigure instanceof Pawn){
                        addPawnMove(possibleMoves, actualPlayer, nextColumn, nextRow);
                    }
                    else{
                        if(isFigure(gameField[nextColumn][nextRow])){
                            //  if figure is from actual player, we cannot move there
                            if(actualPlayer.equals(gameField[nextColumn][nextRow].getFigureOnTheField().getPlayer())){
                                possibleMoves[nextColumn][nextRow] = false;
                            }
                            //  if figure is from opponent player we can move
                            else{
                                possibleMoves[nextColumn][nextRow] = true;
                            }
                            //  when we find figure on way we will stop iteration behind
                            break;
                        }
                        //  otherwise we add another possible move in our direction
                        possibleMoves[nextColumn][nextRow] = true;
                    }
                }
                else{
                    break;
                }
            }
        }
        if(test){
            iteratePossibleMovesForCheck(actualFigure, possibleMoves);
        }
    }

    private MoveType controlTypeOfMove(int shiftColumn, int shiftRow) {
        
        if((shiftColumn != 0) && (shiftRow != 0)){
            return MoveType.DIAGONAL;
        }
        else if((shiftColumn != 0) && (shiftRow == 0)){
            return MoveType.HORIZONTAL;
        }
        else{
            return MoveType.VERTICAL;
        }
    }
    /**
     * Method setup steps for iteration of direction
     */
    private int setupStepsForIteration(Figure figure){
        //  if figure is king we need to setup one step because we are using bishop and rook move (whole distance)
            if(figure instanceof King || figure instanceof Knight){
                if(!overstepRangeOfBoard(shiftColumn, shiftRow, figure.getX(), figure.getY())){
                    return 1;
                }
            }
            else if(figure instanceof Pawn){
                Pawn pawnFigure = (Pawn) figure;
                if(pawnFigure.isFirstMove() && typeOfMove.equals(MoveType.VERTICAL)){
                    return 2;
                    //  when we finish first move with pawn we have to set that to false - pawn now can go just in 1 step not 2
                }
                else{
                    return 1;
                }
            }
            else{
                //  this is maximal steps could be on board for other figures
                return 7;
            }
            return 0;
    }
    
    protected boolean overstepRangeOfBoard(int shiftColumn, int shiftRow, int actualPositionX, int actualPositionY){
        int positionForColumn = actualPositionX + shiftColumn;
        int positionForRow = actualPositionY + shiftRow;
        return (positionForColumn < START_POSITION_GAME_FIELD) || (positionForColumn > END_POSITION_GAME_FIELD)
                || (positionForRow < START_POSITION_GAME_FIELD) || (positionForRow > END_POSITION_GAME_FIELD);
    } 
    
    private void addPawnMove(boolean[][] possibleMoves, Player actualPlayer, int nextColumn, int nextRow){
        if(typeOfMove.equals(MoveType.VERTICAL)){
            if(isFigure(gameField[nextColumn][nextRow])){
                possibleMoves[nextColumn][nextRow] = false;
            }
            else{
                possibleMoves[nextColumn][nextRow] = true;
            }
        }
        else if(typeOfMove.equals(MoveType.DIAGONAL)){
            if(isFigure(gameField[nextColumn][nextRow])){
                if(!actualPlayer.equals(gameField[nextColumn][nextRow].getFigureOnTheField().getPlayer()))
                    possibleMoves[nextColumn][nextRow] = true;
            }
            else{
                possibleMoves[nextColumn][nextRow] = false;
            }
        }
    }

    public boolean isFigure(Cell cellForControl){
        return !cellForControl.isEmpty();
    }
    
    public void iteratePossibleMovesForCheck(Figure actualFigure, boolean[][] possibleMoves){
        for(int j = 0; j < possibleMoves[0].length; j++){
            for(int i = 0; i < possibleMoves.length; i++){
                //  CONTROL CHECK
                if(possibleMoves[i][j]){
                    testFutureCheck(actualFigure, gameField[i][j]);
                }
            }
        }
    }
    
    public boolean testActualCheck(){
        boolean controlActualCheck = true;
        return isCheck(controlActualCheck);
    }
    /**
     * Method will control if choosen move will be possible, beacuse after that could be check
     * Control all of opponent figure possible moves for imaginare choosen move
     * @param actualFigure
     * @param cellForMove  
     */
    public void testFutureCheck(Figure actualFigure, Cell cellForMove){
        
        System.out.println("TEST CHECK - actual figure: " + actualFigure + ", cellForMove: " + cellForMove);
        //  figure which will be taken from opponent
        Figure testBackFigure = null;
        //  control if cell for move will be empty or not
        boolean isTestBackCellEmpty = true;
        boolean[][] possibleMovesOfFigure;
        //  if there is any figure we wanna save that figure for later change back
        //  test back
        if(cellForMove.getFigureOnTheField() != null){
            testBackFigure = gameManager.findFigure(cellForMove.getFigureOnTheField());
            isTestBackCellEmpty = false;
        }
        //  actual possible moves for choosen figure
        possibleMovesOfFigure = actualFigure.getPossibleMoves();
        //  move figure to choosen cell (cell for move)
        moveFigureToCell(cellForMove, actualFigure);        
        //  try to find check on king for this move
        //  if si check than we cant move there
        //  we need to know if we control check from actual position or future positions
        boolean controlActualCheck = false;
        
        if(isCheck(controlActualCheck)){
            //  set that possible move to false
            possibleMovesOfFigure[cellForMove.getX()][cellForMove.getY()] = false;
        }
        //  move figures back
        moveFigureTestBack(actualFigure, cellForMove, testBackFigure, isTestBackCellEmpty);
    }
    /**
     * Method replace figure after testing check on king back
     * @param figure
     * @param cellForMove
     * @param figureForEmptyCell
     * @param isCellEmpty 
     */
    private void moveFigureTestBack(Figure figure, Cell cellForMove, Figure figureForEmptyCell, boolean isCellEmpty){
        //  set figure to cell where figure was before move
        gameField[figure.getX()][figure.getY()].setFigureOnTheField(figure);
        //  set cell where we replace figure back to is not emty now
        gameField[figure.getX()][figure.getY()].setIsEmpty(false);
        //  set figureForEmptyCell which we save for back cell for move
        gameField[cellForMove.getX()][cellForMove.getY()].setFigureOnTheField(figureForEmptyCell);
        //  set cell is not empty now
        gameField[cellForMove.getX()][cellForMove.getY()].setIsEmpty(isCellEmpty);
    }
    
    public void moveFigureToCell(Cell cellForMove, Figure figure){
        //  set moving figure for new cell
        gameField[cellForMove.getX()][cellForMove.getY()].setFigureOnTheField(figure);
        //  set cell is not empty now
        gameField[cellForMove.getX()][cellForMove.getY()].setIsEmpty(false);
        //  set cell from moving figure is without figure (null)
        gameField[figure.getX()][figure.getY()].setFigureOnTheField(null);
        //  set cell old cell to empty
        gameField[figure.getX()][figure.getY()].setIsEmpty(true);
    }
    /**
     * Method will check all possible moves for opp figures and look if contains my king
     * @param futureBoard
     * @param cellForMove 
     */
    private boolean isCheck(boolean controlActualCheck){

        List<Figure> figuresForCheckControl;
        
        if(controlActualCheck){
            figuresForCheckControl = this.createMovesForFigures(actualPlayerOnTurn, false);
        }
        else{
            figuresForCheckControl = addOpponentMoves();
        }     
        return anyFigureGiveCheck(figuresForCheckControl);
    }
    
    /**
     * Method find if in possible moves any figures is king - then is check
     * @param figuresCouldGiveCheck
     * @return 
     */
    public boolean anyFigureGiveCheck(List<Figure> figuresCouldGiveCheck){
 
        boolean[][] possibleMovesOfFigure;
        //  iterate all of opp figures
        for(Figure movingFigure : figuresCouldGiveCheck){
            //  initialize possible moves of opp figures
            possibleMovesOfFigure = movingFigure.getPossibleMoves();

            for(int row = 0; row < possibleMovesOfFigure[0].length; row++){
                for(int column = 0; column < possibleMovesOfFigure.length; column++){
                    if(possibleMovesOfFigure[column][row]
                        && isHereKing(movingFigure, column, row)){
                        return true;
                    }
                }
            }  
        }
        return false;
    }
    /**
     * Method control if on given cell is opponent king
     * @param movingFigure
     * @param column
     * @param row
     * @return 
     */
    public boolean isHereKing(Figure movingFigure, int column, int row){
        
        Figure figureForControl = gameBoard.getGameField()[column][row].getFigureOnTheField();
        
        return (figureForControl != null) && (!figureForControl.getPlayer().equals(movingFigure.getPlayer())) && (figureForControl instanceof King);
    }
    
    /**
     * Method add possible moves for all of opponent figures
     * @return opponent figures
     */
    private List<Figure> addOpponentMoves(){
        //  opponent figures
        List<Figure> opponentFigures;
        
        Player whitePlayer = gameManager.getWhitePlayer();
        
        Player blackPlayer = gameManager.getBlackPlayer();
        //  we control which player is on turn and after we call adding possible moves for opponent
        if(actualPlayerOnTurn.equals(blackPlayer)){
           opponentFigures = this.createMovesForFigures(whitePlayer, false);
        }
        else{
            opponentFigures = this.createMovesForFigures(blackPlayer, false);
        }
        return opponentFigures;
    }

    /**
     * Method replace capture figure to capture list
     * @param figureForCapture 
     */
    public void figureToCaptureList(Figure figureForCapture){
        
        Player ownerOfFigure = figureForCapture.getPlayer();
        Player whitePlayer = gameManager.getWhitePlayer();
        
        if(ownerOfFigure.equals(whitePlayer)){
            CapturedFigures panelWhiteFigures = gameManager.getCapturedWhite();
            panelWhiteFigures.getCapturedFigures().add(figureForCapture);
            sortListByPoints(panelWhiteFigures.getCapturedFigures());
        }
        else{
            CapturedFigures panelBlackFigures = gameManager.getCapturedBlack();
            panelBlackFigures.getCapturedFigures().add(figureForCapture);
            sortListByPoints(panelBlackFigures.getCapturedFigures());
        }
        removeNullsFromCollections();
    }
    /**
     * Method sort given list of figures by points for capturing them
     * @param figures 
     */
    private void sortListByPoints(List<Figure> figures){
        Collections.sort(figures, Comparator.comparing(Figure::getFigurePoints)
                                            .thenComparing(Figure::getNameOfFigure));
    }
    
    private void removeNullsFromCollections(){
        //  remove all null from the lists of figures
        gameBoard.getCaptureBlackFigures().removeAll(Collections.singleton(null));
        gameBoard.getCaptureWhiteFigures().removeAll(Collections.singleton(null));
    }
    /**
     * Method return true if actual player have any possible move with any figure
     * @return true/false if there is any possible move
     */
    public Boolean haveAnyPossibleMove(){
        
        List<Figure> listOfActualFigures = gameManager.getPlayerFigures(actualPlayerOnTurn);
    
        for(Figure actFigure : listOfActualFigures){
            boolean[][] possibleMovesActFigure = actFigure.getPossibleMoves();

            for(int i = 0; i < possibleMovesActFigure[0].length; i++){
                for(int j = 0; j < possibleMovesActFigure.length; j++){
                    if(possibleMovesActFigure[j][i]){
                        return true;
                    }
                }
            }
        }
        return false; 
    }
    /**
     * Method add given points to player
     * @param points 
     */
    public void addPlayerPoints(int points){
        actualPlayerOnTurn.addGamePoints(points);
    }
    
    public void tryMove(Cell selectedCell, Figure selectedFigure, Cell cellForMove) {
        
        boolean [][] possibleMoves = selectedFigure.getPossibleMoves();
        
        if(possibleMoves[cellForMove.getX()][cellForMove.getY()]){
            //  promote pawn
            if((selectedFigure instanceof Pawn) && (positionBeforePromote(selectedFigure))){
                String figureForPromotePawn = gameManager.getChessJFrame().showFiguresForPromote(selectedFigure);
                
                selectedFigure = createNewFigure(figureForPromotePawn, selectedFigure);
                selectedFigure.setPlayer(actualPlayerOnTurn);
            }
            
            if(selectedFigure != null){
                cellForMove.setSelectedCell(SelectedCell.SELECT);
                
                doMove(selectedCell, selectedFigure, cellForMove);
                setupAfterMoveOrCastling();
            }
        }
        else{
            //  Wrong move
            cellForMove.setSelectedCell(SelectedCell.WRONG_SELECT);
            selectedCell.setSelectedCell(SelectedCell.WRONG_SELECT);
        }
        //  reset choosen cell for new move
        //  cell for move will be reset automatically
        gameManager.setSelectedCell(null);
    }
    
    public void setupFinishedSteps(Figure figure, int oldPosition, int newPosition){
        
        Pawn pawnFigure = (Pawn) figure;
        int changeInPosition = Math.abs(oldPosition - newPosition);
        int numberOfFinishedSteps = pawnFigure.getPositionOnBoardY();
        pawnFigure.setPositionOnBoardY(numberOfFinishedSteps + changeInPosition);
    }
    
    public boolean positionBeforePromote(Figure figure){
        
        Pawn pawnFigure = (Pawn) figure;
        return  pawnFigure.getPositionOnBoardY() == (gameField.length - 2);
    }
    
    public Figure createNewFigure(String nameOfFigure, Figure figureForPromote){
        
        int positionX = figureForPromote.getX();
        int positionY = figureForPromote.getY();
        Color colorForFigure = figureForPromote.getColorOfFigure();
        
        switch(nameOfFigure){
            case "queen":   
                return new Queen(colorForFigure, nameOfFigure, positionX, positionY);
            case "rook":   
                return new Rook(colorForFigure, nameOfFigure, positionX, positionY);
            case "knight":   
                return new Knight(colorForFigure, nameOfFigure, positionX, positionY);
            case "bishop":   
                return new Bishop(colorForFigure, nameOfFigure, positionX, positionY);
            default:
                return null;
        }
    }
    
    /**
     * Method check if is possible do choosen castling between rook and king
     * @param selectedCell
     * @param rook
     * @param cellWithKing
     * @param king 
     */
    public void tryCastling(Cell selectedCell, Figure rook, Cell cellWithKing, Figure king){
        
        int numberOfColumnsForKingMove = 2;
        int[] columnsForCastling = getColumnsForCastle(rook, king, numberOfColumnsForKingMove);
        boolean isCastlingPossible = testCastling(rook, king, columnsForCastling);
        //  if we met the rules for castling we do that and return true
        if(isCastlingPossible){
            doCastling(rook, king, columnsForCastling);
            this.setFigureNotFirstMove(king);
            this.setFigureNotFirstMove(rook);
            
            setupAfterMoveOrCastling();
        }
        else{
            gameManager.getSelectedCell().setSelectedCell(SelectedCell.WRONG_SELECT);
            cellWithKing.setSelectedCell(SelectedCell.WRONG_SELECT);
            System.out.println("Wrong move");
        }
    }
    
    public boolean testCastling(Figure rook, Figure king, int[] columnsForCatling){
        
        int rowForControl = rook.getY();
        int rookColumn = rook.getX();
        System.out.println("king.isFirstMove() : " + king.isFirstMove());
        System.out.println("rook.isFirstMove() : " + rook.isFirstMove());
        //  for castling have to be first move king and rook, no check to king
        if(king.isFirstMove() && rook.isFirstMove() && !isCheck(false)){
            if(!this.isCastlingInCheck(rowForControl, columnsForCatling)){
                int[] columnsBetweenFigure = getColumnsForCastle(rook, king, getColumnsBetweenFigures(rook, king));
                // if rook is on 0 column is queen side(big castling)
                // if rook is on 7 column is other side(small castling)
                if(rookColumn == 0){
                    return testBigCastling(king, columnsBetweenFigure);
                }
                else if(rookColumn == 7){
                    return testSmallCastling(king, columnsBetweenFigure);
                } 
            }
        }
        return false;
    }
    /**
     * Method test which type of castling is possible and return array of castling columns
     * @param rook
     * @param king
     * @param numberOfColumns
     * @return columnsForCastling columns where we want to make castling
     */
    public int[] getColumnsForCastle(Figure rook, Figure king, int numberOfColumns){

        int typeOfCastling = rook.getX() - king.getX();
        int[] columnsForCastling = new int[numberOfColumns];
        //  shift in column
        int shift = 1;
        System.out.println("testTypeOfCastling: ");
        
        if(typeOfCastling < 0){
            for(int column = 0; column < columnsForCastling.length; column++){
                columnsForCastling[column] = king.getX() - shift;
                shift++;
            }
        }
        else{
            for(int column = 0; column < columnsForCastling.length; column++){
                System.out.println("column:" + column);
                columnsForCastling[column] = king.getX() + shift;
                shift++;
            }
        }
        return columnsForCastling;
    }
    
    public int getColumnsBetweenFigures(Figure firstFigure, Figure secondFigure){
        //  -1 because we wanna count just columns between figures
        return Math.abs(firstFigure.getX() - secondFigure.getX()) - 1;
    }
    
    private boolean isCastlingInCheck(int rowForControl, int[] columnsForCastling){

        List<Figure> figuresForControl = addOpponentMoves();
        //  possible moves of opponents figures
        boolean[][] possibleMovesOfFigure;
        //  iterate all of opp figures
        for(Figure actualFigure : figuresForControl){
            //  initialize possible moves of opp figures
            possibleMovesOfFigure = actualFigure.getPossibleMoves();
            //  for every figure we want to control all possible moves
            //  we want to check if king when move will not be in check on the way
            //  castling . king move in first two cells (that's why 0 and 1 column from king column)
            for(int column : columnsForCastling){
                if(possibleMovesOfFigure[column][rowForControl]){
                    return true;
                }
            }
        }
        return false;
    } 
    
    public boolean testSmallCastling(Figure king, int[] columnsForCheck){
        
        return testFigureBetweenCastling(king, columnsForCheck);
    }
    
    public boolean testBigCastling(Figure king, int[] columnsForCheck){
        
        return testFigureBetweenCastling(king, columnsForCheck);
    }
    
    public boolean testFigureBetweenCastling(Figure king, int[] columnsForCheck){
        System.out.println("testFigureBetweenCastling:");
        System.out.println("");
        for(int column : columnsForCheck){
            System.out.println("columnForCheck:"+ column);
            //  check if no figure is between the rook and king
            if(gameField[column][king.getY()].getFigureOnTheField() != null){
                return false;
            }
        }
        return true;
    }
    
    public void doMove(Cell selectedCell, Figure selectedFigure, Cell cellForMove){
        if(cellForMove.getFigureOnTheField() != null){
                Figure captureFigure = cellForMove.getFigureOnTheField();
                this.addPlayerPoints(captureFigure.getFigurePoints());
                figureToCaptureList(captureFigure);
            }
            //  set moving figure for new cell
            this.moveFigureToCell(cellForMove, selectedFigure);
            
            if(selectedFigure instanceof Pawn){
                setupFinishedSteps(selectedFigure, selectedFigure.getY(), cellForMove.getY());
            }
            if(selectedFigure instanceof Pawn || selectedFigure instanceof Rook || selectedFigure instanceof King){
                
                this.setFigureNotFirstMove(selectedFigure);
            }
            this.setupFigureCoordinates(selectedFigure, cellForMove.getX(), cellForMove.getY());
    }
    /**
     * Method setup figures (x,y) and
     * @param rook given rook for castling
     * @param king given king for castling
     * @param castlingColumns columns where we want to do castling
     */
    public void doCastling(Figure rook, Figure king, int[] castlingColumns) {
        System.out.println("CASTLING!!!");
        //  for doing castling we need to inicialize row of castling
        int rowForCastling = king.getY();
        //  we need to set up cells after castling figures to empty cell
        setupCellToEmpty(gameField[king.getX()][rowForCastling]);
        setupCellToEmpty(gameField[rook.getX()][rowForCastling]);
        //  second position in array castling columns is future position for replace king
        setupFigureCoordinates(king, castlingColumns[1], rowForCastling);
        
        setupFigureToCell(gameField[castlingColumns[1]][rowForCastling],king);
        
        this.setFigureNotFirstMove(king);
        
        //  first position in array castling columns is future position for replace rook
        setupFigureCoordinates(rook, castlingColumns[0], rowForCastling);
        
        setupFigureToCell(gameField[castlingColumns[0]][rowForCastling],rook);
        
        this.setFigureNotFirstMove(rook);     
    }
    /**
     * Method setup cell after given figure to empty cell/ no figure on
     * @param cell 
     */
    public void setupCellToEmpty(Cell cell){
        gameField[cell.getX()][cell.getY()].setFigureOnTheField(null);
        gameField[cell.getX()][cell.getY()].setIsEmpty(true);
    }
    
    /**
     * Method setup cell after given figure to empty cell/ no figure on
     * @param cell
     * @param figure 
     */
    public void setupFigureToCell(Cell cell, Figure figure){
        gameField[cell.getX()][cell.getY()].setFigureOnTheField(figure);
        gameField[cell.getX()][cell.getY()].setIsEmpty(false);
    }
    
    /**
     * Method setup cells which are in select state to no select state
     * That is for color painting red, yellow, and normal (no select)
     */
    public void setNoSelectCells(){
        Arrays.stream(gameBoard.getGameField()).forEach(x -> {
            for(Cell c : x){
                if(!c.getSelectedCell().equals(SelectedCell.NO_SELECT)){
                    c.setSelectedCell(SelectedCell.NO_SELECT);
                }
            }
        });
    }
    
    public void setupFigureCoordinates(Figure figure, int x, int y){
        figure.setX(x);
        figure.setY(y);
    }
    
    public void setFigureNotFirstMove(Figure figure){
        //  if it was first move, we setup first move to false for next steps(only one step)
        if(figure.isFirstMove()){
            figure.setFirstMove(false);
        }
    }
    
    private void setupAfterMoveOrCastling(){
        //  now we need to control if opponent give us check
        boolean isCheckFromOpponent = this.testActualCheck();
        //  change player on turn
        gameManager.changePlayers();
        
        actualPlayerOnTurn = gameManager.getPlayerOnTurn();
        //  we want to create all moves for actual player
        createMovesForFigures(actualPlayerOnTurn, true);

        boolean isPossibleMove = this.haveAnyPossibleMove();

        if(!isPossibleMove){
            if(isCheckFromOpponent){
                System.out.println("CHECKMATE");
                typeOfEndGame = "CHECKMATE";
            }
            else{
                System.out.println("---PAT---");
                typeOfEndGame = "PAT";
            }
            gameManager.setEndGame(true);
        }
        //  reset choosen cell for new move
        //  cell for move will be reset automatically
        gameManager.setSelectedCell(null);
        gameManager.setSelectedFigure(null);
        this.setNoSelectCells();
    }
}
