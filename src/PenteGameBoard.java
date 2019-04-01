import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard extends JPanel
{
	
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;
	public static final int SIDE_SQUARES = 19;
	public static final int INNER_START = 7;
	public static final int INNER_END = 11;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	
	int bWidth, bHeight;
	//PenteGameRunner myGame;
	
	//private BoardSquare testSquare;
	private int squareW, squareH;
	private JFrame myFrame;
	
	//variables for playing the game
	//its assumed that P1 would be the darkstone (moves first)
	int playerTurn;
	boolean player1IsComputer = false;
	boolean player2IsComputer = false;
	String p1Name, p2Name;
	
	
	//data structure for the board
	private BoardSquare[][] gameBoard;
	
	
	public PenteGameBoard(int w, int h)
	{
		//store these variables
		bWidth = w;
		bHeight = h;
	//	myGame = g;
		
		this.setSize(w, h);
		this.setBackground(Color.CYAN);
		
		squareW = bWidth/this.SIDE_SQUARES;
		squareH = bHeight/this.SIDE_SQUARES;
		
		//testSquare = new BoardSquare(0, 0, squareW, squareH);
		gameBoard = new BoardSquare[SIDE_SQUARES][SIDE_SQUARES];
		
		for(int col = 0; col < SIDE_SQUARES; col++)
		{	
			for (int row = 0; row < SIDE_SQUARES; row++)
			{
				
			gameBoard[row][col] = new BoardSquare(col * squareW, row * squareH, squareW, squareH);
			if(col >= INNER_START && col <= INNER_END)
			{
				if( row >= INNER_START && row <= INNER_END)
				{
					gameBoard[row][col].setInner();
				}
			}
		/*	if(col >= 8 && col <= 12 && row >= 8 && row <= 12)
			{
				gameBoard[row][col].setInner();
			}
			
			if((row + col) % 2 == 0)
			{
				gameBoard[row][col].setState(WHITESTONE);
			} else {
				gameBoard[row][col].setState(BLACKSTONE);
			}
			
			
			*/}
			
			
		}
		
		
		
		
		
	}
	
		//method to do drawing
		public void paintComponent(Graphics g)
		{
			
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, bWidth, bHeight);
			
			//do this 19 x 19 times
			//testSquare.drawMe(g);
			
			
			for(int col = 0; col < SIDE_SQUARES; col++)
			{
				for(int row = 0; row < SIDE_SQUARES; row++)
				{
				
				gameBoard[row][col].drawMe(g);
				
				}
			}
			
			
		}
		
		public void resetBoard()
		{
			for(int row = 0; row < SIDE_SQUARES; row++)
			{
				for(int col = 0; col < SIDE_SQUARES; col++)
				{
					gameBoard[row][col].setState(EMPTY);
				}
			}
		
		}
		
		public void startNewGame()
		{
			resetBoard();
			
			p1Name = JOptionPane.showInputDialog("Name of player 1 (or type 'c' for computer)");
			if(p1Name.equals('c') || p1Name.equals("Computer") || p1Name.equals("comp"))
			{
				player1IsComputer = true;
			}
			
			p2Name = JOptionPane.showInputDialog("Name of player 2 (or type 'c' for computer)");
			if(p2Name.equals('c') || p2Name.equals("Computer") || p2Name.equals("comp"))
			{
				player2IsComputer = true;
			}
			
			playerTurn = PLAYER1_TURN;
			this.gameBoard[SIDE_SQUARES/2][SIDE_SQUARES/2].setState(BLACKSTONE);
			changePlayerTurn();
			
			this.repaint();
		}
		
		public void changePlayerTurn()
		{
			playerTurn *= -1;
		}
		
		/*
		public void updateSizes()
		{
		
		if(myFrame.getWidth() != bWidth || myFrame.getHeight() != bHeight + 20)
		{
			bWidth = myFrame.getWidth();
			bHeight = myFrame.getHeight() - 20;
			
			squareW = bWidth/this.SIDE_SQUARES;
			squareH = bHeight/this.SIDE_SQUARES;
			
			resetSquares(squareW, squareH);
		}
		
		}
		
		public void resetSquares(int w, int h)
		{
			for(int row = 0; row < SIDE_SQUARES; row++)
			{
				for(int col = 0; col < SIDE_SQUARES; col++)
				{
					gameBoard[row][col].setXLoc(col*w);
					gameBoard[row][col].setYLoc(row*h);
					gameBoard[row][col].setWidth(w);
					gameBoard[row][col].setHeight(h);
				}
			}
			
			
		}
		
		*/
		
		
	
}
