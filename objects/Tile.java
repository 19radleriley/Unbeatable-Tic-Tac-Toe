/*
 * Author: Riley Radle
 * Description: 
 *      Contains a Tile class to represent each tile 
 *      on the tic tac toe board.  Each tile has three
 *      attributes, an X location, a Y location, and 
 *      a tile state (X O or N).
 */

package objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Tile extends JPanel implements MouseListener
{
    private int state;
    private int row;
    private int col;

    // Constructor
    public Tile() 
    {
        super();
        state = TileState.N;
        setGraphics(state);
    }

    public Tile(int row, int col, int state) 
    {
        super();
        this.row = row;
        this.col = col;
        this.state = state;
        this.addMouseListener(this);
        setGraphics(state);
    }

    public void setGraphics(int state)
    {
        // First time the method is called, set up the graphics of the tile
        if (state == TileState.N)
        {
            this.setLayout(new BorderLayout());
            this.setBackground(Color.lightGray);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createLineBorder(Color.black));
        }

        // Subsequent times the method is called, set the icon of the tile to be X or O
        else 
        {
            JLabel graphic = state == TileState.X ? new JLabel("X") : new JLabel("O");
            graphic.setFont(new Font("Calibri", Font.PLAIN, 120));
            graphic.setHorizontalAlignment(JLabel.CENTER);
            graphic.setVerticalAlignment(JLabel.CENTER);
            this.add(graphic);
        }
    }

    public int getState() 
    {
        return state;
    }

    public int getRow() 
    {
        return row;
    }

    public int getCol() 
    {
        return col;
    }

    public void setState(int state) 
    {
        this.state = state;
    }

    public void setLocation(int row, int col) 
    {
        this.row = row;
        this.col = col;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile clicked = (Tile)e.getSource();

        System.out.printf("This was tile: (%d, %d)", clicked.getRow(), clicked.getCol());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
