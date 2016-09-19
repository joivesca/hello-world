package mx.com.chilitech.access.server;




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
 
public class DispacherServidorPasarela 
{
    public static DispacherServidorPasarela enlace;
    public static Object doneLock;
    public static Consola cmd = new Consola();
     
    public DispacherServidorPasarela() {enlace = this;}
     
     
     
     
    public static void main(String Args[]) throws IOException
    {
        doneLock = new Object();
         
        JOptionPane.showMessageDialog(null,"Bienvenido al servidor de administracion Remota por comandos");
    String puerto = JOptionPane.showInputDialog(null,"Ingrese el puerto en el que desea escuchar");
        ServerSocket ss = new ServerSocket(Integer.parseInt(puerto));
        Socket conexion; // se crea la variable Socket de conexion
         
        JOptionPane.showMessageDialog(null,"Servidor Pasarela iniciado");
        while(true)//bucle infinito
        {
//El ss.accept se queda a la espera de un cliente para continuar y luego le entrega el socket del cliente a la variable            
            conexion = ss.accept();
            //crea una variable ServidorPasarela y le pasa el socket de conexion
            ServidorPasarela hilo = new ServidorPasarela(conexion,enlace,doneLock);
            hilo.start();   //envia el hilo de ejecusion al servidor con el valor de la conexion y continua el bucle
             
        }
    }     
}