package controller;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chess.Board;
import chess.Piece;
import view.MainFrame;

public class MainController {
  
    public MainController() {
    	System.out.println("1");
		window = new MainFrame();
    	System.out.println("2");
        Scanner scan = new Scanner(System.in);
        String readName = JOptionPane.showInputDialog("Please choose an username");
        while(readName == null || readName.trim().equals("") || readName.length() > 10){
            // null, empty, whitespace(s) not allowed.
            readName = JOptionPane.showInputDialog("Invalid. Please enter again:");
            if(readName.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }
        String colorToPlay = JOptionPane.showInputDialog("Please choose a color to play as");
        while(colorToPlay == null || !(colorToPlay.trim().toLowerCase().equals("w") || colorToPlay.trim().toLowerCase().equals("b") || colorToPlay.trim().toLowerCase().equals("white") || colorToPlay.trim().toLowerCase().equals("black"))){
            // null, empty, whitespace(s) not allowed.
            colorToPlay = JOptionPane.showInputDialog("Invalid color. Please enter again:");
        }
        client = new Client(readName, colorToPlay.trim().toLowerCase().charAt(0),scan);
        //System.out.println("g"+colorToPlay);
        drawAvailable = false;
        gameBoard = new Board();
        color = "white";
    	System.out.println("3");
	}
    
    /**
	 * Initializes the window with the default parameters and title
	 */
	public void initGraphic(){
		window.setTitle(_tittle );
		window.setVisible(true);
		window.setBounds(0, 0, 936, 615);
		window.setResizable(false);
		//window.setBounds(0, 0, 596, 615);
		//window.setExtendedState( Frame.MAXIMIZED_BOTH );
		window.setBackground(Color.WHITE);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainController main = getInstance();
		
        main.startClient();
		main.initGraphic();

        main.gameBoard.print();	
	}
	
	//returns 0 when not a move, 1 when cool, -1 when game over
	public int makeAPlay(String move){
		System.out.println(color);
		System.out.println(move.charAt(0)!=color.charAt(0));
		System.out.println(move);
		if(move.charAt(0)!=color.charAt(0))
			return 0;
		move.replaceAll("\n", "");
		move.replaceAll("\r", "");
		if(move.length() < 5 )
			return 0;
		move = move.substring(move.length()-5);
		System.out.println("move "+move);
		if(drawAvailable){
            if(move.contains("draw")){
                System.out.println("The game is a draw.");
                return -1;
            }else{
                drawAvailable = false;
            }
        }

        if(move.contains("resign")){
            System.out.println(color + " resigns");
            System.out.println(colorToggle(color) + " wins the game!");
            return -1;
        }

        try {
            gameBoard.performMove(move, color, true);
        } catch (Exception e) {
            // Ask for user input again
            System.out.println("Not a move, sent as message");
            return 0;
        } 

        Piece[][] oldBoard = gameBoard.board.clone();

        if(!gameBoard.canAnyPieceMakeAnyMove(colorToggle(color))){
            if(gameBoard.isInCheck(colorToggle(color))){
                System.out.println("Checkmate. " + color + " wins");
                System.out.println("Game over!");
            }else{
                System.out.println("Stalemate!");
            }
            return -1;
        }

        gameBoard.board = oldBoard;

        if(gameBoard.isInCheck(colorToggle(color))){
            System.out.println(colorToggle(color) + " is in check.");
        }

        if(move.contains("draw?")){
            drawAvailable = true;
        }

        //Now I have to check to see if either player is in check or checkmate
        //I also have to see if there is a stalemate

        color = colorToggle(color);

        return 1;
	}
	
	private void startClient() {
		Thread thread = new Thread(client);
		thread.start();
	}

	public static String colorToggle(String color){
        if(color.equals("white")){
            return "black";
        }
        return "white";
    }

	/**
	 * Singleton
	 * @return MainController instance of the singleton pattern
	 */
	public static MainController getInstance() {
		if ( main == null ) {
			main = new MainController();
		}
		return main;
	}

    private Board gameBoard;
    private String color;
	private boolean drawAvailable;
	private MainFrame window;
	private Client client;
	private static MainController main;
	private String _tittle = "Shared Chess Client";


	public void printPiece(Piece piece, int rankCount, int fileCount) {
		window.printPiece(piece, rankCount, fileCount);
	}

	public void addText(String line) {
		window.addText(line);
	}

	public void sendMessage() {
		String s = "";
		s+=window.getChatWriteText();
		System.out.println("Sending "+s);
		client.addMessageToServerThread(s);
		window.resetChatWrite();
	}

	public void setBoard(String boardState) {
		if(boardState.charAt(0) == 'w')
			color = "white";
		else
			color = "black";
		gameBoard = new Board(boardState.substring(1));
		//gameBoard.setBoard(boardState.substring(1));
		System.out.println(boardState + "\nlaaan "+color);
		window.changeColorThatPlays(color);
		window.deletePieces();
		gameBoard.print();
	}

	public String getColor() {
		return color;
	}
}
