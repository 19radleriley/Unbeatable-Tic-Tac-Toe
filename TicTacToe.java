/*
 * Author: Riley Radle
 * Description: 
 *      Contains a tic tac toe game object 
 *      with several methods that allow the game
 *      to be ran from a driver program. 
 */



import java.util.Scanner;

public class TicTacToe 
{
      private int boardWidth;
      private float difficulty;
      private Board gameBoard;

      public TicTacToe(int boardWidth, int difficultyLevel)
      {
          this.boardWidth = boardWidth;
          gameBoard = new Board(boardWidth);
          setDifficulty(difficultyLevel);
      }

      public boolean gameWon()
      {
          return gameBoard.gameWon() != 0;
      }
      
      public boolean gameTied()
      {
          return gameBoard.gameTied();
      }

      public void playerTurn(int player, Scanner scan)
      {
          if (player == TileType.X) 
              humanTurn(player, scan);
          else
          {
              // Make the AI "sleep" for a second before making their move
              try 
              {                 
                 Thread.sleep(1000);
              }
              catch (Exception e)
              {
                 System.out.println("Unable to Sleep");
              }
              
              aiTurn(player);
          }
      }

      public void print()
      {
          gameBoard.print();
          System.out.println();
      }

      public void newRound()
      {
         gameBoard = new Board(boardWidth);
      }
      
      private void humanTurn(int player, Scanner scan)
      {
          System.out.println("You're up...what's your move?");
          boolean validTile = false;
          while (validTile == false)
          {
              System.out.println("Enter the row of your mark: ");
              int x = getPlayerMove(scan);
      
              System.out.println("Enter the column of your mark: ");
              int y = getPlayerMove(scan);
              System.out.println();

              validTile = gameBoard.setTile(x, y, player);

              if (validTile == false)
              {
                  System.out.println("That tile is already taken, please choose again.");
              }
          }        
      }

      private int getMove()
      {
          Scanner scan = new Scanner(System.in);
          boolean validInput = false;
          int input = -1;
          while (validInput == false)
          {
              if (scan.hasNextInt())
                  input = scan.nextInt();
              
              if (input > (boardWidth - 1) || input < 0)
              {
                  System.out.println("That was invalid");
                  continue;
              }
              validInput = true;
          }
          scan.close();
          return input;
      }
      
      private int getPlayerMove(Scanner scan)
      {
         while (true)
         {
            int input = scan.nextInt();
            
            if (input > (boardWidth - 1) || input < 0)
               System.out.println("That was invalid");
            else
            {
               return input; 
            }
         }   
      }

      private void aiTurn(int player)
      {
          // The AI will make a random move
          double random = Math.random();

          if (random > difficulty)
          { 
              boolean validTile = false;

              while (validTile == false)
              {
                  int x = (int)(Math.random() * 3);
                  int y = (int)(Math.random() * 3);
                  
                  validTile = gameBoard.setTile(x, y, player);
              }
          }
          // The AI will choose the best move
          else
          {
              Tile bestTile = gameBoard.getBestMove();
              gameBoard.setTile(bestTile.getX(),bestTile.getY(), player);
          }
      }

      private void setDifficulty(int level)
      {
          if (level == 1)
              difficulty = 0.5f;
          else if (level == 2)
              difficulty = 0.7f;
          else if (level == 3)
              difficulty = 0.9f;
          else if (level == 4)
              difficulty = 1.0f;
      }
}
