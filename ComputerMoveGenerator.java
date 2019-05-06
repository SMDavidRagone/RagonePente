import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComputerMoveGenerator
{
	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	public static final int ONE_DEF = 1;
	public static final int TWO_DEF = 2;
	public static final int TWO_OPEN = 3;
	public static final int TWO_CAP = 4;
	
	PenteGameBoard myGame;
	int myStone;
	
	ArrayList<CMObject> oMoves = new ArrayList<CMObject>(); //offensive moves
	ArrayList<CMObject> dMoves = new ArrayList<CMObject>(); //defensive moves
	
	
	
	//probably need array lists
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor)
	{
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as a player " + myStone);
	}
	
	public int[] getComputerMover()
	{
		int[] newMove = new int[2];
		//TON OF PROGRAMMING TO PLAY THE GAME
		
		newMove[0] = -1;
		newMove[1] = 0;
		dMoves.clear();
		oMoves.clear();
		
		offMoves();
		defMoves(); //find defensive moves
		sortDefPriorities();
		
		System.out.println("First Def move: " + dMoves.get(0));
		System.out.println("Last Def move: " + dMoves.get(dMoves.size()-1));
		
	
		
		
		if(dMoves.size() > 0)
		{
			//Testing
			//int whichOne = (int)(Math.random() * dMoves.size());
			CMObject ourMove = dMoves.get(0);
			newMove[0] = ourMove.getRow();
			newMove[1] = ourMove.getCol();
			
		} else
		{
			newMove = generateRandomMove();
		}
		
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newMove;
	}
	
	public void sortDefPriorities()
	{
		//Comparator<CMObject> compareByPriority = (CMObject o1, CMObject o2) ->
		//o1.getPriorityInt().compareTo(o2.getPriorityInt());
		Collections.sort(dMoves);
	}
	
	public void offMoves() //find all offensive moves
	{
	//	findOneOff(row, col);
		//findTwoOff(row, col);
		//findThreeOff(row, col);
		//findFourOff(row, col);
		
		
		
		
		
		
		
		
		
	}
	
	
	public void defMoves() //find all defensive moves
	{
		for(int row = 0; row < PenteGameBoard.SIDE_SQUARES; row++)
		{
			for(int col = 0; col < PenteGameBoard.SIDE_SQUARES; col++)
			{
				if(myGame.getBoard()[row][col].getState() == myStone * -1)
				{
				
				
				
				
				
				
				findOneDef(row, col);
				findTwoDef(row, col);
				//findThreeDef(row, col);
				//findFourDef(row, col);
				
				
				
				
				
				
				
				
				
				}
			}
		}
		
		
		
		
		
	}
	
	public boolean isOnBoard(int r, int c)
	{
		boolean isOn = false;
		if(r >=0 && r <= PenteGameBoard.SIDE_SQUARES)
		{
			if(c >= 0 && c <= PenteGameBoard.SIDE_SQUARES)
			{
				isOn = true;
			}
		}
		
		return isOn;
	}
	
	public void findOneDef(int r, int c)
	{
		for(int rL = -1; rL <=1; rL++)
		{
			for(int uD = -1; uD <=1; uD++)
			{
				try
				{
					if(myGame.getBoard()[r + rL][c + uD].getState() == PenteGameBoard.EMPTY)
					{
						CMObject newMove = new CMObject();
						newMove.setRow(r + rL);
						newMove.setCol(c + uD);
						newMove.setPriority(ONE_DEF);
						newMove.setMoveType(DEFENSE);
						dMoves.add(newMove);
					}
				} catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Checked square is off the board in"
							+ " findOneDef at [" + r + "," + c + "]");
				}
			}
		}
	}
	
	public void printPriorities()
	{
		for(CMObject m: dMoves)
		{
			System.out.println(m);
		}
	}
	
	public void setDefMove(int r, int c, int p)
	{
		CMObject newMove = new CMObject();
		newMove.setRow(r);
		newMove.setCol(c);
		newMove.setPriority(p);
		newMove.setMoveType(DEFENSE);
		dMoves.add(newMove);
	}
	
	public void findTwoDef(int r, int c)
	{
		for(int rL = -1; rL <=1; rL++)
		{
			for(int uD = -1; uD <=1; uD++)
			{
				try
				{
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == PenteGameBoard.EMPTY)
						{
							//if r-rL is the wall
							if(isOnBoard(r-rL, c-uD) == false)
							{
								setDefMove(r +(rL * 2), c + (uD * 2), TWO_DEF);
								CMObject newMove = new CMObject();
								newMove.setRow(r + rL);
								newMove.setCol(c + uD);
								newMove.setPriority(TWO_DEF);
								newMove.setMoveType(DEFENSE);
								dMoves.add(newMove);
							} else if(myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
							{
								setDefMove(r +(rL * 2), c + (uD * 2), TWO_OPEN);
								
							} else if(myGame.getBoard()[r - rL][c - uD].getState() == myStone)
							{
								setDefMove(r +(rL * 2), c + (uD * 2), TWO_CAP);
								
							}
							
							
							//if r-Rl
							
							
							
							
							
							
							
							
							CMObject newMove = new CMObject();
							newMove.setRow(r + rL);
							newMove.setCol(c + uD);
							newMove.setPriority(TWO_DEF);
							newMove.setMoveType(DEFENSE);
							dMoves.add(newMove);
						}
					}
				} catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Checked square is off the board in"
							+ " findOneDef at [" + r + "," + c + "]");
				}
			}
		}
	}
	
	
	public void findOneOff(int r, int c)
	{
		
	}
	
	
	
	public int[] generateRandomMove()
	{
		int[] move = new int[2];
		
		boolean done = false;
		
		int newR, newC;
		do
		{
			newR = (int)(Math.random() * PenteGameBoard.SIDE_SQUARES);
			newC = (int)(Math.random() * PenteGameBoard.SIDE_SQUARES);
			
			if(myGame.getBoard()[newR][newC].getState() == PenteGameBoard.EMPTY)
			{
				done = true;
				move[0] = newR;
				move[1] = newC;
			}
			
		} while(!done);
		
			return move;
	}
	
	
	public void sleepForAMove() throws InterruptedException
	{
		Thread currThread = Thread.currentThread();
		
		currThread.sleep(PenteGameBoard.SLEEP_TIME);
	}
}
