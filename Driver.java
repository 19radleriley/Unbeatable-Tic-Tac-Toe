
/*
 * Author: Riley Radle
 * Description: 
 *      This is the driver that directs the flow of the 
 *      tic tac toe game.  
 */
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

public class Driver implements ActionListener
{
    private GameWindow ttt;
    private AI ai;
    private int currentPlayer;

    public static void main(String[] args) 
    {
        Driver d = new Driver();
        d.startTTT();
    }

    public void startTTT()
    {
        // FIXME : Settings should be saved, not reset every time the app is run
        new Settings(0, TileType.X, Color.black, false, false);

        currentPlayer = Settings.playerType;
        ttt = new GameWindow(this);
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
        ttt = new GameWindow(this);
        ttt.mainScreen();
    }
 
    private void playTTT(boolean isSinglePlayer)
    {
        ai = isSinglePlayer ? new AI() : null;
        ttt.dispose();
        ttt = new GameWindow(this);
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
            boolean tileSet = ttt.board.setTile(tile, Settings.playerType);
            gameOver = checkForWinOrTie();

            // If the game isn't over and the user set a tile, the AI will move
            if (gameOver == false && tileSet)
            {
                ai.playTurn(ttt.board);
                ttt.chat.setText(ai.getSaying());
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

            // If there is no AI, then the loser should go first in the next game
            if (ai == null)
                currentPlayer = winner == 3 ? TileType.O : TileType.X;
            
            JOptionPane.showMessageDialog(null, winMessage);
            return true;
        }

        if (ttt.board.gameTied())
        {
            currentPlayer = Settings.playerType;
            JOptionPane.showMessageDialog(null, "The game was a tie");
            return true;
        }

        return false;
    }

    private void viewStats() 
    {
     
    }

    private void updateSettings() 
    {
        new SettingsWindow();
    }

}
