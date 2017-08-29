package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"Key", "Value"})
@XmlRootElement(name = "KVPair")
public class KVPair {
	private String key, value;
	
	@XmlElement (name = "Key")
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@XmlElement (name = "Value")
	public void setValue(String value){
		this.value = value;
	}
}
