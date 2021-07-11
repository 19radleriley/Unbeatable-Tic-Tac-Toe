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
    private int currentPlayer;
    private Data saveData;

    public static void main(String[] args) 
    {
        // Start the game
        Driver d = new Driver();
        d.startTTT();
    }

    /**
     * This method starts the Tic Tac Toe program by 
     * reading the save data, and then creating a
     * new GameWindow().
     */
    public void startTTT()
    {
        // Attempt to read the game data.
        try 
        {
            saveData = (Data)DataManagement.loadData(SAVE_FILE);
        }
        // If there is no game data, create one with the defaults
        catch (Exception e) 
        {
            // Exception occurs when there is no saved data.
            // Create a new one.
            saveData = new Data();
        }

        // Initialize the first player of the game
        // and create a new GameWindow on its main screen.
        currentPlayer = saveData.getPlayerType();
        ttt = new GameWindow(this, this.saveData);
        ttt.mainScreen();
    }

    @Override
    /**
     * Handles the actions caused by events in the GameWindow
     */
    public void actionPerformed(ActionEvent e) 
    {
        // Start up a new TTT board in single player mode.
        if (e.getSource() == ttt.singlePlayer)
            playTTT(true);

        // Start a new TTT board in two player mode.
        else if (e.getSource() == ttt.twoPlayer)
            playTTT(false);

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
            playerTurn((Tile)e.getSource());
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
    private void playTTT(boolean isSinglePlayer)
    {
        // If single player, there will be an AI object
        ai = isSinglePlayer ? new AI(saveData) : null;
        ttt.dispose();
        ttt = new GameWindow(this, saveData);
        ttt.playScreen();
    }

    /**
     * This method plays out one turn of Tic Tac Toe.
     * It handles the logic for both single and two player.
     * 
     * @param tile : The Tile that was clicked
     */
    private void playerTurn(Tile tile)
    {
        boolean gameOver = false;

        // Two Player 
        if (ai == null)
        {
            boolean tileSet = ttt.board.setTile(tile, currentPlayer);
            gameOver = checkForWinOrTie();

            // Tile was set and game is not over, switch to the other player 
            if (tileSet && gameOver == false)
                currentPlayer *= -1;
        }
        // Single Player 
        else
        {
            // Set the tile and then check for a win
            boolean tileSet = ttt.board.setTile(tile, saveData.getPlayerType());
            gameOver = checkForWinOrTie();

            // If the game isn't over and the user set a tile, the AI will move
            if (gameOver == false && tileSet)
            {
                ai.playTurn(ttt.board);
                ttt.chat.setText("\"" + ai.getSaying() + "\" -AI");
                gameOver = checkForWinOrTie();
            }
        }


        // If the game is over, restart the game
        if (gameOver == true)
            ttt.newGame();
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
