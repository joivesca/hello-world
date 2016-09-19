package mx.com.chilitech.ftp.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TransferfileClient {
	
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    
    TransferfileClient(Socket soc) {
        try {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        } catch(Exception ex) {
        }        
    }
    
    void sendFile() throws Exception {        
        
        String filename;
        System.out.print("Enter File Name :");
        filename=br.readLine();
            
        File f = new File(filename);
        if(!f.exists()) {
            System.out.println("File not Exists...");
            dout.writeUTF("File not found");
            return;
        }
        
        dout.writeUTF(filename);
        
        String msgFromServer=din.readUTF();
        if(msgFromServer.compareTo("File Already Exists")==0) {
            String Option;
            System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
            Option=br.readLine();            
            if(Option=="Y") {
                dout.writeUTF("Y");
            } else {
                dout.writeUTF("N");
                return;
            }
        }
        
        System.out.println("Sending File ...");
        FileInputStream fin=new FileInputStream(f);
        int ch;
        do {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        } while(ch!=-1);
        fin.close();
        System.out.println(din.readUTF()); 
    }
    
    void receiveFile() throws Exception {
        String fileName;
        System.out.print("Enter File Name :");
        fileName=br.readLine();
        dout.writeUTF(fileName);
        String msgFromServer=din.readUTF();
        
        if(msgFromServer.compareTo("File Not Found")==0) {
            System.out.println("File not found on Server ...");
            return;
        } else if(msgFromServer.compareTo("READY")==0) {
            System.out.println("Receiving File ...");
            File f=new File(fileName);
            if(f.exists()) {
                String Option;
                System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                Option=br.readLine();            
                if(Option=="N") {
                    dout.flush();
                    return;    
                }                
            }
            FileOutputStream fout=new FileOutputStream(f);
            int ch;
            String temp;
            do  {
                temp=din.readUTF();
                ch=Integer.parseInt(temp);
                if(ch!=-1) {
                    fout.write(ch);                    
                }
            } while(ch!=-1);
            fout.close();
            System.out.println(din.readUTF());     
        }
    }

    public void displayMenu() throws Exception {
        while(true) {    
            System.out.println("[ MENU ]");
            System.out.println("1. Send File");
            System.out.println("2. Receive File");
            System.out.println("3. Exit");
            System.out.print("\nEnter Choice :");
            int choice;
            choice=Integer.parseInt(br.readLine());
            if(choice==1) {
                dout.writeUTF("SEND");
                sendFile();
            }else if(choice==2) {
                dout.writeUTF("GET");
                receiveFile();
            } else {
                dout.writeUTF("DISCONNECT");
                System.exit(1);
            }
        }
    }
}