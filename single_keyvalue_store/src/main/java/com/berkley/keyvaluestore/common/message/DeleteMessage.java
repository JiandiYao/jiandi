package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"key", "tpcopid"})
@XmlRootElement(name = "KVMessage")

public class DeleteMessage {

	private String key;
	private String tpcopid;
	
	public String getKey() {
		return key;
	}
	
	@XmlElement(name = "Key")
	public void setKey(String key) {
		this.key = key;
	}
	public String getTpcopid() {
		return tpcopid;
	}
	@XmlElement(name = "TPCOpId")
	public void setTpcopid(String tpcopid) {
		this.tpcopid = tpcopid;
	}
	
}
