package Mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


public class Try
{
	WebDriver driver = new FirefoxDriver();
	List<String> elements = new ArrayList<String>();
	List<String> duplicateElements = new ArrayList<String>();
	Map<Integer,String> lineTagMapper = new HashMap<Integer,String>();
	Map<String,Integer> countTagMapper = new HashMap<String,Integer>();
	@Test
	public void TestMethod() throws InterruptedException, IOException
	{
		int tempIndex=0,count=1;
		driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
		String[] pageSourceInLines = driver.getPageSource().split("\n");
		for(String line : pageSourceInLines)
		{
			System.out.println("Line no : "+count + "****"+ line);
			count++;
			char[] indiChar = new char[line.length()];
			line.getChars(0, line.length(), indiChar, 0);
			for(int index=0;index<indiChar.length;index++)
			{
				if(indiChar[index]=='<')
				{
					if(indiChar[index+1]=='!')
					{
						int received = iterator(indiChar,index);
						if(received==0)
						{
							iterator(indiChar,index);
						}
						else
						{
							index=received;
						}
					}
					else
					{
						String tagName="";
						tempIndex = index+1;
						while(indiChar[tempIndex]!=' ')
						{
							if(indiChar[tempIndex]=='>')
							{
								break;
							}
							else
							{
								tagName = tagName+indiChar[tempIndex];
								tempIndex++;
							}
						}
						elements.add(tagName);
						lineTagMapper.put(count, tagName);
					}
				}
			}
		}
		duplicateElements.addAll(elements);
		for(String elements : this.elements)
		{
			if(elements.equals("body"))
			{
				break;
			}
			else
			{
				duplicateElements.remove(elements);
			}
		}
		System.out.println(elements);
//		duplicateElements is necessary for calculating xpath
		System.out.println(duplicateElements);
//		Counts the number of repetition of each tag
		mapperFunction();
		System.out.println(countTagMapper);
	}
	public void mapperFunction()
	{
		int count=0;
		Set<String> distinctTags = new HashSet<String>(duplicateElements);
		System.out.println(distinctTags);
		System.out.println(duplicateElements.size());
		for(String tag : distinctTags)
		{
			int j=0;
			System.out.println("This is searched");
			System.out.println(tag);
			while(j<duplicateElements.size())
			{
				System.out.print(duplicateElements.get(j)+"  ");
				if(duplicateElements.get(j).equals(tag))
				{
					System.out.println();
					System.out.print(duplicateElements.get(j));
					System.out.println("********************************");
					count++;
				}
				j++;
			}
			countTagMapper.put(tag,count);
		}
	}
	public List<String> findXPath(String tag,int count)
	{
		
		System.out.println(this.elements);
		return null;
	}
	public int iterator(char[] c,int index)
	{
		System.out.println("Lines commented");
		while(c[index]=='-')
		{
			index++;
		}
		if(c[index+1]=='-' && c[index+2]=='>')
		{
			return index;
		}
		else
		{
			return 0;
		}
	}
}


