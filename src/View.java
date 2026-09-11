import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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

    ArrayList<Label> roomScoreLabelList = new ArrayList<Label>();
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
        roomScoreInfoPanel.setLayout(new GridLayout(15, 1));


        roomScoreLabelList.add(new Label("P1 - Jao"));
        roomScoreLabelList.add(new Label("Points: 0"));
        roomScoreLabelList.add(new Label("P2 - Sergio"));
        roomScoreLabelList.add(new Label("Points: 0"));

        for (int i = 0; i < roomScoreLabelList.toArray().length; i++){
        roomScoreInfoPanel.add(roomScoreLabelList.get(i));
        }

        p1InfosPanel.add(p1GameScoreLabel);
        p1InfosPanel.add(p1NameLabel);
        p1InfosPanel.add(isP1TurnIndicatorCanvas);

        p2InfosPanel.add(p2GameScoreLabel);
        p2InfosPanel.add(p2NameLabel);
        p2InfosPanel.add(isP2TurnIndicatorCanvas);

        int gridLen = 3;

        boardPanel.setLayout(new GridLayout(gridLen, gridLen));

        for (int i= 0; i < (gridLen * gridLen); i++){
            Button btn = new Button("X");
            btn.setFont(new Font("Arial", Font.BOLD, 50));
            boardPanel.add(btn);
        }

        gameBoardPanel.setLayout(new BorderLayout());
        gameBoardPanel.add(p1InfosPanel, BorderLayout.NORTH);
        gameBoardPanel.add(boardPanel, BorderLayout.CENTER);
        gameBoardPanel.add(p2InfosPanel, BorderLayout.SOUTH);

        roomInfosPanel.add(roomInfosLabel, BorderLayout.NORTH);

        frame.add(roomScoreInfoPanel, BorderLayout.WEST);
        frame.add(gameBoardPanel, BorderLayout.CENTER);
        frame.add(roomInfosPanel, BorderLayout.EAST);
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
