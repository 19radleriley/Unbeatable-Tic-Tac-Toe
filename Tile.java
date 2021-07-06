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
    private int state;

    public Tile(int row, int col, int state) 
    {
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

    private void setGraphics(int state)
    {
        if (state != Data.PLAYER_N)
        {
            String text = state == Data.PLAYER_X ? "X" : "O";
            this.setText(text);
            this.setFont(new Font("Calibri", Font.PLAIN, 120));
        }
        else
        {
            this.setText("");
        }
    }
}
