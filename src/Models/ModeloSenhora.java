package Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

@Getter
@Setter
public class ModeloSenhora {
    private List<String> board = new ArrayList<>(List.of("", "", "", "", "", "", "", "", ""));
    private boolean isP1Turn;
    private int p1Points, p2Points, p1Score, p2Score,
            gamesPlayed, roundsPlayed, turnsPlayed, p1RoundsWon, p2RoundsWon;
    private String p1Char, p2Char;

    public ModeloSenhora(){isP1Turn = getRandomBoolean(); }

    /*int[] seconds = {0};
    Timer timer = new Timer(1000, e -> {
        seconds[0]++;
        int h = seconds[0] / 3600;
        int m = (seconds[0] % 3600) / 60;
        int s = seconds[0] % 60;
        String.format("%02d:%02d:%02d", h, m, s);
    });*/

    public void play(int x) {
        x -= 1;
        if (!board.get(x).equals("")){return;}
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
        if (board.get(0) == board.get(1) && board.get(1) == board.get(2)) {return board.get(0);}
        if (board.get(3) == board.get(4) && board.get(4) == board.get(5)) {return board.get(3);}
        if (board.get(6) == board.get(7) && board.get(7) == board.get(8)) {return board.get(6);}

        if (board.get(0) == board.get(3) && board.get(3) == board.get(6)) {return board.get(0);}
        if (board.get(1) == board.get(4) && board.get(4) == board.get(7)) {return board.get(1);}
        if (board.get(2) == board.get(5) && board.get(5) == board.get(8)) {return board.get(2);}

        if (board.get(0) == board.get(4) && board.get(4) == board.get(8)) {return board.get(0);}
        if (board.get(2) == board.get(4) && board.get(4) == board.get(6)) {return board.get(2);}

        return "";
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
        clearBoard();
        isP1Turn = getRandomBoolean();
    }

    private void clearBoard(){
        board = new ArrayList<>(List.of("", "", "", "", "", "", "", "", ""));
    }

    private static boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
