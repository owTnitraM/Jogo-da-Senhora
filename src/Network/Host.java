package Network;

import Models.Player;

import java.util.ArrayList;

public class Host {
    private ArrayList<Player> players = new ArrayList<>();

    private Object getPlayerByNick(String nick) {
        for (Player p : players) {
            if (p.getNick().equals(nick)) {
                return p;
            }
        }
        return "Player not found";
    }

    private Object getPlayerById(int id) {
        for (Player p : players) {
            if (p.getId() ==  id) {
                return p;
            }
        }
        return "Player not found";
    }

    private boolean addPlayer(String nick) {
        int id = 1;

        if (!players.isEmpty()) {
            for (Player p : players) {
                if (p.getId() == id) {
                    id += 1;
                }
                else  {
                    if(p.getNick().equals(nick)) {
                        return false;
                    }
                    else {
                        players.add(new Player(nick, id));
                        return true;
                    }
                }
            }
        }
        else  {
            players.add(new Player(nick, id));
            return true;
        }
        return false;

    }
}
