package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Conectado al servidor en " + ip + ":" + port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(new Receiver(in)).start();
    }

    public void enviar(String mensaje) {
        out.println(mensaje);
        out.flush();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    private class Receiver implements Runnable {
        private BufferedReader in;

        public Receiver(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            String mensaje;
            try {
                while ((mensaje = in.readLine()) != null) {
                    tratarMensaje(mensaje);
                }
            } catch (IOException e) {
                System.out.println("Conexión cerrada: " + e.getMessage());
            }
        }
    }

    private void tratarMensaje(String msg) {
        if (msg.startsWith("RES")) {
            System.out.println("Resultado del ataque: " + msg.substring(4));
        } else if (msg.startsWith("CHAT")) {
            System.out.println("Rival dice: " + msg.substring(5));
        }
    }
}
