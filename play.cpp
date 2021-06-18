/*
 * Author: Riley Radle
 * Description: 
 *      This is the driver that directs the flow of the 
 *      tic tac toe game.  
 */

#include "objects/tic_tac_toe.cpp"
#include <iostream>

using namespace std;

int main()
{
    // Seed the random number generator 
    srand(time(NULL));

    // Get the number of tiems the user wants to play and the board size
    cout << "Welcome to TIC TAC TOE\n";
    cout << "Enter the number of times you wish to play\n";
    int num_games;
    cin >> num_games;

    cout << "Enter the difficulty you wish to play on\n";
    cout << "1. Easy || 2. Medium || 3. Hard || 4. Unbeatable\n";
    int difficulty_level;
    cin >> difficulty_level;

    // Set up needed variables and objects
    int player_wins = 0;
    int player = rand() < .50 ? X : O;
    Tic_Tac_Toe* game = new Tic_Tac_Toe(3, difficulty_level);


    // Run the driver for the user's desired number of games.
    for (int i = 0; i < num_games; i++)
    {
        // The person to go first will always be the previous loser
        player = -player;
        bool player_won = false;
        bool game_tied = false;

        while (player_won == false && game_tied == false)
        {
            // Play out the next turn
            game->player_turn(tile_state(player));
            game->print();

            // Determine the state of the game 
            player_won = game->game_won();
            if (player_won == false)
                game_tied = game->game_tied();
            
            // Change the player
            if (player_won == false && game_tied == false)
                player = -player;
        }

        string end_game;

        if (player_won && player == X)
        {
            end_game = "Congratulations! You won!\n";
            player_wins += 1;
        }
        else if (player_won && player == O)
            end_game = "Oof...Better Luck Next Time\n";
        else
            end_game = "The game was a tie!\n";

        cout << end_game << endl;

        // Clears the board for a new round.
        game->new_round();
    }

    // Ends the game and frees all of the memory 
    game->end_game();

    if (player_wins > (num_games / 2))
    {
        cout << "\"You got the best of me this time\" -AI\n";
    }

    return 0;
}