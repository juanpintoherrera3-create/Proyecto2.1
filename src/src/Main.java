package src;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Quieres iniciar como (S)ervidor o (C)liente?");
        String opcion = sc.nextLine().trim().toUpperCase();

        try {
            if (opcion.equals("S")) {
                Server server = new Server();
                server.start(5000);
                System.out.println("Servidor listo. Escribe tus mensajes o 'exit' para salir.");

                while (true) {
                    String msg = sc.nextLine();
                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Cerrando servidor...");
                        server.stop();
                        break;
                    }
                    server.enviar("CHAT " + msg);
                }

            } else if (opcion.equals("C")) {
                System.out.print("Escribe la IP del servidor: ");
                String ip = sc.nextLine();
                Client client = new Client();
                client.connect(ip, 5000);
                System.out.println("Conectado. Escribe tus mensajes o 'exit' para salir.");

                while (true) {
                    String msg = sc.nextLine();
                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Cerrando cliente...");
                        client.stop();
                        break;
                    }
                    client.enviar("CHAT " + msg);
                }

            } else {
                System.out.println("Opción inválida.");
            }

        } catch (IOException e) {
            System.out.println("Error de red: " + e.getMessage());
        }

        sc.close();
        System.out.println("Programa terminado.");
    }
}
