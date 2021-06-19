/*
 * Author: Riley Radle
 * Description: 
 *      Contains a Tile class to represent each tile 
 *      on the tic tac toe board.  Each tile has three
 *      attributes, an X location, a Y location, and 
 *      a tile state (X O or N).
 */

package objects;

public class Tile 
{
   private int state; 
   private int x;
   private int y; 
  
   
   // Constructor
   public Tile()
   {
       state = TileState.N;
   }

   public Tile(int x, int y, int state)
   {
       this.x = x;
       this.y = y;
       this.state = state;
   }

   public int getState()
   {
       return state;
   }

   public int getX()
   {
       return x;
   }

   public int getY()
   {
       return y;
   }

   public void setState(int state)
   {
       this.state = state;
   }

   public void setLocation(int x, int y)
   {
       this.x = x;
       this.y = y;
   }
}
