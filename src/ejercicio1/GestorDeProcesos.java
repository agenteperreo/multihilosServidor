package ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GestorDeProcesos extends Thread {

    //Creamos la variable en la que guardaremos el numero oculto
    static int numeroAleatorio = (int) (Math.random() * 100) + 1;

    //Socket para conectarnos con el cliente
    DatagramSocket socket;

    //Datagrama que nos enviará el cliente
    DatagramPacket datagramaEntrada;

    //Constructor de la clase
    public GestorDeProcesos(DatagramSocket socket, DatagramPacket datagramaEntrada) {
        super();
        this.socket = socket;
        this.datagramaEntrada = datagramaEntrada;
    }

    //Metodo run que ejecutara cada hilo
    @Override
    public void run() {

        //Llamamos a la funcion realizarProceso en la que se daran las pistas que le da al cliente
        realizarProceso();
    }

    public void realizarProceso() {

        // Recepción de mensaje del cliente
        String mensajeRecibido = new String(datagramaEntrada.getData());
        System.out.println("(Servidor): Mensaje recibido: " + mensajeRecibido.trim());

        //Parseamos la respuesta del cliente
        int respCliente = Integer.parseInt(mensajeRecibido.trim());

        // 5 - Generación y envío de la respuesta
        System.out.println("(Servidor): Enviando datagrama...");
        byte[] mensajeEnviado;

        //Si la respuesta del cliente es mayor que el numero oculto
        if (respCliente > numeroAleatorio) {
            //La respuesta "El numero es menor" se guarda en el array de bytes
            mensajeEnviado = new String("El numero es menor").getBytes();
        //Si la respuesta del usuario es menor que el numero oculto
        } else if (respCliente < numeroAleatorio) {
            //La respuesta "El numero es mayor" se guarda en el array de bytes
            mensajeEnviado = new String("El numero es mayor").getBytes();
        //Si no
        } else {
            //La respuesta "¡¡¡Has acertado!!!" se guarda en el array de bytes
            mensajeEnviado = new String("¡¡¡Has acertado!!!").getBytes();
        }

        //Se crea el datagrama que se va a enviar mediante el array de bytes
        DatagramPacket packetSalida = new DatagramPacket(mensajeEnviado, mensajeEnviado.length, datagramaEntrada.getAddress(), datagramaEntrada.getPort());

        try {
            //Enviamos el datagrama
            socket.send(packetSalida);
        } catch (IOException e) {
            System.err.println("ERROR: Algo sucedió en el envio del paquete");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
