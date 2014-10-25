package Mine;
import java.io.IOException;
import java.util.Stack;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class XpathFinder
{
	WebDriver driver = new FirefoxDriver();
	Stack<String> elements = new Stack<String>();
	Stack<String> actualElements = new Stack<String>();
	@Test
	public void TestMethod() throws InterruptedException, IOException
	{ 
		try
		{
			driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
			String source= driver.getPageSource();
			Parser parser = new Parser (source);
			NodeVisitor linkvisitor = new NodeVisitor() 
			{
				public void visitTag(Tag tag) 
				{
					if (tag.getTagName().equalsIgnoreCase("input")) 
					{
						String xpath=tag.getTagName();
						TagNode node = (TagNode) tag.getParent();
						NodeList allNodes = node.getChildren();
						while(true)
						{
							if(node == (TagNode) tag.getParent())
							{
								NodeFilter nf = new TagNameFilter(tag.getTagName());
								if(allNodes.extractAllNodesThatMatch(nf).size()!=0)
								{
									int count=0;
									Node tempNode;
									while(true)
									{
										if(tag.getPreviousSibling()!=null)
										{
											count++;
											tempNode= tag.getPreviousSibling();
										}
										else
										{
											break;
										}
									}
									int index =  allNodes.extractAllNodesThatMatch(nf).size() - count;
									System.out.println(allNodes.extractAllNodesThatMatch(nf).size()+"    "+tag.getTagName()+"["+index+"]");
									System.out.println("RRRRRRRRRRRRRR");
								}
							}
							else
							{
								NodeFilter nf = new TagNameFilter(node.getTagName());
								Node tempNode = node;
								if(allNodes.extractAllNodesThatMatch(nf).size()!=0)
								{
									int count=0;
									while(true)
									{
										if(tempNode.getPreviousSibling()!=null)
										{
											count++;
											tempNode= tempNode.getPreviousSibling();
										}
										else
										{
											break;
										}
									}
									int index =  allNodes.extractAllNodesThatMatch(nf).size() - count;
									System.out.println(tag.getTagName()+"["+index+"]");
								}
								System.out.println("RRRRRRRRRRRRRR");
							}
							String parentName = node.getTagName();
							if(parentName.equalsIgnoreCase("html"))
							{
								xpath=xpath+"/"+parentName;
								elements.push(xpath);
								break;
							}
							else
							{
								xpath=xpath+"/"+parentName;
								node=(TagNode) node.getParent();
							}
							System.out.println("SSSSSSSSSSSSSSSSSSS");
						}
					}
				}
			};
			parser.visitAllNodesWith(linkvisitor);
			driver.close();
			reverseToGetActualXPath();
		}
		catch (ParserException pe)
		{
			pe.printStackTrace ();
		}
	}
	public void reverseToGetActualXPath()
	{
		for(String path : elements)
		{
			String actual="/";
			String rev[] = path.split("/");
			for(int i=rev.length-1;i>=0;i--)
			{
				if(i==0)
				{
					actual+=rev[i];	
				}
				else
				{
					actual+=rev[i]+"/";
				}
			}
			actualElements.add(actual);
		}
		System.out.println(actualElements);
	}

	public String findReverse(String idOrName)
	{
		char[] c = new char[idOrName.length()];
		idOrName.getChars(0,idOrName.length(), c, 0);
		String varNameRev="";
		for(int i=c.length;i>0;i--)
		{
			if(c[i]=='.')
			{
				break;
			}
			else
			{
				varNameRev+=c[i];
			}
		}
		char[] var = new char[varNameRev.length()];
		varNameRev.getChars(0, varNameRev.length(), var, 0);
		String varName = "";
		for(int i=varNameRev.length()-1;i>=0;i--)
		{
			varName+=var[i];
		}
		return varName;
	}
}
