/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.radimkolacny.chess.gamefield;

import cz.radimkolacny.chess.figures.Figure;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author radimkolacny
 */
public class CapturedFigures extends JPanel{
    //  side of figure picture
    private final static int SIDE = 25;
 
    private List<Figure> capturedFigures;
    //  position of paint figure on panel
    private int position = 0;

    public CapturedFigures() {
        
        super();
        capturedFigures = new java.util.ArrayList<>();
    }

    public List<Figure> getCapturedFigures() {
        return capturedFigures;
    }

    public void setCapturedFigures(List<Figure> capturedFigures) {
        this.capturedFigures = capturedFigures;
    }
    
    private void paintCapturedFigures(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        position = 0;
        
        for(Figure figure : capturedFigures){
            if(figure != null){
                g2d.drawImage(figure.getImageOfFigure(), position * (SIDE), 0, SIDE, SIDE,this);
                position++;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        paintCapturedFigures(g);
    }
}
