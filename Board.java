/*
 * Author: Riley Radle
 * Description: 
 *      Contains a board class which represents a
 *      game board as an arrangement of tiles.  The 
 *      tiles are stored in a 1 dimensional array
 *      but can be thought of as a 3 * 3 grid.
 */


import javax.swing.*;

import java.awt.*;

public class Board extends JPanel
{
      private Tile[] board;
      private int width; 
      private Data saveData;
       
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
                  Tile tile = new Tile(row, col, TileType.N);
                  tile.addActionListener(driver);
                  tile.setFocusable(false);

                  // Add the tile to the board
                  this.add(tile);
                  board[row * width + col] = tile;
              }
          }
      }

      public void toggleTiles(boolean clickable)
      {
          for (int i = 0; i < board.length; i++)
          {
              board[i].setEnabled(clickable);
          }
      }

      public boolean setTile(Tile tile, int state)
      {
          // Before assigning, check to make sure the tile is un-occupied.
          if (tile != null && tile.getState() == TileType.N)
          {
              // Set the tile state and return true.
              tile.setState(state);
              return true;
          }
          // Tile was not set; return false
          return false;
      }

      public Tile getTile(int row, int col)
      {
          return board[row * width + col];
      }

      public boolean gameTied()
      {
        //   System.out.println("Running");
          for(int row = 0; row < width; row++)
          {
              for (int col = 0; col < width; col++)
              {
                  if (board[row * width + col].getState() == TileType.N)
                      return false;
              }
          }

          // If all of the tiles are occupied (and presumably no winner), then tie.
          return true;
      }

      public int gameWon()
      {
          // Sum each direction and check for a 0
          int r1 = board[0].getState() + board[1].getState() + board[2].getState();
          if (r1 == -3 || r1 == 3) return r1; 

          int r2 = board[3].getState() + board[4].getState() + board[5].getState();
          if (r2 == -3 || r2 == 3) return r2; 

          int r3 = board[6].getState() + board[7].getState() + board[8].getState();
          if (r3 == -3 || r3 == 3) return r3; 

          int c1 = board[0].getState() + board[3].getState() + board[6].getState();
          if (c1 == -3 || c1 == 3) return c1; 

          int c2 = board[1].getState() + board[4].getState() + board[7].getState();
          if (c2 == -3 || c2 == 3) return c2; 

          int c3 = board[2].getState() + board[5].getState() + board[8].getState();
          if (c3 == -3 || c3 == 3) return c3; 

          int d1 = board[0].getState() + board[4].getState() + board[8].getState();
          if (d1 == -3 || d1 == 3) return d1; 

          int d2 = board[2].getState() + board[4].getState() + board[6].getState();
          if (d2 == -3 || d2 == 3) return d2; 

          // There are no zeros, the player has not won 
          return 0;
       }

      public void reset()
      {
          for (Tile t : board)
          {
              t.setState(TileType.N);
          }
      }

      public Tile getBestMove()
      {
          Tile bestTile = null;
          boolean isMaximizingPlayer = saveData.getPlayerType() == TileType.X ? true : false;
          int bestValue = isMaximizingPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;

          // Loop through all of the possible next moves
          for (int x = 0; x < width; x++)
          {
              for (int y = 0; y < width; y++)
              {
                  // Make sure the tile is not already occupied 
                  if (board[x * width + y].getState() == TileType.N)
                  {
                      // Set the board as if the AI picked this spot
                      board[x * width + y].setState(-saveData.getPlayerType());

                      // Find the ultimate value of this path
                      int moveValue = minimax(isMaximizingPlayer);

                      // Set the board back to normal
                      board[x * width + y].setState(TileType.N);

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
                      if (board[x * width + y].getState() == TileType.N)
                      {
                          // Set the board as if the player made this move
                          board[x * width + y].setState(TileType.X);
                          int value = minimax(false);

                          // Set the board back to normal                        
                          board[x * width + y].setState(TileType.N);

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
                      if (board[x * width + y].getState() == TileType.N)
                      {
                          // Set the board as if the player made this move
                          board[x * width + y].setState(TileType.O);
                          int value = minimax(true);

                          // Set the board back to normal                        
                          board[x * width + y].setState(TileType.N);

                          bestValue = Math.min(bestValue, value);
                      }
                  }
              }
              return bestValue;
          }
      }
}
