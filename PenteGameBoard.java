import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard extends JPanel implements MouseListener, MouseMotionListener
{
	
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;
	public static final int SIDE_SQUARES = 19;
	public static final int INNER_START = 7;
	public static final int INNER_END = 11;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	public static final int WIN_CAPTURES = 10;
	public static final int SLEEP_TIME = 100;
	public static boolean WIN_CAP = false;
	
	int bWidth, bHeight;
	//PenteGameRunner myGame;
	
	//private BoardSquare testSquare;
	private int squareW, squareH;
	private JFrame myFrame;
	
	//variables for playing the game
	//its assumed that P1 would be the darkstone (moves first)
	private int playerTurn;
	private boolean player1IsComputer = false;
	private boolean player2IsComputer = false;
	private String p1Name, p2Name;
	private int p1Captures, p2Captures;
	private boolean dsmt = false; //2nd darkstone placed on the board
	private boolean gameOver = false;
	
	//data structure for the board
	private BoardSquare[][] gameBoard;
	private PenteScore myScoreBoard;
	
	
	private ComputerMoveGenerator p1ComputerPlayer = null;
	private ComputerMoveGenerator p2ComputerPlayer = null;
	
	public PenteGameBoard(int w, int h, PenteScore sb)
	{
		//store these variables
		bWidth = w;
		bHeight = h;
		myScoreBoard = sb;
		
		
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
			}
			
			
		}
		
		initialDisplay();
		repaint();
		
		//add mouse listener capability
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setFocusable(true);
		
		
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
					gameBoard[row][col].setWinningSquare(false);
					p1Captures = 0;
					p2Captures = 0;
					gameOver = false;
					
				}
			}
			//this.paintImmediately(0, 0, bWidth, bHeight);
			this.repaint();
		
		}
		
		public void startNewGame(boolean firstGame)
		{
			p1Captures = 0;
			p2Captures = 0;
			gameOver = false;
			
			if(firstGame)
			{
				p1Name = JOptionPane.showInputDialog("Name of player 1 (or type 'c' for computer)");
				if(p1Name != null)
				{
					if(p1Name != null && p1Name.toLowerCase().equals("c")  || p1Name.toLowerCase().equals("Computer") || p1Name.toLowerCase().equals("comp"))
				{
					player1IsComputer = true;
					System.out.println("plaer 1 is a computer");
					p1ComputerPlayer = new ComputerMoveGenerator(this, BLACKSTONE);
				}
				}
				
			}
				myScoreBoard.setName(p1Name, BLACKSTONE);
				myScoreBoard.setCaptures(p1Captures, BLACKSTONE);
				
				//resetBoard();
				
			if(firstGame)
			{
				p2Name = JOptionPane.showInputDialog("Name of player 2 (or type 'c' for computer)");
				if(p2Name != null && p2Name.toLowerCase().equals("c") || p2Name.toLowerCase().equals("Computer") || p2Name.toLowerCase().equals("comp"))
				{
					player2IsComputer = true;
					System.out.println("plaer 2 is a computer");
					p2ComputerPlayer = new ComputerMoveGenerator(this, WHITESTONE);
				}
			}
				myScoreBoard.setName(p2Name, WHITESTONE);
				myScoreBoard.setCaptures(p2Captures, WHITESTONE);
				
				resetBoard();
				playerTurn = PLAYER1_TURN;
				this.gameBoard[SIDE_SQUARES/2][SIDE_SQUARES/2].setState(BLACKSTONE);
				dsmt = false;
				changePlayerTurn();
				System.out.println("Its now the turn of: " + playerTurn);
				
				checkForComputerMove(playerTurn);
				
				this.repaint();
				
			
		}
		
		public void changePlayerTurn()
		{
			playerTurn *= -1;
			System.out.println("Its now the turn of: " + playerTurn);
			myScoreBoard.setPlayerTurn(playerTurn);
		}
		
		public void checkClick(int clickX, int clickY)
		{
			if(!gameOver)
			{
			for(int row = 0; row < SIDE_SQUARES; row++)
			{
				for(int col = 0; col < SIDE_SQUARES; col++)
				{
				
				boolean squareClicked = gameBoard[row][col].isClicked(clickX, clickY);
				if(squareClicked)
				{	
					if(gameBoard[row][col].getState() == EMPTY)
					{
					if(!darkStoneProblem(row, col))
					{
						System.out.println("You clicked the square at [" + row + "," + col + "]");
						gameBoard[row][col].setState(playerTurn);
						checkCaptures(row, col, playerTurn);
						this.repaint();
						fiveInARow(playerTurn);
						checkForWin(playerTurn);
						this.changePlayerTurn();
						checkForComputerMove(playerTurn);
					} 
					else
					{
						JOptionPane.showMessageDialog(null, "2nd Dark Stone move must be"
								+ " outside of the light shaded box in the middle.");
					}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "This square is taken, click another");
					}
				}
				}	
			}
			}
		}
		
		public void checkForComputerMove(int whichPlayer)
		{
			if(whichPlayer == this.PLAYER1_TURN && this.player1IsComputer)
			{
				if(!gameOver)
				{
					System.out.println("checking for p1 computer move");
					int[] nextMove = this.p1ComputerPlayer.getComputerMove();
					int newR = nextMove[0];
					int newC = nextMove[1];
					gameBoard[newR][newC].setState(playerTurn);
					this.repaint();
					checkCaptures(newR, newC, playerTurn);
					this.paintImmediately(0, 0, bWidth, bHeight);
					this.checkForWin(playerTurn);
					if(!gameOver)
						{
							this.changePlayerTurn();
							checkForComputerMove(playerTurn);
						}
				}
		}else if(whichPlayer == this.PLAYER2_TURN && this.player2IsComputer)
		{
			System.out.println("checking for p2 computer move");
			int[] nextMove = this.p2ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			this.repaint();
			checkCaptures(newR, newC, playerTurn);
			this.paintImmediately(0, 0, bWidth, bHeight);
			this.checkForWin(playerTurn);
			if(!gameOver)
			{
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
		}
		}
		
		public boolean darkStoneProblem(int r, int c)
		{
			boolean dsp = false;
			
			if((!dsmt) && (playerTurn == BLACKSTONE))
			{
				if(r >= INNER_START 
					&& r <= INNER_END 
					&& c >= INNER_START 
					&& c <= INNER_END)
					{
						dsp = true;
					}	
				else
				{
					dsmt = true;
				}
			}
			return dsp;
		}
		
		public boolean darkStoneProblemComputerMoveList(int r, int c)
		{
			boolean dsp = false;
			
			if((!dsmt) && (playerTurn == BLACKSTONE))
			{
				if(r >= INNER_START 
					&& r <= INNER_END 
					&& c >= INNER_START 
					&& c <= INNER_END)
					{
						dsp = true;
					}	
				else
				{
					
				}
			}
			return dsp;
		}
		
		
		public boolean fiveInARow(int whichPlayer)
		{
			boolean isFive = false;
			
			for(int row = 0; row < SIDE_SQUARES; row++)
			{
				for(int col = 0; col < SIDE_SQUARES; col++)
					
					
				{
					
					
					for(int rL = -1; rL <=1; rL++)
					{
						for(int uD = -1; uD <=1; uD++)
						{
							if(checkWin(row, col, playerTurn, rL, uD))
							{
								isFive = true;
							}
						}
					}
				}
			}
			
			
			return isFive;
		}
		
		public void checkForWin(int whichPlayer)
		{
			if(whichPlayer == PLAYER1_TURN)
			{
				if(p1Captures >= WIN_CAPTURES)
				{
					JOptionPane.showMessageDialog(null, "Congradualations " + p1Name + ", wins!"
							+ "\n with " + p1Captures + " captures");
					gameOver = true;
				}
				else
				{
					if(fiveInARow(whichPlayer))
					{
						JOptionPane.showMessageDialog(null, "Congradualations " + p1Name + ", wins!"
								+ "\n with " + p1Captures + " captures");
						gameOver = true;
					}
				}
			}
			
			if(whichPlayer == PLAYER2_TURN)
			{
				if(p1Captures >= WIN_CAPTURES)
				{
					JOptionPane.showMessageDialog(null, "Congradualations " + p2Name + ", wins!"
							+ "\n with " + p2Captures + " captures");
					gameOver = true;
				}
				else
				{
					if(fiveInARow(whichPlayer))
					{
						JOptionPane.showMessageDialog(null, "Congradualations " + p2Name + ", wins!"
								+ "\n with " + p2Captures + " captures");
						gameOver = true;
					}
				}
			}
			
			
		}
		
		public boolean checkWin(int r, int c, int pt, int upDown, int rightLeft)
		{
			try
			{
				
			boolean win = false;
			
			if(rightLeft !=0 || upDown != 0)
			{
			if(gameBoard[r][c].getState() == pt)
			{
				if(gameBoard[r + upDown][c + rightLeft].getState() == pt)
				{
					if(gameBoard[r + upDown*2][c + rightLeft *2].getState() == pt)
					{
						if(gameBoard[r + upDown*3][c + rightLeft*3].getState() == pt)
						{
							if(gameBoard[r + upDown*4][c + rightLeft*4].getState() == pt)
							{
								
				win = true;
				gameBoard[r][c].setWinningSquare(true);
				gameBoard[r + upDown][c + rightLeft].setWinningSquare(true);
				gameBoard[r + upDown*2][c + rightLeft *2].setWinningSquare(true);
				gameBoard[r + upDown*3][c + rightLeft*3].setWinningSquare(true);
				gameBoard[r + upDown*4][c + rightLeft*4].setWinningSquare(true);
				
				
				
				System.out.println(upDown + rightLeft);
				if(pt == this.PLAYER1_TURN)
				{
					System.out.println("Congradualations " + p1Name + "! YOU WIN! " + rightLeft + "," + upDown);
					
				} else
				{
					System.out.println("Congradualations " + p2Name + "! YOU WIN! " + rightLeft + "," + upDown);
				}
				
							}
						}
					}
				}
			}
			}
			if(p1Captures >= 10 || p2Captures >= 10)
			{
				win = true;
			}
			
			return win;
			
			
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("You have an error " + e.getMessage());
				return false;
			}
		}
		
		public void checkCaptures(int r, int c, int pt)
		{
			boolean didCapture;
			
			
			for(int rL = -1; rL <=1; rL++)
			{
				for(int uD = -1; uD <=1; uD++)
				{
					didCapture = checkCapture(r, c, pt, rL, uD);

				}
			}
		}
		
		public boolean checkCapture(int r, int c, int pt, int upDown, int rightLeft)
		{
			try
			{
			
			boolean cap = false;
			
			if(gameBoard[r + upDown][c + rightLeft].getState() == pt*-1)
			{
				if(gameBoard[r + upDown*2][c + rightLeft *2].getState() == pt*-1)
				{
					if(gameBoard[r + upDown*3][c + rightLeft*3].getState() == pt)
					{
				System.out.println("It's a capture! " + rightLeft + "," + upDown);
				gameBoard[r + upDown][c + rightLeft].setState(EMPTY);
				gameBoard[r + upDown*2][c + rightLeft * 2].setState(EMPTY);
				
				cap = true;
				if(pt == this.PLAYER1_TURN)
				{
					p1Captures = p1Captures+2;
					this.myScoreBoard.setCaptures(p1Captures, playerTurn);
				} else
				{
					p2Captures = p2Captures+2;
					this.myScoreBoard.setCaptures(p2Captures, playerTurn);
				}
				
					}
				}
			}
			
			
			return cap;
			
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("You have an error " + e.getMessage());
				return false;
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub
			System.out.println("You clicked me");
			System.out.println("And you clicked at [" + e.getX() + "," + e.getY() +"]");
				this.checkClick(e.getX(), e.getY());
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void initialDisplay()
		{
			this.gameBoard[6][0].setState(BLACKSTONE);
			this.gameBoard[7][0].setState(BLACKSTONE);
			this.gameBoard[8][0].setState(BLACKSTONE);
			this.gameBoard[9][0].setState(BLACKSTONE);
			this.gameBoard[10][0].setState(BLACKSTONE);
			this.gameBoard[11][0].setState(BLACKSTONE);
			this.gameBoard[12][0].setState(BLACKSTONE);
			
			this.gameBoard[6][1].setState(BLACKSTONE);
			this.gameBoard[9][1].setState(BLACKSTONE);
			
			this.gameBoard[6][2].setState(BLACKSTONE);
			this.gameBoard[7][2].setState(BLACKSTONE);
			this.gameBoard[8][2].setState(BLACKSTONE);
			this.gameBoard[9][2].setState(BLACKSTONE);
			this.gameBoard[6][2].setState(BLACKSTONE);
			
			this.gameBoard[6][3].setState(WHITESTONE);
			this.gameBoard[7][3].setState(WHITESTONE);
			this.gameBoard[8][3].setState(WHITESTONE);
			this.gameBoard[9][3].setState(WHITESTONE);
			this.gameBoard[10][3].setState(WHITESTONE);
			this.gameBoard[11][3].setState(WHITESTONE);
			this.gameBoard[12][3].setState(WHITESTONE);
			
			this.gameBoard[6][4].setState(WHITESTONE);
			this.gameBoard[9][4].setState(WHITESTONE);
			this.gameBoard[12][4].setState(WHITESTONE);
			
			this.gameBoard[6][5].setState(WHITESTONE);
			this.gameBoard[9][5].setState(WHITESTONE);
			this.gameBoard[12][5].setState(WHITESTONE);
			
			this.gameBoard[6][6].setState(WHITESTONE);
			this.gameBoard[9][6].setState(WHITESTONE);
			this.gameBoard[12][6].setState(WHITESTONE);
			
			this.gameBoard[6][7].setState(BLACKSTONE);
			this.gameBoard[7][7].setState(BLACKSTONE);
			this.gameBoard[8][7].setState(BLACKSTONE);
			this.gameBoard[9][7].setState(BLACKSTONE);
			this.gameBoard[10][7].setState(BLACKSTONE);
			this.gameBoard[11][7].setState(BLACKSTONE);
			this.gameBoard[12][7].setState(BLACKSTONE);
			
			this.gameBoard[8][8].setState(BLACKSTONE);
			this.gameBoard[9][9].setState(BLACKSTONE);
			this.gameBoard[10][10].setState(BLACKSTONE);
			
			this.gameBoard[6][11].setState(BLACKSTONE);
			this.gameBoard[7][11].setState(BLACKSTONE);
			this.gameBoard[8][11].setState(BLACKSTONE);
			this.gameBoard[9][11].setState(BLACKSTONE);
			this.gameBoard[10][11].setState(BLACKSTONE);
			this.gameBoard[11][11].setState(BLACKSTONE);
			this.gameBoard[12][11].setState(BLACKSTONE);
			
			this.gameBoard[6][12].setState(WHITESTONE);
			
			this.gameBoard[6][13].setState(WHITESTONE);
			this.gameBoard[7][13].setState(WHITESTONE);
			this.gameBoard[8][13].setState(WHITESTONE);
			this.gameBoard[9][13].setState(WHITESTONE);
			this.gameBoard[10][13].setState(WHITESTONE);
			this.gameBoard[11][13].setState(WHITESTONE);
			this.gameBoard[12][13].setState(WHITESTONE);
			
			this.gameBoard[6][14].setState(WHITESTONE);
			this.gameBoard[6][15].setState(WHITESTONE);
			
			this.gameBoard[6][16].setState(BLACKSTONE);
			this.gameBoard[7][16].setState(BLACKSTONE);
			this.gameBoard[8][16].setState(BLACKSTONE);
			this.gameBoard[9][16].setState(BLACKSTONE);
			this.gameBoard[10][16].setState(BLACKSTONE);
			this.gameBoard[11][16].setState(BLACKSTONE);
			this.gameBoard[12][16].setState(BLACKSTONE);
			
			this.gameBoard[6][17].setState(BLACKSTONE);
			this.gameBoard[9][17].setState(BLACKSTONE);
			this.gameBoard[12][17].setState(BLACKSTONE);
			
			this.gameBoard[6][18].setState(BLACKSTONE);
			this.gameBoard[9][18].setState(BLACKSTONE);
			this.gameBoard[12][18].setState(BLACKSTONE);
			
			
		}
		
		public BoardSquare[][] getBoard()
		{
			return gameBoard;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("The mouse was moved");
			checkEnter(e.getX(), e.getY());
			repaint();
		}
		
		public void checkEnter(int enterX, int enterY)
		{
			for(int row = 0; row < SIDE_SQUARES; row++)
			{
				for(int col = 0; col < SIDE_SQUARES; col++)
				{
				
					boolean squareEntered = gameBoard[row][col].isEntered(enterX, enterY);
					if(squareEntered)
					{	
						gameBoard[row][col].setSquareColor();
						repaint();
					}	else
					{
						gameBoard[row][col].resetSquareColor();
						repaint();
					}
				} 
			} 
			repaint(); 
		}
		
		public boolean getDarkStoneMove2Taken()
		{
			return dsmt;
		}
		
	}
