import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Circling implements ActionListener
{
    private class Letter extends JLabel
    {
        int direction;
    }

    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    private Letter X;
    private Letter O;
    private Timer timer;
    private GameWindow window;

    public Circling(GameWindow window)
    {
        this.window = window;

        timer = new Timer(10, this);

        X = addLetter("X");
        X.direction = UP;
        O = addLetter("O"); 
        O.direction = DOWN;
        
        window.outer.add(O);
        window.outer.add(X);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == timer)
        {
            move(X);
            move(O);

            bounce(X);
            bounce(O);
        }        
    }

    private void move(Letter l)
    {
        if (l.direction == UP)
            l.setLocation(l.getX(), l.getY() - 1);

        else if (l.direction == RIGHT)
            l.setLocation(l.getX() + 1, l.getY());

        else if (l.direction == DOWN)
            l.setLocation(l.getX(), l.getY() + 1);

        else if (l.direction == LEFT)
            l.setLocation(l.getX() - 1, l.getY());
    }

    private void bounce(Letter l)
    {
        if (l.getY() < 0 && l.direction == UP)
            l.direction = RIGHT;

        else if (l.getX() < 0 && l.direction == LEFT)
            l.direction = UP;
        
        else if (l.getY() > window.outer.getHeight() - l.getHeight() && l.direction == DOWN)
            l.direction = LEFT;

        else if (l.getX() > window.outer.getWidth() - l.getWidth() && l.direction == RIGHT)
            l.direction = DOWN;
    }

    private Letter addLetter(String letter)
    {
        Letter l = new Letter();
        l.setText(letter);
        l.setSize(50, 50);
        l.setFont(new Font("Calibri", Font.PLAIN, l.getWidth()));
        l.setHorizontalAlignment(JLabel.CENTER);

        if (letter == "O")
            l.setLocation(window.width - l.getWidth(), 0);
        else
            l.setLocation(0, window.height - l.getHeight());

        return l;
    }
}

