import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BoardSquare 
{
	
	private int xLoc, yLoc;
	private int sWidth, sHeight;
	
	private int sState; // Open, Player1, Player 2
	
	private Color sColor; //for the main color of the squares
	private Color lColor; //for the lines that cross in the center of the squares
	private Color bColor; //for the border color of the squares
	private Color innerC; //for the inner 5x5 squares
	private Color darkStoneColor = new Color(25, 25, 25);
	private Color lightStoneColor = new Color(255, 255, 255);
	private Color shadowWhite = new Color(200, 200, 200);
	private Color shadowBlack = new Color(15, 15, 15);
	private Color stoneShine = new Color(230,230, 230);
	private Color hoverColor = new Color(40, 170, 25);
	private boolean isInner = false;
	private boolean isWinningSquare = false;
	
	
	//constructor
	public BoardSquare(int x, int y, int w, int h)
	{
		
		xLoc = x;
		yLoc = y;
		sWidth = w;
		sHeight = h;
		
		sColor = new Color(40, 110, 25);
		lColor = new Color(40, 110, 25);
		bColor = new Color(255, 255, 255);
		innerC = new Color(40, 150, 25);
		
		
		
		sState = PenteGameBoard.EMPTY;
		
		
	}
	
		public void setInner()
		{
			isInner = true;
		}
		
	
	public void drawMe(Graphics g)
	{
		//step 1 draw basic color...
		if(isInner)
		{
			g.setColor(innerC);
			
		} else
		{
			g.setColor(sColor);
		}
		
		
		//g.setColor(sColor);
		g.fillRect(xLoc, yLoc, sWidth, sHeight);
		
		//step 2 draw border colorborder color...
		
			
		g.setColor(bColor);
		g.drawRect(xLoc, yLoc, sWidth, sHeight);
		
		//new step 3 drawing the shadow on the stones then the shine
		if(sState != PenteGameBoard.EMPTY)
		{
			if(sState == PenteGameBoard.BLACKSTONE)
			{
				
				g.setColor(shadowBlack);
			} else
			{
				g.setColor(shadowWhite);
			}
			
			g.fillOval(xLoc, yLoc+3, sWidth-4, sHeight-4);
			
			
			
	
	
		}
		
		if(isInner)
		{
			g.setColor(innerC);
			
		} else
		{
		g.setColor(lColor);
		}
		//horizontal line
	//	g.drawLine(xLoc, yLoc + sHeight/2, xLoc + sWidth, yLoc + sHeight/2);
		//vertical line
	//	g.drawLine(xLoc + sWidth/2, yLoc, xLoc + sWidth/2, yLoc + sHeight);
		
		
			
		if(sState == PenteGameBoard.BLACKSTONE)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			
			g2.setColor(darkStoneColor);
			
			g2.setStroke(new BasicStroke(1));
			
			
			g.fillOval(xLoc+2, yLoc+2, sWidth-4, sHeight-4);
			
			//drawing the shine on stones
			g2.setStroke(new BasicStroke(3));
			g.setColor(stoneShine);
			g.drawArc(
					xLoc+(int)(sWidth*0.55),
					yLoc+10, 
					(int)(sWidth*0.20), 
					(int)(sHeight*0.25),
					0,
					90
					);
			g2.setStroke(new BasicStroke(1));
		}
		
		if(sState == PenteGameBoard.WHITESTONE)
		{
			g.setColor(lightStoneColor);
			g.fillOval(xLoc+3, yLoc+3, sWidth-6, sHeight-6);
			
		}
		
		if(isWinningSquare == true)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			
			g2.setColor(Color.RED);
			
			g2.drawOval(xLoc+2, yLoc+2, sWidth-4, sHeight-4);
			g2.setStroke(new BasicStroke(1));
			
			
			
			
		}
		
	}
	
	public void setState(int newState)
	{
		if(newState < -1 || newState > 1)
		{
			System.out.println(newState + "is an illegal. State must be between -1 and 1");
		} else
		{
			sState = newState;
		}
		
	}
	
	public void setSquareColor()
	{
		sColor = hoverColor;
	}
	
	//accessor method to get state for board
	public int getState()
	{
		return sState;
	}
	
	public void setWidth(int newW)
	{
		sWidth = newW;
	}
	
	public void setHeight(int newH)
	{
		sHeight = newH;
	}
	
	public void setXLoc(int newX)
	{
		xLoc = newX;
	}
	
	public void setYLoc(int newY)
	{
		yLoc = newY;
	}
	
	
	public void resetSquareColor()
	{
		sColor = new Color(40, 110, 25);
	}
	
	public boolean isClicked(int clickX, int clickY)
	{
		boolean didYouClickMe = false;
		
		if(xLoc < clickX && clickX < xLoc + sWidth)
		{
			if(yLoc < clickY && clickY < yLoc + sHeight)
			{
				didYouClickMe = true;
			}
		}
		
		return didYouClickMe;
	}
	
	
	public boolean isEntered(int enterX, int enterY)
	{
		boolean didYouEnterMe = false;
		
		if(xLoc < enterX && enterX < xLoc + sWidth)
		{
			if(yLoc < enterY && enterY < yLoc + sHeight)
			{
				didYouEnterMe = true;
			}
		}
		if(xLoc > enterX && enterX > xLoc + sWidth)
		{
			if(yLoc > enterY && enterY > yLoc + sHeight)
			{
				didYouEnterMe = false;
			}
		}
		
		return didYouEnterMe;
	}
	
	
	public void setWinningSquare(boolean newState)
	{
		isWinningSquare = newState;
	}

	public void setSquareColor1() {
		// TODO Auto-generated method stub
		sColor = hoverColor;
	}
	
}