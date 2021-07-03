/*
 * Author: Riley Radle
 * Description: 
 *      This is the driver that directs the flow of the 
 *      tic tac toe game.  
 */
import javax.swing.*;
import java.awt.event.*;

public class Driver implements ActionListener
{
    private static final String SAVE_FILE = "SaveData";
    private GameWindow ttt;
    private AI ai;
    private int currentPlayer;
    private Data saveData;

    public static void main(String[] args) 
    {
        Driver d = new Driver();
        d.startTTT();
    }

    public void startTTT()
    {
        try 
        {
            saveData = (Data)DataManagement.loadData(SAVE_FILE);
        }
        catch (Exception e) 
        {
            // If there is no save data, create a new one
            System.out.println("UNABLE TO LOAD DATA");
            saveData = new Data();
        }

        currentPlayer = saveData.getPlayerType();
        ttt = new GameWindow(this, this.saveData);
        ttt.mainScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == ttt.singlePlayer)
            playTTT(true);

        else if (e.getSource() == ttt.twoPlayer)
            playTTT(false);

        else if (e.getSource() == ttt.settings) 
            updateSettings();

        else if (e.getSource() == ttt.stats)
            viewStats();
        else if (e.getSource() == ttt.modeSelect)
            modeSelect();
        else 
            playerTurn((Tile)e.getSource());
    }

    private void modeSelect()
    {
        ttt.dispose();
        ttt = new GameWindow(this, saveData);
        ttt.mainScreen();
    }
 
    private void playTTT(boolean isSinglePlayer)
    {
        ai = isSinglePlayer ? new AI(saveData) : null;
        ttt.dispose();
        ttt = new GameWindow(this, saveData);
        ttt.playScreen();
    }

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

    private boolean checkForWinOrTie()
    {
        int winner = ttt.board.gameWon();
        if (winner == 3 || winner == -3)
        {
            String winMessage = winner == 3 ? "X's won the game!" : "O's won the game!";

            // ===== Record the stats =====
           
            // Make sure they are in single player
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
                currentPlayer = winner == 3 ? TileType.O : TileType.X;
            
            JOptionPane.showMessageDialog(null, winMessage);
            return true;
        }

        if (ttt.board.gameTied())
        {
            // ===== Record the stats =====

            // Make sure they are in single player
            if (ai != null)
            {
                saveData.gamePlayed(false);
                saveData();
            }

            currentPlayer = saveData.getPlayerType();
            JOptionPane.showMessageDialog(null, "The game was a tie");
            return true;
        }

        return false;
    }

    private void viewStats() 
    {
        int gamesPlayed = saveData.getGamesPlayed();
        int gamesWon = saveData.getGamesWon();
        float percentage = (float)gamesWon / gamesPlayed;

        String stats = String.format("Games Played: %d\n" + "Games Won: %d\n" + 
                                     "Win Percentage: %.2f", gamesPlayed, gamesWon,
                                     percentage);

        // Just use a JOPtionPane for this...it will be much easier
        JOptionPane.showMessageDialog(null, stats, "Single Player Stats", JOptionPane.PLAIN_MESSAGE);
    }

    private void updateSettings() 
    {
        new SettingsWindow(saveData);
    }

    private void saveData()
    {
        try 
        {
            DataManagement.saveData(saveData, SAVE_FILE);
        } 
        catch (Exception e) 
        {
            System.out.println("UNABLE TO SAVE DATA");
        }
    }
}
