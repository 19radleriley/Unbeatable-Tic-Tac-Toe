import java.io.ObjectInputStream;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class DataManagement
{
    public static void saveData(Serializable data, String fileName) throws Exception
    {
        try (ObjectOutputStream output = new ObjectOutputStream(
            Files.newOutputStream(Paths.get(fileName)))) 
        {
            output.writeObject(data);
        }
    }  

    public static Object loadData(String fileName) throws Exception
    {
        try (ObjectInputStream input = new ObjectInputStream(
            Files.newInputStream(Paths.get(fileName)))) 
        {
            return input.readObject();
        }
    }     
}

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

    public Data()
    {
        this.difficulty = Data.EASY;
        this.playerType = Data.PLAYER_X;
        this.movesFirst = true;

        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

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