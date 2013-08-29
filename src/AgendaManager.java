// **************************************************************************************************************/
// Created By : Navaneetha Krishnan & Reddigari Gopinath Reddy
// Version    : 1.0
// Date       : 11/01/2012
// Course     : Advanced Operating System
// **************************************************************************************************************/

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class AgendaManager {

	// Associated array lists
 	 ArrayList<Integer> inputlist 	= new ArrayList<Integer>(); 
	 ArrayList<String>  Namelist		= new ArrayList<String>(); 
	 ArrayList<Integer> insinputlist = new ArrayList<Integer>(); 
	 ArrayList<String>  insnamelist	= new ArrayList<String>(); 
     
	// Variables which are used throughout in the program
	 public String[] input;
	 public int Execcycle = 0;
	 public int arrsize;
	 long Start_time;
	 long End_time;
    
	 //constuctor 
 	 public AgendaManager(){
	 
	 int cnt=1;
	 int namecnt=1;
	 
	 //reading input file
	 try {
	 		read_file();
	 }
	 catch(Exception e){
	 		System.out.println("Error in input file!!");
	 }
	 
	 inputlist.add(-1);
	 Namelist.add("Head");
	 
	 for (int i=0;i<input.length;i++)
	 {
	  
	 	// Check if any input format
		if ((!(input[i].isEmpty())) && ((input[i].indexOf('(')==-1) && (input[i].indexOf(')')==-1)))
		{ System.out.println("Input format in the file is wrong");
		  System.exit(1);
		}
	 	
		if (!(input[i].isEmpty()))
		{
			String[] splittedarr = input[i].split("[(),]+");

			for (int j=0;j<splittedarr.length;j++)
			{	
				splittedarr[j].trim();
			
				if ((!(splittedarr[j].isEmpty())) && (!(splittedarr[j].equals(" "))))
				{
					if (isInteger(splittedarr[j])) {
						if (i==0) 	
							inputlist.add((inputlist.size()),(Integer.parseInt((splittedarr[j].trim()))));		
						else 
						insinputlist.add(Integer.parseInt((splittedarr[j].trim())));
					}
					else{		
						if (i==0) 
							Namelist.add((Namelist.size()),splittedarr[j]);	
						else 
							insnamelist.add(splittedarr[j]);	
					}
				}		
			}
	 
			if (i==0) {
				// Building initial Heap
				buildheap();
				heap_extract_max();
			}
			else{
				// Insert into heap
				for(int k=0;k<insinputlist.size();k++)
				{	
					heap_insert(insnamelist.get(k),insinputlist.get(k));				
				}
				insinputlist.clear();
				insnamelist.clear();
				heap_extract_max();
			}	 
		}	
	 }
	 
	 // After input file read
	 while (inputlist.size() >1) {	
		heap_extract_max();
	 }
		
	 // Displaying Elapsed time	
	 End_time = System.currentTimeMillis();
	 System.out.println("The inference terminates after "+Execcycle+" cycles");
	 double time_diff = End_time - Start_time;
	 double elapsed_time = time_diff/1000;
	 System.out.println(" Elapsed time for "+ Execcycle +" cycle: "+elapsed_time+" Secs");
	 
	}

	//function to build heap 
	public void buildheap(){
		
		arrsize = inputlist.size()-1;
	 	for (int i=((inputlist.size())/2); i>=1; i--){		 
	 	     heapify(i);
	 	 }	
	}
	
	//function to extract max value from heap
	public void heap_extract_max()
	{
		int max=0;
		String maxname = " ";
		if (inputlist.size() >= 1) {
			
			max = inputlist.get(1);
			maxname = Namelist.get(1);
			
			Execcycle++;
			System.out.println("------- Execution "+Execcycle+" -------");
			System.out.println("Activated Rules in Agenda");
			
			for (int i=1;i<inputlist.size();i++){
				System.out.print("("+Namelist.get(i)+","+inputlist.get(i)+") ");
				if (i<(inputlist.size()-1))
					System.out.print(", ");
			}
					
			inputlist.set(1,inputlist.get((inputlist.size()-1)));
			Namelist.set(1,Namelist.get((Namelist.size()-1)));
			arrsize = heap_remove() ;
			heapify(1);
		}
		
		System.out.println("");		
		System.out.println(" ");		
		System.out.println("Exceuted Rule: "+maxname);				
		System.out.println(" ");		
	} 
	
	//function to build the heap
	public void heapify(int i)
	{
	
		int j=2*i;
		int k=(2*i)+1;
		int largeno = i;
		int temp = 0;
		String tempname= " ";
			
		if (j <=arrsize) {
			if (inputlist.get(j)>inputlist.get(i)){
				 largeno = j;
				  }
		 }
		 
		if (k <=arrsize) {
		   if (inputlist.get(k)> inputlist.get(largeno)) {
			 	largeno = k;
				}
		}	 
		 
		if (largeno != i){
			temp = inputlist.get(largeno);
			tempname = Namelist.get(largeno);
			inputlist.set(largeno,inputlist.get(i));
			Namelist.set(largeno,Namelist.get(i));
			inputlist.set(i,temp);		
			Namelist.set(i,tempname);
			heapify(largeno);
		}
	}
   
	//function to insert element into the heap
	public void heap_insert(String elementname, int element)
	{
		int i = inputlist.size();
		
		while ((i>1) && (inputlist.get(i/2) < element))
		{	
			if (i==inputlist.size())
			{		
				inputlist.add(i,inputlist.get(i/2));
				Namelist.add(i,Namelist.get(i/2));
			}
			else
			{
				inputlist.set(i,inputlist.get(i/2));
				Namelist.set(i,Namelist.get(i/2));
			}
			i=i/2;
		}	
		
		if (i==inputlist.size())
		{		
			inputlist.add(i,element);
			Namelist.add(i,elementname);
		}
		else
		{
			inputlist.set(i,element);
			Namelist.set(i,elementname);
		}
		arrsize++;
	}
	
	 //function to remove an element from the heap
	 public int heap_remove(){
		
		inputlist.remove((inputlist.size()-1));
		Namelist.remove((Namelist.size()-1));
		int newsize = inputlist.size()-1;
	 	return newsize;
	 } 
	 
	 // function to read input file
	 public void read_file() throws Exception{
	 
	 	Scanner scanner 		= new Scanner (System.in);
		
		// User input
		System.out.println("Enter absolute file path:");
		String Filepath = scanner.nextLine();
		Start_time = System.currentTimeMillis();
		
		File file =new File(Filepath);
		
		// Existing file check
		if (!(file.exists()))
		{
			System.out.println("File is not found");
			System.exit(2);
		}
		
		FileInputStream fis	= new FileInputStream(Filepath);
  		FileReader fileReader = new FileReader(Filepath);
      BufferedReader in_rdfile = new BufferedReader(fileReader);
      List<String> lines = new ArrayList<String>();
  
  		String strLine;
		
  		//Read File Line By Linec
  		while ((strLine = in_rdfile.readLine()) != null)   {
		   lines.add(strLine);		  
  		}
		
  		//Close the input stream
  		in_rdfile.close();
		
		input = lines.toArray(new String[lines.size()]);		 
	}
    
   // To distingush between priority value & priority name	
	public boolean isInteger( String element )
	{
 	 	try
  	 	{
      Integer.parseInt( (element.trim()) );
      return true;
   	}
   	catch( Exception e)
   	{
  	    return false;
 	  }
	}
  
	 public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }
	 
    public static void main(String[] args) {
    	    //creating new object to class AgendaManager
	  		AgendaManager A = new AgendaManager();
    }
}  

