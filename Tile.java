import javax.swing.*;
import java.awt.*;

/*
 * Author: Riley Radle
 * Description: 
 *      This class represents each tile 
 *      on the tic tac toe board.  Each tile has three
 *      attributes.  Each tile is a JButton.  Their event
 *      is handled in Driver.java.
 */
public class Tile extends JButton
{
    /** The state of the Tile (X, O, or N) */
    private int state;

    /**
     * Constructor sets up the state of this Tile
     *
     * @param state : X, O, or N
     */
    public Tile(int state) 
    {
        this.state = state;
    }

    /**
     * @return : The state of this Tile
     */
    public int getState() 
    {
        return state;
    }

    /**
     * Set the state of this Tile
     * 
     * @param state : X, O, or N
     */
    public void setState(int state) 
    {
        this.state = state;
        setGraphics(state);
    }

    /**
     * Set the graphics of this Tile based on its state
     * 
     * @param state : X, O, or N
     */
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
