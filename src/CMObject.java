
public class CMObject {
	//DATA
	private int priority = 0;
	private int row = -1, col = -1;
	private int moveType = 0; //Offensive or Defensive
	
	
	//I have no constructor so I use the default one
	
	//Set methods
	public void setPriority(int newP)
	{
		priority = newP;
	}
	
	public void setRow(int newR)
	{
		row = newR;
	}
	
	public void setCol(int newC)
	{
		col = newC;
	}
	
	public void setMoveType(int newMT)
	{
		moveType = newMT;
	}
	
	//Set accessor get methods
		public int getPriority()
		{
			return priority;
		}
		
		public int getRow()
		{
			return row;
		}
		
		public int getCol()
		{
			return col;
		}
		
		public int getMoveType()
		{
			return moveType;
		}
	
}
