package ejercicio2;

import java.io.*;
import java.net.Socket;

public class GestorDeProcesos extends Thread{
    private Socket socket;
    private OutputStream os;

    private InputStream is;

    public GestorDeProcesos(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            realizarProceso();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void realizarProceso() throws IOException {
        os = this.socket.getOutputStream();
        is = this.socket.getInputStream();

        String ip="", ruta;

        // 4 - Intercambiar datos con el cliente
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);

        File archivo=new File("src/ejercicio2/ipS");

        //Guardamos la ruta enviada y la mostramos
        ruta=br.readLine();
        System.out.println(ruta);
        try {
            if (archivo.exists()) {
                isr = new FileReader(archivo);
                br = new BufferedReader(isr);
                while((ip = br.readLine())!=ruta) {

                }
            }
        } finally {
            os.close();
            is.close();
        }

    }
}
