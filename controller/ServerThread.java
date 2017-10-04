// place this file the path such ends with: ChatServer/client/ServerThread.java

package controller;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket socket;
    private String userName;
    private char playerColor;
    private boolean isAlived;
    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    public ServerThread(Socket socket, String userName, char color){
        this.socket = socket;
        this.userName = userName;
        messagesToSend = new LinkedList<String>();
        playerColor = color;
    }

    public void addNextMessage(String message){
    	System.out.println("Sending: "+message);
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run(){
        System.out.println("Welcome :" + userName);
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        MainController.getInstance().addText("Welcome :" + userName);
        MainController.getInstance().addText("Local Port :" + socket.getLocalPort());
        MainController.getInstance().addText("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());
        MainController.getInstance().addText("Address = " + socket.getLocalAddress() + ":" + socket.getPort());

        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);
            // BufferedReader userBr = new BufferedReader(new InputStreamReader(userInStream));
            // Scanner userIn = new Scanner(userInStream);

            while(!socket.isClosed()){
                if(serverInStream.available() > 0){
                    if(serverIn.hasNextLine()){
                    	String s = serverIn.nextLine();
                        System.out.println(s);
                        String boardState = s.substring(0, 129);
                        String move = s.substring(129);
                        System.out.println(boardState);
                        System.out.println(move);
                        if (MainController.getInstance().makeAPlay(move) == 0 && move.charAt(0) == playerColor)
                        	MainController.getInstance().addText(move.substring(1));
                        MainController.getInstance().setBoard(boardState);
                    }
                }
                if(hasMessages){
                    String nextSend = "";
                    synchronized(messagesToSend){
                        nextSend = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    serverOut.println(playerColor+userName + " > " + nextSend);
                    serverOut.flush();
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
