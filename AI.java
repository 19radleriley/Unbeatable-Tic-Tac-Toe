
/**
 * Author: Riley Radle 
 * Description: 
 *      Class represents the AI for the single player 
 *      tic tac toe mode.  
 */
public class AI
{
    /**
     * Sayings that will be outputted for different
     * difficulties in single player mode
     */
    private final String[] easySayings = 
    {
        "I think that was a good move?",
        "You're good at tic tac toe!",
        "Take it easy on me, would ya?",
        "You're a pro, wow",
        "Teach me your ways!",
        "Pick on someone your own size!",
        "You're really playing on easy? Come on!"
    };

    private final String[] mediumSayings = 
    {
        "I have been practicing",
        "That was most likely a good move",
        "Hmmmmm...tricky",
        "I still have more learning to do",
        "I think I have you this time!",
        "You practice too much", 
        "That was a bad move...wait...never mind",
        "This is going to be the game!"
    };

    private final String[] hardSayings = 
    {
        "I have been practicing A LOT",
        "This is going to be fun...for me",
        "Are you sure about that move?",
        "You haven't been practicing have you?",
        "I've got you this time",
        "You've gotten too comfortable",
        "The student has become the master!",
        "You're good, but not good enough",
    };

    private final String[] unbeatableSayings = 
    {
        "Yawn...",
        "You've already lost",
        "You're trying...so cute",
        "Humans...so flawed",
        "First tic tac toe, then the world",
        "If at first you don't succeed, tie, tie, again",
        "This is too fun!",
        "Don't blink, you might miss my move"
    };

    /** Reference to the user's saved Data */
    private Data saveData;

    /**
     * Constructor initializes the 
     * reference to user's saved data. 
     * 
     * @param saveData
     */
    public AI(Data saveData)
    {
        this.saveData = saveData;
    }

    /**
     * Play's a single turn for the AI.
     * Based on the difficulty, there will 
     * be a preset chance that the AI makes 
     * either the best move, or a random move.
     * 
     * @param board : The game board 
     */
    public void playTurn(Board board)
    {
        // Get the probability that the best move is made.
        float prob = bestMoveProbability();

        // The AI will make the best move
        if (Math.random() < prob)
        {
            Tile bestTile = board.getBestMove();
            board.setTile(bestTile, -saveData.getPlayerType()); 
        }
        // The AI will pick a random move
        else
        {
            boolean validTile = false;

            while (validTile == false)
            {
                int row = (int)(Math.random() * 3);
                int col = (int)(Math.random() * 3);
                
                validTile = board.setTile(board.getTile(row, col), -saveData.getPlayerType());
            }
        }
    }

    /**
     * Get a random saying from the corresponding
     * list of sayings, based on the difficulty in Data
     * 
     * @return : An AI saying as a String
     */
    public String getSaying()
    {
        // Use the chooseRandom() helper to get a saying based on difficulty.
        switch(saveData.getDifficulty())
        {
            case 0:
                return chooseRandom(easySayings);
            case 1:
                return chooseRandom(mediumSayings);
            case 2:
                return chooseRandom(hardSayings);            
            case 3:
                return chooseRandom(unbeatableSayings);
        }     
        
        return "";
    }

    /**
     * Based on the difficulty in Data, determine
     * the probability that the AI will make the best move
     * 
     * @return : The probability that the AI will make the best move.
     */
    private float bestMoveProbability()
    {
        // Choose a probability(float) based on the difficulty.
        switch(saveData.getDifficulty())
        {
            case 0:
                return 0.25f;
            case 1: 
                return 0.5f;
            case 2:
                return 0.75f;
            case 3:
                return 1.0f;
        }

        return 1.0f;
    }

    /**
     * Helper method that chooses a random String from a list 
     * of Strings. This is used by the getSaying() method.
     * @param sayings
     * @return
     */
    private String chooseRandom(String[] sayings)
    {
        // Randomly choose and index, and then return that String.
        int index = (int)(Math.random() * sayings.length);
        return sayings[index];
    }
}