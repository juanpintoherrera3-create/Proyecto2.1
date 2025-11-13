package Pruebas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Esperando conexión del rival...");
        clientSocket = serverSocket.accept();
        System.out.println("Jugador conectado desde: " + clientSocket.getInetAddress());

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        new Thread(new Receiver(in)).start();

        System.out.println("Receptor iniciado");

    }

    public void enviar(String mensaje) {
        out.println(mensaje);
        out.flush();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    // Hilo que escucha continuamente los mensajes del rival
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

    // Aquí decides qué hacer con cada mensaje recibido
    private void tratarMensaje(String msg) {
        if (msg.startsWith("ATK")) {
            String coord = msg.substring(4);
            System.out.println("Ataque recibido en " + coord);

            // Lógica de juego: verificar si hay barco, etc.
            // Ejemplo: enviar respuesta inmediata
            enviar("RES Hit"); // o "RES Miss"
        } else if (msg.startsWith("CHAT")) {
            System.out.println("Mensaje del rival: " + msg.substring(5));
        }
    }
}
