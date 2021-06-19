package objects;

import javax.swing.JFrame;

public class GameWindow extends JFrame
{
    public GameWindow()
    {
        super("Tic Tac Toe");
        this.setLocation(50, 50);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}