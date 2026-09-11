import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class View {
    Frame frame = new Frame("Jogo da Senhora de Idade");

    // Main
    Label startScreenLabel = new Label("Jogo da senhora", 1);

    Button singlePlayerButton = new Button("1 machine");
    Button enterButton = new Button("Enter room");
    Button hostButton = new Button("Host room");

    Panel startScreenButtonsPanel = new Panel();

    // Game

    Label roomScoresLabel = new Label("P1: 0\nP2: 0");
    Panel roomScoreInfoPanel = new Panel();

    Label p1GameScoreLabel = new Label("Score: 0");
    Label p1NameLabel = new Label("Name: ");
    Canvas isP1TurnIndicatorCanvas = new Canvas() /*{
        @Override
        public void paint(Graphics g) {
            // Escolhe a cor baseada na variável 'verde'
            if (true) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.WHITE);
            }
            // Desenha um círculo preenchido (x, y, largura, altura)
            g.fillOval(100, 80, 100, 100);
        }
    }*/;

    Panel p1InfosPanel = new Panel();


    Panel boardPanel = new Panel();

    Canvas isP2TurnIndicatorCanvas = new Canvas();
    Label p2NameLabel = new Label("Name: ");
    Label p2GameScoreLabel = new Label("Score: 0");

    Panel p2InfosPanel = new Panel();

    Panel gameBoardPanel = new Panel();

    Label roomInfosLabel = new Label("Host: \nP2: \nGames Played: ");
    Panel roomInfosPanel = new Panel();

    public View() {
        buildWindow(500, 500);
        setListeners();
    }

    private void buildWindow(int windowWidth, int windowHeight) {
        frame.setLayout(new BorderLayout());
        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);

        startScreenButtonsPanel.add(singlePlayerButton);
        startScreenButtonsPanel.add(enterButton);
        startScreenButtonsPanel.add(hostButton);

        /*frame.add(startScreenLabel, BorderLayout.CENTER);
        frame.add(startScreenButtonsPanel, BorderLayout.SOUTH);*/

        roomScoreInfoPanel.add(roomScoresLabel);

        gameBoardPanel.add(p1InfosPanel);
        gameBoardPanel.add(boardPanel);
        gameBoardPanel.add(p2InfosPanel);

        roomInfosPanel.add(roomInfosLabel);

        frame.add(roomInfosPanel);
        frame.add(gameBoardPanel);
        frame.add(roomScoreInfoPanel);
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
