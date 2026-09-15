package Network;

import Models.Player;

import java.net.Socket;
import java.util.ArrayList;

public class Room {
    private final ArrayList<Player> players = new ArrayList<>();
    private String password;
    private String roomName;
    private int mode;

    public Room(String password, String roomName, int mode) {
        this.roomName = roomName;
        this.password = password;
        this.mode = mode;
    }

    public boolean addPlayer(String nick , Socket playerSocket) {
        int id = 1;
        boolean added = false;
        if (!players.isEmpty()) {
            while (!added) {
                for (Player p : players) {
                    if (p.getId() == id) {
                        id += 1;
                    }
                    else  {
                        players.add(new Player(nick, id, playerSocket));
                        added = true;
                        return true;
                    }
                }
            }
        }
        else  {
            players.add(new Player(nick, id, playerSocket));
            return true;
        }
        return false;
    }

    public void kickPlayer(int id) {
        if (!players.isEmpty()) {
            for (Player p : players) {
                if (p.getId() == id) {
                    players.remove(p);
                }
            }
        }
    }

    public Player ByNick(String nick) {
        for (Player p : players) {
            if (p.getNick().equals(nick)) {
                return p;
            }
        }
        return null;
    }

    public Player ById(int id) {
        for (Player p : players) {
            if (p.getId() ==  id) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void clearRoom() {
        players.clear();
        password = null;
        roomName = null;
    }


}
