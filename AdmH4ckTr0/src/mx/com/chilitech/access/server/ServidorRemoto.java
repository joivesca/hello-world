package mx.com.chilitech.access.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
 
public class ServidorRemoto 
{
    public static void main(String args[]) throws Exception 
    {
        JOptionPane.showMessageDialog(null,"Bienvenido al Servidor remoto");
        String ip = JOptionPane.showInputDialog(null,"Ingrese la IP o el HostName del servidor pasarela");
        String puerto = JOptionPane.showInputDialog(null,"Ingrese el puerto del servidor pasarela");
        //crea el socket de coneccion y inicialisa los parametros
        Socket conexion = new Socket(ip, Integer.parseInt(puerto));
               
        //crea el bufer de lectura de entrada o resepcion con un puntero a lo que resive por la conexion   
        BufferedReader resiveSoketCMD = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
         
                 
     //crea la variable de impresion o envio del socket con un puntero hacia la conexion de salida   
        PrintWriter envioSocketCMD = new PrintWriter(conexion.getOutputStream(),true);
         
         
        String respuestaCMD = new String("ServidorRemoto");
        String exeCMD = new String("");
         
        envioSocketCMD.println(respuestaCMD);//envia la cadena al servidor
        JOptionPane.showMessageDialog(null,"Servidor Remoto iniciado");
        exeCMD=resiveSoketCMD.readLine();
        while(!exeCMD.equals("APAGAR"))
        {
            String res = exeComando(exeCMD);
            envioSocketCMD.println(res);
            exeCMD=resiveSoketCMD.readLine();
        }
         
         
        envioSocketCMD.flush();//vacia el canal de envio
    envioSocketCMD.close();
        resiveSoketCMD.close();
    conexion.close();
     
    }
     
    static public String exeComando(String c)
    {
        String s = null;
        String re = "";
        String er = "";
 
                try {
 
                        // Determinar en qué SO estamos
                        String so = System.getProperty("os.name");
                        System.out.println("El sistema es =&gt; "+so);
 
                        String comando;
 
                        // Comando para Linux
                        if (so.equals("Linux"))
                                comando = c;
 
                        // Comando para Windows
                        else
                                comando = "cmd /c "+c;
 
                        // Ejcutamos el comando
                        Process p = Runtime.getRuntime().exec(comando);
 
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
                        // Leemos la salida del comando
                        //Ésta es la salida standard del comando
                        while ((s = stdInput.readLine()) != null) 
                        {    
                            re+=s+"##";
                        }
                        if(re != null)return re;
                        // Leemos los errores si los hubiera
                        //Ésta es la salida standard de error del comando (si la hay)
                        while ((s = stdError.readLine()) != null) 
                        {
                                er += s+"##";
                        }
                        if(er != null)return er;
                        System.exit(0);
                } catch (IOException e) {
                        System.out.println("Excepción: ");
                        e.printStackTrace();
                        System.exit(-1);
                }
       return "nada";
    }
     
}