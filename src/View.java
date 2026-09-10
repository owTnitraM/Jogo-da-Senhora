import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View {
    Frame frame = new Frame("Jogo da Senhora de Idade");

    // Main
    Label startScreenLabel = new Label("Jogo da senhora", 1);

    Button singlePlayerButton = new Button("1 machine");
    Button enterButton = new Button("Enter room");
    Button hostButton = new Button("Host room");

    Panel startScreenButtonsPanel = new Panel();

    // Game

    Label scoresLabel = new Label("P1: 0\nP2: 0");
    Panel roomScoreInfoPanel = new Panel();

    Panel gameBoardPanel = new Panel();

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

        frame.add(startScreenLabel, BorderLayout.CENTER);
        frame.add(startScreenButtonsPanel, BorderLayout.SOUTH);

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
