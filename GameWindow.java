

import javax.swing.*;

import java.awt.*;

public class GameWindow extends JFrame
{
    public static final int SINGLE_PLAYER = 1;
    public static final int TWO_PLAYER = 2;
    public static final int MOVE = 3;

    private int width = 500;
    private int height = 350;

    // Components of the GameWindow
    private Driver driver;
    private JPanel container;
    public JButton singlePlayer;
    public JButton twoPlayer;
    public Board board;
    public JLabel chat;
    public JMenuItem settings;
    public JMenuItem stats;
    public JMenuItem modeSelect;

    public GameWindow(Driver driver)
    {
        super("TIC TAC TOE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
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

        container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        this.setJMenuBar(menu);
        this.add(container);
        this.setVisible(true);

        this.driver = driver;
    }

    public void mainScreen()
    {
        this.setSize(width, height);
        addTitle("Tic Tac Toe");
        addModeButtons();
        addFootNote();
        this.setVisible(true);
    }

    public void playScreen()
    {
        container.setLayout(new BorderLayout());
        this.width = 500;
        this.height = 700;
        this.setSize(width, height);
        addBoard();
        addChatBox();
        this.setVisible(true);
    }

    public void newGame()
    {
        board.reset();
    }

    // ================== Private Methods for Creating and adding Components ==================

    private void addTitle(String title)
    {
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(new Font("Consolas", Font.ITALIC, 75));
        container.add(label);
    }

    private void addModeButtons()
    {
        singlePlayer = new JButton("Single Player");
        singlePlayer.setFont(new Font("Consolas", Font.PLAIN, 50));
        singlePlayer.setFocusable(false);
        singlePlayer.addActionListener(driver);
        container.add(singlePlayer);

        twoPlayer = new JButton("Two Player");
        twoPlayer.setFont(new Font("Consolas", Font.PLAIN, 50));
        twoPlayer.setFocusable(false);
        twoPlayer.addActionListener(driver);
        container.add(twoPlayer);
    }

    private void addBoard()
    {
        JPanel boardContainer = new JPanel();
        boardContainer.setLayout(new BorderLayout());
        boardContainer.setPreferredSize(new Dimension(width, (height / 4) * 3));

        board = new Board(driver, 3);
        boardContainer.add(board); 

        container.add(boardContainer);
    }

    private void addChatBox()
    {
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.setOpaque(true);
        bottom.setPreferredSize(new Dimension(width, height / 4));


        chat = new JLabel();
        chat.setHorizontalAlignment(JLabel.CENTER);
        chat.setText("Welcome to TIC TAC TOE");

        bottom.add(chat);
        container.add(bottom, BorderLayout.SOUTH);
    }

    private void addFootNote()
    {
        JLabel footNote = new JLabel("  Author: Riley Radle");
        footNote.setFont(new Font("Calibri", Font.ITALIC, 12));
        this.add(footNote, BorderLayout.SOUTH);
    }
}
