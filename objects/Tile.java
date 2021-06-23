/*
 * Author: Riley Radle
 * Description: 
 *      Contains a Tile class to represent each tile 
 *      on the tic tac toe board.  Each tile has three
 *      attributes, an X location, a Y location, and 
 *      a tile state (X O or N).
 */

package objects;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tile extends JPanel implements MouseListener {
    private int state;
    private int x;
    private int y;

    // Constructor
    public Tile() 
    {
        state = TileState.N;
    }

    public Tile(int x, int y, int state) 
    {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getState() 
    {
        return state;
    }

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }

    public void setState(int state) 
    {
        this.state = state;
    }

    public void setLocation(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        // Implement the logic to click on the tile
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        // Implement the logic to click on the tile
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        // Will likely be unused
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        // Will likely be unused
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        // Will likely be unused
    }
}
