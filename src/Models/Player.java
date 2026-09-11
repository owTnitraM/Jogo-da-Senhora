package Models;

import lombok.*;

@Getter
@Setter
public class Player {
    private final int id;
    private String nick;
    private int score;

    public Player(String nick,  int id) {
        this.id = id;
        this.nick = nick;
    }



}


