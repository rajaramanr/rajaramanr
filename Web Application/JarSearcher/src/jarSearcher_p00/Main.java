package jarSearcher_p00;

import java.io.IOException;
import java.util.List;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		int noOfInstances=1;
		FileSearcher search = new FileSearcher();
		search.SearchFiles();
		List<String> files = search.getFiles();
		if(files.isEmpty())
		{
			System.out.println("No such files ");					
		}
		for(String name : files)
		{
			System.out.println(noOfInstances+" : " +name);
			noOfInstances++;
		}
	}
}
