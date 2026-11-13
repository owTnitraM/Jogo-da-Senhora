package Models;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Versao Swing (GUI Designer) da tela do jogo.
 * A estrutura visual e a mesma do View.java: menu inicial + tela do jogo,
 * trocadas por um CardLayout.
 *
 * <p>Todos os componentes sao componentes padrao do Swing (JPanel, JLabel,
 * JButton, ...), sem classes extras, para que o arquivo ViewForm.form
 * continue sendo editavel no Swing UI Designer.</p>
 *
 * <p>Os antigos Canvas de pontos viraram {@link JLabel}s com um icone
 * circular gerado em memoria (ver {@link #circleIcon(int, boolean)}).</p>
 */
public class ViewForm {

    // ===== Cores dos indicadores (mesmas do View.java)
    private static final Color OUTER_GREEN = new Color(0, 119, 32);
    private static final Color INNER_GREEN = new Color(64, 194, 64);
    private static final Color GRAY = new Color(128, 128, 128);
    private static final Color LIGHT_GRAY = new Color(226, 226, 226);

    private static final int POINT_SIZE = 30;
    private static final int TURN_SIZE = 25;

    private static final ImageIcon POINT_ON = circleIcon(POINT_SIZE, true);
    private static final ImageIcon POINT_OFF = circleIcon(POINT_SIZE, false);
    private static final ImageIcon TURN_ON = circleIcon(TURN_SIZE, true);
    private static final ImageIcon TURN_OFF = circleIcon(TURN_SIZE, false);

    // ===== Setup
    private JPanel root;
    private JFrame frame;

    // ===== Main Menu
    private JPanel menuPanel;
    private JLabel menuTitleLabel;

    private JPanel bottomMenu;
    private JPanel menuPlayerInfoPanel;

    private JPanel p1NameSelectPanel;
    private JLabel p1NameInputLabel;
    private JTextField p1NameTextField;

    private JPanel p1CharSelectPanel;
    private JLabel p1CharInputLabel;
    private JTextField p1CharTextField;

    private JLabel sameCharWarningLabel;

    private JPanel p2CharSelectPanel;
    private JLabel p2CharInputLabel;
    private JTextField p2CharTextField;

    private JPanel p2NameSelectPanel;
    private JLabel p2NameInputLabel;
    private JTextField p2NameTextField;

    private JPanel buttonGridPanel;
    private JButton playButton;

    // ===== Game
    private JPanel gamePanel;

    // top round counter bar
    private JPanel roundPointsPanelContainer;
    private JPanel roundPointsPanel;
    private JLabel roundPointsPanelP1Indicator;
    private JLabel roundPointsPanelP2Indicator;
    private JLabel winIndicatorLabel;

    private JLabel p1Point1Label;
    private JLabel p1Point2Label;
    private JLabel p1Point3Label;

    private JLabel p2Point1Label;
    private JLabel p2Point2Label;
    private JLabel p2Point3Label;

    // player info (left), board (center) and game info (right)
    private JPanel gameContentPanel;
    private JPanel gamePlayerInfoPanel;

    private JPanel p1InfoPanel;
    private JPanel p1TurnIndicatorContainer;
    private JLabel p1TurnIndicator;
    private JPanel p1InfoLabelPanel;
    private JLabel p1NameLabel;
    private JLabel p1ScoreLabel;

    private JPanel p2InfoPanel;
    private JPanel p2TurnIndicatorContainer;
    private JLabel p2TurnIndicator;
    private JPanel p2InfoLabelPanel;
    private JLabel p2NameLabel;
    private JLabel p2ScoreLabel;

    private JButton exitButton;

    // Game Board
    private JPanel boardContainer;
    private JPanel board;
    private JButton boardButton1;
    private JButton boardButton2;
    private JButton boardButton3;
    private JButton boardButton4;
    private JButton boardButton5;
    private JButton boardButton6;
    private JButton boardButton7;
    private JButton boardButton8;
    private JButton boardButton9;

    // right container with overall info panel
    private JPanel gameInfoPanel;
    private JPanel gameInfoLabelPanel;
    private JLabel sessionTime;
    private JLabel gamesPlayed;
    private JLabel roundsPlayed;
    private JLabel turnsPlayed;
    private JLabel p1RoundsWon;
    private JLabel p2RoundsWon;

    private JButton continueButton;

    // ===== Estado da tela
    private final ArrayList<JButton> boardButtonList = new ArrayList<>();
    private final ArrayList<JLabel> playerPointLabels = new ArrayList<>();

    public ViewForm() {
        this(800, 480);
    }

    public ViewForm(int windowWidth, int windowHeight) {
        $$$setupUI$$$();
        registerComponents();
        if (!GraphicsEnvironment.isHeadless()) {
            buildFrame(windowWidth, windowHeight);
        }
    }

    private void registerComponents() {
        boardButtonList.add(boardButton1);
        boardButtonList.add(boardButton2);
        boardButtonList.add(boardButton3);
        boardButtonList.add(boardButton4);
        boardButtonList.add(boardButton5);
        boardButtonList.add(boardButton6);
        boardButtonList.add(boardButton7);
        boardButtonList.add(boardButton8);
        boardButtonList.add(boardButton9);

        playerPointLabels.add(p1Point1Label);
        playerPointLabels.add(p1Point2Label);
        playerPointLabels.add(p1Point3Label);
        playerPointLabels.add(p2Point1Label);
        playerPointLabels.add(p2Point2Label);
        playerPointLabels.add(p2Point3Label);

        for (JLabel point : playerPointLabels) {
            point.setIcon(POINT_OFF);
            point.setPreferredSize(new Dimension(POINT_SIZE, POINT_SIZE));
        }

        p1TurnIndicator.setIcon(TURN_OFF);
        p2TurnIndicator.setIcon(TURN_OFF);
        p1TurnIndicator.setPreferredSize(new Dimension(TURN_SIZE, TURN_SIZE));
        p2TurnIndicator.setPreferredSize(new Dimension(TURN_SIZE, TURN_SIZE));

        exitButton.setPreferredSize(new Dimension(125, 40));
        continueButton.setPreferredSize(new Dimension(125, 40));
        continueButton.setVisible(false);

        sameCharWarningLabel.setForeground(Color.RED);

        // Tabuleiro sempre quadrado, como no View.java
        board.setPreferredSize(new Dimension(330, 330));
        boardContainer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                keepBoardSquare();
            }
        });
    }

    private void keepBoardSquare() {
        int size = Math.min(boardContainer.getWidth(), boardContainer.getHeight());

        if (size > 0 && board.getPreferredSize().width != size) {
            board.setPreferredSize(new Dimension(size, size));
            boardContainer.revalidate();
        }
    }

    /**
     * Desenha o circulo de ponto/vez: verde quando ativo, cinza quando inativo.
     * (Era o paint() dos Canvas anonimos do View.java.)
     */
    private static ImageIcon circleIcon(int size, boolean active) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(active ? INNER_GREEN : LIGHT_GRAY);
        g2.fillOval(0, 0, size - 1, size - 1);

        g2.setColor(active ? OUTER_GREEN : GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(1, 1, size - 2, size - 2);

        g2.dispose();
        return new ImageIcon(image);
    }

    private void buildFrame(int windowWidth, int windowHeight) {
        frame = new JFrame("Jogo da Senhora de Idade");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        changeScreen("MENU");
    }

    public void changeScreen(String s) {
        CardLayout cardLayout = (CardLayout) root.getLayout();

        switch (s) {
            case "MENU":
                cardLayout.show(root, "MENU");
                break;
            case "GAME":
                cardLayout.show(root, "GAME");
                break;
            default:
                break;
        }
    }

    public void setP1TurnIndicator(boolean b) {
        p1TurnIndicator.setIcon(b ? TURN_ON : TURN_OFF);
    }

    public void setP2TurnIndicator(boolean b) {
        p2TurnIndicator.setIcon(b ? TURN_ON : TURN_OFF);
    }

    public void setPlayerPointActive(JLabel label, boolean b) {
        label.setIcon(b ? POINT_ON : POINT_OFF);
    }

    public void setContinueButtonVisible(boolean visible) {
        continueButton.setVisible(visible);
        continueButton.getParent().revalidate();
        continueButton.getParent().repaint();
    }

    public void validatePointsPanel() {
        if (winIndicatorLabel.getParent() != null) {
            winIndicatorLabel.getParent().revalidate();
            winIndicatorLabel.getParent().repaint();
        }
        if (roundPointsPanel.getParent() != null) {
            roundPointsPanel.getParent().revalidate();
            roundPointsPanel.getParent().repaint();
        }
    }

    // ===== Getters (substituem o @Getter do View.java)

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getRoot() {
        return root;
    }

    public ArrayList<JButton> getBoardButtonList() {
        return boardButtonList;
    }

    public ArrayList<JLabel> getPlayerPointLabels() {
        return playerPointLabels;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public JTextField getP1NameTextField() {
        return p1NameTextField;
    }

    public JTextField getP2NameTextField() {
        return p2NameTextField;
    }

    public JTextField getP1CharTextField() {
        return p1CharTextField;
    }

    public JTextField getP2CharTextField() {
        return p2CharTextField;
    }

    public JLabel getSameCharWarningLabel() {
        return sameCharWarningLabel;
    }

    public JLabel getP1NameLabel() {
        return p1NameLabel;
    }

    public JLabel getP2NameLabel() {
        return p2NameLabel;
    }

    public JLabel getP1ScoreLabel() {
        return p1ScoreLabel;
    }

    public JLabel getP2ScoreLabel() {
        return p2ScoreLabel;
    }

    public JLabel getWinIndicatorLabel() {
        return winIndicatorLabel;
    }

    public JLabel getRoundPointsPanelP1Indicator() {
        return roundPointsPanelP1Indicator;
    }

    public JLabel getRoundPointsPanelP2Indicator() {
        return roundPointsPanelP2Indicator;
    }

    public JLabel getSessionTime() {
        return sessionTime;
    }

    public JLabel getGamesPlayed() {
        return gamesPlayed;
    }

    public JLabel getRoundsPlayed() {
        return roundsPlayed;
    }

    public JLabel getTurnsPlayed() {
        return turnsPlayed;
    }

    public JLabel getP1RoundsWon() {
        return p1RoundsWon;
    }

    public JLabel getP2RoundsWon() {
        return p2RoundsWon;
    }

    // ===== Helpers usados pelo codigo gerado

    private static GridConstraints cell(int row, int column, int anchor, int fill, int hSizePolicy, int vSizePolicy) {
        return new GridConstraints(row, column, 1, 1, anchor, fill,
                hSizePolicy, vSizePolicy, null, null, null, 0, false);
    }

    private static GridConstraints cell(int row, int column, int anchor, int fill, int hSizePolicy, int vSizePolicy, Dimension preferredSize) {
        return new GridConstraints(row, column, 1, 1, anchor, fill,
                hSizePolicy, vSizePolicy, null, preferredSize, null, 0, false);
    }

    private static final int FIXED = GridConstraints.SIZEPOLICY_FIXED;
    private static final int GROW = GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW;
    private static final int WIDE = GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW;

    private static Insets insets(int top, int left, int bottom, int right) {
        return new Insets(top, left, bottom, right);
    }

    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new CardLayout(0, 0));

        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout(0, 0));
        root.add(menuPanel, "MENU");

        menuTitleLabel = new JLabel();
        menuTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuTitleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        menuTitleLabel.setText("Velha");
        menuPanel.add(menuTitleLabel, BorderLayout.CENTER);

        bottomMenu = new JPanel();
        bottomMenu.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        menuPanel.add(bottomMenu, BorderLayout.SOUTH);

        menuPlayerInfoPanel = new JPanel();
        menuPlayerInfoPanel.setLayout(new GridLayoutManager(1, 5, insets(0, 0, 0, 0), 0, 0, true, false));
        bottomMenu.add(menuPlayerInfoPanel, cell(0, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        p1NameSelectPanel = new JPanel();
        p1NameSelectPanel.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        menuPlayerInfoPanel.add(p1NameSelectPanel, cell(0, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        p1NameInputLabel = new JLabel();
        p1NameInputLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p1NameInputLabel.setText("Name:");
        p1NameSelectPanel.add(p1NameInputLabel, cell(0, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, FIXED, GROW));

        p1NameTextField = new JTextField();
        p1NameSelectPanel.add(p1NameTextField, cell(1, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, WIDE, GROW, new Dimension(150, -1)));

        p1CharSelectPanel = new JPanel();
        p1CharSelectPanel.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        menuPlayerInfoPanel.add(p1CharSelectPanel, cell(0, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        p1CharInputLabel = new JLabel();
        p1CharInputLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p1CharInputLabel.setText("P1 character:");
        p1CharSelectPanel.add(p1CharInputLabel, cell(0, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, FIXED, GROW));

        p1CharTextField = new JTextField();
        p1CharTextField.setText("X");
        p1CharSelectPanel.add(p1CharTextField, cell(1, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, WIDE, GROW, new Dimension(150, -1)));

        sameCharWarningLabel = new JLabel();
        sameCharWarningLabel.setForeground(Color.RED);
        sameCharWarningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sameCharWarningLabel.setText("");
        menuPlayerInfoPanel.add(sameCharWarningLabel, cell(0, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GROW, GROW));

        p2CharSelectPanel = new JPanel();
        p2CharSelectPanel.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        menuPlayerInfoPanel.add(p2CharSelectPanel, cell(0, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        p2CharInputLabel = new JLabel();
        p2CharInputLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2CharInputLabel.setText("P2 character:");
        p2CharSelectPanel.add(p2CharInputLabel, cell(0, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, FIXED, GROW));

        p2CharTextField = new JTextField();
        p2CharTextField.setText("O");
        p2CharSelectPanel.add(p2CharTextField, cell(1, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, WIDE, GROW, new Dimension(150, -1)));

        p2NameSelectPanel = new JPanel();
        p2NameSelectPanel.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        menuPlayerInfoPanel.add(p2NameSelectPanel, cell(0, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        p2NameInputLabel = new JLabel();
        p2NameInputLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2NameInputLabel.setText("Name:");
        p2NameSelectPanel.add(p2NameInputLabel, cell(0, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, FIXED, GROW));

        p2NameTextField = new JTextField();
        p2NameSelectPanel.add(p2NameTextField, cell(1, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, WIDE, GROW, new Dimension(150, -1)));

        buttonGridPanel = new JPanel();
        buttonGridPanel.setLayout(new GridLayoutManager(1, 5, insets(0, 0, 0, 0), 0, 0, true, false));
        bottomMenu.add(buttonGridPanel, cell(1, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        buttonGridPanel.add(new Spacer(), cell(0, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GROW, GROW));
        buttonGridPanel.add(new Spacer(), cell(0, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GROW, GROW));

        playButton = new JButton();
        playButton.setText("Play");
        buttonGridPanel.add(playButton, cell(0, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        buttonGridPanel.add(new Spacer(), cell(0, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GROW, GROW));
        buttonGridPanel.add(new Spacer(), cell(0, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GROW, GROW));

        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(0, 0));
        root.add(gamePanel, "GAME");

        roundPointsPanelContainer = new JPanel();
        roundPointsPanelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gamePanel.add(roundPointsPanelContainer, BorderLayout.NORTH);

        roundPointsPanel = new JPanel();
        roundPointsPanel.setLayout(new GridLayoutManager(1, 9, insets(0, 0, 0, 0), 0, 0, true, false));
        roundPointsPanelContainer.add(roundPointsPanel);

        roundPointsPanelP1Indicator = new JLabel();
        roundPointsPanelP1Indicator.setFont(new Font("Lexend", Font.BOLD, 14));
        roundPointsPanelP1Indicator.setHorizontalAlignment(SwingConstants.LEFT);
        roundPointsPanelP1Indicator.setText("X");
        roundPointsPanel.add(roundPointsPanelP1Indicator, cell(0, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED));

        p1Point1Label = new JLabel();
        roundPointsPanel.add(p1Point1Label, cell(0, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED, new Dimension(POINT_SIZE, POINT_SIZE)));

        p1Point2Label = new JLabel();
        roundPointsPanel.add(p1Point2Label, cell(0, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED, new Dimension(POINT_SIZE, POINT_SIZE)));

        p1Point3Label = new JLabel();
        roundPointsPanel.add(p1Point3Label, cell(0, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED, new Dimension(POINT_SIZE, POINT_SIZE)));

        winIndicatorLabel = new JLabel();
        winIndicatorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winIndicatorLabel.setText(" - ");
        roundPointsPanel.add(winIndicatorLabel, cell(0, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED));

        p2Point3Label = new JLabel();
        roundPointsPanel.add(p2Point3Label, cell(0, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED, new Dimension(POINT_SIZE, POINT_SIZE)));

        p2Point2Label = new JLabel();
        roundPointsPanel.add(p2Point2Label, cell(0, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED, new Dimension(POINT_SIZE, POINT_SIZE)));

        p2Point1Label = new JLabel();
        roundPointsPanel.add(p2Point1Label, cell(0, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED, new Dimension(POINT_SIZE, POINT_SIZE)));

        roundPointsPanelP2Indicator = new JLabel();
        roundPointsPanelP2Indicator.setFont(new Font("Lexend", Font.BOLD, 14));
        roundPointsPanelP2Indicator.setHorizontalAlignment(SwingConstants.RIGHT);
        roundPointsPanelP2Indicator.setText("O");
        roundPointsPanel.add(roundPointsPanelP2Indicator, cell(0, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GROW, FIXED));

        gameContentPanel = new JPanel();
        gameContentPanel.setLayout(new BorderLayout(0, 0));
        gamePanel.add(gameContentPanel, BorderLayout.CENTER);

        JLabel gamePaddingLabel = new JLabel();
        gamePaddingLabel.setText("");
        gamePanel.add(gamePaddingLabel, BorderLayout.SOUTH);

        gamePlayerInfoPanel = new JPanel();
        gamePlayerInfoPanel.setLayout(new GridBagLayout());
        gameContentPanel.add(gamePlayerInfoPanel, BorderLayout.WEST);

        p1InfoPanel = new JPanel();
        p1InfoPanel.setLayout(new BorderLayout(0, 0));
        gamePlayerInfoPanel.add(p1InfoPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets(2, 5, 2, 5), 0, 0));

        p1TurnIndicatorContainer = new JPanel();
        p1TurnIndicatorContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        p1InfoPanel.add(p1TurnIndicatorContainer, BorderLayout.WEST);

        p1TurnIndicator = new JLabel();
        p1TurnIndicatorContainer.add(p1TurnIndicator);

        p1InfoLabelPanel = new JPanel();
        p1InfoLabelPanel.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        p1InfoPanel.add(p1InfoLabelPanel, BorderLayout.CENTER);

        p1NameLabel = new JLabel();
        p1NameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p1NameLabel.setText("P1 - null");
        p1InfoLabelPanel.add(p1NameLabel, cell(0, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GROW, FIXED));

        p1ScoreLabel = new JLabel();
        p1ScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p1ScoreLabel.setText("Score: 0");
        p1InfoLabelPanel.add(p1ScoreLabel, cell(1, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GROW, FIXED));

        p2InfoPanel = new JPanel();
        p2InfoPanel.setLayout(new BorderLayout(0, 0));
        gamePlayerInfoPanel.add(p2InfoPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets(2, 5, 2, 5), 0, 0));

        p2TurnIndicatorContainer = new JPanel();
        p2TurnIndicatorContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        p2InfoPanel.add(p2TurnIndicatorContainer, BorderLayout.WEST);

        p2TurnIndicator = new JLabel();
        p2TurnIndicatorContainer.add(p2TurnIndicator);

        p2InfoLabelPanel = new JPanel();
        p2InfoLabelPanel.setLayout(new GridLayoutManager(2, 1, insets(0, 0, 0, 0), 0, 0, false, true));
        p2InfoPanel.add(p2InfoLabelPanel, BorderLayout.CENTER);

        p2NameLabel = new JLabel();
        p2NameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2NameLabel.setText("P2 - null");
        p2InfoLabelPanel.add(p2NameLabel, cell(0, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GROW, FIXED));

        p2ScoreLabel = new JLabel();
        p2ScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        p2ScoreLabel.setText("Score: 0");
        p2InfoLabelPanel.add(p2ScoreLabel, cell(1, 0, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GROW, FIXED));

        // Empurra o botao Exit para o fim do painel
        JPanel gamePlayerInfoSpacer = new JPanel();
        gamePlayerInfoSpacer.setLayout(new BorderLayout(0, 0));
        gamePlayerInfoPanel.add(gamePlayerInfoSpacer, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, insets(2, 5, 2, 5), 0, 0));

        exitButton = new JButton();
        exitButton.setText("Exit");
        gamePlayerInfoPanel.add(exitButton, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets(2, 5, 2, 5), 0, 0));

        boardContainer = new JPanel();
        boardContainer.setLayout(new GridBagLayout());
        gameContentPanel.add(boardContainer, BorderLayout.CENTER);

        board = new JPanel();
        board.setLayout(new GridLayoutManager(3, 3, insets(0, 0, 0, 0), 0, 0, true, true));
        boardContainer.add(board, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets(0, 0, 0, 0), 0, 0));

        Font boardFont = new Font("Lexend", Font.BOLD, 22);
        JButton[] boardButtons = {boardButton1 = new JButton(), boardButton2 = new JButton(),
                boardButton3 = new JButton(), boardButton4 = new JButton(), boardButton5 = new JButton(),
                boardButton6 = new JButton(), boardButton7 = new JButton(), boardButton8 = new JButton(),
                boardButton9 = new JButton()};

        for (JButton b : boardButtons) {
            b.setText("");
            b.setFont(boardFont);
        }

        board.add(boardButton1, cell(0, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton2, cell(0, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton3, cell(0, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton4, cell(1, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton5, cell(1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton6, cell(1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton7, cell(2, 0, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton8, cell(2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));
        board.add(boardButton9, cell(2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GROW, GROW));

        // ----- Right: overall info + continue
        gameInfoPanel = new JPanel();
        gameInfoPanel.setLayout(new GridBagLayout());
        gameContentPanel.add(gameInfoPanel, BorderLayout.EAST);

        gameInfoLabelPanel = new JPanel();
        gameInfoLabelPanel.setLayout(new GridBagLayout());
        gameInfoPanel.add(gameInfoLabelPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 2.0, GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, insets(2, 5, 2, 5), 0, 0));

        sessionTime = new JLabel();
        sessionTime.setHorizontalAlignment(SwingConstants.RIGHT);
        sessionTime.setText("Session Time: ");
        gameInfoLabelPanel.add(sessionTime, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets(2, 5, 2, 5), 0, 0));

        gamesPlayed = new JLabel();
        gamesPlayed.setHorizontalAlignment(SwingConstants.RIGHT);
        gamesPlayed.setText("Games Played: ");
        gameInfoLabelPanel.add(gamesPlayed, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets(2, 5, 2, 5), 0, 0));

        roundsPlayed = new JLabel();
        roundsPlayed.setHorizontalAlignment(SwingConstants.RIGHT);
        roundsPlayed.setText("Rounds Played: ");
        gameInfoLabelPanel.add(roundsPlayed, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets(2, 5, 2, 5), 0, 0));

        turnsPlayed = new JLabel();
        turnsPlayed.setHorizontalAlignment(SwingConstants.RIGHT);
        turnsPlayed.setText("Turns Played: ");
        gameInfoLabelPanel.add(turnsPlayed, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets(2, 5, 2, 5), 0, 0));

        p1RoundsWon = new JLabel();
        p1RoundsWon.setHorizontalAlignment(SwingConstants.RIGHT);
        p1RoundsWon.setText("P1 Rounds Won: ");
        gameInfoLabelPanel.add(p1RoundsWon, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets(2, 5, 2, 5), 0, 0));

        p2RoundsWon = new JLabel();
        p2RoundsWon.setHorizontalAlignment(SwingConstants.RIGHT);
        p2RoundsWon.setText("P2 Rounds Won: ");
        gameInfoLabelPanel.add(p2RoundsWon, new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, insets(2, 5, 2, 5), 0, 0));

        JPanel gameInfoSpacer = new JPanel();
        gameInfoSpacer.setLayout(new BorderLayout(0, 0));
        gameInfoLabelPanel.add(gameInfoSpacer, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, insets(2, 5, 2, 5), 0, 0));

        continueButton = new JButton();
        continueButton.setText("Continue");
        gameInfoPanel.add(continueButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets(2, 5, 2, 5), 0, 0));
    }


    public JComponent $$$getRootComponent$$$() {
        return root;
    }
}
