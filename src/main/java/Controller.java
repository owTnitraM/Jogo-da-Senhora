import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class Controller implements ActionListener {
    private final ModeloSenhora modeloSenhora;
    private final ViewForm view;
    private final Timer timer = new Timer();
    private final int sleepTime = 1;
    private int sessionSeconds = 0;

    private javax.swing.Timer pauseTimer;
    private boolean roundPaused = false;

    public Controller() {
        this.modeloSenhora = new ModeloSenhora();
        this.view = new ViewForm();
        setTimer();
        setActionEvents();
        view.changeScreen("MENU");
    }

    public Controller(int width, int height) {
        this.modeloSenhora = new ModeloSenhora();
        this.view = new ViewForm(width, height);
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
            view.getBoardButtonList().get(i).setText("");
        }
        resetTopBar();
    }

    private void resetTopBar() {
        stopPauseTimer();
        roundPaused = false;

        for (int i = 0; i < 6; i++) {
            view.setPlayerPointActive(view.getPlayerPointLabels().get(i), false);
        }

        view.setContinueButtonVisible(false);
        modeloSenhora.setWinState(false);
        view.getWinIndicatorLabel().setText(" - ");
        view.validatePointsPanel();

        syncTurnIndicators();
    }

    private void syncTurnIndicators() {
        view.setP1TurnIndicator(modeloSenhora.isP1Turn());
        view.setP2TurnIndicator(!modeloSenhora.isP1Turn());
    }

    private void afterPause(int seconds, Runnable action) {
        stopPauseTimer();

        pauseTimer = new javax.swing.Timer(seconds * 1000, e -> {
            pauseTimer = null;
            action.run();
        });
        pauseTimer.setRepeats(false);
        pauseTimer.start();
    }

    private void stopPauseTimer() {
        if (pauseTimer != null) {
            pauseTimer.stop();
            pauseTimer = null;
        }
    }

    private void handlePlayButton() {
        String p1c = view.getP1CharTextField().getText().trim();
        String p2c = view.getP2CharTextField().getText().trim();
        if ((p1c.equals("")) || (p2c.equals(""))) {
            view.getSameCharWarningLabel().setText("Char cannot be empty.");
            return;
        }
        if (p1c.equals(p2c)) {
            view.getSameCharWarningLabel().setText("Char cannot be the same.");
            return;
        }
        if ((p1c.length() > 3 || (p2c.length() > 3))) {
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

        syncTurnIndicators();

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
        if (roundPaused) return;
        if (modeloSenhora.isWinState()) return;
        if (view.getBoardButtonList().get(btn).getText().equals("")) {
            view.getBoardButtonList().get(btn).setText(
                    modeloSenhora.isP1Turn() ? modeloSenhora.getP1Char() : modeloSenhora.getP2Char());
            modeloSenhora.play(btn + 1);
            syncTurnIndicators();

            String w = modeloSenhora.checkWin();
            if (!w.equals("")) {
                doRoundWin(w);
                return;
            }

            if (modeloSenhora.checkDraw()) {
                doDraw();
                return;
            }

            updateStats();
        }
    }

    private void doDraw() {
        // Mostra o empate e so depois limpa o tabuleiro (sem travar a tela)
        view.getWinIndicatorLabel().setText("Tie!");
        view.validatePointsPanel();
        updateStats();

        roundPaused = true;
        afterPause(sleepTime, () -> {
            view.getWinIndicatorLabel().setText(" - ");
            view.validatePointsPanel();

            modeloSenhora.doDraw();
            resetBoardButtons();
            syncTurnIndicators();
            updateStats();

            roundPaused = false;
        });
    }

    private void doRoundWin(String w) {
        modeloSenhora.doRoundWin(w);

        boolean p1 = w.equals(modeloSenhora.getP1Char());

        int points = p1
                ? modeloSenhora.getP1Points()
                : modeloSenhora.getP2Points();

        int offset = p1 ? 0 : 3;

        for (int i = 0; i < points; i++) {
            view.setPlayerPointActive(
                    view.getPlayerPointLabels().get(i + offset),
                    true
            );
        }

        view.getWinIndicatorLabel().setText(p1
                ? modeloSenhora.getP1Char() + " won"
                : modeloSenhora.getP2Char() + " won");
        view.validatePointsPanel();
        updateStats();

        roundPaused = true;
        afterPause(sleepTime, () -> {
            view.getWinIndicatorLabel().setText(" - ");
            view.validatePointsPanel();

            resetBoardButtons();
            roundPaused = false;

            updateStats();
            checkGameWin();
        });
    }

    private void resetBoardButtons() {
        for (int i = 0; i < 9; i++) {
            view.getBoardButtonList().get(i).setText("");
        }
    }

    private void checkGameWin() {
        String w = modeloSenhora.checkGameWin();
        if (w.equals("")) return;

        modeloSenhora.doGameWin(w);

        boolean p1 = w.equals(modeloSenhora.getP1Char());

        view.getWinIndicatorLabel().setText(p1
                ? modeloSenhora.getP1Char() + " won!"
                : modeloSenhora.getP2Char() + " won!");
        view.validatePointsPanel();
        view.setContinueButtonVisible(true);
        updateStats();

        afterPause(sleepTime + 1, () -> {
            view.getWinIndicatorLabel().setText(" - ");
            view.validatePointsPanel();
        });
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
