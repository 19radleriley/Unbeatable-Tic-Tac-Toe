#include "tile.cpp"
#include <iostream>

// May or may not use this

// Represents a board for the tic tac toe game 

class Board
{
    private:
        Tile* board;
        int width;
         
    public:
        Board()
        {
            // Store in a 1D array for more cache hits
            board = new Tile[9];
            width = 3;
        }

        bool set_tile(int x, int y, tile_state tt)
        {
            // Before assigning, check to make sure the tile is un-occupied.
            if (board[x * width + y].get_state() == N)
            {
                // Set the tile state and return true.
                board[x * width + y].set_state(tt);
                return true;
            }
            // Tile was not set; return false
            return false;
        }

        bool game_finished()
        {
            // Sum each direction and check for a 0
            int r1 = board[0].get_state() + board[1].get_state() + board[2].get_state();
            if (r1 == -3 || r1 == 3) return true; 

            int r2 = board[3].get_state() + board[4].get_state() + board[5].get_state();
            if (r2 == -3 || r2 == 3) return true; 

            int r3 = board[6].get_state() + board[7].get_state() + board[8].get_state();
            if (r3 == -3 || r3 == 3) return true; 

            int c1 = board[0].get_state() + board[3].get_state() + board[6].get_state();
            if (c1 == -3 || c1 == 3) return true; 

            int c2 = board[1].get_state() + board[4].get_state() + board[7].get_state();
            if (c2 == -3 || c2 == 3) return true; 

            int c3 = board[2].get_state() + board[5].get_state() + board[8].get_state();
            if (c3 == -3 || c3 == 3) return true; 

            int d1 = board[0].get_state() + board[4].get_state() + board[8].get_state();
            if (d1 == -3 || d1 == 3) return true; 

            int d2 = board[2].get_state() + board[4].get_state() + board[6].get_state();
            if (d2 == -3 || d2 == 3) return true; 

            // There are no zeros, the player has not won 
            return false;
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
};

