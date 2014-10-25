package DoneAsJohnTold;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


public class ExtensionToNameIDXPATH
{
	WebDriver driver = new FirefoxDriver();
	List<String> linesNeeded = new ArrayList<String>();
	List<String> idElements = new ArrayList<String>();
	List<String> nameElements = new ArrayList<String>();
	Map<String,List<String>> webElements = new HashMap<String,List<String>>();
	public int getInputTagContents(char[] c)
	{
		String words = "";
		int j=1;
		while(c[j]!='/' && c[j]!='<')
		{
			words += c[j];
			j++;
		}
		if(words.startsWith("input"))
		{
			linesNeeded.add(words);
		}
		checkForNextInput(j,c);
		return j;
	}
	public void checkForNextInput(int j,char[] c)
	{
		String str="";
		for(int i=j;i<c.length;i++)
		{
			 str = str+c[i];
		}
		if(str.contains("<input"))
		{
			str.getChars(0, str.length(), c, 0);
			getInputTagContents(c);
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
		webElements.put("ID", idElements);
		webElements.put("Name", nameElements);
		System.out.println(webElements);
		driver.close();
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
			else if(line.contains("name=\""))
			{
				line.getChars(line.indexOf("name=\""), line.length(), c, 0);
				for(int index=4;c[index]!='\"';index++)
				{
					individualElementId = individualElementId + c[index];
				}
				nameElements.add(individualElementId);
			}
		}		
	}
}
