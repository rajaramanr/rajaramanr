package Mine;
 import org.htmlparser.Parser;
 import org.htmlparser.Tag;
 import org.htmlparser.Text;
 import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
 
 public class MyVisitor extends NodeVisitor
 {
     public MyVisitor ()
     {
     }

     public void visitTag (Tag tag)
     {
         System.out.println ("\n" + tag.getTagName () + tag.getStartPosition ());
     }

     public void visitStringNode (Text string)
     {
         System.out.println (string);
     }

     public static void main (String[] args) throws ParserException
     {
    	 WebDriver driver = new FirefoxDriver();
    	 driver.get("https://dpmqa217.eris.com/cleartrust/ct_logon_en.html");
         Parser parser = new Parser (driver.getPageSource());
         MyVisitor visitor = new MyVisitor ();
         parser.visitAllNodesWith (visitor);
         driver.close();
     }
 }
