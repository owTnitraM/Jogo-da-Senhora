package Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectHost {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void connect(String ip, int port, String nick) throws Exception {

        socket = new Socket(ip, port);

        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        out = new PrintWriter(
                socket.getOutputStream(),
                true
        );

        // primeira mensagem = nick
        out.println(nick);

        System.out.println("Conectado ao servidor!");

        listenServer();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void listenServer() {

        Thread thread = new Thread(() -> {

            try {

                String message;

                while ((message = in.readLine()) != null) {
                    System.out.println("Servidor: " + message);
                }

            } catch (Exception e) {
                System.out.println("Conexão encerrada.");
            }

        });

        thread.start();
    }

    public void disconnect() throws Exception {
        socket.close();
    }
}
