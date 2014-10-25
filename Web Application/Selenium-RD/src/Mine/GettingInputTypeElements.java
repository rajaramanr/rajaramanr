package Mine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


public class GettingInputTypeElements
{
	WebDriver driver = new FirefoxDriver();
	@Test
	public void TestMethod() throws InterruptedException
	{
		List<String> elements = new ArrayList<String>();
		driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
		String[] pageSource = driver.getPageSource().split("\n");
		int i=0;
		Map<Integer,List<Integer>> mapper = new HashMap<Integer,List<Integer>>();
		for(String line : pageSource)
		{
			System.out.println(i+"*****"+line);
			if(line.contains("<input"))
			{
				if((line.contains("type=\"submit\""))||(line.contains("type=\"text\""))||(line.contains("type=\"button\""))||(line.contains("type=\"radio\""))||(line.contains("type=\"checkbox\"")))
				{
					int count=0;
					String[] str = line.split(" ");
					List<Integer> indices = new ArrayList<Integer>();
					for(String s : str)
					{
						if(s.contains("<input"))
						{
							indices.add(count);
						}
						count++;
					}
					mapper.put(i, indices);
				}
				else
				{
					continue;
				}
				i++;
			}
			else
			{
				i++;
				continue;
			}
		}
		Set<Integer> keys = mapper.keySet();
		for(int key : keys)
		{
			String actual = pageSource[key+1];					
			String[] words = actual.split(" ");
			List<Integer> actualIndices = mapper.get(key);
			if(actualIndices.size()==1)
			{
				int index = actualIndices.get(0);
				System.out.println(actual);
				for(int j=index ;j<words.length;j++)
				{
					if(words[j].startsWith("id"))
					{
						String id = "";
						char c[] = new char[words[j].length()];
						words[j].getChars(0, words[j].length(), c, 0);
						for(int ch=4 ; ch<c.length ; ch++)
						{
							if(c[ch]=='\"')
							{
								System.out.println("breaking");
								break;
							}
							else
							{
								id+=String.valueOf(c[ch]);
							}
						}
						elements.add(id);
						break;
					}
					else
					{
						continue;
					}
				}
			}
			else
			{
				for(int index : actualIndices)
				{
					for(int j=index ;j<words.length;j++)
					{
						if(words[j].startsWith("id"))
						{
							String id = "";
							char c[] = new char[words[j].length()];
							words[j].getChars(0, words[j].length(), c, 0);
							for(int ch=4 ; ch<c.length ; ch++)
							{
								if(c[ch]=='\"')
								{
									System.out.println("breaking");
									break;
								}
								else
								{
									id+=String.valueOf(c[ch]);
								}
							}
							elements.add(id);
							break;
						}
						else
						{
							continue;
						}
					}
				}
			}
		}
		System.out.println(elements);
		driver.close();
	}
}
