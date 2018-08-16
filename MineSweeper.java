import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Random;
import java.lang.Object;


public class MineSweeper extends JPanel implements ActionListener, MouseListener 
{
	
	//Create MineSweeper GUI for a 10 x 10 game
	public MineSweeper()
	{
		this.setSize(800,800);
		this.setLayout(new GridLayout(10,10));
		grid = new Boardbutton[10][10];				//Create grid as 10 x 10 of Boardbutton class object
		bomb = new boolean[10][10];				//Mines referred to as bombs
		counter = new int[10][10];
		isRevealed = new boolean[10][10];
		identifier = new String[10][10];
		buildGrid();						//Method to place bombs and calculate number counts
	}
	
	//Declaration of private variables to be used in methods
	private boolean bomb[][];
	private Boardbutton grid[][];
	private int counter[][];
	private int numBombs = 25;
	private int bombsSet = 0;
	private int columns = 10;
	private int rows = 10;
	private Random rand = new Random();
	private boolean isRevealed[][];
	private String identifier[][];
	private boolean setCounter = false;
	private int check = 0;
	
	//Method to create the properties of the grid
	private void buildGrid()
	{
		//Nested loop to iterate through all buttons to add action and mouse listeners to each
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				Boardbutton temp = new Boardbutton(i,j);
				temp.addActionListener(this);
				temp.addMouseListener(this);
				grid[i][j] = temp;
				this.add(grid[i][j]);
			}
		}
		
		//While loop to randomly place a specified number of bombs throughout the grid
		while(bombsSet < numBombs)
		{
			int randHeight = rand.nextInt(columns );
			int randWidth = rand.nextInt(rows);
			if(!grid[randWidth][randHeight].bomb)
			{
				grid[randWidth][randHeight].bomb = true;
				grid[randWidth][randHeight].identifier = "B";
				bombsSet++;
			}
		}
		
		//Nested loop to set the count of bombs next to a button for each button
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				grid[i][j].counter = setCounter(i,j);	
			}
		}
		
		//Nested loop to determine the identifier for each button in the grid
		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				if(grid[i][j].counter > 0 && !grid[i][j].bomb)
				{
					grid[i][j].identifier = Integer.toString(grid[i][j].counter);
				}
			}
		}
	}
	
	//Method to scan all adjacent buttons if they exist to determine the count of adjacent bombs
	private int setCounter(int x, int y)
	{
		int adjCounter = 0;
		
		//Try-Catch blocks to account for buttons that will be on edges with no buttons on some sides.
		//"B" identifier indicates a bomb button
		try
		{	
			if(grid[x][y + 1].identifier == "B")		//Top center adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		try
		{	
			if(grid[x][y - 1].identifier == "B")		//Bottom center adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		try
		{	
			if(grid[x + 1][y + 1].identifier == "B")	//Top right adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}

		try
		{	
			if(grid[x + 1][y].identifier == "B")		//Right adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		try
		{	
			if(grid[x + 1][y - 1].identifier == "B")	//Bottom right adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		try
		{	
			if(grid[x - 1][y + 1].identifier == "B")	//Top left adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		try
		{	
			if(grid[x - 1][y].identifier == "B")		//Left adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		try
		{	
			if(grid[x - 1][y - 1].identifier == "B")	//Bottom left adjacent button
			{
				adjCounter++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		return adjCounter;					//Return total count as identifier string value
	}
	
	//Main method to create instance of MineSweeper
	public static void main(String[] args)
	{
		MineSweeper m = new MineSweeper();
		JFrame frame = new JFrame("Chad Long's MineSweeper");
		frame.add(m);
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	//Method to branch between left and right mouse click events
	public void mouseClicked(MouseEvent e) 
	{
		Boardbutton temp = (Boardbutton) e.getSource();
		if(e.getButton() == 1)
		{
			leftClickLogic(temp);
		}
		else
		{
			rightClickLogic(temp);
		}
	}
	
	//Method to define behavior fr the player who wats to reveal the value of a button
	public void leftClickLogic(Boardbutton temp)
	{	
			//If a player selects a bomb, they lose the game
			if(temp.identifier == "B")
			{
				youLose();
			}
		
			//If a player selects a button that is not a bomb and is not marked as a bomb previously, enter here.
			if(temp.identifier != "B" && temp.identifier != "Flag")
			{
				//If player selects a blank space, indicated by counter = 0, then reveal adjacent blanks
				if(temp.counter == 0)
				{
					revealContents(grid,temp.row,temp.column);
					checkForAdjacentBlanks(temp);
				}
				//Else if a player selects a button with a counter value, reveal the counter value to the player and change the square to Red
				else
				{
					grid[temp.row][temp.column].setText(grid[temp.row][temp.column].identifier);
					grid[temp.row][temp.column].isRevealed = true;
					grid[temp.row][temp.column].setBackground(Color.RED);
				}
			}
			
			//If a player left clicks on a button previously marked as "Flag," enter here
			if(temp.identifier == "Flag")
			{
				//If a player selects a bomb, they lose the game
				if(temp.bomb = true)
				{
					youLose();
				}
				
				//If player selects a blank space, indicated by counter = 0, then reveal adjacent blanks
				if(temp.counter == 0)
				{
					temp.identifier = " ";
					revealContents(grid,temp.row,temp.column);
					checkForAdjacentBlanks(temp);
				}
				
				//If a player selects a button with a counter value, reveal the counter value to the player 
				if(temp.counter > 0)
				{
					temp.identifier = Integer.toString(temp.counter);
					revealContents(grid,temp.row,temp.column);
					checkForAdjacentBlanks(temp);
				}
			}
			
			//Check to see if player has won the game
			getWin();
	}
	
	//Method to scan for adjacent blank buttons to reveal if a player selects a balnk space
	public void checkForAdjacentBlanks(Boardbutton temp)
	{
		//Try-Catch blocks to account for buttons that will be on edges with no buttons on some sides.
		try 
		{
			//Confirm that the block is not a bomb and hasn't already been revealed
			if(grid[temp.row][temp.column + 1].identifier != "B" && grid[temp.row][temp.column + 1].isRevealed == false)	//Right adjacent button
			{
				revealContents(grid,temp.row,temp.column + 1);
				checkForAdjacentBlanks(grid[temp.row][temp.column + 1]);	//call adjacent scan recursively for this button to extend the scan
			}
		} 
		catch (ArrayIndexOutOfBoundsException e) {}
		
		try 
		{
			if(grid[temp.row][temp.column - 1].identifier != "B" && grid[temp.row][temp.column - 1].isRevealed == false)	//Left adjacent button
			{
				revealContents(grid,temp.row,temp.column - 1);
				checkForAdjacentBlanks(grid[temp.row][temp.column - 1]);
			}
		} 
		catch (ArrayIndexOutOfBoundsException e) {}
		
		try 
		{
			if(grid[temp.row + 1][temp.column].identifier != "B" && grid[temp.row + 1][temp.column].isRevealed == false)	//Top adjacent button
			{
				revealContents(grid,temp.row + 1,temp.column);
				checkForAdjacentBlanks(grid[temp.row + 1][temp.column]);
			}
		} 
		catch (ArrayIndexOutOfBoundsException e) {}
		
		try 
		{
			if(grid[temp.row - 1][temp.column].identifier != "B" && grid[temp.row - 1][temp.column].isRevealed == false)	//Bottom adjacent button
			{
				revealContents(grid,temp.row - 1,temp.column);
				checkForAdjacentBlanks(grid[temp.row - 1][temp.column]);
			}
		} 
		catch (ArrayIndexOutOfBoundsException e) {}
		
	}
	
	//Method to define logic for a player selecting a button to label with a flag to indicate a potential bomb
	public void rightClickLogic(Boardbutton temp)
	{
		grid[temp.row][temp.column].identifier = "Flag";
		grid[temp.row][temp.column].setText(grid[temp.row][temp.column].identifier);
		grid[temp.row][temp.column].setBackground(Color.BLUE);
		
		//Check to see if player has won the game
		getWin();
	}
	
	//Method to reveal the entire grid and print out the lost game message
	public void youLose()
	{
		for(int i = 0; i < 10; i++)
		{
			for( int j = 0; j < 10; j++)
			{
				revealContents(grid, i, j);
			}	
		}
			
		System.out.println("You have lost the game");
	}
	
	//Method to reveal the contents of a button
	public void revealContents(Boardbutton grid[][], int i, int j)
	{
		grid[i][j].setText(grid[i][j].identifier);
		grid[i][j].isRevealed = true;
		grid[i][j].setBackground(Color.RED);	
	}

	//Method to define the conditions for a player winning the game
	public void getWin()
	{
		int flagCounter = 0;
		
		//Scan the entire grid to see if the flag counters match the bomb locations
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				if(grid[i][j].identifier == "Flag" && grid[i][j].bomb == true)
				{
					flagCounter++;
				}
			}
		}
		
		//If the player finds all of the bombs, reveal the board and print out the winning message
		if(flagCounter == numBombs)
		{
			System.out.println("You win");
			for(int i = 0; i < 10; i++)
			{
				for(int j = 0; j < 10; j++)
				{
					if(grid[i][j].identifier == "Flag")
					{
						grid[i][j].identifier = "B";
					}
					revealContents(grid, i, j);
				}
			}
		}
	}

	//Required methods for ActionListener and MouseListener
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void actionPerformed(ActionEvent arg0) {}
	
}
