package xml.jaxb;

import javax.xml.bind.annotation.*;


@XmlType(propOrder = {"key", "value", "tpcopId"})
@XmlRootElement(name = "KVMessage")
public class PutMessage {
	
	
	private String type;
	private String key;
	private String value;
	private String tpcopId;
	
	@XmlAttribute(name = "type", required = true)
	public void setType(String type){
		this.type = type;
	}
	
	@XmlElement (name = "Key")
	public void setKey(String key){
		this.key = key;
	}
	
	@XmlElement (name = "Value")
	public void setValue(String value){
		this.value = value;
	}
	
	@XmlElement(name = "TPCOpId")
	public void setTpcopId(String tpcopId){
		this.tpcopId = tpcopId;
	}

	public String getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getTpcopId() {
		return tpcopId;
	}
	
	@Override
	public String toString(){
		
		return "The put key value pair------Key: "+ this.key + 
				", Value: "+ this.value + ", TPCOpid: "+ this.tpcopId + "\n";
		
	}
}
