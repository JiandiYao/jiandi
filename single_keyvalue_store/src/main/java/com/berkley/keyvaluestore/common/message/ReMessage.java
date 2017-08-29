package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.*;


@XmlType(propOrder = {"Message"})
@XmlRootElement(name = "KVMessage")

public class ReMessage {

	private String message;
	private String type;
	
	@XmlAttribute(name = "type", required = true)
	public void setType(String type){
		this.type = type;
	}
	
	@XmlElement (name = "Message")
	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getType() {
		return type;
	}
	
	
}

