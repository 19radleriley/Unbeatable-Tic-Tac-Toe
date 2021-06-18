/*
 * Author: Riley Radle
 * Description: 
 *      Contains a Tile class to represent each tile 
 *      on the tic tac toe board.  Each tile has three
 *      attributes, an X location, a Y location, and 
 *      a tile state (X O or N).
 */

#include "tile_state.cpp"

// Make a class that represents a tile for the tic tac toe board
class Tile
{
    private:
        tile_state state; 
        int x;
        int y;

    public:
        // Constructor
        Tile()
        {
            state = N;
        }

        Tile(int x, int y, tile_state state)
        {
            this->x = x;
            this->y = y;
            this->state = state;
        }

        tile_state get_state()
        {
            return state;
        }

        int get_x()
        {
            return x;
        }

        int get_y()
        {
            return y;
        }

        void set_state(tile_state state)
        {
            this->state = state;
        }

        void set_location(int x, int y)
        {
            this->x = x;
            this->y = y;
        }
};
