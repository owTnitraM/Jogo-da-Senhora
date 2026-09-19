package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HostCreate {

    private boolean ready = false;
    private final Room room;
    private final int port;

    private volatile boolean starting;
    private ServerSocket serverSocket;

    public HostCreate(String password, String roomName, int mode, int port) {
        this.room = new Room(password, roomName, mode);
        this.port = port;
        this.starting = false;
    }

    public void start() throws IOException {
        starting = true;

        System.out.println("Tentando abrir servidor na porta " + port);

        serverSocket = new ServerSocket(port);

        System.out.println("Servidor aberto na porta " + port);

        try {
            while (starting) {

                System.out.println("Esperando jogador...");

                ready = true;

                Socket playerSocket = serverSocket.accept();

                System.out.println("Jogador conectado!");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(playerSocket.getInputStream())
                );

                String message = in.readLine();

                System.out.println("Recebido: " + message);

                if (message == null) {
                    playerSocket.close();
                    continue;
                }

                String[] mParts = message.split(";", -1);

                String nick = mParts[0];

                boolean added = room.addPlayer(nick, playerSocket);

                if (added) {
                    System.out.println("Player entrou: " + nick);
                } else {
                    playerSocket.close();
                }
            }
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }

    public void stop() {
        starting = false;

        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isReady() {return ready;}

    public Room getRoom() {
        return room;
    }
}