package Models;

import lombok.*;

import java.net.Socket;

@Getter
@Setter
public class Player {
    private final int id;
    private String nick;
    private int score;
    private Socket playerSocket = null;

    public Player(String nick,  int id, Socket playerSocket) {
        this.id = id;
        this.nick = nick;
        this.playerSocket = playerSocket;
    }
}


