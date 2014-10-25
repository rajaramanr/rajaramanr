package DoneAsJohnTold;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ParsingUsingPatternMatching
{
	public static void main (String[] args) throws IOException 
	{
		WebDriver driver = new FirefoxDriver();
		driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
		String content = driver.getPageSource();
		char[] c = new char[content.length()];
		content.getChars(0,	content.length(),c,0);
		List<String> linesNeeded = new ArrayList<String>();
		List<String> elements = new ArrayList<String>();
		List<Integer> startElements = new ArrayList<Integer>();
		List<Integer> endElements = new ArrayList<Integer>();
		Pattern tag = Pattern.compile("<input .*? />");
		Matcher mtag = tag.matcher(content);
		while (mtag.find())
		{
			int startIndex = mtag.start();
			int endIndex = mtag.end();
			startElements.add(startIndex);
			endElements.add(endIndex);
		}
		System.out.println(startElements);
		System.out.println(endElements);
		for(int i=0;i<startElements.size();i++)
		{
			String str = "";
			for(int index=startElements.get(i);index<endElements.get(i);index++)
			{
				str+=c[index];
			}
			System.out.println(str);
			linesNeeded.add(str);
		}
		for(String line : linesNeeded)
		{
			char linesAsChar[] = new char[line.length()];	
			String individualElementId = "";
			if(line.contains("id=\""))
			{
				line.getChars(line.indexOf("id=\""), line.length(),linesAsChar, 0);
				for(int index=4;linesAsChar[index]!='\"';index++)
				{
					individualElementId = individualElementId + linesAsChar[index];
				}
				elements.add(individualElementId);
			}
		}	
		System.out.println(elements);
		driver.close();
	}
}