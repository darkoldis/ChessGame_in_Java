/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.gamefield;

import cz.radimkolacny.chess.figures.Figure;
/**
 *
 * @author radimkolacny
 */
public class Cell{

    private int x;
    private int y;

    private boolean isEmpty;
    private Figure figureOnTheField;
    private SelectedCell selectedCell;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        figureOnTheField = null;
        isEmpty = true;
        selectedCell = SelectedCell.NO_SELECT;
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Figure getFigureOnTheField() {
        return figureOnTheField;
    }

    public void setFigureOnTheField(Figure figureOnTheField) {
        this.figureOnTheField = figureOnTheField;
    }

    public SelectedCell getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(SelectedCell selectedCell) {
        this.selectedCell = selectedCell;
    }
    
    @Override
    public String toString() {
        return "Cell{" + "x=" + x + ", y=" + y + ", isEmpty=" + isEmpty + ", figureOnTheField=" + figureOnTheField + '}';
    }
}
