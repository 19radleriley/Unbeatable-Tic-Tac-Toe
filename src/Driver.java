package src;
import javax.swing.*;
import java.awt.event.*;

/*
 * Author: Riley Radle
 * Description: 
 *      This is the driver that directs the flow of the 
 *      tic tac toe game.  
 */
public class Driver implements ActionListener
{
    /** Constant String for the save file */
    private static final String SAVE_FILE = "SaveData";

    /** Other needed variables */
    private GameWindow ttt;
    private AI ai;
    private Data saveData;
    private int currentPlayer;

    public static void main(String[] args) 
    {
        // Start the game
        Driver d = new Driver();
        d.startProgram();
    }

    /**
     * This method starts the Tic Tac Toe program by 
     * reading the save data, and then creating a
     * new GameWindow().
     */
    public void startProgram()
    {
        // Attempt to read the game data.
        try 
        {
            saveData = (Data)DataManagement.loadData(SAVE_FILE);
        }
        // If there is no game data, create one with the defaults
        catch (Exception e) 
        {
            // If there is no save data, create a new one
            saveData = new Data();
        }

        // Initialize the first player of the game
        // and create a new GameWindow on its main screen.
        currentPlayer = saveData.getPlayerType();
        ttt = new GameWindow(this, this.saveData);
        ttt.mainScreen();
    }

    /**
     * This method sets up a new GameWindow when 
     * a user wants to go back to the main screen
     * and select a different mode.
     */
    private void modeSelect()
    {
        ttt.dispose();
        ttt = new GameWindow(this, saveData);
        ttt.mainScreen();
    }

    /**
     * This method sets up a new window where the 
     * user can play TTT either in single or two player mode.
     * 
     * @param isSinglePlayer : Boolean representing single or two player 
     */
    private void setupMode(boolean isSinglePlayer)
    {
        // If single player, there will be an AI object
        ai = isSinglePlayer ? new AI(saveData) : null;
        ttt.dispose();
        ttt = new GameWindow(this, saveData);
        ttt.playScreen();

        // If the user wants the AI to play first, initialize the variable
        if (saveData.getMovesFirst() == false)
            ai.playTurn(ttt);
    }

    @Override
    /**
     * Handles the actions caused by events in the GameWindow
     */
    public void actionPerformed(ActionEvent e) 
    {
        // Start up a new TTT board in single player mode.
        if (e.getSource() == ttt.singlePlayer)
        {
            setupMode(true);
            currentPlayer = saveData.getPlayerType(); 
        }

        // Start a new TTT board in two player mode.
        else if (e.getSource() == ttt.twoPlayer)
        {
            setupMode(false);
            currentPlayer = saveData.getPlayerType(); 
        }

        // Open up a settings window.
        else if (e.getSource() == ttt.settings) 
            updateSettings();
        
        // Open up a stats window.
        else if (e.getSource() == ttt.stats)
            viewStats();
        
        // Take the user back to the main screen
        else if (e.getSource() == ttt.modeSelect)
            modeSelect();

        // Else, the event must be from one of the Tiles
        // Play the next turn
        else 
            playTurn((Tile)e.getSource());
    }

    /**
     * This method directs the flow of  one turn of Tic Tac Toe.
     * It handles the logic for both single and two player.
     * 
     * @param tile : The Tile that was clicked
     */
    private void playTurn(Tile tile)
    {
        boolean gameOver = false;

        // Move and then check for win or tie
        boolean successfulMove = userMove(tile, currentPlayer == saveData.getPlayerType());
        if (successfulMove)
            gameOver = checkForWinOrTie();

        // If the AI exists we are in single player mode. Check for successful move first.
        if (successfulMove && ai != null && !gameOver)
        {
            ai.playTurn(ttt);
            gameOver = checkForWinOrTie();
        }

        // If the game is over, restart the game
        if (gameOver == true)
        {
            ttt.newGame();

            // If the ai is supposed to play first, play a turn
            if (ai != null && saveData.getMovesFirst() == false)
                ai.playTurn(ttt);
        }
    }

    /**
     * This method handles the logic for a user's move.
     * It is primarily used for two player mode.
     * @param tile
     * @param isAI
     * @return
     */
    private boolean userMove(Tile tile, boolean isAI)
    {
        // Set the tile
        boolean successfulMove = ttt.board.setTile(tile, currentPlayer);
        
        // Switch players
        if (successfulMove && ai == null)
            currentPlayer *= -1;
        
        return successfulMove;
    }

    /**
     * This method checks to see if the current
     * tic tac toe game is in a terminal state.
     * 
     * @return : True if the a player has won or game has tied
     *           False if there is no winner and no tie
     */
    private boolean checkForWinOrTie()
    {
        int winner = ttt.board.gameWon();

        // There was a winner.
        if (winner == 3 || winner == -3)
        {
            String winMessage = winner == 3 ? "X's won the game!" : "O's won the game!";
           
            // Make sure they are in single player before ~~Recording Stats~~
            if (ai != null)
            {
                // User won the game
                if (winner == (3 * saveData.getPlayerType()))
                    saveData.gamePlayed(true);
                else
                    saveData.gamePlayed(false);
                saveData();
            }

            // If there is no AI, then the loser should go first in the next game
            if (ai == null)
                currentPlayer = winner == 3 ? Data.PLAYER_O : Data.PLAYER_X;
            
            // Display's who won the game through a pop up window
            JOptionPane.showMessageDialog(null, winMessage);
            return true;
        }

        if (ttt.board.gameTied())
        {
            // Make sure they are in single player before ~~Recording Stats~~
            if (ai != null)
            {
                saveData.gamePlayed(false);
                saveData();
            }

            // Set the player of the next game to be user's player type.
            // Output to the user that the game was a a tie. 
            currentPlayer = saveData.getPlayerType();
            JOptionPane.showMessageDialog(null, "The game was a tie");
            return true;
        }

        return false;
    }

    /**
     * This method displays the user's stats 
     * via a pop up window when the stats button is clicked.
     */
    private void viewStats() 
    {
        // Get the stats 
        int gamesPlayed = saveData.getGamesPlayed();
        int gamesWon = saveData.getGamesWon();
        float percentage = (float)gamesWon / gamesPlayed;

        // Format a string for output
        String stats = String.format("Games Played: %d\n" + "Games Won: %d\n" + 
                                     "Win Percentage: %.2f", gamesPlayed, gamesWon,
                                     percentage);
       
        // Output the stats via a pop up window for simplicity.
        JOptionPane.showMessageDialog(null, stats, "Single Player Stats", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * This method invokes a new settings window
     * which will allow the user to update their settings.
     */
    private void updateSettings() 
    {
        new SettingsWindow(saveData);
    }

    /**
     * This method saves all of the data to the save file.
     * The String for the save file is saved in a constant at the top of the file.
     */
    private void saveData()
    {
        try 
        {
            DataManagement.saveData(saveData, SAVE_FILE);
        } 
        catch (Exception e) 
        {
        }
    }
}
