package core;

import java.util.ArrayList;

/**
 *
 * @author Jason Wheeler
 */
public class Board 
{
    // each board has 16 dice in a 4 x 4 layout
    private final int NUMBER_OF_DICE = 16;
    private final int NUMBER_OF_SIDES = 6;
    private final int GRID = 4;
    

    // create an ArrayList to save all Boggle data
    ArrayList<String> boggleData = new ArrayList<String>();
    // create an ArrayList with 16 dice in it and current Boggle data
    ArrayList<Die> boggleDice = new ArrayList<Die>();
    
    public Board(ArrayList<String> inData)
    {
        boggleData = inData;
    }
    
    // all the data for each die
    private void populateDice()
    {    
        Die die;        
        int counter = 0;
        
        // loop through 16 times for each die
        for(int dice = 0; dice < NUMBER_OF_DICE; dice++)
        {
            // create an instance of Die
            die = new Die();
            
            // add 6 items of the array list to populate the die sides
            for (int side = 0; side < NUMBER_OF_SIDES; side++)
            {
                die.addLetter(boggleData.get(counter).toString());
                counter++;
            }
            
            // Temporary for Assignment 1
            /* System.out.print("Die " + dice + ": ");
            die.displayAllLetters();
            System.out.println(); */
            
            boggleDice.add(die);
        }
        
    }
    
    // current Boggle view, only one side of each die is displayed
    public ArrayList shakeDice()
    {    
        int counter = 0;
        
        populateDice();
        
        for(int i = 0; i < 16; i++)
        {
            Die die = boggleDice.get(i);
            die.getLetter();
        }   
        
        return boggleDice;
    }
}
