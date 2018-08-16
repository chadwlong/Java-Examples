import javax.swing.*;
import java.awt.*;

public class Boardbutton extends JButton
{
	public boolean bomb;
	public int counter;
	public int row;
	public int column;
	public boolean isRevealed;
	public String identifier;
	
	Boardbutton(int row, int column)
	{
		this.row = row;
		this.column = column;
		counter = 0;			//Integer to hold the count of the number of bombs that are adjacent to the button
		bomb = false;			//Boolean to identify if button is or is not a mine, called a bomb in this case
		isRevealed = false;		//Boolean to identify if button "identifier" field is or is not revealed
		identifier = " ";		//String to indicate value of button
	}
}
