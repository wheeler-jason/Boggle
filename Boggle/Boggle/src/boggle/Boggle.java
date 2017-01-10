package boggle;

import core.Board;
import inputOutput.ReadDataFile;
import userInterface.BoggleUi;

import java.util.ArrayList;

/**
 *
 * @author Jason Wheeler
 */
public class Boggle 
{
    // Array list to store data value of each die
    private static ArrayList boggleData = new ArrayList();
    
    // name of the data file
    private static String dataFileName = new String("BoggleData.txt");
    // name of the dictionary file
    private static String dictionayFileName = new String("TemporaryDictionary.txt");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // read in the data file
        ReadDataFile data = new ReadDataFile(dataFileName);
        data.populateData();

        ReadDataFile dictionary = new ReadDataFile(dictionayFileName);
        dictionary.populateData();

        // create instance of Board passing the boggleData
        Board board = new Board(data.getData());

        // create instance of BoggleUi passing board as an arguement 
        BoggleUi boggleUi = new BoggleUi(board, dictionary.getData());
    }
    
}
