package battleship.modelo;

import java.io.*;
import java.net.*;

public class NetworkServer {
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        client = server.accept();
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
    }

    public void send(Object o) throws IOException {
        out.writeObject(o);
        out.flush();
    }

    public Object receive() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public void stop() throws IOException {
        if (client != null) client.close();
        if (server != null) server.close();
    }
}
