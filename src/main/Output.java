package main ;

import java.io.BufferedWriter ;
import java.io.FileWriter ;
import java.io.IOException ;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList ;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Output 
{
	
	private static final String fileName = "Results.txt" ;
	
	public static void SaveInputFile(String var1, String var2)
	{
		try
		{	
			FileWriter fileWriter = new FileWriter (fileName + ".txt") ;
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
            System.out.println("Error writing to file '" + fileName + "'") ;
        }
	}	
	
	public static void ClearFile()
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
	
	public static void SaveOutputFile(List<List<Double>> popHist)
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

	public static void UpdateOutputFile()
	{
		ClearFile() ;
		List<List<Double>> recordsPop = new ArrayList<>() ;
		List<Double> artrosPopAsDoubleList = Records.artrosPopAtNRounds.stream().map(i -> (double) i).collect(Collectors.toList()) ;
		recordsPop.add(artrosPopAsDoubleList) ;
		SaveOutputFile(recordsPop) ;
	}
}