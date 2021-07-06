import java.io.ObjectInputStream;
import java.io.*;
import java.nio.file.*;

/**
 * Author: Riley Radle
 * Description: 
 *      This class allows for a Data object to be saved
 *      to and loaded from a file.  This allows for a 
 *      user's game data to be witheld across multiple 
 *      runs of the program.
 */
public class DataManagement
{
    /**
     * Saves the data to a file
     * 
     * @param data : A serializable object to hold the game data
     * @param fileName : The name of the file as a String
     * @throws Exception
     */
    public static void saveData(Serializable data, String fileName) throws Exception
    {
        try (ObjectOutputStream output = new ObjectOutputStream(
            Files.newOutputStream(Paths.get(fileName)))) 
        {
            output.writeObject(data);
        }
    }  

    /**
     * Loads data from a file
     * 
     * @param fileName : The name of the file as a String
     * @throws Exception
     */
    public static Object loadData(String fileName) throws Exception
    {
        try (ObjectInputStream input = new ObjectInputStream(
            Files.newInputStream(Paths.get(fileName)))) 
        {
            return input.readObject();
        }
    }     
}

/**
 * Author: Riley Radle
 * Description: 
 *      This class is a serializable object 
 *      which can more securely be saved 
 *      to a file.  Data holds the user's 
 *      settings and stats, as well as other
 *      constants used throughout the project.
 *      Data is saved to the file SaveData
 */
class Data implements Serializable
{
    // Needed for serialization
    private static final long serialVersionUID = 1L;

    // Constants
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int UNBEATABLE = 3;

    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = -1;
    public static final int PLAYER_N = 0;

    // Game stats 
    private int gamesWon = 0;
    private int gamesPlayed = 0;

    // Game Settings
    private int difficulty = EASY;
    private int playerType = PLAYER_X;
    private boolean movesFirst = true;
    
    /**
     * In the constructor, set up the default 
     * settings, and values of the stats.
     * This is called if there is no data
     * to be read from the file SaveData
     */
    public Data()
    {
        this.difficulty = Data.EASY;
        this.playerType = Data.PLAYER_X;
        this.movesFirst = true;

        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    /** Setters and getters for the saved game data */

    public void gamePlayed(boolean userWon)
    {
        if (userWon)
            gamesWon += 1;

        gamesPlayed += 1;
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void setMovesFirst(boolean movesFirst)
    {
        this.movesFirst = movesFirst;
    }

    public boolean getMovesFirst()
    {
        return movesFirst;
    }

    public void setPlayerType(int playerType)
    {
        this.playerType = playerType;
    }

    public int getPlayerType()
    {
        return playerType;
    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    public int getDifficulty()
    {
        return difficulty;
    }
}