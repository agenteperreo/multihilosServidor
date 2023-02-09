package ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GestorDeProcesos extends Thread {
    static int numeroAleatorio = (int) (Math.random() * 100) + 1;
    static boolean acertado = false;
    DatagramSocket socket;
    DatagramPacket datagramaEntrada;

    public GestorDeProcesos(DatagramSocket socket, DatagramPacket datagramaEntrada) {
        super();
        this.socket = socket;
        this.datagramaEntrada = datagramaEntrada;
    }

    @Override
    public void run() {
        realizarProceso();
    }

    public void realizarProceso() {
        while (!acertado) {

            // Recepción de mensaje del cliente
            String mensajeRecibido = new String(datagramaEntrada.getData());
            System.out.println("(Servidor): Mensaje recibido: " + mensajeRecibido);

            //Parseamos la respuesta del cliente
            int respCliente = Integer.parseInt(mensajeRecibido);

            // 5 - Generación y envío de la respuesta
            System.out.println("(Servidor): Enviando datagrama...");
            byte[] mensajeEnviado;

            if (respCliente > numeroAleatorio) {
                mensajeEnviado = new String("El numero es menor").getBytes();
            } else if (respCliente < numeroAleatorio) {
                mensajeEnviado = new String("El numero es mayor").getBytes();
            } else {
                mensajeEnviado = new String("¡¡¡Has acertado!!!").getBytes();
                acertado = true;
            }

            DatagramPacket packetSalida = new DatagramPacket(mensajeEnviado, mensajeEnviado.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());

            try {
                socket.send(packetSalida);
            } catch (IOException e) {
                System.err.println("ERROR: Algo sucedió en el envio del paquete");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
