package battleship.modelo;

import java.io.*;
import java.net.*;

public class NetworkClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Object o) throws IOException {
        out.writeObject(o);
        out.flush();
    }

    public Object receive() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public void close() throws IOException {
        if (socket != null) socket.close();
    }
}
