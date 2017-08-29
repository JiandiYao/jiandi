package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"Message"})
@XmlRootElement(name = "KVMessage")

public class IgnoreMessage {
	private String type;
	
	public String getType() {
		return type;
	}

	@XmlAttribute(name = "type", required = true)
	public void setType(String type){
		this.type = type;
	}
}
