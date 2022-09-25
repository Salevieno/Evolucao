package Output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Results 
{
	public void PrintInput (String StringInput, double[] ArrayInput, double[][] DoubleArrayInput)
	{
		/*This function prints the full input*/
		
		System.out.println();
		System.out.println("*Input*");	
		System.out.println("Example string input = " + StringInput);
		System.out.println("Example array input = " + Arrays.toString(ArrayInput));
		System.out.println("Example double array input = " + Arrays.deepToString(DoubleArrayInput));
	}
	
	public void PrintOutput (String StringOutput, double[] ArrayOutput, double[][] DoubleArrayOutput)
	{
		/*This function prints the main output*/
		
		System.out.println();
		System.out.println("*Output*");
		System.out.println("Example string output = " + StringOutput);
		System.out.println("Example array output = " + Arrays.toString(ArrayOutput));
		System.out.println("Example double array output = " + Arrays.deepToString(DoubleArrayOutput));
	}
	
	public void SaveInputFile(String filename, String var1, String var2)
	{
		try
		{	
			FileWriter fileWriter = new FileWriter (filename + ".txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 
			
			// Section 1
			bufferedWriter.write("**Section1**");
			bufferedWriter.newLine();
			bufferedWriter.write("Variables");
			bufferedWriter.newLine();
			bufferedWriter.write(var1 + "	");	
			bufferedWriter.write(var2 + "	");
			bufferedWriter.newLine();
			bufferedWriter.write("_____________________________________________________");
			
			bufferedWriter.close();
		}		
		catch(IOException ex) 
		{
            System.out.println("Error writing to file '" + filename + "'");
        }
	}	
	
	public void SaveOutputFile(String filename, int[][] PopHist, int[][] FoodHist)
	{
		try
		{	
			FileWriter fileWriter = new FileWriter (filename + ".txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 
			
			// Section 1
			for (int i = 0; i <= PopHist.length - 1; i += 1)
			{
				bufferedWriter.write("Artros = " + PopHist[i][0] + " " + PopHist[i][1] + " Food = " + FoodHist[i][0]);
				bufferedWriter.newLine();	
			}			
			bufferedWriter.close();
		}		
		catch(IOException ex) 
		{
            System.out.println("Error writing to file '" + filename + "'");
        }
	}	
}