package core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jason Wheeler
 */
public class Die 
{
    private final int NUMBER_OF_SIDES = 6;
    String currentLetter;
    
    // create the array for the letters
    private ArrayList<String> letters = new ArrayList<String>();
    
	// Grab a random letter from the arraylist of letters
    public void randomLetter()
    {
        Random random = new Random();
        int value =  random.nextInt(NUMBER_OF_SIDES);      
        currentLetter = letters.get(value);
    }
    
	// Get a random letter from the arraylist of letters
    public String getLetter()
    {
        randomLetter();
        return currentLetter;
    }

	// Add a letter to the arraylist of letters
    public void addLetter(String letter)
    {
        letters.add(letter);
    }
    
	// Print all the letters in the arraylist
    public void displayAllLetters()
    {     
        for(String value : letters)
        {
            System.out.print(value + " ");
        }
    }
}
