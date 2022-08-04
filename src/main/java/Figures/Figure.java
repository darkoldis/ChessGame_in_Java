/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Figures;

import Moves.Move;
import cz.radimkolacny.chess.Board;
import cz.radimkolacny.chess.Player;
import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author radimkolacny
 */
public abstract class Figure implements Serializable{
    //  points when we capture this figure
    protected int figurePoints;
    //  start position
    private int x;
    private int y;
    //  color of this figure
    private Color colorOfFigure;
    //  image of this figure
    private transient Image imageOfFigure;
    //  owner of figure
    private Player player;
    //  path of directories with images
    private String pathOfImage = "src/resources/";
    //  color of image for dealing with black/white
    private String colorOfImage = "";
    //  name of figure
    private String nameOfFigure = "";
    //  actual move we deal with
    public Move moveOfFigure = null;
    
    private boolean[][] possibleMoves = null;
    
    private boolean firstMove;

    public Figure(Color colorOfFigure, String nameOfFigure, int x, int y) {
        //  color of figure
        this.colorOfFigure = colorOfFigure;
        //  name of figure
        this.nameOfFigure = nameOfFigure;
        //  set starting position of figure
        this.x = x;
        this.y = y;
        //  setting color of image by colorOfFigure
        setColorOfImage(colorOfFigure);
        
        setImageOfFigure();
        
        initPossibleMoves();
    }
    
    public final void initPossibleMoves(){
        possibleMoves = new boolean[Board.getCELLS_ON_SIDE()][Board.getCELLS_ON_SIDE()];
    }

    public int getFigurePoints() {
        return figurePoints;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isFirstMove() {
        return firstMove;
    }

    public final void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public Color getColorOfFigure() {
        return colorOfFigure;
    }

    public void setColorOfFigure(Color colorOfFigure) {
        this.colorOfFigure = colorOfFigure;
    }

    public Image getImageOfFigure() {
        return imageOfFigure;
    }

    public final void setImageOfFigure() {
        ImageIcon imageIcon;
        imageIcon = new ImageIcon(pathOfImage + colorOfImage + this.nameOfFigure + ".png");
        imageOfFigure = imageIcon.getImage();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPathOfImage() {
        return pathOfImage;
    }

    public void setPathOfImage(String pathOfImage) {
        this.pathOfImage = pathOfImage;
    }

    public String getColorOfImage() {
        return colorOfImage;
    }

    public final void setColorOfImage(Color figureColor) {
        if(figureColor.equals(Color.BLACK)) {
            colorOfImage = "black_";
        }
        else{
            colorOfImage = "white_";
        }
    }
    
    @Override
    public String toString(){
        return colorOfImage + nameOfFigure;
    }

    public Move getMoveOfFigure() {
        return moveOfFigure;
    }

    public void setMoveOfFigure(Move moveOfFigure) {
        this.moveOfFigure = moveOfFigure;
    }

    public boolean[][] getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(boolean[][] possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public String getNameOfFigure() {
        return nameOfFigure;
    }

    public void setNameOfFigure(String nameOfFigure) {
        this.nameOfFigure = nameOfFigure;
    }
    
    
}