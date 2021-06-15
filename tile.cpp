#include "tile_state.cpp"

// Make a class that represents a tile for the tic tac toe board
class Tile
{
    private:
        tile_state tile; 

    public:
        // Constructor
        Tile()
        {
            tile = N;
        }

        tile_state get_state()
        {
            return tile;
        }

        void set_state(tile_state tt)
        {
            tile = tt;
        }
};
