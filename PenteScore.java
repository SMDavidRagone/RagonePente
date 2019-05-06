import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PenteScore extends JPanel implements ActionListener
{
	//data
	private JLabel p1Name, p2Name;
	private JTextField p1Captures;
	private JTextField p2Captures;
	private JTextField wTurnField;
	private JButton resetButton;
	private Color backColor;
	
	private int spWidth, spHeight;
	
	private Font myFont = new Font("Arial", Font.PLAIN, 24);
	private Color bBlack = new Color(50, 70, 50);
	
	private PenteGameBoard myBoard = null;
	
	private boolean firstGame = true;
	
	public PenteScore(int w, int h)
	{
		backColor = new Color(50, 100, 50);
		
		spWidth = w;
		spHeight = h;
		
		this.setSize(spWidth, spHeight);
		this.setBackground(backColor);
		
		this.setVisible(true);
		
		addInfoPlaces();
		
		
	}
		
		public void addInfoPlaces()
		{
			JPanel p1Panel = new JPanel();
			p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.PAGE_AXIS));
			p1Panel.setSize(spWidth, (int)(spHeight * 0.45));
			p1Panel.setBackground(new Color(50, 50, 50));
			
		//	p1Panel.setOpaque(false);
			
			p1Name = new JLabel("Player 1 Name");
			p1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
			p1Name.setFont(myFont);
			p1Name.setForeground(Color.WHITE);
			p1Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			p1Captures = new JTextField("Player 1 Captures");
			p1Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
			p1Captures.setFont(myFont);
			p1Captures.setForeground(Color.WHITE);
			p1Captures.setBackground(bBlack);
			p1Captures.setHorizontalAlignment(SwingConstants.CENTER);
			p1Captures.setFocusable(false); //this stops people typing in score
			
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,50)));
			p1Panel.add(p1Name);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			p1Panel.add(p1Captures);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			
			Border b = BorderFactory.createLineBorder(Color.WHITE, 4, true);
			p1Panel.setBorder(b);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			this.add(p1Panel);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
			
			resetButton = new JButton("New Game");
			resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			resetButton.setFont(myFont);
			resetButton.setForeground(Color.WHITE);
			resetButton.setHorizontalAlignment(SwingConstants.CENTER);
			resetButton.addActionListener(this);
			
			this.add(resetButton);
			
			resetButton.add(Box.createRigidArea(new Dimension(spWidth-40,80)));
			Border b2 = BorderFactory.createLineBorder(Color.WHITE, 4, true);
			resetButton.setBorder(b2);
			
			
			JPanel p2Panel = new JPanel();
			p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.PAGE_AXIS));
			p2Panel.setSize(spWidth, (int)(spHeight * 0.45));
			p2Panel.setBackground(new Color(180, 250, 200));
			//p2Panel.setOpaque(false);
			
			p2Name = new JLabel("Player 2 Name");
			p2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
			p2Name.setFont(myFont);
			p2Name.setForeground(Color.BLACK);
			p2Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			p2Captures = new JTextField("Player 2 Captures");
			p2Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
			p2Captures.setFont(myFont);
			p2Captures.setForeground(Color.BLACK);
			p2Captures.setHorizontalAlignment(SwingConstants.CENTER);
			p2Captures.setFocusable(false);
			
			p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,50)));
			p2Panel.add(p2Name);
			p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			p2Panel.add(p2Captures);
			p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			
			Border b3 = BorderFactory.createLineBorder(Color.WHITE, 4, true);
			
			p2Panel.setBorder(b3);
			
			this.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			this.add(p2Panel);
			this.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
			
			
			//whose turn info
			JPanel wTurn = new JPanel();
			wTurn.setLayout(new BoxLayout(wTurn, BoxLayout.Y_AXIS));
			wTurn.setSize(spWidth, (int)(spHeight * 0.25));
			wTurn.setBackground(new Color(180, 250, 200));
			
			wTurnField = new JTextField("It's ??? turn now");
			wTurnField.setAlignmentX(Component.CENTER_ALIGNMENT);
			wTurnField.setFont(myFont);
			wTurnField.setForeground(bBlack);
			wTurnField.setHorizontalAlignment(SwingConstants.CENTER);
			wTurnField.setFocusable(false);
			
			wTurn.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
			wTurn.add(wTurnField);
			wTurn.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
			
			wTurn.setBorder(b2);
			
			
			this.add(Box.createRigidArea(new Dimension(spWidth-40,10)));
			this.add(wTurn);
		}
		
		public void setName(String n, int whichPlayer)
		{
			if(whichPlayer == PenteGameBoard.BLACKSTONE)
			{
				p1Name.setText("Player 1: " + n);
			} else
			{
				p2Name.setText("Player 2: " + n);
			}
			repaint();
			
			
		}
		
		public void setCaptures(int c, int whichPlayer)
		{
			if(whichPlayer == PenteGameBoard.BLACKSTONE)
			{
				p1Captures.setText(Integer.toString(c));
				Rectangle r = p1Captures.getVisibleRect();
				p1Captures.paintImmediately(r);
			}
			else
			{
				p2Captures.setText(Integer.toString(c));
				Rectangle r = p2Captures.getVisibleRect();
				p2Captures.paintImmediately(r);
			}
			
			
			
		}
		
		public void setPlayerTurn(int whichPlayer)
		{
			if(whichPlayer == PenteGameBoard.BLACKSTONE)
			{
				wTurnField.setBackground(bBlack);
				wTurnField.setForeground(Color.WHITE);
				System.out.println(p1Name.getText());
				int cLoc = p1Name.getText().indexOf(":");
				String n = p1Name.getText().substring(cLoc + 2, p1Name.getText().length());
				wTurnField.setText("It's " + n + "'s turn now");
				//wTurnField.setText(p1Name.getText());
			}
			else
			{
			
			wTurnField.setBackground(new Color(250, 250, 250));
			wTurnField.setForeground(Color.BLACK);
			System.out.println(p2Name.getText());
			int cLoc = p2Name.getText().indexOf(":");
			String n = p2Name.getText().substring(cLoc + 2, p2Name.getText().length());
			wTurnField.setText("It's " + n + "'s turn now");
			//wTurnField.setText(p2Name.getText());
			
			}
			
			
			
		if(firstGame = true)
		{
			wTurnField.repaint();
		}
		else
		{
			Rectangle r = wTurnField.getVisibleRect();
			wTurnField.paintImmediately(r);
		}
		}

		
		public void setGameBoard(PenteGameBoard gb)
		{
			myBoard = gb;
		}
		
		
		
		
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			
			System.out.println("You clicked me");
			JOptionPane.showMessageDialog(null, "Starting a new game now");
			firstGame = false;
			if(myBoard != null) myBoard.startNewGame(false);
			
		}
			
			
			
		
}
