/*
 * Author: Riley Radle
 * Description: 
 *      Contains a tic tac toe game object 
 *      with several methods that allow the game
 *      to be ran from a driver program. 
 */

#include "board.cpp"
#include <iostream>
#include <thread>
#include <chrono>
#include <vector>

class Tic_Tac_Toe
{
    private:
        int board_width;
        float difficulty;
        Board* game_board;

        void human_turn(tile_state player)
        {
            cout << "You're up...what's your move?\n";
            bool valid_tile = false;
            while (valid_tile == false)
            {
                cout << "Enter the row of your mark: ";
                int x = get_player_move();
        
                cout << "Enter the column of your mark: ";
                int y = get_player_move();
                cout << endl;

                valid_tile = game_board->set_tile(x, y, player);

                if (valid_tile == false)
                {
                    cout << "That tile is already taken, please choose again.\n";
                }
            }        
        }

        int get_player_move()
        {
            bool valid_input = false;
            int input;
            while (valid_input == false)
            {
                cin >> input;

                if (input > (board_width - 1) || input < 0)
                {
                    cout << "That was invalid\n";
                    continue;
                }
                valid_input = true;
            }
            return input;
        }

        void AI_turn(tile_state player)
        {
            // The AI will make a random move
            float random = float(rand());
            random /= RAND_MAX;

            if (random > difficulty)
            { 
                bool valid_tile = false;

                while (valid_tile == false)
                {
                    int x = rand() % 3;
                    int y = rand() % 3;
                    
                    valid_tile = game_board->set_tile(x, y, player);
                }
            }
            // The AI will choose the best move
            else
            {
                Tile best_tile = game_board->get_best_move();
                game_board->set_tile(best_tile.get_x(),best_tile.get_y(), player);
            }
        }

        void set_difficulty(int level)
        {
            if (level == 1)
                difficulty = 0.5;
            else if (level == 2)
                difficulty = 0.7;
            else if (level == 3)
                difficulty = 0.9;
            else if (level == 4)
                difficulty = 1.0;
        }

    public:
        // Contstructor 
        Tic_Tac_Toe(int board_width, int difficulty_level)
        {
            this->board_width = board_width;
            game_board = new Board(board_width);
            set_difficulty(difficulty_level);
        }

        bool game_won()
        {
            return game_board->game_won();
        }
        
        bool game_tied()
        {
            return game_board->game_tied();
        }

        void player_turn(tile_state player)
        {
            if (player == X) 
                human_turn(player);
            else
            {
                // Make the AI "sleep for a second" before making their move
                this_thread::sleep_for(chrono::seconds(1));
                AI_turn(player);
            }
        }

        void print()
        {
            game_board->print();
            std::cout << endl;
        }

        void new_round()
        {
            game_board->free_data();
            game_board = new Board(board_width);
        }

        void end_game()
        {
            game_board->free_data();
            delete this; 
        }
};
