/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess;

import Figures.Bishop;
import Figures.Figure;
import Figures.Knight;
import Figures.King;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author radimkolacny
 */
public class Board extends JPanel{
    //  side of game field
    private final static int SIDE = 50;
    //  number of cells on sides
    private final static int CELLS_ON_SIDE = 8;
    //  game field
    private final Cell[][] gameField = new Cell[CELLS_ON_SIDE][CELLS_ON_SIDE];
    //  black figures
    private List<Figure> blackFigures = new java.util.ArrayList<>();
    //  white figures
    private List<Figure> whiteFigures = new java.util.ArrayList<>();
    
    //  taken black figures
    private List<Figure> captureBlackFigures = new java.util.ArrayList<>();
    //  white figures
    private List<Figure> captureWhiteFigures = new java.util.ArrayList<>();
    /**¨
     * ¨letters for columns on board
     */
    private final String[] lettersForColumn = {"h", "g", "f", "e", "d", "c", "b", "a"};
    /**
     *  number for rows on board
     */
    private final String[] numbersForRow = {"1", "2", "3", "4", "5", "6", "7", "8"};

    /**
     * Constructor will call method for creating figures, cells and set the positions for them
     */
    public Board() {
        super();
        createFigures();
        createCells();
        setFiguresForCell();
    }

    public static int getSIDE() {
        return SIDE;
    }

    public static int getCELLS_ON_SIDE() {
        return CELLS_ON_SIDE;
    }
    
    public Cell[][] getGameField() {
        return gameField;
    }

    public List<Figure> getBlackFigures() {
        return blackFigures;
    }

    public List<Figure> getWhiteFigures() {
        return whiteFigures;
    }

    public List<Figure> getCaptureBlackFigures() {
        return captureBlackFigures;
    }

    public void setCaptureBlackFigures(List<Figure> takenBlackFigures) {
        this.captureBlackFigures = takenBlackFigures;
    }

    public List<Figure> getCaptureWhiteFigures() {
        return captureWhiteFigures;
    }

    public void setCaptureWhiteFigures(List<Figure> takenWhiteFigures) {
        this.captureWhiteFigures = takenWhiteFigures;
    }

    public List<Figure> getFiguresFromBoard() {
        return readFiguresFromBoard();
    } 
    
    /**
     * Method read all figures from actual state of board
     * @return list of actual figures on board
     */
    private List<Figure> readFiguresFromBoard(){
        
        List<Figure> figuresFromBoard = new ArrayList<>();
        
        for (int j = 0; j < gameField[0].length; j++)
        {
            for (int i = 0; i < gameField.length; i++)
            {
                Figure actualFigure = gameField[i][j].getFigureOnTheField();
            
                if(actualFigure != null){
                    figuresFromBoard.add(actualFigure);
                }
            }
        }
        return figuresFromBoard;
    }
    /**
     * Method will create all figures and fill lists (black/white) with them
     */
    private void createFigures(){
        
        Figure blackBishopLeft = new Bishop(Color.BLACK, "bishop", 2, 0);
        blackFigures.add(blackBishopLeft);
        Figure blackBishopRight = new Bishop(Color.BLACK, "bishop", 5, 0);
        blackFigures.add(blackBishopRight);
        Figure blackHorseLeft = new Knight(Color.BLACK, "knight", 1, 0);
        blackFigures.add(blackHorseLeft);
        Figure blackHorseRight = new Knight(Color.BLACK, "knight", 6, 0);
        blackFigures.add(blackHorseRight);
        Figure blackTowerLeft = new Rook(Color.BLACK, "rook", 0, 0);
        blackFigures.add(blackTowerLeft);
        Figure blackTowerRight = new Rook(Color.BLACK, "rook", 7, 0);
        blackFigures.add(blackTowerRight);
        Figure blackQueen = new Queen(Color.BLACK, "queen", 3, 0);
        blackFigures.add(blackQueen);
        Figure blackKing = new King(Color.BLACK, "king", 4, 0);
        blackFigures.add(blackKing);
        
        Figure blackPawn1 = new Pawn(Color.BLACK, "pawn", 0, 1);
        Figure blackPawn2 = new Pawn(Color.BLACK, "pawn", 1, 1);
        Figure blackPawn3 = new Pawn(Color.BLACK, "pawn", 2, 1);
        Figure blackPawn4 = new Pawn(Color.BLACK, "pawn", 3, 1);
        Figure blackPawn5 = new Pawn(Color.BLACK, "pawn", 4, 1);
        Figure blackPawn6 = new Pawn(Color.BLACK, "pawn", 5, 1);
        Figure blackPawn7 = new Pawn(Color.BLACK, "pawn", 6, 1);
        Figure blackPawn8 = new Pawn(Color.BLACK, "pawn", 7, 1);
        blackFigures.add(blackPawn1);
        blackFigures.add(blackPawn2);
        blackFigures.add(blackPawn3);
        blackFigures.add(blackPawn4);
        blackFigures.add(blackPawn5);
        blackFigures.add(blackPawn6);
        blackFigures.add(blackPawn7);
        blackFigures.add(blackPawn8);

        Figure whiteBishopLeft = new Bishop(Color.WHITE, "bishop", 2, 7);
        whiteFigures.add(whiteBishopLeft);
        Figure whiteBishopRight = new Bishop(Color.WHITE, "bishop", 5, 7);
        whiteFigures.add(whiteBishopRight);
        Figure whiteHorseLeft = new Knight(Color.WHITE, "knight", 1, 7);
        whiteFigures.add(whiteHorseLeft);
        Figure whiteHorseRight = new Knight(Color.WHITE, "knight", 6, 7);
        whiteFigures.add(whiteHorseRight);
        Figure whiteTowerLeft = new Rook(Color.WHITE, "rook", 0, 7);
        whiteFigures.add(whiteTowerLeft);
        Figure whiteTowerRight = new Rook(Color.WHITE, "rook", 7, 7);
        whiteFigures.add(whiteTowerRight);
        Figure whiteQueenLeft = new Queen(Color.WHITE, "queen", 3, 7);
        whiteFigures.add(whiteQueenLeft);
        Figure whiteKingRight = new King(Color.WHITE, "king", 4, 7);
        whiteFigures.add(whiteKingRight);
        
        Figure whitePawn1 = new Pawn(Color.WHITE, "pawn", 0, 6);
        Figure whitePawn2 = new Pawn(Color.WHITE, "pawn", 1, 6);
        Figure whitePawn3 = new Pawn(Color.WHITE, "pawn", 2, 6);
        Figure whitePawn4 = new Pawn(Color.WHITE, "pawn", 3, 6);
        Figure whitePawn5 = new Pawn(Color.WHITE, "pawn", 4, 6);
        Figure whitePawn6 = new Pawn(Color.WHITE, "pawn", 5, 6);
        Figure whitePawn7 = new Pawn(Color.WHITE, "pawn", 6, 6);
        Figure whitePawn8 = new Pawn(Color.WHITE, "pawn", 7, 6);
        whiteFigures.add(whitePawn1);
        whiteFigures.add(whitePawn2);
        whiteFigures.add(whitePawn3);
        whiteFigures.add(whitePawn4);
        whiteFigures.add(whitePawn5);
        whiteFigures.add(whitePawn6);
        whiteFigures.add(whitePawn7);
        whiteFigures.add(whitePawn8);
    }

