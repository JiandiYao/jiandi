package xml.jaxb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

public class PutMessageTest extends TestCase {
	private static final String KVMessage_put_XML = "./target/PutMessage-jaxb.xml";

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

    	PutMessage message = new PutMessage();
    	message.setKey("key");
    	message.setType(MessageType.PUT_VALUE_REQUEST);
    	message.setTpcopId("2PPC Operation ID");
    	message.setValue("value");
    	
    	JAXBContext context = JAXBContext.newInstance(PutMessage.class);
    	Marshaller m = context.createMarshaller();
    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    	m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
    	m.setProperty("com.sun.xml.internal.bind.xmlHeaders",
    		      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        m.marshal(message, System.out);

       
        // Write to File
        m.marshal(message, new File(KVMessage_put_XML));

        // get variables from our xml file, created before
        System.out.println();
        System.out.println("Output from our XML File: ");
        Unmarshaller um = context.createUnmarshaller();
        PutMessage message2 = (PutMessage) um.unmarshal(new FileReader(KVMessage_put_XML));
        
        System.out.print(message2);
    }
}
