import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import java.net.URL;

class KeywordExtractTrain{

	//Checking for the stopping words
	public static boolean exist(String str){
		String stopwords[] = {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};
		for(int i=0;i<stopwords.length;i++){
			if(str.equalsIgnoreCase(stopwords[i])){
				return true;
			}
		}
		return false;
	}

	//Checking for the URLs
	public static boolean isLink(String str){
		try{
			new URL(str);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	public static void main(String[] args) throws IOException{
		ArrayList<String> lines = new ArrayList<String>();

		//Taking training file as input
		FileInputStream fis = new FileInputStream("train.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		while((line = br.readLine())!=null)
			lines.add(line);
		br.close();	

		int fileNumberRel = 0;//File number for relevant tweet files
		int fileNumberIrr = 0;//File number for irrelevant tweet files
		HashMap<String,Integer> map= new HashMap<String,Integer>();//HashMap to count frequency of each word in tweet text
		
		//Look Iterating over each line in training set
		for(int i=0;i<lines.size();i++){

			String arr[] = lines.get(i).split(",,,"); //Split tweet on delimiter ",,,"
			String p = arr[arr.length-1];
			String k[] = p.split("\\s+");

			String q = k[k.length-1];//Giving relevant or irrelevant tweets
			//System.out.println(q);
			String temp = arr[2];

			String filename = "";
			String filename1 = "";


			if(q.equals("Relevant")){
				filename = "tw_r_"+fileNumberRel+".txt"; //Giving file name to relevant files with tweet text
				filename1 = "tw_r_"+fileNumberRel+".key"; //File name for .key files
				fileNumberRel++;
			}
			else if(q.equals("Irrelevant")){
				filename = "tw_ir_"+fileNumberIrr+".txt";
				filename1 = "tw_ir_"+fileNumberIrr+".key";
				fileNumberIrr++;
			}
			try{
				PrintWriter writer = new PrintWriter("train/"+filename,"UTF-8"); //Writing the .txt files
				writer.println(temp);
				writer.close();
			}
			catch (IOException e) {
				System.out.println("Error+++++");
			}
			String a[] = temp.split(" "); //Splitting tweet text on spaces
			String sk = "";
			for(int j=0;j<a.length;j++){  //Iterating over each word in a tweet text

				if(a[j].length()>2&&!exist(a[j])&&!isLink(a[j])){ 	//Checking that word is neither a stop word nor a URL
					if((a[j].charAt(a[j].length()-1)>=97&&a[j].charAt(a[j].length()-1)<=122)||(a[j].charAt(a[j].length()-1)>=65&&a[j].charAt(a[j].length()-1)<=90)||(a[j].charAt(a[j].length()-1)>=48&&a[j].charAt(a[j].length()-1)<=57)){   //Removing , and . from the end of a word
						if(map.containsKey(a[j])){
							map.put(a[j],map.get(a[j])+1);   //Increasing the frequency of a word if it exist in hashmap
						}
						else{
							map.put(a[j],1);  // Adding new word to hashmap
						}
						sk = sk+a[j]+"\n";   //String containing only relevant words(without stopwords)
					}
					else{
						String ab = a[j].substring(0,a[j].length()-1);
						if(map.containsKey(ab)){
							map.put(ab,map.get(ab)+1);
						}
						else{
							map.put(ab,1);
						}
						sk = sk+ab+"\n";
					}
					
				}
			}
			try{
				PrintWriter writer1 = new PrintWriter("train_key/"+filename1,"UTF-8");
				writer1.println(sk); //Writing .key file
				writer1.close();
			}
			catch (IOException e) {
				System.out.println("Error");
			}

		}
		Set<Entry<String, Integer>> set = map.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
		PrintWriter out = null;
		try{
			out = new PrintWriter(new FileWriter("output_test.csv"));
			for(Map.Entry<String, Integer> entry:list){
            	out.println(entry.getKey()+"		"+entry.getValue());
        	} 
		}
		catch(IOException e){
			System.err.println(e.getMessage());
		}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
}