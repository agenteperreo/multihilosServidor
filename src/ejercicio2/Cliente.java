package ejercicio2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        //Abrimos el escaner
        Scanner sc = new Scanner(System.in);

        //Ruta que envia el cliente al servidor
        String ruta;

        try {
            // 1 - Crear un socket de tipo cliente indicando IP y puerto del servidor
            System.out.println("(Cliente) Estableciendo conexión con el servidor");
            Socket cliente = new Socket(InetAddress.getLocalHost(), 41600);

            // 2 - Abrir flujos de lectura y escritura
            InputStream is = cliente.getInputStream();
            OutputStream os = cliente.getOutputStream();
            System.out.println("(Cliente) Conexión establecida");

            // 3 - Intercambiar datos con el servidor
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            //Enviamos la ruta que queremos
            System.out.println("Dime la url que quieras investigar: ");
            ruta=sc.next();

            bw.write(ruta);
            bw.flush();

            // Leo mensajes que me envía el servidor
            System.out.println("El servidor me envía el siguiente mensaje: " + is.read());

            // 4 - Cerrar flujos de lectura y escritura
            osw.close();
            bw.close();
            is.close();
            os.close();

            // 5 - Cerrar la conexión
            System.out.println("(Cliente) Cerrando conexiones...");
            cliente.close();
            System.out.println("(Cliente) Conexiones cerradas...");

        } catch (UnknownHostException e) {
            System.err.println("No se encuentra el host especificado.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Se ha producido un error en la conexión con el servidor.");
            e.printStackTrace();
        }
    }
}
