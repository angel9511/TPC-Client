// place this file the path such ends with: ChatServer/client/Client.java

package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private static final String host = "localhost";
    private static final int portNumber = 4444;

    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner scan;
    private ServerThread serverThread;
    private char playerColor;

    public Client(String userName, char color, Scanner scan) {
    	this.scan = scan;
		this.userName = userName;
		this.playerColor = color;
        this.serverHost = host;
        this.serverPort = portNumber;
	}

    public void addMessageToServerThread(String message){
    	System.out.println(message);
    	serverThread.addNextMessage(message);
    }
    
	public void run(){
        try{
        	scan = new Scanner(System.in);
        
            Socket socket = new Socket(serverHost, serverPort);
            Thread.sleep(1000); // waiting for network communicating.

            serverThread = new ServerThread(socket, userName, playerColor);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            serverThread.addNextMessage("has joined");
            while(serverAccessThread.isAlive()){
                if(scan.hasNextLine()){
                	String s = scan.nextLine();
                    serverThread.addNextMessage(s);
                    
                }
                // NOTE: scan.hasNextLine waits input (in the other words block this thread's process).
                // NOTE: If you use buffered reader or something else not waiting way,
                // NOTE: I recommends write waiting short time like following.
                // else {
                //    Thread.sleep(200);
                // }
            }
        }catch(IOException ex){
            System.err.println("Fatal Connection error!");
            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
        }
    }
	
}
