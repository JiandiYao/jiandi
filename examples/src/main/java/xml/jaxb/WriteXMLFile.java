package xml.jaxb;
/*
 * A full tutorial can be found http://www.w3schools.com/dom/dom_examples.asp
 * 
 */
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {

	public static void main(String[] args){
		
		try {
			/*standard way */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
		
			//root element
			Element rootElement = doc.createElement("company");
			doc.appendChild(rootElement);
			
			//staff element
			Element staff = doc.createElement("staff");
			rootElement.appendChild(staff);
			
			
			//set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			staff.setAttributeNode(attr);
			
			Element firstname = doc.createElement("firstname");
			firstname.appendChild(doc.createTextNode("Tom"));
			staff.appendChild(firstname);
			
			Element lastname = doc.createElement("lastname");
			lastname.appendChild(doc.createTextNode("Hook"));
			staff.appendChild(lastname);
			
			Element salary = doc.createElement("salary");
			salary.appendChild(doc.createTextNode("1000000000000"));
			rootElement.appendChild(salary);
			
			//write the content to XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("target/test.xml"));
//			StreamResult result = new StreamResult(System.out);
		
			transformer.transform(source, result);
			System.out.println("\nFile saved");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
