package ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        String mensajeRecibido;
        try {
            // 1 - Crear DatagramSocket y le indicamos el puerto
            System.out.println("(Servidor) Creando socket...");
            socket = new DatagramSocket(41600);

            while (true) {
                // 2 - Crear array de bytes que actuará de buffer
                byte[] buffer = new byte[64];

                // 3 - Creación de datagrama con la clase DatagramPacket
                DatagramPacket datagramaEntrada = new DatagramPacket(buffer, buffer.length);

                // 4 - Recepción del datagrama mediante el método receive
                System.out.println("(Servidor) Esperando peticiones...");
                socket.receive(datagramaEntrada);

                new GestorDeProcesos(socket, datagramaEntrada).start();

            }
            // 6 - Cierre del socket
//			System.out.println("(Servidor): Cerrando la conexión...");
//			socket.close();
//			System.out.println("(Servidor): Conexión cerrada");

        } catch (SocketException e) {
            System.out.println("ERROR: Error al crear el Socket");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("ERROR: Error al recibir el paquete");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
