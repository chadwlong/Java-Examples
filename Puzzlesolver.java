import java.io.*;
import java.util.*;

public class Puzzlesolver 
{
	//Method to check if solution is found by Solver method
	public static Boolean arrayCheck(char puzzle[][])
	{
		//First check character set to A
		char check = 'A';
		
		//Fixed size of puzzles is 4 x 4
		int dimension = 4;
		
		//Nested loop through entire 4 x 4 puzzle to find A through O
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				//Check to find character in puzzle
				if(check == puzzle[i][j])
				{
					//increment character value to search for next character
					check++;
				}
				
				//End search if all characters up to O are found indicating the solution is found
				else if(check == 'P')
				{
					return true;
				}
				
				//If all characters up to O not found, return no solution
				else
				{
					return false;
				}		
			}
		}
		//default return value
		return false;
	}
	
	//Method to find an entire row of the puzzle for printing
	public static String printRow(int row, char puzzle[][])
	{
		int dimension = 4;
		
		String temp = "";
		
		for(int i = 0; i < dimension; i++)
		{
			temp += puzzle[row][i];
		}
		
		return temp;
	}
	
	//Main method to read in original puzzle file and start the solving algorithm
	public static void main(String[] args) throws IOException 
	{
		//Default array size higher than guaranteed 16 from fix puzzle size of 4 x 4
		int Array_Size = 50;
		
		int dimension = 4;
		
		//Initial depth of recursion set at 1 to find single move solutions
		int depth = 1;
		
		//Read in puzzle from file
		String filename = args[0];
		
		File f = new File(filename);
		
		Scanner scan = new Scanner(f);
		
		String[] ref = new String[Array_Size];
		
		int scanner_Marker = 0;
		
		//Loop through all lines of puzzle
		while(scan.hasNextLine())
		{
			ref[scanner_Marker] = scan.nextLine();
			scanner_Marker++;
		}
		
		scan.close();
		
		//Initialize 4x4 character array to hold puzzle
		char [][] puzzle = new char [dimension][dimension];
		
		//Nested loop to iterate through initial array to set beginning values
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				puzzle[i][j] = ref[i].charAt(j);
			}
		}
		
		//Find the coordinates of the Dot, seen as a period in the puzzle
		int r = findDotRow(puzzle);
		
		int c = findDotColumn(puzzle);
		
		//Initiate recursive solver algorithm  
		Solver(puzzle, r, c, depth);
		
		//Print out moves list
		System.out.println(moves_List);
		
		//Print out number of moves
		System.out.println(iteration + " " + "moves");
		
	}
	
	//Method to swap two characters based on a given direction and return the new puzzle
	public static char [][] swap(char puzzle[][], int row, int column, int direction)
	{
		//Swap the current character with the character above
		if(direction == 1)
		{
			char temp = ' ';
			temp = puzzle[row][column];
			puzzle[row][column] = puzzle[row - 1][column];
			puzzle[row - 1][column] = temp;
			return puzzle;
		}
		
		//Swap the current character with the character to the right
		if(direction == 2)
		{
			char temp = ' ';
			temp = puzzle[row][column];
			puzzle[row][column] = puzzle[row][column + 1];
			puzzle[row][column + 1] = temp;
			return puzzle;
		}
		
		//Swap the current character with the character to the left
		if(direction == 3)
		{
			char temp = ' ';
			temp = puzzle[row][column];
			puzzle[row][column] = puzzle[row][column - 1];
			puzzle[row][column - 1] = temp;
			return puzzle;
		}
		
		//Swap the current character with the character below
		if(direction == 4)
		{
			char temp = ' ';
			temp = puzzle[row][column];
			puzzle[row][column] = puzzle[row + 1][column];
			puzzle[row + 1][column] = temp;
			return puzzle;
		}
		return puzzle;
	}
	
	//Variables needed for Solver method
	static boolean win = false;		//State for indicating a solution
	static int iteration = 1;		//Value to track the depth level
	static String moves_List = "";	//Solution string of moves to print to console
	
	public static void Solver(char puzzle[][], int row, int column, int depth)
	{
		int direction = 0;
		
		int end = 0;
		
		//Only check if depth still has at least one more move before finding the solution
		if(depth != end)
		{	
			//UP direction = 1
			//Only search above if not on the top row of the puzzle
			if(row > 0)
			{
				direction = 1;
				puzzle = swap(puzzle, row, column, direction);
				Solver(puzzle, row - 1, column, depth - 1);			//Recursive call 
				puzzle = swap(puzzle, row, column, direction);
				
				//Check to see if the puzzle is solved
				if(win)
				{
					//Add move to solution string and return
					moves_List = "(" + row + "," + column + ")" + " " + "->" + " " + moves_List;
					return;
				}
			}
			
			//RIGHT direction = 2
			//Only search to the right if not on the far right column of the puzzle
			if(column < 3)
			{
				direction = 2;
				puzzle = swap(puzzle, row, column, direction);
				Solver(puzzle, row, column + 1, depth - 1);			//Recursive call
				puzzle = swap(puzzle, row, column, direction);
				
				//Check to see if the puzzle is solved
				if(win)
				{
					//Add move to solution string and return
					moves_List = "(" + row + "," + column + ")" + " " + "->" + " " + moves_List;
					return;
				}
			}
			
			//LEFT direction = 3
			//Only search to the left if not on the far left column of the puzzle
			if(column > 0)
			{
				direction = 3;
				puzzle = swap(puzzle, row, column, direction);
				Solver(puzzle, row, column - 1, depth - 1);			//Recursive call
				puzzle = swap(puzzle, row, column, direction);
				
				//Check to see if the puzzle is solved
				if(win)
				{
					//Add move to solution string and return
					moves_List = "(" + row + "," + column + ")" + " " + "->" + " " + moves_List;
					return;
				}
			}
			//DOWN direction = 4
			//Only search below if not on the bottom row of the puzzle
			if(row < 3)
			{
				direction = 4;
				puzzle = swap(puzzle, row, column, direction);
				Solver(puzzle, row + 1, column, depth - 1);			//Recursive call
				puzzle = swap(puzzle, row, column, direction);
				
				//Check to see if the puzzle is solved
				if(win)
				{
					//Add move to solution string and return
					moves_List = "(" + row + "," + column + ")" + " " + "->" + " " + moves_List;
					return;
				}
			}
		}
		//Base Case
		else
		{
			//Check for a winning solution if no more moves available at current depth or location
			win = arrayCheck(puzzle);
			
			if(win)
			{
				//Add move to solution string and return
				moves_List = "(" + row + "," + column + ")";
				return;
			}
		}
		
		//If solution not found at current depth, increase the depth and restart the Solver algorithm
		if(depth == iteration)
		{
			iteration++;								//Increase number of moves, or depth, tracker
			Solver(puzzle, row, column, depth + 1);		//Solver call at increased depth
		}
	}
	
	//Method to find the column location of the period character
	public static int findDotColumn(char puzzle[][])
	{
		int dimension = 4;
		
		char marker = '.';
		
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				if(puzzle[i][j] == marker)
				{
					return j;
				}
			}
		}
		//default return value
		return -1;
	}
	
	//Method to find the row location of the period character
	public static int findDotRow(char puzzle[][])
	{
		int dimension = 4;
		
		char marker = '.';
		
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				if(puzzle[i][j] == marker)
				{
					return i;
				}
			}
		}
		//default return value
		return -1;
	}
}
