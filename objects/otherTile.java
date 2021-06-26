package objects;

import java.awt.Color;

import javax.swing.*;

public class otherTile extends JPanel
{
    private int state;
    private int x;
    private int y;

    public otherTile()
    {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public int getState() 
    {
        return state;
    }

    public int getRow() 
    {
        return x;
    }

    public int getCol() 
    {
        return y;
    }
    
}