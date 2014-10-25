package UsingHTMLParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class ParsingUsingHTMLParser_SimpleUsingConfigProperty
{
	WebDriver driver = new FirefoxDriver();
	List<String> idOrNameElements = new ArrayList<String>();
	List<String> xpathElements = new ArrayList<String>();
	Map<String,List<String>> mapper = new HashMap<String,List<String>>();
	Map<String,String> configMapper = new HashMap<String,String>();
	@Test
	public void TestMethod() throws IOException
	{ 
		try
		{
			fetchFromConfig();
			driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
			String source= driver.getPageSource();
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
							if(configMapper.containsKey(type.toLowerCase()) && configMapper.get(type).equalsIgnoreCase("yes"))
							{
								String idValue = it.getAttribute("id");
								if(idValue==null)
								{
									idValue = it.getAttribute("name");
								}
								if(idValue==null)
								{
									idValue = "XPATH";
									xpathElements.add(idValue);
								}
								else
								{
									idOrNameElements.add(idValue);
								}
							}
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
							xpathElements.add(idValue);
						}
						else
						{
							idOrNameElements.add(idValue);
						}
					}
				}
			};
			parser.visitAllNodesWith(nodeVisitor);
			mapper.put("ID_OR_NAME", idOrNameElements);
			mapper.put("XPATH", xpathElements);
			System.out.println(mapper);
			createSeleniumClassFile();
			driver.close();
		}
		catch (ParserException pe)
		{
			pe.printStackTrace ();
		}
	}
	private void fetchFromConfig() throws IOException 
	{
		File f = new File("C:\\Users\\rajaraman-r\\Desktop\\Dumps\\Delete\\Selenium-R&D\\Selenium-RD\\src\\UsingHTMLParser\\config.properties");
		FileInputStream fis = new FileInputStream(f);
		Properties prop = new Properties();
		prop.load(fis);
		Set<Object> keys = prop.keySet();
		for(Object key : keys)
		{
			String k = key.toString();
			String value = prop.getProperty(k);
			configMapper.put(k.toLowerCase(), value.toLowerCase());
		}
	}
	private void createSeleniumClassFile() throws IOException
	{
		String className="Login";
		File f = new File("C:\\Users\\rajaraman-r\\Desktop\\Dumps\\Delete\\Selenium-R&D\\Selenium-RD\\src\\UsingHTMLParser\\"+className+".java");
		FileWriter fos = new FileWriter(f);
		BufferedWriter buffout = new BufferedWriter(fos);
//		getting for package name
		String packageName = f.getParentFile().getName();
		buffout.write("package "+packageName+";\n\n");
		buffout.write("import org.openqa.selenium.WebElement;\nimport org.openqa.selenium.support.FindBy;\nimport org.openqa.selenium.support.How;\n\n\n");
		buffout.write("public class "+className+"{\n");
		for(String key : mapper.keySet())
		{
			if(key.equalsIgnoreCase("ID_OR_NAME"))
			{
				for(String idOrName : mapper.get(key))
				{
					String varName[] = idOrName.split("\\.");
					buffout.write("	@FindBy(how = How.ID_OR_NAME, using =\""+idOrName+"\")\nprivate WebElement "+varName[varName.length-1]+";\n\n\n");
				}
			}
			else
			{
				int i=1;
				for(String xpath : mapper.get(key))
				{
					buffout.write("	@FindBy(how = How.XPATH, using =\""+xpath+"\")\nprivate WebElement "+xpath+"_"+i+";\n\n\n");
					i++;
				}
			}
		}
		buffout.write("}");
		buffout.close();
		System.out.println("Completed");
	}
}
