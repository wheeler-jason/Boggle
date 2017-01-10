package inputOutput;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jason Wheeler
 */
public class ReadDataFile 
{
    Scanner inputFile;
    String dataFileName;
    ArrayList<String> data = new ArrayList<String>();
    
	// Constructor function
	// @param fileName: the name of the input file must be passed in when instantiating this object
    public ReadDataFile(String fileName)
    {
        dataFileName = fileName; 
    }
    
	// Reads all the input data into the "data" arraylist
    public void populateData()
    {
        try
        {
            URL url = getClass().getResource(dataFileName);
            File file = new File(url.toURI());

            inputFile = new Scanner(file);
            
            while(inputFile.hasNext())
            {
                data.add(inputFile.next());
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        finally
        {
            if(inputFile != null)
                inputFile.close();
        }
    }    
    
	// Returns the input data
    public ArrayList<String> getData()
    {
        return data;
    }
}