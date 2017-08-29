package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"message", "tpcopId"})
@XmlRootElement(name = "KVMessage")

public class AbortMessage {

	private String type;
	private String message;
	private String tpcopid;
	public String getType() {
		return type;
	}
	
	@XmlAttribute(name = "type", required= true)
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	@XmlElement(name = "Message")
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTpcopid() {
		return tpcopid;
	}
	@XmlElement(name = "TPCOPid")
	public void setTpcopid(String tpcopid) {
		this.tpcopid = tpcopid;
	}
	
	public String toString(){
		return "Abort Message content: message = " + this.message + ", tpcopid = " + this.tpcopid;
	}
	
}
