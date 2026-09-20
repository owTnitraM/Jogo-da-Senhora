import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

@Getter
@Setter
public class View {

    // ===== Setup
    private Frame frame = new Frame("Jogo da Senhora de Idade");
    private CardLayout cardLayout = new CardLayout();
    private Panel screensPanel = new Panel(cardLayout);

    private Label paddingLabel = new Label("");

    private Color outerGreen = new Color(0, 128, 0);
    private Color innerGreen = new Color(11, 114, 41);
    private Color gray = new Color(128, 128, 128);
    private Color lightGray = new Color(226, 226, 226);

    // ===== Main Menu

    // goes on screensPanel
    private Panel menuPanel = new Panel(new BorderLayout());

    // contains 2 rows, inputs for name and char (top), play button (bottom)
    private Panel bottomMenu = new Panel(new GridLayout(2, 1));

    // contains the panels that form label and input for name and char selection (has a padding in the middle/3rd spot that turns into the sameCharWarningLabel)
    private Label sameCharWarningLabel = new Label("", 1);
    private Panel menuPlayerInfoPanel = new Panel(new GridLayout(1, 5));
    // label + textfield panels
    private Panel
            p1CharSelectPanel = new Panel(new GridLayout(2, 1)),
            p1NameSelectPanel = new Panel(new GridLayout(2, 1)),
            p2CharSelectPanel = new Panel(new GridLayout(2, 1)),
            p2NameSelectPanel = new Panel(new GridLayout(2, 1));

    private Label
            p1CharInputLabel = new Label("P1 character:"),
            p1NameInputLabel = new Label("Name:"),
            p2CharInputLabel = new Label("P2 character:"),
            p2NameInputLabel = new Label("Name:");

    private TextField
            p1CharTextField = new TextField("X", 3),
            p1NameTextField = new TextField(20),
            p2CharTextField = new TextField("O", 3),
            p2NameTextField = new TextField(20);

    // bottom of bottomMenu Panel
    private Panel buttonGridPanel = new Panel(new GridLayout(1, 5));
    private Button playButton = new Button("Play");

    private Label menuTitleLabel = new Label("Veia", 1);


    // ===== GAME

    // Has North and South "paddings", also the main screen that goes on screensPanel
    private Panel gamePanel = new Panel(new BorderLayout());

    // top round counter bar
    private Panel roundPointsPanelContainer = new Panel();
    private Panel roundPointsPanel = new Panel(new GridLayout(1, 7));

