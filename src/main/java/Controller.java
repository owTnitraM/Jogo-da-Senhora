import Models.ModeloSenhora;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class Controller implements ActionListener {
    private final ModeloSenhora modeloSenhora;
    private final View view;
    private final Timer timer = new Timer();
    private final int sleepTime = 1;
    private int sessionSeconds = 0;

    public Controller() {
        this.modeloSenhora = new ModeloSenhora();
        this.view = new View();
        setTimer();
        setActionEvents();
        view.changeScreen("MENU");
    }

    public Controller(int width, int height) {
        this.modeloSenhora = new ModeloSenhora();
        this.view = new View(width, height);
        setTimer();
        setActionEvents();
        view.changeScreen("MENU");
    }

    private void setTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                EventQueue.invokeLater(() -> {
                    sessionSeconds++;
                    view.getSessionTime().setText("Session Time: " + formatTime(sessionSeconds));
                });
            }
        }, 0, 1000);
    }

    private void setActionEvents() {
        view.getPlayButton().addActionListener(e -> handlePlayButton());
        view.getExitButton().addActionListener(e -> handleExitButton());
        view.getContinueButton().addActionListener(e -> handleContinueButton());

        for (int i = 0; i < view.getBoardButtonList().toArray().length; i++) {
            int finalI = i;
            view.getBoardButtonList().get(i).addActionListener(e -> handleBoardPress(finalI));
        }
    }

    private void handleExitButton() {
        changeScreen("MENU");
        modeloSenhora.reset();

        for (int i = 0; i < view.getBoardButtonList().toArray().length; i++) {
            view.getBoardButtonList().get(i).setLabel("");
        }
        resetTopBar();
    }

    private void resetTopBar() {
        for (int i = 0; i < 6; i++) {
            view.setPlayerPointCanvas(view.getPlayerPointCanvasList().get(i), false);
        }

        view.setContinueButtonVisible(false);
        modeloSenhora.setWinState(false);
        view.getWinIndicatorLabel().setText(" - ");
        view.validatePointsPanel();
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

        view.setP1TurnIndicator(modeloSenhora.isP1Turn() ? true : false);
        view.setP2TurnIndicator(!modeloSenhora.isP1Turn() ? true : false);

        view.getRoundPointsPanelP1Indicator().setText(modeloSenhora.getP1Char());
        view.getRoundPointsPanelP2Indicator().setText(modeloSenhora.getP2Char());

        updateStats();

        changeScreen("GAME");
    }

    private void handleContinueButton() {
        modeloSenhora.rematch();
        resetTopBar();
    }

    private void updateStats() {
        view.getGamesPlayed().setText("Games Played: " + modeloSenhora.getGamesPlayed());
        view.getRoundsPlayed().setText("Rounds Played: " + modeloSenhora.getRoundsPlayed());
        view.getTurnsPlayed().setText("Turns Played: " + modeloSenhora.getTurnsPlayed());
        view.getP1RoundsWon().setText("P1 Rounds Won: " + modeloSenhora.getP1RoundsWon());
        view.getP2RoundsWon().setText("P2 Rounds Won: " + modeloSenhora.getP2RoundsWon());
        view.getP1ScoreLabel().setText("Score: " + modeloSenhora.getP1Score());
        view.getP2ScoreLabel().setText("Score: " + modeloSenhora.getP2Score());
    }

    private void handleBoardPress(int btn) {
        if (modeloSenhora.isWinState()) return;
        if (view.getBoardButtonList().get(btn).getLabel().equals("")) {
            view.getBoardButtonList().get(btn).setLabel(
                    modeloSenhora.isP1Turn() ? modeloSenhora.getP1Char() : modeloSenhora.getP2Char());
            modeloSenhora.play(btn + 1);
            view.setP1TurnIndicator(modeloSenhora.isP1Turn() ? true : false);
            view.setP2TurnIndicator(!modeloSenhora.isP1Turn() ? true : false);

            String w = modeloSenhora.checkWin();
            if (!w.equals("")) {
                doRoundWin(w);
            }

            if (modeloSenhora.checkDraw()) {
                doDraw();
            }

            updateStats();
        }
    }

    private void doDraw() {
        view.getWinIndicatorLabel().setText("Tie!");
        sleepAndResetTopPanel();

        resetBoardButtons();
        modeloSenhora.doDraw();
        view.setP1TurnIndicator(modeloSenhora.isP1Turn() ? true : false);
        view.setP2TurnIndicator(!modeloSenhora.isP1Turn() ? true : false);
    }

    private void doRoundWin(String w) {
        modeloSenhora.doRoundWin(w);

        boolean p1 = w.equals(modeloSenhora.getP1Char());

        int points = p1
                ? modeloSenhora.getP1Points()
                : modeloSenhora.getP2Points();

        int offset = p1 ? 0 : 3;

        for (int i = 0; i < points; i++) {
            view.setPlayerPointCanvas(
                    view.getPlayerPointCanvasList().get(i + offset),
                    true
            );
        }

        view.getWinIndicatorLabel().setText(p1
                ? modeloSenhora.getP1Char() + " won"
                : modeloSenhora.getP2Char() + " won");

        sleepAndResetTopPanel();
        resetBoardButtons();
        updateStats();
        checkGameWin();
    }

    private void sleepAndResetTopPanel() {
        view.validatePointsPanel();
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        view.getWinIndicatorLabel().setText(" - ");
        view.validatePointsPanel();
    }

    private void resetBoardButtons() {
        for (int i = 0; i < 9; i++) {
            view.getBoardButtonList().get(i).setLabel("");
        }
    }

    private void checkGameWin() {
        String w = modeloSenhora.checkGameWin();
        if (w.equals("")) return;

        view.setContinueButtonVisible(true);

        modeloSenhora.doGameWin(w);

        boolean p1 = w.equals(modeloSenhora.getP1Char());

        view.getWinIndicatorLabel().setText(p1
                ? modeloSenhora.getP1Char() + " won!"
                : modeloSenhora.getP2Char() + " won!");
        view.validatePointsPanel();
        try {
            TimeUnit.SECONDS.sleep(sleepTime + 1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        view.getWinIndicatorLabel().setText(" - ");
        view.validatePointsPanel();
    }

    private void changeScreen(String s) {
        view.changeScreen(s);
    }

    private static String formatTime(int totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
