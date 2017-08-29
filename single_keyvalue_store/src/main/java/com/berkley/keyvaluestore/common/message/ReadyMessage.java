package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KVMessage")
public class ReadyMessage {

	private String type;
	private String tpcopid;
	public String getType() {
		return type;
	}
	@XmlAttribute(name = "type", required = true)
	public void setType(String type) {
		this.type = type;
	}
	public String getTpcopid() {
		return tpcopid;
	}
	@XmlElement(name = "TPCOpId")
	public void setTpcopid(String tpcopid) {
		this.tpcopid = tpcopid;
	}
	
	
}
