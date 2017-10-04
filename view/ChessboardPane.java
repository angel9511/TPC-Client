package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import chess.Piece;
import controller.MainController;

public class ChessboardPane extends JPanel {
	
	
	/**
	 * Loads into objects the images that will be used by the user
	 *  in their animations, then it creates a list with those images
	 *  and a map where their names are the keys
	 */
	public ChessboardPane() {
		try {
			blackbishop = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black bishop.png"));
			blackking = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black king.png"));
			blacknight = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black knight.png"));
			blackpawn = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black pawn.png"));
			blackqueen = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black queen.png"));
			blackrook = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black rook.png"));
			chessboard = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/chessboard.png"));
			whitebishop = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white bishop.png"));
			whiteking = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white king.png"));
			whiteknight = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white knight.png"));
			whitepawn = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white pawn.png"));
			whitequeen = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white queen.png"));
			whiterook = ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white rook.png"));
//			ghost.setI();
//			 zero.setI(ImageIO.read(Canvas.class.getResourceAsStream("/images/2.jpg")));
//			 luffy.setI(ImageIO.read(Canvas.class.getResourceAsStream("/images/3.jpg")));
		} catch (IOException e) {	}
//		imageDictionary.put("fantasma",  ghost );
//		imageDictionary.put("enmascarado",  zero );
//		imageDictionary.put("cultivador",  luffy );
//		elements.add( ghost );
//		elements.add( zero );
//		elements.add( luffy );
//		elements.add( lizardon );
	}
	
	/**
	 * Draws every image that is visible into the canvas
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(chessboard, 0, 0, null);
		//System.out.println("nyo " +elements.size()+" "+elementsX.size()+" "+elementsY.size());
		for(int i = 0; i < elements.size() && i < elementsX.size() && i<elementsY.size(); i++)
		{
			g.drawImage(elements.get(i), elementsX.get(i), elementsY.get(i), 60, 60, null);
		}
	}
	
	
	private BufferedImage blackbishop;
	private BufferedImage blackking;
	private BufferedImage blacknight;
	private BufferedImage blackpawn;
	private BufferedImage blackqueen;
	private BufferedImage blackrook;
	private BufferedImage chessboard;
	private BufferedImage whitebishop;
	private BufferedImage whiteking;
	private BufferedImage whiteknight;
	private BufferedImage whitepawn;
	private BufferedImage whitequeen;
	private BufferedImage whiterook;
	private List<BufferedImage>  elements = new LinkedList<>();
	private List<Integer>  elementsX = new LinkedList<>();
	private List<Integer>  elementsY = new LinkedList<>();
	
	public void printPiece(Piece piece, int rankCount, int fileCount) {
		//System.out.println(piece.toString());
		try{
			if(piece.toString().equals("bB"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black bishop.png")));
			if(piece.toString().equals("bK"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black king.png")));
			if(piece.toString().equals("bN"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black knight.png")));
			if(piece.toString().equals("bp"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black pawn.png")));
			if(piece.toString().equals("bQ"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black queen.png")));
			if(piece.toString().equals("bR"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/black rook.png")));
			if(piece.toString().equals("wB"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white bishop.png")));
			if(piece.toString().equals("wK"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white king.png")));
			if(piece.toString().equals("wN"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white knight.png")));
			if(piece.toString().equals("wp"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white pawn.png")));
			if(piece.toString().equals("wQ"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white queen.png")));
			if(piece.toString().equals("wR"))
				elements.add( ImageIO.read(ChessboardPane.class.getResourceAsStream("/images/white rook.png")));
			//chessboard starts at x,y=25, cells have width and height 65
			elementsY.add(25+(65*(7-fileCount)));
			elementsX.add(25+(65*(rankCount)));
			//System.out.println(elements.size()+ " "+elementsX.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deletePieces() {
		elements.clear();
		elementsX.clear();
		elementsY.clear();
	}
}
