import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Author: Riley Radle
 * Descrition: 
 *      This class creates a new JFrame that allows the 
 *      the user to update their game settings. These 
 *      settings are saved in SaveData.
 */
public class SettingsWindow extends JFrame implements ActionListener
{
    /** Reference to the user's save data */
    private Data saveData;

    /** Components for SettingsWindow */
    private JButton save;
    private JButton cancel;
    private JRadioButton easy;
    private JRadioButton medium;
    private JRadioButton hard;
    private JRadioButton unbeatable;
    private JRadioButton playerX;
    private JRadioButton playerO;

    /**
     * This constructor sets up and adds all of the
     * components for the SettingsWindow. It also initializes
     * a reference to the user's saved data.
     * 
     * @param saveData : Reference to user's saved data
     */
    public SettingsWindow(Data saveData)
    {
        // Initialize saved data
        this.saveData = saveData;

        // Set up basic JFrame components
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
        playerO = addRadioButton("O", playerType, playerTypeContainer);

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

        // Display the settings already enabled in saved data
        showCurrentSettings();
    }

    @Override
    /**
     * Handles the actions caused by events in the SettingsWindow
     */
    public void actionPerformed(ActionEvent e) 
    {
        // The user clicked on the save button
        if (e.getSource() == save)
        {
            // Check for the player type 
            if (playerX.isSelected())
                saveData.setPlayerType(Data.PLAYER_X);
            else if (playerO.isSelected())
                saveData.setPlayerType(Data.PLAYER_O);

            // Check for the difficulty
            if (easy.isSelected())
                saveData.setDifficulty(Data.EASY);
            else if (medium.isSelected())
                saveData.setDifficulty(Data.MEDIUM);
            else if (hard.isSelected())
                saveData.setDifficulty(Data.HARD);
            else if (unbeatable.isSelected())
                saveData.setDifficulty(Data.UNBEATABLE);

            // Save the data to the SaveData file
            try 
            {
                DataManagement.saveData(saveData, "SaveData");
            } 
            catch (Exception exception) 
            {
                System.out.println("UNABLE TO SAVE DATA");
            }

            this.dispose();
        }
        // The user doesn't want to save their changes
        else if (e.getSource() == cancel)
            this.dispose();
    }

    /**
     * This method sets the radio buttons to display the current
     * settings that the user already has selected once the 
     * SettingsWindow is opened.  
     */
    private void showCurrentSettings()
    {
        // Display the current difficulty
        if (saveData.getDifficulty() == Data.EASY)
            easy.setSelected(true);
        else if (saveData.getDifficulty() == Data.MEDIUM)
            medium.setSelected(true);
        else if (saveData.getDifficulty() == Data.HARD)
            hard.setSelected(true);
        else if (saveData.getDifficulty() == Data.UNBEATABLE)
            unbeatable.setSelected(true);

        // Display the user's player type
        if (saveData.getPlayerType() == Data.PLAYER_X)
            playerX.setSelected(true);
        else if (saveData.getPlayerType() == Data.PLAYER_O)
            playerO.setSelected(true);
    }

    /**
     * This method sets up and adds new radio button.
     * This allows for less code in the constructor.
     * 
     * @param text : The String that will be displayed on the button
     * @param group : The group that the button belongs to 
     * @param container : The JPanel to add the button to
     * @return
     */
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