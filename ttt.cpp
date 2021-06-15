#include "board.cpp"
#include <iostream>
#include <sstream>
#include <thread>
#include <chrono>
#include <vector>

using namespace std;

int get_user_input();
bool player_turn(tile_state player, Board* board);
bool human_turn(tile_state player, Board* board);
bool AI_turn(tile_state player, Board* board);

int main()
{
    Board* board = new Board();
    bool game_finished = false;
    srand(time(NULL));
    int player = X;

    while (game_finished == false)
    {
        game_finished = player_turn(tile_state(player), board);

        // Print out the board
        board->print();    
        cout << "\n";

        // Switch to the other player  
        if (game_finished == false)
            player = -player;
    }

    cout << "The game is finished, congratulations player " << player <<"!\n";
    board->free_data();
}

bool player_turn(tile_state player, Board* board)
{
    if (player == X) 
        return human_turn(player, board);
    else
    {
        // Make the AI "sleep for a second" before making their move
        this_thread::sleep_for(chrono::seconds(1));
        return AI_turn(player, board);
    }
}

bool human_turn(tile_state player, Board* board)
{
    cout << "You're up...what's your move?\n";
    bool valid_tile = false;
    while (valid_tile == false)
    {
        cout << "Enter the row of your mark: ";
        int x = get_user_input();
 
        cout << "Enter the column of your mark: ";
        int y = get_user_input();
        cout << endl;

        valid_tile = board->set_tile(x, y, player);

        if (valid_tile == false)
        {
            cout << "That tile is already taken, please choose again.\n";
        }
    }
 
    return board->game_finished();    
}

bool AI_turn(tile_state player, Board* board)
{
    bool valid_tile = false;

    while (valid_tile == false)
    {
        int x = rand() % 3;
        int y = rand() % 3;
        
        valid_tile = board->set_tile(x, y, player);
    }
    return board->game_finished();
}

int get_user_input()
{
    bool valid_input = false;
    int x;
    while (valid_input == false)
    {
        cin >> x;

        if (x > 2 || x < 0)
        {
            cout << "That was invalid\n";
            continue;
        }
        valid_input = true;
    }
    return x;
}
