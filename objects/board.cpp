/*
 * Author: Riley Radle
 * Description: 
 *      Contains a board class which represents a
 *      game board as an arrangement of tiles.  The 
 *      tiles are stored in a 1 dimensional array
 *      but can be thought of as a width * width 
 *      grid (3x3 by default).
 */

#include "tile.cpp"
#include <iostream>
#include <algorithm>

using namespace std;

// May or may not use this

// Represents a board for the tic tac toe game 

class Board
{
    private:
        Tile* board;
        int width;

        int minimax(bool isMaximizingPlayer)
        {
            // If the game is in a terminal state, return the value of the board
            if (this->game_tied() || this->game_won())
            {
                return this->game_won();
            }

            // Turn would be the human player
            if (isMaximizingPlayer)
            {
                int best_value = INT_MIN;

                for (int x = 0; x < width; x++)
                {
                    for (int y = 0; y < width; y++)
                    {
                        if (board[x * width + y].get_state() == N)
                        {
                            // Set the board as if the player made this move
                            board[x * width + y].set_state(X);
                            int value = minimax(false);

                            // Set the board back to normal                        
                            board[x * width + y].set_state(N);


                            best_value = max(best_value, value);
                        }
                    }
                }
                return best_value;
            }

            // Turn would be the AI
            else
            {
                int best_value = INT_MAX;

                for (int x = 0; x < width; x++)
                {
                    for (int y = 0; y < width; y++)
                    {
                        if (board[x * width + y].get_state() == N)
                        {
                            // cout << "Current tile: " << x << ", " << y << endl;

                            // Set the board as if the player made this move
                            board[x * width + y].set_state(O);
                            int value = minimax(true);

                            // Set the board back to normal                        
                            board[x * width + y].set_state(N);

                            best_value = min(best_value, value);
                        }
                    }
                }
                return best_value;
            }

        }
         
    public:
        Board(int width)
        {
            // Store in a 1D array for more cache hits
            board = new Tile[width * width];
            this->width = width;


            // Set the locations of all of the tiles on the board
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < width; y++)
                {
                    board[x * width + y].set_location(x, y);
                }
            }
        }

        bool set_tile(int x, int y, tile_state state)
        {
            // Before assigning, check to make sure the tile is un-occupied.
            if (board[x * width + y].get_state() == N)
            {
                // Set the tile state and return true.
                board[x * width + y].set_state(state);
                return true;
            }
            // Tile was not set; return false
            return false;
        }

        bool game_tied()
        {
            for(int x = 0; x < width; x++)
            {
                for (int y = 0; y < width; y++)
                {
                    if (board[x * width + y].get_state() == N)
                        return false;
                }
            }

            // If all of the tiles are occupied (and presumably no winner), then tie.
            return true;
        }

        int game_won()
        {
            // Sum each direction and check for a 0
            int r1 = board[0].get_state() + board[1].get_state() + board[2].get_state();
            if (r1 == -3 || r1 == 3) return r1; 

            int r2 = board[3].get_state() + board[4].get_state() + board[5].get_state();
            if (r2 == -3 || r2 == 3) return r2; 

            int r3 = board[6].get_state() + board[7].get_state() + board[8].get_state();
            if (r3 == -3 || r3 == 3) return r3; 

            int c1 = board[0].get_state() + board[3].get_state() + board[6].get_state();
            if (c1 == -3 || c1 == 3) return c1; 

            int c2 = board[1].get_state() + board[4].get_state() + board[7].get_state();
            if (c2 == -3 || c2 == 3) return c2; 

            int c3 = board[2].get_state() + board[5].get_state() + board[8].get_state();
            if (c3 == -3 || c3 == 3) return c3; 

            int d1 = board[0].get_state() + board[4].get_state() + board[8].get_state();
            if (d1 == -3 || d1 == 3) return d1; 

            int d2 = board[2].get_state() + board[4].get_state() + board[6].get_state();
            if (d2 == -3 || d2 == 3) return d2; 

            // There are no zeros, the player has not won 
            return 0;
         }

         // To make this work for a secon player, just change the value of O in enum to 2, and add another check for 6 ^

        void print()
        {
            for(int x = 0; x < width; x++)
            {
                for (int y = 0; y < width; y++)
                {
                    Tile tile = board[x * width + y];
                    if (tile.get_state() == X)
                        std::cout << "X ";
                    else if (tile.get_state() == O)
                        std::cout << "O ";
                    else
                        std::cout << "- ";
                }
                std::cout << "\n";
            }
        }

        void free_data()
        {
            delete[] board;
        }

        // Note that this returns the tile, not the pointer
        Tile get_best_move()
        {
            Tile* best_tile = NULL;
            int highest_value = INT_MAX;

            // Loop through all of the possible next moves
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < width; y++)
                {
                    // Make sure the tile is not already occupied 
                    if (board[x * width + y].get_state() == N)
                    {
                        // Set the board as if the AI picked this spot
                        board[x * width + y].set_state(O);

                        // Find the ultimate value of this path
                        // cout << "Starting minimax\n";
                        int move_value = minimax(true);

                        // Set the board back to normal
                        board[x * width + y].set_state(N);

                        // See if this move had the highest value
                        if (move_value < highest_value)
                        {
                            highest_value = move_value;
                            best_tile = &board[x * width + y];
                        }
                    }
                }
            }

            return *best_tile;
        }
};

/*
    Here is what I need: 
    1. Functions that determine a terminal state of the game
    2. Function that returns a value based on the terminal state of the game.
    3. Function that clears those board states (store the states so that we don't have to recompute
       between finding the terminal state and the value of the terminal state)

    or we coud just have this function return 
*/  

