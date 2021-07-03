

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame
{
    // Height and width components
    public int width = 550;
    public int height = 350;

    // Components of the GameWindow
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

        this.saveData = saveData;
        this.driver = driver;
    }

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
        new Circling(this);
        this.pack();
        this.setVisible(true);
        this.repaint();
    }

    public void playScreen()
    {
        container = new JPanel();
        container.setLayout(new BorderLayout());
        this.add(container);
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
        label.setFont(new Font("Consolas", Font.PLAIN, 75));
        label.setHorizontalAlignment(JLabel.CENTER);
        container.add(label);
    }

    private void addModeButtons()
    {
        singlePlayer = new JButton("Single Player");
        singlePlayer.setFont(new Font("Consolas", Font.ITALIC, 50));
        singlePlayer.setFocusable(false);
        singlePlayer.addActionListener(driver);
        container.add(singlePlayer);

        twoPlayer = new JButton("Two Player");
        twoPlayer.setFont(new Font("Consolas", Font.ITALIC, 50));
        twoPlayer.setFocusable(false);
        twoPlayer.addActionListener(driver);
        container.add(twoPlayer);
    }

    private void addBoard()
    {
        JPanel boardContainer = new JPanel();
        boardContainer.setLayout(new BorderLayout());
        boardContainer.setPreferredSize(new Dimension(width, (height / 4) * 3));

        board = new Board(driver, saveData, 3);
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
        chat.setFont(new Font("Calibri", Font.PLAIN, 20));
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
