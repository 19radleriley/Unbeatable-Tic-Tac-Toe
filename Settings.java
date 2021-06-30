
import java.awt.*;

public class Settings
{
    // Difficulty Constants
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int UNBEATABLE = 3;

    public static int difficulty;
    public static int playerType;
    // public static Color theme;
    // public static boolean music;
    // public static boolean graphics;
    // public static boolean movesFirst;

    public Settings(int difficulty, int playerType, Color theme, boolean music, boolean graphics)
    {
        Settings.difficulty = difficulty;
        Settings.playerType = playerType;
        // Settings.theme = theme;
        // Settings.music = music;
        // Settings.graphics = graphics;
    }
}