package RoseindiaExample;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMElements{
	static public void main(String[] arg){
		try {
			String xmlFile = "src\\RoseindiaExample\\Persons.xml";
			File file = new File(xmlFile);
			if(file.exists()){
				// Create a factory
				DocumentBuilderFactory factory = 
						DocumentBuilderFactory.newInstance();
				// Use the factory to create a builder
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				// Get a list of all elements in the document
				NodeList list = doc.getElementsByTagName("*");
				System.out.println(list.getLength());
				System.out.println("XML Elements: ");
				for (int i=0; i<list.getLength(); i++) {
					// Get element
					Element element = (Element)list.item(i);
					System.out.println(element.getNodeName());
				}
			}
			else{
				System.out.print("File not found!");
			}
		}
		catch (Exception e) {
			System.exit(1);
		}
	}
}