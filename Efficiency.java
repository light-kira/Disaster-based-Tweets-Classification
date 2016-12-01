import java.util.*;
import java.io.*;

class Efficiency{
	public static void main(String[] args) throws IOException{
		FileInputStream f = new FileInputStream("output_results.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(f));
		String line = null;
		int count = 0;
		int total = 0;
		while((line = br.readLine())!=null){
			String s[] = line.split(" ");
			String a[] = s[0].split("_");
			if(a[1].charAt(0)==s[1].charAt(0))
				count++;
			total++;
		}
		System.out.println(count+"  "+total);
		double efficiency = ((double)count/(double)total)*100;
		System.out.println("Efficiency = "+efficiency);
	}
}