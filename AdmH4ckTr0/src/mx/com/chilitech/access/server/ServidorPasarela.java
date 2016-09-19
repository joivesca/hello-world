package mx.com.chilitech.access.server;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
 
 
public class ServidorPasarela extends Thread //servidor que atiende varios hilos de ejecucion
{
    private Socket conexion = null;
    public DispacherServidorPasarela enlace;
    private Object doneLock; 
     
    String cadena;
    BufferedReader entradaDatos;
    PrintWriter salidaDatos;
     
    SimpleDateFormat formato1 = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss");
     
    public ServidorPasarela(Socket conexion,DispacherServidorPasarela e,Object o1) 
    {
        this.conexion = conexion;
        this.enlace = e;
        this.doneLock = o1;
    }
 
    @Override
    public void run() 
    {
        try
        {
            //Cadena
            cadena = new String("");
            //crea el bufer de lectura de entrada o resepcion con un puntero a lo que resive por la conexion
            entradaDatos = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            //crea la variable de impresion o envio del socket con un puntero hacia la conexion de salida
            salidaDatos = new PrintWriter(conexion.getOutputStream(),true);
            //espera a lee los datos que le llega al bufer del servidor y lo almasena en cadena
            cadena = entradaDatos.readLine();
             
            if(!cadena.equals("ServidorRemoto"))
            {
                Date fechaInicio = new Date();            
                String cadenaFecha0 = formato1.format(fechaInicio);
                String cadenaHora0 = formato2.format(fechaInicio);
                System.out.println("Threat Iniciado por el cliente "+conexion.getInetAddress().getHostName()+" el "+cadenaFecha0+" a las "+cadenaHora0);
            }
             
                while(!cadena.equals("APAGAR"))
                {
 
                    switch(cadena)
                    {
                        case "ServidorRemoto":  servidorRemoto();
                                                break;
 
                            default: clienteRemoto();                           
                     }
                }
                 
            Date fechaSalida = new Date();            
            String cadenaFecha = formato1.format(fechaSalida);
            String cadenaHora = formato2.format(fechaSalida);
            System.out.println("Threat Cerrado por el cliente "+conexion.getInetAddress().getHostName()+" el "+cadenaFecha+" a las "+cadenaHora);
            salidaDatos.flush();
            salidaDatos.close();
            entradaDatos.close();
            conexion.close();
         
        } catch (IOException e) {System.out.println(e);}
    }
     
    public synchronized void servidorRemoto()
    {
        while(true) 
        {
           try
           {
               synchronized(doneLock)
                {
                    doneLock.wait();
                     
                    salidaDatos.println(enlace.cmd.getComando());
                    enlace.cmd.setComando(null);
                 
                   enlace.cmd.setRespuesta(entradaDatos.readLine()); 
                   doneLock.notify(); 
                }
                                              
            } catch (Exception e) {System.out.println("ServidorRemoto "+e);}
        }
    }
     
    public synchronized void clienteRemoto()
    {
        try {
               synchronized(doneLock)
               {
                    enlace.cmd.setComando(cadena);                    
                    doneLock.notify(); 
                
                    doneLock.wait();
                    salidaDatos.println(enlace.cmd.getRespuesta());
                    enlace.cmd.setRespuesta(null); 
                    cadena = entradaDatos.readLine();
                     
                }
                                 
            } catch (Exception e) {System.out.println("Cliente:"+e);}
                             
    }
     
}