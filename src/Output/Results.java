package Output ;

import java.io.BufferedWriter ;
import java.io.FileWriter ;
import java.io.IOException ;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList ;

public abstract class Results 
{
	public static void SaveInputFile(String filename, String var1, String var2)
	{
		try
		{	
			FileWriter fileWriter = new FileWriter (filename + ".txt") ;
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) ; 
			
			// Section 1
			bufferedWriter.write("**Section1**") ;
			bufferedWriter.newLine() ;
			bufferedWriter.write("Variables") ;
			bufferedWriter.newLine() ;
			bufferedWriter.write(var1 + "	") ;	
			bufferedWriter.write(var2 + "	") ;
			bufferedWriter.newLine() ;
			bufferedWriter.write("_____________________________________________________") ;
			
			bufferedWriter.close() ;
		}		
		catch(IOException ex) 
		{
            System.out.println("Error writing to file '" + filename + "'") ;
        }
	}	
	
	public static void ClearFile(String fileName)
	{
		try
		{	
			Files.newBufferedWriter(Paths.get(fileName) , StandardOpenOption.TRUNCATE_EXISTING) ;
		}		
		catch(IOException ex) 
		{
            System.out.println("Error clearing file '" + fileName + "'") ;
        }
	}
	
	public static void SaveOutputFile(String fileName, ArrayList<ArrayList<Double>> popHist)
	{
		try
		{	
			FileWriter fileWriter = new FileWriter (fileName, true) ;
			BufferedWriter bfWriter = new BufferedWriter(fileWriter) ; 
			
			for (int i = 0 ; i <= popHist.size() - 1 ; i += 1)
			{
				for (int j = 0 ; j <= popHist.get(i).size() - 1 ; j += 1)
				{
					bfWriter.write(""+popHist.get(i).get(j)) ;
					bfWriter.newLine() ;		
				}
			}			
			bfWriter.close() ;
		}		
		catch(IOException ex) 
		{
            System.out.println("Error writing to file '" + fileName + "'") ;
        }
	}	
}