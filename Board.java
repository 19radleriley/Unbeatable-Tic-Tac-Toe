
import javax.swing.*;
import java.awt.*;

/*
 * Author: Riley Radle
 * Description: 
 *      Represents a game board as an arrangement of tiles.  
 *      The tiles are stored in an array (because original 
 *      implementation was in C) but can be thought of as a 3 * 3 grid.
 */
public class Board extends JPanel 
{
    /** Tile array, board width, and user's data */
    private Tile[] board;
    private int width;
    private Data saveData;

    /**
     * Constructor sets up the array of tiles
     * and also basic JSwing elements.
     * 
     * @param driver : Reference to the driver (which is an ActionListener)
     * @param saveData : Reference to user's data
     * @param width : Width of the board
     */
    public Board(Driver driver, Data saveData, int width) 
    {
        // Store in a 1D array for more cache hits
        board = new Tile[width * width];
        this.width = width;
        this.saveData = saveData;

        // Set the layout of the board to be a grid
        this.setLayout(new GridLayout(width, width));

        // Create all of the tiles for the board
        for (int row = 0; row < width; row++) 
        {
            for (int col = 0; col < width; col++) 
            {
                Tile tile = new Tile(Data.PLAYER_N);
                tile.addActionListener(driver);
                tile.setFocusable(false);

                // Add the tile to the board
                this.add(tile);
                board[row * width + col] = tile;
            }
        }
    }

    /**
     * Sets a specific tile to a specific
     * state -- X, O, or N -- 
     * 
     * @param tile : Reference to the Tile that is being updated
     * @param state : The state the Tile is being changed to
     * @return : False if a tile is already set to X or O 
     */
    public boolean setTile(Tile tile, int state) 
    {
        // Before assigning, check to make sure the tile is un-occupied.
        if (tile != null && tile.getState() == Data.PLAYER_N) 
        {
            // Set the tile state and return true.
            tile.setState(state);
            return true;
        }
        // Tile was not set; return false
        return false;
    }

    /**
     * Get the tile at a specific row and column
     * 
     * @param row : Row of the tile
     * @param col : Column of the tile
     * @return : The corresponding Tile
     */
    public Tile getTile(int row, int col) 
    {
        return board[row * width + col];
    }

    /**
     * This method determines if the 
     * Tic Tac Toe game is a tie.
     * 
     * @return : True if tied, else false
     */
    public boolean gameTied() 
    {
        for (int row = 0; row < width; row++) 
        {
            for (int col = 0; col < width; col++) 
            {
                if (board[row * width + col].getState() == Data.PLAYER_N)
                    return false;
            }
        }

        // If all of the tiles are occupied (and presumably no winner), then tie.
        return true;
    }

    /**
     * This method determines if a player has won the game,
     * and if so, which player has won.
     * 
     * @return : An integer describing who won the game
     *           0 : No winner
     *           3 : X's won
     *          -3 : O's won
     */
    public int gameWon() 
    {
        // Sum each "lane" of the board (across, up/down, and diagonal) and check for a winner.
        int r1 = board[0].getState() + board[1].getState() + board[2].getState();
        if (r1 == -3 || r1 == 3)
            return r1;

        int r2 = board[3].getState() + board[4].getState() + board[5].getState();
        if (r2 == -3 || r2 == 3)
            return r2;

        int r3 = board[6].getState() + board[7].getState() + board[8].getState();
        if (r3 == -3 || r3 == 3)
            return r3;

        int c1 = board[0].getState() + board[3].getState() + board[6].getState();
        if (c1 == -3 || c1 == 3)
            return c1;

        int c2 = board[1].getState() + board[4].getState() + board[7].getState();
        if (c2 == -3 || c2 == 3)
            return c2;

        int c3 = board[2].getState() + board[5].getState() + board[8].getState();
        if (c3 == -3 || c3 == 3)
            return c3;

        int d1 = board[0].getState() + board[4].getState() + board[8].getState();
        if (d1 == -3 || d1 == 3)
            return d1;

        int d2 = board[2].getState() + board[4].getState() + board[6].getState();
        if (d2 == -3 || d2 == 3)
            return d2;

        // If no lanes summed to 3 or -3, there is no winner.
        return 0;
    }

    /**
     * Reset all of the Tiles on the board 
     * to have a Tile state of N. This is called
     * when there is a new game to be played.
     */
    public void reset() 
    {
        // Loop through and reset the Tiles.
        for (Tile t : board) 
        {
            t.setState(Data.PLAYER_N);
        }
    }

    /**
     * This method determines (in conjunction with minimax())
     * what is the next best move that can be made.
     * 
     * @return : The Tile corresponding to the best move
     */
    public Tile getBestMove() 
    {
        Tile bestTile = null;
        boolean isMaximizingPlayer = saveData.getPlayerType() == Data.PLAYER_X ? true : false;
        int bestValue = isMaximizingPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        // Loop through all of the possible next moves
        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < width; y++) 
            {
                // Make sure the tile is not already occupied
                if (board[x * width + y].getState() == Data.PLAYER_N) 
                {
                    // Set the board as if the AI picked this spot
                    board[x * width + y].setState(-saveData.getPlayerType());

                    // Find the ultimate value of this path
                    int moveValue = minimax(isMaximizingPlayer);

                    // Set the board back to normal
                    board[x * width + y].setState(Data.PLAYER_N);

                    // See if this move had the best value
                    if (isMaximizingPlayer) 
                    {
                        if (moveValue < bestValue) 
                        {
                            bestValue = moveValue;
                            bestTile = board[x * width + y];
                        }
                    } 
                    else 
                    {
                        if (moveValue > bestValue) 
                        {
                            bestValue = moveValue;
                            bestTile = board[x * width + y];
                        }
                    }
                }
            }
        }

        return bestTile;
    }

    /**
     * This method runs a recursive minimax algorithm
     * to determine which is the best Tile to select
     * next.  It is used by getBestMove().
     * 
     * See below link for more information on minimax algorithm
     * https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-1-introduction/ 
     * 
     * @param isMaximizingPlayer : Boolean representing if the player is X
     * @return : The "value" that a selected Tile has
     */
    private int minimax(boolean isMaximizingPlayer) 
    {
        // If the game is in a terminal state, return the value of the board
        if (this.gameTied() || this.gameWon() != 0) 
        {
            return this.gameWon();
        }

        // Turn would be the human player
        if (isMaximizingPlayer) 
        {
            int bestValue = Integer.MIN_VALUE;

            for (int x = 0; x < width; x++) 
            {
                for (int y = 0; y < width; y++) 
                {
                    if (board[x * width + y].getState() == Data.PLAYER_N) 
                    {
                        // Set the board as if the player made this move
                        board[x * width + y].setState(Data.PLAYER_X);
                        int value = minimax(false);

                        // Set the board back to normal
                        board[x * width + y].setState(Data.PLAYER_N);

                        bestValue = Math.max(bestValue, value);
                    }
                }
            }
            return bestValue;
        }

        // Turn would be the AI
        else 
        {
            int bestValue = Integer.MAX_VALUE;

            for (int x = 0; x < width; x++) 
            {
                for (int y = 0; y < width; y++) 
                {
                    if (board[x * width + y].getState() == Data.PLAYER_N) 
                    {
                        // Set the board as if the player made this move
                        board[x * width + y].setState(Data.PLAYER_O);
                        int value = minimax(true);

                        // Set the board back to normal
                        board[x * width + y].setState(Data.PLAYER_N);

                        bestValue = Math.min(bestValue, value);
                    }
                }
            }
            return bestValue;
        }
    }
}
