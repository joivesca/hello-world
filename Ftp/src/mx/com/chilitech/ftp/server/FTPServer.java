package mx.com.chilitech.ftp.server;

import java.net.ServerSocket;

public class FTPServer {

	public static void main(String[] args) throws Exception{
		ServerSocket socket = new ServerSocket(5217);
		System.out.println("FTP Server Started on Port Number 5217");
        while(true) {
            System.out.println("Waiting for Connection ...");
            new Transferfile(socket.accept());
        }
	}
}
