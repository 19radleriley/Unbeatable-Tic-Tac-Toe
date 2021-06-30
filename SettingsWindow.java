import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsWindow extends JFrame implements ActionListener
{
    private JButton save;
    private JButton cancel;

    private JRadioButton easy;
    private JRadioButton medium;
    private JRadioButton hard;
    private JRadioButton unbeatable;

    private JRadioButton playerX;
    private JRadioButton playerY;

    public SettingsWindow()
    {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridLayout(3, 1));
        this.setSize(200, 300);
        this.setResizable(false);
                
        // Make the window always appear in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)screenSize.getWidth() / 2 - this.getWidth() / 2,
            (int)screenSize.getHeight() / 2 - this.getHeight() / 2);

        // Create a group and a container for the difficulties
        ButtonGroup difficulty = new ButtonGroup();
        JPanel difficultyContainer = new JPanel();
        difficultyContainer.setLayout(new GridLayout(5, 1));
        difficultyContainer.add(new JLabel("Difficulty"));


        // Add buttons to select the difficulty
        easy = addRadioButton("Easy", difficulty, difficultyContainer);
        medium = addRadioButton("Medium", difficulty, difficultyContainer);;
        hard = addRadioButton("Hard", difficulty, difficultyContainer);;
        unbeatable = addRadioButton("Unbeatable", difficulty, difficultyContainer);;

        // Add buttons to select the player type (X or O)
        ButtonGroup playerType = new ButtonGroup();
        JPanel playerTypeContainer = new JPanel();
        playerTypeContainer.setLayout(new GridLayout(4, 1));
        playerTypeContainer.add(new JLabel("Player Type"));

        // Create radio buttons for the player types
        playerX = addRadioButton("X", playerType, playerTypeContainer);
        playerY = addRadioButton("O", playerType, playerTypeContainer);

        // Add a buttons to save or cancel and close the window
        JPanel closeContainer = new JPanel();
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        closeContainer.add(cancel);
        
        save = new JButton("Save");
        save.addActionListener(this); 
        closeContainer.add(save);

        this.add(playerTypeContainer);
        this.add(difficultyContainer);
        this.add(closeContainer);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == save)
        {
            // Check for the player type 
            if (playerX.isSelected())
                Settings.playerType = TileType.X;
            else if (playerY.isSelected())
                Settings.playerType = TileType.O;

            // Check for the difficulty
            else if (easy.isSelected())
                Settings.difficulty = Settings.EASY;
            else if (medium.isSelected())
                Settings.difficulty = Settings.MEDIUM;
            else if (hard.isSelected())
                Settings.difficulty = Settings.HARD;
            else if (unbeatable.isSelected())
                Settings.difficulty = Settings.UNBEATABLE;

            this.dispose();
        }
        else if (e.getSource() == cancel)
            this.dispose();
    }

    private JRadioButton addRadioButton(String text, ButtonGroup group, JPanel container)
    {
        JRadioButton button = new JRadioButton(text);
        button.setFont(new Font("Calibri", Font.PLAIN, 12));
        group.add(button);
        container.add(button);
        button.addActionListener(this);

        return button;
    }

}