    private Canvas p2Point1Canvas = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };
    private Canvas p1Point1Canvas = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };
    private Canvas p1Point2Canvas = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };
    private Canvas p2Point2Canvas = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };
    private Canvas p1Point3Canvas = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };
    private Canvas p2Point3Canvas = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };

    private ArrayList<Canvas> playerPointCanvasList = new ArrayList<>();

    // main grid with player info (left), game board (center), room info and exit button (right)
    //private Panel gameContentPanel = new Panel(new GridLayout(1, 3));
    private Panel gameContentPanel = new Panel(new BorderLayout());

    // left container, p1 top, p2 mid and resign button bottom
    private Panel gamePlayerInfoPanel = new Panel(new GridBagLayout());

    private Panel p1TurnIndicatorContainer = new Panel(new FlowLayout(FlowLayout.CENTER));
    private Panel p2TurnIndicatorContainer = new Panel(new FlowLayout(FlowLayout.CENTER));

    // canvas on the left and infoLabelPenal in the center
    private Panel p1InfoPanel = new Panel(new BorderLayout());
    private Canvas p1TurnIndicator = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };

    // has the name and score one on top of the other
    private Panel p1InfoLabelPanel = new Panel(new GridLayout(2, 1));
    private Label p1NameLabel = new Label("P1 - null");
    private Label p1ScoreLabel = new Label("Score: 0");

    // same as the p1 elements
    private Panel p2InfoPanel = new Panel(new BorderLayout());
    private Canvas p2TurnIndicator = new Canvas(){
        boolean active = false;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(active ? innerGreen : lightGray);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

            g2.setColor(active ? outerGreen : gray);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }
    };

    private Panel p2InfoLabelPanel = new Panel(new GridLayout(2, 1));
    private Label p2NameLabel = new Label("P2 - null");
    private Label p2ScoreLabel = new Label("Score: 0");

    // Bottom element below the player infos
    private Button exitButton = new Button("Exit");

    // Game Board
    Panel boardContainer = new Panel(new GridBagLayout()){
        @Override
        public void doLayout() {
            int size = Math.min(getWidth(), getHeight());

            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            board.setBounds(x, y, size, size);
        }
    };
    private Panel board = new Panel(new GridLayout(3, 3)) {
        @Override
        public void setBounds(int x, int y, int width, int height) {
            int size = Math.min(width, height);
            super.setBounds(x, y, size, size);
        }
        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            int size = Math.max(d.width, d.height);
            return new Dimension(size, size);
        }
    };
    //private SquarePanel board = new SquarePanel(new GridLayout(3, 3));
    private final ArrayList<Button> boardButtonList = new ArrayList<Button>();

    // right conteiner with overall info panel
    private Panel gameInfoPanel = new Panel(new GridBagLayout());

    // contains the labels
    private Panel gameInfoLabelPanel = new Panel(new GridBagLayout());

    private Label sessionTime = new Label("Session Time: ");
    private Label roundTime = new Label("Round Time: ");
    private Label turnTime = new Label("Turn Time: ");
    private Label gamesPlayed = new Label("Games Played: ");
    private Label roundsPlayed = new Label("Rounds Played: ");
    private Label turnsPlayed = new Label("Turns Played: ");
    private Label p1RoundsWon = new Label("P1 Rounds Won: ");
    private Label p2RoundsWon = new Label("P2 Rounds Won: ");

    private ArrayList<Label> gameInfoLabelList = new ArrayList<>();

    private Button continueButton = new Button("Continue");

    public View() {
        build(800, 480);
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
        screensPanel.add(menuPanel, "MENU");

        buildGamePanel();
        screensPanel.add(gamePanel, "GAME");

        frame.setLayout(new BorderLayout());
        frame.add(screensPanel, BorderLayout.CENTER);

        changeScreen("MENU");
    }

    private void buildMainMenu() {
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                buttonGridPanel.add(playButton);
            } else {
                buttonGridPanel.add(new Label(""));
            }
        }

        p1NameSelectPanel.add(p1NameInputLabel);
        p1NameSelectPanel.add(p1NameTextField);
        p1CharSelectPanel.add(p1CharInputLabel);
        p1CharSelectPanel.add(p1CharTextField);
        p2CharSelectPanel.add(p2CharInputLabel);
        p2CharSelectPanel.add(p2CharTextField);
        p2NameSelectPanel.add(p2NameInputLabel);
        p2NameSelectPanel.add(p2NameTextField);

        sameCharWarningLabel.setForeground(Color.RED);

        menuPlayerInfoPanel.add(p1NameSelectPanel);
        menuPlayerInfoPanel.add(p1CharSelectPanel);
        menuPlayerInfoPanel.add(sameCharWarningLabel);
        menuPlayerInfoPanel.add(p2CharSelectPanel);
        menuPlayerInfoPanel.add(p2NameSelectPanel);

        bottomMenu.add(menuPlayerInfoPanel, buttonGridPanel);
        bottomMenu.add(buttonGridPanel);

        menuPanel.add(bottomMenu, BorderLayout.SOUTH);
        menuPanel.add(menuTitleLabel, BorderLayout.CENTER);

        screensPanel.add(menuPanel, "MENU");
    }

    private void buildGamePanel() {

        playerPointCanvasList.add(p2Point1Canvas);
        playerPointCanvasList.add(p1Point1Canvas);
        playerPointCanvasList.add(p1Point2Canvas);
        playerPointCanvasList.add(p2Point2Canvas);
        playerPointCanvasList.add(p1Point3Canvas);
        playerPointCanvasList.add(p2Point3Canvas);

        for (Canvas c : playerPointCanvasList){
            c.setPreferredSize(new Dimension(30, 30));
            c.repaint();
        }

        roundPointsPanel.add(p1Point1Canvas);
        roundPointsPanel.add(p1Point2Canvas);
        roundPointsPanel.add(p1Point3Canvas);
        roundPointsPanel.add(new Label(" - ", 1));
        roundPointsPanel.add(p2Point3Canvas);
        roundPointsPanel.add(p2Point2Canvas);
        roundPointsPanel.add(p2Point1Canvas);

        roundPointsPanelContainer.add(roundPointsPanel);

        p1TurnIndicator.setPreferredSize(new Dimension(25, 25));
        p2TurnIndicator.setPreferredSize(new Dimension(25, 25));

        p1TurnIndicatorContainer.add(p1TurnIndicator);
        p2TurnIndicatorContainer.add(p2TurnIndicator);

        p1InfoLabelPanel.add(p1NameLabel);
        p1InfoLabelPanel.add(p1ScoreLabel);
        p1InfoPanel.add(p1TurnIndicatorContainer, BorderLayout.WEST);
        p1InfoPanel.add(p1InfoLabelPanel);

        p2InfoLabelPanel.add(p2NameLabel);
        p2InfoLabelPanel.add(p2ScoreLabel);
        p2InfoPanel.add(p2TurnIndicatorContainer, BorderLayout.WEST);
        p2InfoPanel.add(p2InfoLabelPanel);

        GridBagConstraints c0 = new GridBagConstraints();

        c0.insets = new Insets(2, 5, 2, 5);

        c0.gridx = 0;
        c0.fill = GridBagConstraints.HORIZONTAL;
        c0.weightx = 1;
        c0.weighty = 0;

        // P1
        c0.gridy = 0;
        gamePlayerInfoPanel.add(p1InfoPanel, c0);

        // P2
        c0.gridy = 1;
        gamePlayerInfoPanel.add(p2InfoPanel, c0);

        // Push button to the bottom
        c0.gridy = 2;
        c0.weighty = 1;
        c0.fill = GridBagConstraints.VERTICAL;
        gamePlayerInfoPanel.add(new Panel(), c0);

        // Button
        c0.gridy = 3;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.HORIZONTAL;
        gamePlayerInfoPanel.add(exitButton, c0);

        /*gamePlayerInfoPanel.add(p1InfoPanel);
        gamePlayerInfoPanel.add(p2InfoPanel);
        gamePlayerInfoPanel.add(exitButton);*/

        for (int i = 0; i < 9; i++) {
            Button b = new Button("");
            boardButtonList.add(b);
            board.add(b);
        }

        gameInfoLabelList.add(sessionTime);
        gameInfoLabelList.add(roundTime);
        gameInfoLabelList.add(turnTime);
        gameInfoLabelList.add(gamesPlayed);
        gameInfoLabelList.add(roundsPlayed);
        gameInfoLabelList.add(turnsPlayed);
        gameInfoLabelList.add(p1RoundsWon);
        gameInfoLabelList.add(p2RoundsWon);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 2;
        gbc.insets = new Insets(2, 5, 2, 5);


        GridBagConstraints gbcc = new GridBagConstraints();
        gbcc.gridx = 0;
        gbcc.gridy = GridBagConstraints.RELATIVE;
        gbcc.anchor = GridBagConstraints.NORTHEAST;
        gbcc.fill = GridBagConstraints.NONE;
        gbcc.weightx = 1;
        gbcc.weighty = 0;
        gbcc.insets = new Insets(2, 5, 2, 5);

        for (Label l : gameInfoLabelList) {
            l.setAlignment(Label.RIGHT);
            gameInfoLabelPanel.add(l, gbcc);
        }

        gbcc.weighty = 1;
        gbcc.fill = GridBagConstraints.VERTICAL;
        gameInfoLabelPanel.add(new Panel(), gbcc);

        gameInfoPanel.add(gameInfoLabelPanel, gbc);
        gbc.weighty = 1;
        gameInfoPanel.add(continueButton, gbc);

        continueButton.setVisible(false);

        Dimension buttonSize = new Dimension(125, 40);

        continueButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        /*gameContentPanel.add(gamePlayerInfoPanel);
        gameContentPanel.add(board);
        gameContentPanel.add(gameInfoPanel);*/

        GridBagConstraints bc = new GridBagConstraints();
        bc.anchor = GridBagConstraints.CENTER;
        bc.weightx = 1;
        bc.weighty = 1;
        bc.fill = GridBagConstraints.NONE;

        boardContainer.add(board, bc);

        gameContentPanel.add(gamePlayerInfoPanel, BorderLayout.WEST);
        gameContentPanel.add(boardContainer, BorderLayout.CENTER);
        gameContentPanel.add(gameInfoPanel, BorderLayout.EAST);

        gamePanel.add(gameContentPanel);
        gamePanel.add(roundPointsPanelContainer, BorderLayout.NORTH);
        gamePanel.add(new Label(""), BorderLayout.SOUTH);

        screensPanel.add(gamePanel, "GAME");

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

    public void changeScreen(String s) {
        System.out.println("CHANGE SCREEN view call: " + s);
        switch (s) {
            case "MENU":
                cardLayout.show(screensPanel, "MENU");
                break;
            case "GAME":
                cardLayout.show(screensPanel, "GAME");
                break;
        }
    }
}