    private void createCells(){
        for (int j = 0; j < gameField[0].length; j++)
        {
            for (int i = 0; i < gameField.length; i++)
            {
                gameField[i][j] = new Cell(i, j);
            }
        }
    }
 
    private void setFiguresForCell(){
   
        setFiguresForColorList(blackFigures);
 
        setFiguresForColorList(whiteFigures);
    }
    /**
     * Method will set given figures for cells
     * @param figures 
     */
    private void setFiguresForColorList(List<Figure> figures){
        for(Figure figure : figures){
            gameField[figure.getX()][figure.getY()]
                 .setFigureOnTheField(figure);
            gameField[figure.getX()][figure.getY()]
                 .setIsEmpty(false);
        }
    }

    public void paintBoard(Graphics g)
    {
        //  we use this type of graphic for working with sharping of images
        Graphics2D g2d = (Graphics2D) g;
        //  sharping of given parts
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //  starting color for coloring cells
        //  we will switch color one by one for painting proper chess board
        boolean colorWhite = true;
        //  we want to iterate over gamefield(all cells) and paint colored rectangles by them
        for (int j = 0; j < gameField[0].length; j++)
        {
            for (int i = 0; i < gameField.length; i++)
            {
                //  creating rectangles for painting on panel
                if(colorWhite){
                    //  set color for rectangle
                    g2d.setColor(Color.WHITE);
                    if(i < (gameField.length - 1)){
                        colorWhite = false;
                    }
                }
                else{
                     g2d.setColor(Color.GREEN); 
                     if(i < (gameField.length - 1)){
                        colorWhite = true;
                    }
                }
                
                if(gameField[i][j].getSelectedCell().equals(SelectedCell.SELECT)){
                    g2d.setColor(Color.YELLOW);
                }
                else if(gameField[i][j].getSelectedCell().equals(SelectedCell.WRONG_SELECT)){
                    g2d.setColor(Color.RED);
                }
                //  paint rectangle with given color
                g2d.fillRect(i * (SIDE), j * (SIDE), SIDE, SIDE);
                //  call method will paint letters and numbers on board
                paintNumbersLetters(g2d, j, i);
            }
        }
    }
    /**
     * Method paint numbers and letters for recognition of position on board
     * @param g2d
     * @param row
     * @param column 
     */
    private void paintNumbersLetters(Graphics2D g2d, int row, int column){
        int sizeOfFont = 10;
        g2d.setFont(new Font("Segoe Script", Font.BOLD, sizeOfFont));
        g2d.setPaint(Color.GRAY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //  letters we want to paint just on first column
        if(column == 0){
            g2d.drawString(lettersForColumn[row], column * (SIDE), (row * (SIDE)) + sizeOfFont);
        }
        //  numbers we want to paint just on last row
        if(row == 7){
            g2d.drawString(numbersForRow[column], (column * (SIDE)), (row * (SIDE)) + SIDE);
        }
    }
    /**
     * Method will paint start positions of all figures
     * @param g 
     */
    private void paintFigures(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        for (int j = 0; j < gameField[0].length; j++)
        {
            for (int i = 0; i < gameField.length; i++)
            {
                //  if gameField on given position is not empty than draw that figure
                if(!gameField[i][j].isEmpty()){
                    //  draw image of given figure
                    g2d.drawImage(gameField[i][j].getFigureOnTheField().getImageOfFigure(), i * (SIDE), j * (SIDE), SIDE, SIDE,this);
                }
            }
        }
    }
    /**
     * Method will paint board and all figures on it
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //  paint board for chess
        this.paintBoard(g);
        //  paint all figures which are not captured
        this.paintFigures(g);
    }
}
