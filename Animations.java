import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Author: Riley Radle
 * Description: 
 *      This class creates a very basic animation 
 *      that appears on the main screen of the tic
 *      tac toe game.  An X and an O will circle around
 *      the outside of the GameWindow indefinitely.
 */
public class Animations implements ActionListener
{
    /**
     * Private inner class to represent a letter.
     * Each letter is a JLabel and has its own direction.
     */
    private class Letter extends JLabel
    {
        int direction;
    }

    /** Direction Constants */
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    /** Animation Components */
    private Letter X;
    private Letter O;
    private Timer timer;
    private GameWindow window;

    /**
     * This constructor sets up the components 
     * used within the animation.
     * 
     * @param window : The main GameWindow for the project
     */
    public Animations(GameWindow window)
    {
        // Initializes GameWindow and Timer objects
        this.window = window;
        timer = new Timer(10, this);

        // Initialize and add the two letters
        X = addLetter("X");
        X.direction = UP;
        O = addLetter("O"); 
        O.direction = DOWN;
        window.outer.add(O);
        window.outer.add(X);

        // Start the timer for the animations
        timer.start();
    }


    @Override
    /**
     * Handles the actions caused by the timer.
     */
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == timer)
        {
            // Move the letters
            move(X);
            move(O);
            // Bounce the letters
            bounce(X);
            bounce(O);
        }        
    }

    /**
     * This method moves a letter by one pixel based on its direction.
     * 
     * @param l : The letter that is being moved
     */
    private void move(Letter l)
    {
        // Check each direction and move the letter accordingly
        if (l.direction == UP)
            l.setLocation(l.getX(), l.getY() - 1);

        else if (l.direction == RIGHT)
            l.setLocation(l.getX() + 1, l.getY());

        else if (l.direction == DOWN)
            l.setLocation(l.getX(), l.getY() + 1);

        else if (l.direction == LEFT)
            l.setLocation(l.getX() - 1, l.getY());
    }

    /** 
     * This method "bounces" a letter in a different direction
     * when the letter reaches an edge of the window.
     * 
     * @param l : The letter that is being bounced 
     */
    private void bounce(Letter l)
    {
        // Check each edge for a collision
        if (l.getY() < 0 && l.direction == UP)
            l.direction = RIGHT;

        else if (l.getX() < 0 && l.direction == LEFT)
            l.direction = UP;
        
        else if (l.getY() > window.outer.getHeight() - l.getHeight() && l.direction == DOWN)
            l.direction = LEFT;

        else if (l.getX() > window.outer.getWidth() - l.getWidth() && l.direction == RIGHT)
            l.direction = DOWN;
    }

    /**
     * This method sets up and returns a letter object 
     * for use in the animation. 
     * 
     * @param letter : The String that will be displayed in the letter
     * @return : The letter object that has been set up
     */
    private Letter addLetter(String letter)
    {
        // Set up the letter component
        Letter l = new Letter();
        l.setText(letter);
        l.setSize(50, 50);
        l.setFont(new Font("Calibri", Font.PLAIN, l.getWidth()));
        l.setHorizontalAlignment(JLabel.CENTER);

        // Set the starting location of the letter
        if (letter == "O")
            l.setLocation(window.width - l.getWidth(), 0);
        else
            l.setLocation(0, window.height - l.getHeight());

        return l;
    }
}

