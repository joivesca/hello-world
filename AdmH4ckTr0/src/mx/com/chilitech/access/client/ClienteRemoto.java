package mx.com.chilitech.access.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
 
public class ClienteRemoto
{
public static void main(String args[]) throws Exception
{
       JOptionPane.showMessageDialog(null,"Bienvenido al cliente de administracion Remota por comandos");
       String ip = JOptionPane.showInputDialog(null,"Ingrese la IP o el HostName del servidor pasarela");
       String puerto = JOptionPane.showInputDialog(null,"Ingrese el puerto del servidor pasarela");
       //crea el socket de coneccion y inicialisa los parametros
       Socket conexion = new Socket(ip, Integer.parseInt(puerto));
 
       //crea el bufer de lectura de entrada o resepcion con un puntero a lo que resive por la conexion
       BufferedReader resiveSoketCMD = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
 
       //crea la variable de impresion o envio del socket con un puntero hacia la conexion de salida
       PrintWriter envioSocketCMD = new PrintWriter(conexion.getOutputStream(),true);
 
       String inputUser = new String("");
       String respuestaCMD = new String("");
 
       inputUser = JOptionPane.showInputDialog(null,"Para cerrar escriba la palabra: APAGAR \n ingrese el comando a ejecutar:");
 
       while(!inputUser.equals("APAGAR"))
       {
           envioSocketCMD.println(inputUser);//envia la cadena al servidor
           respuestaCMD = resiveSoketCMD.readLine();//lee la respuesta del servidor
           respuestaCMD = respuestaCMD.replaceAll("##", "\n");//Agraga saltos de linea a la respuesta
           JOptionPane.showMessageDialog(null,respuestaCMD);//pinta la respuesta
           System.out.print("\n>");
           //espera otra entrada de consola
           inputUser = JOptionPane.showInputDialog(null,"Para cerrar escriba la palabra: APAGAR \n ingrese el comando a ejecutar:");
        }
 
        if(inputUser.equals("APAGAR"))envioSocketCMD.println(inputUser);
        envioSocketCMD.flush();//vacia el canal de envio
        envioSocketCMD.close();
        resiveSoketCMD.close();
        conexion.close();
}
}