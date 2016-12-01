import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.*;

public class cmp_author_test{
	public static void main(String[] args) throws IOException{
		FileInputStream fstream = new FileInputStream("AuthorKeywords.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		HashSet<String> author_keywords = new HashSet<String>();
		String strLine1 = null;

		//Read File Line By Line
		while ((strLine1 = br.readLine()) != null)   {
		     author_keywords.add(strLine1);
		}				
		PrintWriter writer = new PrintWriter("output_results.txt", "UTF-8");

		String folder_path = "test_key/";
		File folder = new File(folder_path);
		File[] listOfFiles = folder.listFiles();
		int cw_count=0;
		for(int i = 0; i < listOfFiles.length; i++) {		  	
		  	cw_count = 0;
		  	String file_name;		  	  
		    if(listOfFiles[i].isFile()) {
		      	System.out.println("file number "+i);
		      	String file_name1 = listOfFiles[i].getName();
		      	System.out.println(listOfFiles[i].getName());
		      	String file_path;
		      	file_path = "test_key/" + file_name1;
		      	FileInputStream fstream1 = new FileInputStream(file_path);
				BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
				String strLine = null;

				//Read File Line By Line
				while ((strLine = br1.readLine()) != null)   {
		            if(author_keywords.contains(strLine))
		            	cw_count++;     
				}
				System.out.println("cwc="+cw_count);	
				if(cw_count >=1){
					writer.println(file_name1+" relevant");
				}
				else{
					writer.println(file_name1+" irrelevant");
				}
				
		    }
		  }
		  writer.close();  
	 } 
}