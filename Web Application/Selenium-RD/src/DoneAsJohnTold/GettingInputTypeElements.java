package DoneAsJohnTold;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


public class GettingInputTypeElements
{
	WebDriver driver = new FirefoxDriver();
	List<String> linesNeeded = new ArrayList<String>();
	List<String> idElements = new ArrayList<String>();
	
	
	public int getInputTagContents(char[] line)
	{
		String words = "";
		int j=1;
		while(line[j]!='/' && line[j]!='>')
		{
			words += line[j];
			j++;
		}
		linesNeeded.add(words);
		checkForNextInput(j,line);
		return j;
	}
	public void checkForNextInput(int index,char[] restOfTheLine)
	{
		String str="";
		for(int i=index;i<restOfTheLine.length;i++)
		{
			 str = str+restOfTheLine[i];
		}
		if(str.contains("<input"))
		{
			str.getChars(0, str.length(), restOfTheLine, 0);
			getInputTagContents(restOfTheLine);
		}
	}
	private void getElements() 
	{
		for(String line : linesNeeded)
		{
			char c[] = new char[line.length()];	
			String individualElementId = "";
			if(line.contains("id=\""))
			{
				line.getChars(line.indexOf("id=\""), line.length(), c, 0);
				for(int index=4;c[index]!='\"';index++)
				{
					individualElementId = individualElementId + c[index];
				}
				idElements.add(individualElementId);
			}
		}		
	}
	@Test
	public void TestMethod() throws InterruptedException
	{
		driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
		String[] pageSource = driver.getPageSource().split("\n");
		for(String line : pageSource)
		{
			if(line.contains("<input"))
			{
				if((line.contains("type=\"submit\""))||(line.contains("type=\"text\""))||(line.contains("type=\"button\""))||(line.contains("type=\"radio\""))||(line.contains("type=\"checkbox\"")))
				{
					char[] c = new char[line.length()];
					line.getChars(line.indexOf("<input"), line.length(), c, 0);
					line.indexOf("input");
					getInputTagContents(c);
				}
			}
		}
		getElements();
		System.out.println(idElements);
		driver.close();
	}
	
}
