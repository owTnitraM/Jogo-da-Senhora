import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class View {
    // Setup
    private Label paddingLabel = new Label("");
    private CardLayout cardLayout = new CardLayout();
    private Panel screensPanel = new Panel(cardLayout);

    private Frame frame = new Frame("Jogo da Senhora de Idade");

    // Main Menu
    private Panel mainMenuPanel = new Panel(new BorderLayout());
    private Panel mainMenuButtonsPanel = new Panel(new GridLayout(1, 5));

    private Label mainMenuLabel = new Label("Jogo da Senhora de Idade", 1);

    private Button playOfflineButton = new Button("Play Offline");
    private Button enterRoomButton = new Button("Enter");
    private Button hostRoomButton = new Button("Host");

    // Game
    private Panel gamePanel = new Panel(new BorderLayout());

    private Label p1Name = new Label("P1: null");
    private Label p1Score = new Label("Points: 0");
    private Label p2Name = new Label("P2: null");
    private Label p2Score = new Label("Points: 0");

    private Label roomName = new Label("Room: null");
    private Label roomGamesPlayed = new Label("Games: 0");
    private Label roomTime = new Label("Time: 00:00");

    private Panel playerInfoPanel = new Panel(new GridLayout(15, 1));
    private Panel boardPanel = new Panel(new GridLayout(3, 3));
    private Panel roomInfoPanel = new Panel(new GridLayout(15, 1));

    public View() {
        build(500, 500);
        setListeners();
    }
    public View(int w, int h) {
        build(w, h);
        setListeners();
    }

    private void build(int windowWidth, int windowHeight) {
        // Setup
        frame.setLayout(new BorderLayout());
        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);

        buildMainMenu();
        screensPanel.add(mainMenuPanel, "MENU");

        buildGamePanel();
        screensPanel.add(gamePanel, "GAME");

        frame.setLayout(new BorderLayout());
        frame.add(screensPanel, BorderLayout.CENTER);
    }

    private void buildGamePanel() {
        playerInfoPanel.add(p1Name);
        playerInfoPanel.add(p1Score);
        playerInfoPanel.add(paddingLabel);
        playerInfoPanel.add(p2Name);
        playerInfoPanel.add(p2Score);

        roomInfoPanel.add(roomName);
        roomInfoPanel.add(roomGamesPlayed);
        roomInfoPanel.add(roomTime);

        gamePanel.add(playerInfoPanel, BorderLayout.WEST);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(roomInfoPanel, BorderLayout.EAST);
    }

    private void buildMainMenu() {
        mainMenuPanel.add(mainMenuLabel, BorderLayout.CENTER);

        playOfflineButton.addActionListener(e -> cardLayout.show(screensPanel, "GAME"));

        mainMenuButtonsPanel.add(new Label());
        mainMenuButtonsPanel.add(playOfflineButton);
        mainMenuButtonsPanel.add(enterRoomButton);
        mainMenuButtonsPanel.add(hostRoomButton);
        mainMenuButtonsPanel.add(new Label());

        mainMenuPanel.add(mainMenuButtonsPanel, BorderLayout.SOUTH);
    }

    private void setListeners() {
        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
    }
}
