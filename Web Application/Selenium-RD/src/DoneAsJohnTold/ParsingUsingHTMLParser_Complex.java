package DoneAsJohnTold;
import java.io.IOException;
import java.util.Stack;

import org.htmlcleaner.HtmlCleaner;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class ParsingUsingHTMLParser_Complex
{
	WebDriver driver = new FirefoxDriver();
	Stack<String> elements = new Stack<String>();
	@Test
	public void TestMethod() throws InterruptedException, IOException
	{ 

		try
        {
			driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
			String source= driver.getPageSource();

//			Cleaning the contents using HTMLCleaner
			HtmlCleaner cleaner =new HtmlCleaner();
			cleaner.clean(source);
			
//			Parsing the HTML
			Parser parser = new Parser ();
            NodeList list = new NodeList();
            NodeFilter filter = new TagNameFilter ("input");
            parser.setInputHTML(source);
            list = parser.extractAllNodesThatMatch(filter);
            for(int i=0;i<list.size();i++)
            {
            	String individualElementId = "";
            	Node nod = list.elementAt(i);
            	String str = nod.getText();
            	char[] c = new char[str.length()];	
            	if(str.contains("id=\""))
    			{
    				str.getChars(str.indexOf("id=\""),str.length(), c, 0);
    				for(int index=4;c[index]!='\"';index++)
    				{
    					individualElementId = individualElementId + c[index];
    				}
    				elements.push(individualElementId);
    			}
            }
            System.out.println(elements);
            driver.close();
        }
        catch (ParserException pe)
        {
            pe.printStackTrace ();
        }
    }
}

