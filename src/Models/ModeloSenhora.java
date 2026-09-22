package Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class ModeloSenhora {
    private List<String> board = new ArrayList<>(List.of("", "", "", "", "", "", "", "", ""));
    private boolean isP1Turn, winState;
    private int p1Points, p2Points, p1Score, p2Score,
            gamesPlayed, roundsPlayed, turnsPlayed, p1RoundsWon, p2RoundsWon;
    private String p1Char, p2Char;

    public ModeloSenhora() {
        isP1Turn = getRandomBoolean();
    }

    public void play(int x) {
        x -= 1;
        if (!board.get(x).equals("")) {
            return;
        }
        if (isP1Turn) {
            board.set(x, p1Char);
            isP1Turn = !isP1Turn;
        } else {
            board.set(x, p2Char);
            isP1Turn = !isP1Turn;
        }
        turnsPlayed++;
    }

    public String checkWin() {
        if (board.get(0) == board.get(1) && board.get(1) == board.get(2)) {
            return board.get(0);
        }
        if (board.get(3) == board.get(4) && board.get(4) == board.get(5)) {
            return board.get(3);
        }
        if (board.get(6) == board.get(7) && board.get(7) == board.get(8)) {
            return board.get(6);
        }

        if (board.get(0) == board.get(3) && board.get(3) == board.get(6)) {
            return board.get(0);
        }
        if (board.get(1) == board.get(4) && board.get(4) == board.get(7)) {
            return board.get(1);
        }
        if (board.get(2) == board.get(5) && board.get(5) == board.get(8)) {
            return board.get(2);
        }

        if (board.get(0) == board.get(4) && board.get(4) == board.get(8)) {
            return board.get(0);
        }
        if (board.get(2) == board.get(4) && board.get(4) == board.get(6)) {
            return board.get(2);
        }

        return "";
    }

    public void doRoundWin(String w) {
        roundsPlayed++;
        if (w.equals(p1Char)) {
            p1Points++;
            p1RoundsWon++;
        } else {
            p2Points++;
            p2RoundsWon++;
        }
        clearBoard();
    }

    public String checkGameWin() {
        return p1Points >= 3 ? p1Char : p2Points >= 3 ? p2Char : "";
    }

    public void doGameWin(String w) {
        gamesPlayed++;
        if (w.equals(p1Char)) p1Score++;
        else p2Score++;
        winState = true;
    }

    public boolean checkDraw() {
        int emptyCells = 0;
        for (int i = 0; i < 9; i++) {
            if (board.get(i).equals("")) emptyCells++;
        }
        if ((emptyCells == 0) && (checkWin().equals(""))) return true;

        return false;
    }

    public void doDraw() {
        clearBoard();
        isP1Turn = !isP1Turn;
    }

    public void rematch() {
        p1Points = 0;
        p2Points = 0;
        clearBoard();
        isP1Turn = !isP1Turn;
    }

    public void reset() {
        p1Points = 0;
        p2Points = 0;
        p1Score = 0;
        p2Score = 0;
        gamesPlayed = 0;
        roundsPlayed = 0;
        turnsPlayed = 0;
        p1RoundsWon = 0;
        p2RoundsWon = 0;
        clearBoard();
        isP1Turn = getRandomBoolean();
    }

    private void clearBoard() {
        board = new ArrayList<>(List.of("", "", "", "", "", "", "", "", ""));
    }

    private static boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
