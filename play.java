/*
 * Author: Riley Radle
 * Description: 
 *      This is the driver that directs the flow of the 
 *      tic tac toe game.  
 */


import objects.*;
import java.util.Scanner;

public class play 
{
   
   public static void main(String[] args)
   {
      // Create the scanner 
      Scanner scan = new Scanner(System.in);
         
      // Get the number of times the user wants to play and the board size
      System.out.println("Welcome to TIC TAC TOE");
      System.out.println("Enter the number of times you wish to play");
      int numGames = scan.nextInt();
     
      System.out.println("Enter the difficulty you wish to play on");
      System.out.println("1. Easy || 2. Medium || 3. Hard || 4. Unbeatable");
      int difficultyLevel = scan.nextInt();

      // Set up needed variables and objects
      int playerWins = 0;
      int player = Math.random() < .50 ? TileState.X : TileState.O;
      TicTacToe game = new TicTacToe(3, difficultyLevel);


      // Run the driver for the user's desired number of games.
      for (int i = 0; i < numGames; i++)
      {
          // The person to go first will always be the previous loser
          player = -player;
          boolean playerWon = false;
          boolean gameTied = false;

          while (playerWon == false && gameTied == false)
          {
              // Play out the next turn
              game.playerTurn(player, scan);
              game.print();

              // Determine the state of the game 
              playerWon = game.gameWon();
              if (playerWon == false)
                  gameTied = game.gameTied();
              
              // Change the player
              if (playerWon == false && gameTied == false)
                  player = -player;
          }

          String endGame;

          if (playerWon && player == TileState.X)
          {
              endGame = "Congratulations! You won!";
              playerWins += 1;
          }
          else if (playerWon && player == TileState.O)
              endGame = "Oof...Better Luck Next Time";
          else
              endGame = "The game was a tie!\n";
          
          System.out.println(endGame);

          // Clears the board for a new round.
          game.newRound();
      }

      // If the player won the majority of games, output a message.
      if (playerWins > (numGames / 2))
      {
          System.out.println("\"You got the best of me this time\" -AI");
      }  
      
      scan.close();
   }
}
