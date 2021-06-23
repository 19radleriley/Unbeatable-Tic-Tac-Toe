package objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener
{
    public static void main(String[] args) {
        new GameWindow(false);        
    }

    private int width = 500;
    private int height = 700;

    private JButton singlePlayer;
    private JButton twoPlayer;

    public GameWindow(boolean modeSelected)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // Make the window always appear in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)screenSize.getWidth() / 2 - width / 2, (int)screenSize.getHeight() / 2 - height / 2);
        this.setSize(width, height);

        if (modeSelected == false)
        {
            homeScreen();
        }
        else
        {
            tttScreen();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == singlePlayer) 
        {
            // Run the code that starts an AI controlled game
            // JOptionPane.showMessageDialog(null, "You have seleced to play the AI", "SinglePlayer", JOptionPane.PLAIN_MESSAGE);
            this.dispose();
            new GameWindow(true);
        }
        else if (e.getSource() == twoPlayer)
        {
            // Run the code that starts a 2 player game 
            // JOptionPane.showMessageDialog(null, "You have seleced to play another player", "TwoPlayer", JOptionPane.PLAIN_MESSAGE);
            this.dispose();
            new GameWindow(true);
        }
    }

    private void homeScreen()
    {
        this.setTitle("TIC TAC TOE");

        // Main container for everything else to be inside of 
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        
        // Title text
        JLabel title = new JLabel();
        title.setText("TIC TAC TOE");
        title.setFont(new Font("Consolas", Font.ITALIC, 75));

        // Buttons to select the game mode
        singlePlayer = new JButton("Single Player");
        singlePlayer.setFont(new Font("Consolas", Font.PLAIN, 50));
        singlePlayer.setFocusable(false);
        singlePlayer.addActionListener(this);

        twoPlayer = new JButton("Two Player");
        twoPlayer.setFont(new Font("Consolas", Font.PLAIN, 50));
        twoPlayer.setFocusable(false);
        twoPlayer.addActionListener(this);

        // Panel for elements below the button 
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.setSize(500, 500);

        JLabel footNote = new JLabel("  Author: Riley Radle");
        footNote.setFont(new Font("Calibri", Font.ITALIC, 12));
        footNote.setPreferredSize(new Dimension(width, height - (twoPlayer.getY() + twoPlayer.getHeight())));

        bottom.add(footNote, BorderLayout.SOUTH);

        // Add the elements to the container
        container.add(title);
        container.add(singlePlayer);
        container.add(twoPlayer);
        container.add(bottom);

        // Add the container to the frame and then set the frame visible
        this.add(container);
        this.setVisible(true);
    }

    private void tttScreen()
    {
        this.setTitle("Game Screen");
        this.setLayout(new FlowLayout());
        JPanel boardContainer = new JPanel();
        boardContainer.setPreferredSize(new Dimension(width, (height / 4) * 3));
        boardContainer.setBackground(Color.pink);

        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(width, height - (boardContainer.getY() + boardContainer.getHeight())));

        // Will want to create a board (using the board class) and add it to the screen


        // Underneath the board I think I will have an AI text area where the AI "Talks" to the user
        // Need to figure out what I want to do with the two player mode 

        this.add(boardContainer);
        this.setVisible(true);
    }
}



/*
    Potentially switch to using a grid layout 
 */