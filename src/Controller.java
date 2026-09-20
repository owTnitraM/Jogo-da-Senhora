import Models.ModeloSenhora;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class Controller implements ActionListener {
    private final ModeloSenhora modeloSenhora;
    private final View view;

    public Controller() {
        this.modeloSenhora = new ModeloSenhora();
        this.view = new View();
        setActionEvents();
        view.changeScreen("MENU");
    }

    public Controller(ModeloSenhora modeloSenhora, @NonNull View view) {
        this.modeloSenhora = modeloSenhora;
        this.view = view;
        setActionEvents();
        view.changeScreen("MENU");
    }

    private void setActionEvents() {
        view.getPlayButton().addActionListener(e -> handlePlayButton());
        view.getExitButton().addActionListener(e -> handleExitButton());

        for (int i = 0; i < view.getBoardButtonList().toArray().length; i++) {
            int finalI = i;
            view.getBoardButtonList().get(i).addActionListener(e -> handleBoardPress(finalI));
        }

        /*view.setChangeModeButtonActionListener(e -> onModeChange());
        view.setExecuteActionButtonActionListener(e -> onExecuteAction());*/
    }

    private void handleExitButton() {
        changeScreen("MENU");
        modeloSenhora.reset();
    }

    private void handlePlayButton() {
        if ((view.getP1CharTextField().getText().equals("")) || (view.getP2CharTextField().getText().equals(""))) {
            view.getSameCharWarningLabel().setText("Char cannot be empty.");
            return;
        }
        if (view.getP1CharTextField().getText().equals(view.getP2CharTextField().getText())) {
            view.getSameCharWarningLabel().setText("Char cannot be the same.");
            return;
        }
        if ((view.getP1CharTextField().getText().length() > 3 || (view.getP2CharTextField().getText().length() > 3))) {
            view.getSameCharWarningLabel().setText("Char max length is 3.");
            return;
        }
        if ((view.getP1NameTextField().getText().length() > 12 || (view.getP2NameTextField().getText().length() > 12))) {
            view.getSameCharWarningLabel().setText("Name max length is 12.");
            return;
        }

        view.getP1NameLabel().setText("P1: " + view.getP1NameTextField().getText());
        view.getP2NameLabel().setText("P2: " + view.getP2NameTextField().getText());

        modeloSenhora.setP1Char(view.getP1CharTextField().getText());
        modeloSenhora.setP2Char(view.getP2CharTextField().getText());

        String w = modeloSenhora.checkWin();
        if (!w.equals("")){

        };

        updateStats();

        changeScreen("GAME");
    }

    private void updateStats() {
        view.getSessionTime().setText("Session Time: " + "00:00");
        view.getRoundTime().setText("Round Time: " + "00:00");
        view.getTurnTime().setText("Turn Time: " + "00:00");
        view.getGamesPlayed().setText("Games Played: " + modeloSenhora.getGamesPlayed());
        view.getRoundsPlayed().setText("Rounds Played: " + modeloSenhora.getRoundsPlayed());
        view.getTurnsPlayed().setText("Turns Played: " + modeloSenhora.getTurnsPlayed());
        view.getP1RoundsWon().setText("P1 Rounds Won: " + modeloSenhora.getP1RoundsWon());
        view.getP2RoundsWon().setText("P2 Rounds Won: " + modeloSenhora.getP2RoundsWon());
    }

    private void handleBoardPress(int btn) {
        if (view.getBoardButtonList().get(btn).getLabel().equals("")) {
            view.getBoardButtonList().get(btn).setLabel(
                    modeloSenhora.isP1Turn() ? modeloSenhora.getP1Char() : modeloSenhora.getP2Char());
            modeloSenhora.play(btn + 1);
            updateStats();
        }
    }

    public void handleScreenChange(String screen) {

        switch (screen) {
            case "MENU":
                view.changeScreen(screen);
                break;

            case "GAME":
                view.changeScreen(screen);
                break;
        }
    }

    private void changeScreen(String s) {
        System.out.println("CHANGE SCREEN controller call: " + s);
        view.changeScreen(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
