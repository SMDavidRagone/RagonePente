import javax.swing.JFrame;

public class PenteGameRunner 
{
	
	public static void main(String[] args)
	{
		
		int gWidth = 19*60;
		int gHeight = 19*60;
		
		JFrame theGame = new JFrame("Play Pente");
		theGame.setSize(gWidth, gHeight + 20);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight);
		theGame.add(gb);
		
		theGame.setVisible(true);
		gb.startNewGame();
		
		
	}
	
	
}
