package Models;

import lombok.*;

@Getter
@Setter
public class Player {
    private String nick;
    private int score;

    public Player(String nick) {
        this.nick = nick;
    }
}


