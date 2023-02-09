package ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        //Creamos el socket para conectarnos
        DatagramSocket socket;

        //Puerto mediante el que nos vamos a conectar al servidor
        int puertoServidor = 41600;

        //Nombre del servidor
        String nombreServidor = "localhost";

        //Abrimos el escaner
        Scanner sc = new Scanner(System.in);

        //Numero que el usuario cree que es el numero oculto, respuesta que envia el servidor simplificada con trim
        String numeroUsuario, respuestaServidor="";
        try {
            // 1 - Obtención de la dirección del servidor usando el métod getByName de
            // InetAddress
            System.out.println("(Cliente): Estableciendo parámetros de conexión...");
            InetAddress direccionServidor = InetAddress.getByName(nombreServidor);

            // 2 - Creación del socket UDP
            System.out.println("(Cliente): Creando el socket...");
            socket = new DatagramSocket();


            // 3 - Generación del datagrama
            System.out.println("(Cliente): Creando datagrama...");

            //Mientras la respuesta del servidor no sea "¡¡¡Has acertado!!!"
            while(!respuestaServidor.equals("¡¡¡Has acertado!!!")) {

                //Le pedimos un numero al usuario
                System.out.print("Dime el numero que creas que es el oculto: ");
                numeroUsuario = sc.nextLine();

                //Creamos el array de bytes con el que enviaremos el numero
                byte[] bufferSalida = numeroUsuario.getBytes();

                //Creamos el datagrama que vamos a enviar
                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionServidor,
                        puertoServidor);

                // 4 - Envío del datagrama a través de send
                System.out.println("(Cliente) Enviando datagrama...");
                socket.send(paqueteSalida);

                // 5 - Recepción de la respuesta
                System.out.println("(Cliente) Recibiendo respuesta...");
                byte[] bufferEntrada = new byte[64];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length, direccionServidor,
                        puertoServidor);
                socket.receive(paqueteEntrada);

                //Guardamos la respuesta del servidor en la variable creada anteriormente
                respuestaServidor = new String(bufferEntrada).trim();

                //Mostramos la respuesta del servidor
                System.out.println("Mensaje recibido: " + respuestaServidor);
            }
            // 6 - Cierre del socket
            System.out.println("(Cliente): Cerrando conexión...");
            socket.close();
            System.out.println("(Cliente): Conexión cerrada.");

        //Control de excepciones
        } catch (SocketException e) {
            System.err.println("Error al conectar con el servidor.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("No se ha podido enviar o recibir el paquete");
            e.printStackTrace();
        }
    }

}
