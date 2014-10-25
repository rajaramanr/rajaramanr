package jarSearcher_p02;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		FileSearcher search = new FileSearcher();
		search.SearchFiles();
	}
}
