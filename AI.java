

public class AI
{
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

    public void playTurn(Board board)
    {
        if (Math.random() < bestMoveProbability())
        {
            Tile bestTile = board.getBestMove();
            board.setTile(bestTile, -Settings.playerType); 
        }
        else
        {
            boolean validTile = false;

            while (validTile == false)
            {
                int row = (int)(Math.random() * 3);
                int col = (int)(Math.random() * 3);
                
                validTile = board.setTile(board.getTile(row, col), -Settings.playerType);
            }
        }
    }

    public String getSaying()
    {
        switch(Settings.difficulty)
        {
            case 0:
                return chooseRandom(easySayings);
            case 1:
                return chooseRandom(mediumSayings);
            case 2:
                return chooseRandom(hardSayings);            
            case 4:
                return chooseRandom(unbeatableSayings);
        }     
        
        return "";
    }

    private float bestMoveProbability()
    {
        switch(Settings.difficulty)
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

    private String chooseRandom(String[] sayings)
    {
        int index = (int)(Math.random() * sayings.length);
        return sayings[index];
    }
}