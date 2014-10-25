package DoneAsJohnTold;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class ParsingUsingHTMLParser_Simple
{
	WebDriver driver = new FirefoxDriver();
	Stack<String> elements = new Stack<String>();
	Map<String,String> mapper = new HashMap<String,String>();
	@Test
	public void TestMethod() throws InterruptedException, IOException
	{ 
		try
		{
			driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
			String source= driver.getPageSource();
			System.out.println(source);
			Parser parser = new Parser (source);
			NodeVisitor nodeVisitor = new NodeVisitor() 
			{
				public void visitTag(Tag tag) 
				{
					if (tag.getTagName().equalsIgnoreCase("input")) {
						InputTag it = (InputTag) tag;
						String type = it.getAttribute("type");
						if(type.equalsIgnoreCase("hidden"))
						{
							System.out.println("Not Adding");
						}
						else
						{
							String idValue = it.getAttribute("id");
							if(idValue==null)
							{
								idValue = it.getAttribute("name");
							}
							if(idValue==null)
							{
								idValue = "XPATH";
							}
							elements.add(idValue);
						}
					}
					if (tag.getTagName().equalsIgnoreCase("button")) {
						String idValue = tag.getAttribute("id");
						if(idValue==null)
						{
							idValue = tag.getAttribute("name");
						}
						if(idValue==null)
						{
							idValue = "XPATH";
						}
						elements.add(idValue);
					}
				}
			};
			parser.visitAllNodesWith(nodeVisitor);
			System.out.println(elements);
			driver.close();
		}
		catch (ParserException pe)
		{
			pe.printStackTrace ();
		}
	}
}

