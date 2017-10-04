package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import chess.Piece;
import controller.MainController;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * God only knows how this class works.
	 * The TextPane may as well be possesed by demons
	 */
	public MainFrame() {
		chessboard = new ChessboardPane();
		this.getContentPane().setLayout (new GridBagLayout());
		constraints = new GridBagConstraints();
		chatUp = "";
		chatDown = "";
		chatColor = "?? plays\n\n";
		chatLog = new JTextPane();
		chatLog.setEditable(false);
		chatLog.setText(chatColor + chatUp+chatMiddle+chatDown);
		
		chatLog.addKeyListener( new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyChar() == '');
				if(e.getKeyChar() == '\n'){
					if(chatDown.length() != 0)
						MainController.getInstance().sendMessage();
				}else
					if(e.getKeyChar() == '')
						if(chatDown.length() > 0){
							chatDown = ( chatDown.substring(0, chatDown.length()-1 ) );
							chatLog.setText(chatColor + chatUp+chatMiddle+chatDown);
						}else{
							//Do nothing
						}
					else{
						if(chatDown.length() < 23){
							chatDown += e.getKeyChar();
							chatLog.setText(chatColor + chatUp+chatMiddle+chatDown);
							
						}
					}
			}

			@Override
			public void keyTyped(KeyEvent e) {	
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		constraints.gridx = 1; 
		constraints.gridy = 0; 
		constraints.gridwidth = 1; 
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.3;
		constraints.weighty = 1;
		this.getContentPane().add (chatLog, constraints);
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.NONE;
		
		constraints.gridx = 0; 
		constraints.gridy = 0; 
		constraints.gridwidth = 1; 
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipadx = 200;
		constraints.weightx = 1;
		constraints.weighty = 1;
		this.getContentPane().add (chessboard, constraints);
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.NONE;
		
	}
	
	public void addText(String line){
		ArrayList<String> lines = new ArrayList<>(Arrays.asList(chatUp.split("\n")));
//		for(int i = 0; i < lines.size()-1; i++){
//			if(lines.get(i) == ""){
//				lines.remove(i);
//				i--;
//			}
//		}
		System.out.println(lines.size());
		if(lines.size()>=10)
			lines.remove(0);
		lines.add(line);
		chatUp = "";
		for(String string : lines)
			chatUp+=string+"\n";

		chatLog.setText(chatColor + chatUp+chatMiddle+chatDown);
		System.out.println("Chatlog is "+chatLog.getText());
	}

//	/**
//	 * Revalidates itself and repaints itself and the canvas
//	 */
//	public void forceRepaint() {
//		revalidate();
//		repaint();
//		chessboard.repaint();
//	}

	private ChessboardPane chessboard;
	private JTextPane chatLog;
	private JTextPane chatWrite;
	private JScrollPane scrollpanel;
	private JScrollPane scrollconsole;
	private GridBagConstraints constraints;
	private String chatColor, chatUp, chatDown; 
	public static final String chatMiddle = "\n\n\n\n\n\nWrite here:\n";

	public void printPiece(Piece piece, int rankCount, int fileCount) {
		chessboard.printPiece(piece, rankCount, fileCount);
		chessboard.repaint();
	}

	public void deletePieces() {
		chessboard.deletePieces();
	}

	public String getChatWriteText() {
		return chatDown;
	}

	public void resetChatWrite() {
		chatDown = "";
		chatLog.setText(chatUp + chatMiddle + chatDown);
	}

	public void changeColorThatPlays(String color) {
		chatColor = color + " plays\n\n";
		chatLog.setText(chatColor + chatUp+chatMiddle+chatDown);
	}

}
