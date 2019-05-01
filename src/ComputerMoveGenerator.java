import java.util.ArrayList;

public class ComputerMoveGenerator
{
	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	
	
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
		int[] newMove = generateRandomMove();
		//TON OF PROGRAMMING TO PLAY THE GAME
		
		offMoves();
		defMoves();
		
		
		
		
		newMove = generateRandomMove();
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newMove;
	}
	
	public void offMoves() //find all offensive moves
	{
		findOneOff();
		//findTwoOff();
		//findThreeOff();
		//findFourOff();
		
		
		
		
		
		
		
		
		
	}
	
	
	public void defMoves() //find all defensive moves
	{
		findOneDef();
		//findTwoDef();
		//findThreeDef();
		//findFourDef();
		
		
		
		
		
		
		
		
		
	}
	
	public void findOneDef()
	{
		
	}
	
	public void findOneOff()
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
