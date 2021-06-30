/*
 * Author: Riley Radle
 * Description: 
 *      Contains a Tile class to represent each tile 
 *      on the tic tac toe board.  Each tile has three
 *      attributes, an X location, a Y location, and 
 *      a tile state (X O or N).
 */


import javax.swing.*;
import java.awt.*;


public class Tile extends JButton
{
    private int state, row, col;

    public Tile(int row, int col, int state) 
    {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    public int getState() 
    {
        return state;
    }

    public void setState(int state) 
    {
        this.state = state;
        setGraphics(state);
    }

    public int getRow() 
    {
        return row;
    }

    public int getCol() 
    {
        return col;
    }

    public void setLocation(int row, int col) 
    {
        this.row = row;
        this.col = col;
    }

    private void setGraphics(int state)
    {
        if (state != TileType.N)
        {
            String text = state == TileType.X ? "X" : "O";
            this.setText(text);
            this.setFont(new Font("Calibri", Font.PLAIN, 120));
        }
        else
        {
            this.setText("");
        }
    }
}
