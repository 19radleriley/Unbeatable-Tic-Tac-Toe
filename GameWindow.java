
import javax.swing.*;
import java.awt.*;

/**
 * Author: Riley Radle
 * Description: 
 *      This class is a JFrame object that contains
 *      all of the needed visual components for playing 
 *      tic tac toe.  It holds most of the graphics for the project.
 */
public class GameWindow extends JFrame
{
    /** Height and width of the GameWindow */
    public int width = 550;
    public int height = 350;

    /** Components for the GameWindow */
    private Driver driver;
    private Data saveData;
    private JPanel container;
    public JPanel outer;
    public JButton singlePlayer;
    public JButton twoPlayer;
    public Board board;
    public JLabel chat;
    public JMenuItem settings;
    public JMenuItem stats;
    public JMenuItem modeSelect;

    /**
     * The constructor sets up the needed parts of the GameWindow/JFrame.
     * It also initializes some of the components of the window.
     * 
     * @param driver : The Driver for this project (ActionListener)
     * @param saveData : Referenct to the user's saved data
     */
    public GameWindow(Driver driver, Data saveData)
    {
        super("TIC TAC TOE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        
        // Make the window always appear in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)screenSize.getWidth() / 2 - width / 2, (int)screenSize.getHeight() / 2 - height);

        // Add a menubar to the window
        JMenuBar menu = new JMenuBar();
        JMenu dropDown = new JMenu("...");
        modeSelect = new JMenuItem("Mode Select");
        settings = new JMenuItem("Settings");
        stats = new JMenuItem("Stats");
        modeSelect.addActionListener(driver);
        settings.addActionListener(driver);
        stats.addActionListener(driver);

        // Add the items to the menu
        dropDown.add(modeSelect);
        dropDown.add(stats);
        dropDown.add(settings);
        menu.add(dropDown);

        this.setJMenuBar(menu);
        this.setVisible(true);

        // Initialize the save data and the driver for later use.
        this.saveData = saveData;
        this.driver = driver;
    }

    /**
     * This method sets up the components of the main screen. 
     * From here the user can select which mode they wish to play.
     */
    public void mainScreen()
    {
        // Create the outer container
        outer = new JPanel();
        outer.setLayout(null);
        outer.setPreferredSize(new Dimension(width, height));
        outer.setBackground(Color.white);

        // Set the properties for container
        container = new JPanel();
        container.setLayout(new FlowLayout());
        container.setSize(width - 100, height - 100);
        container.setBorder(BorderFactory.createLineBorder(Color.black));
        container.setBounds(
            width / 2 - container.getWidth() / 2,
            height / 2 - container.getHeight() / 2,
            container.getWidth(), container.getHeight());

        // Add all of the elements to container
        addTitle("Tic Tac Toe");
        addModeButtons();
        addFootNote();

        // Add the elements and refresh window
        outer.add(container);
        this.add(outer);
        new Animations(this); // Animations
        this.pack();
        this.setVisible(true);
        this.repaint();
    }

    /**
     * This method sets up the components for single
     * and two player modes.  From here the user can play 
     * a game of tic tac toe.
     */
    public void playScreen()
    {
        // Set up the container
        container = new JPanel();
        container.setLayout(new BorderLayout());
        this.add(container);

        // Change the height of the window
        this.height = 700;
        this.setSize(width, height);

        // Add the board and the chatbox, then repaint the window
        addBoard();
        addChatBox();
        this.setVisible(true);
    }

    public void newGame()
    {
        board.reset();
    }

    /** ================== Private Methods for Creating and adding Components ================== */

    /**
     * This method setus up and adds the Tic Tac Toe
     * title JLabel to the GameWindow on the main screen.
     * 
     * @param title : String that will be displayed as the title
     */
    private void addTitle(String title)
    {
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(new Font("Consolas", Font.PLAIN, 75));
        label.setHorizontalAlignment(JLabel.CENTER);
        container.add(label);
    }

    /**
     * This method sets up and adds the single
     * and two player buttons that are displayed 
     * on the main screen of the GameWindow
     */
    private void addModeButtons()
    {
        // Set up single player
        singlePlayer = new JButton("Single Player");
        singlePlayer.setFont(new Font("Consolas", Font.ITALIC, 50));
        singlePlayer.setFocusable(false);
        singlePlayer.addActionListener(driver);
        container.add(singlePlayer);

        // Set up two player
        twoPlayer = new JButton("Two Player");
        twoPlayer.setFont(new Font("Consolas", Font.ITALIC, 50));
        twoPlayer.setFocusable(false);
        twoPlayer.addActionListener(driver);
        container.add(twoPlayer);
    }

    /**
     * This method sets up and adds a new Tic 
     * Tac Toe board to the GameWindow.
     */
    private void addBoard()
    {
        JPanel boardContainer = new JPanel();
        boardContainer.setLayout(new BorderLayout());
        boardContainer.setPreferredSize(new Dimension(width, (height / 4) * 3));

        board = new Board(driver, saveData, 3);
        boardContainer.add(board); 

        container.add(boardContainer);
    }

    /**
     * This method sets up and adds a "chatbox"
     * below the Tic Tac Toe board.  In single
     * player mode, this is where the AI will 
     * output its sayings. 
     */
    private void addChatBox()
    {
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.setOpaque(true);
        bottom.setPreferredSize(new Dimension(width, height / 4));

        chat = new JLabel();
        chat.setFont(new Font("Calibri", Font.PLAIN, 20));
        chat.setHorizontalAlignment(JLabel.CENTER);
        chat.setText("Welcome to TIC TAC TOE");

        bottom.add(chat);
        container.add(bottom, BorderLayout.SOUTH);
    }

    /**
     * This method adds a small footnote containing
     * my name to the bottom of the GameWindow
     */
    private void addFootNote()
    {
        JLabel footNote = new JLabel("  Author: Riley Radle");
        footNote.setFont(new Font("Calibri", Font.ITALIC, 12));
        this.add(footNote, BorderLayout.SOUTH);
    }
}
