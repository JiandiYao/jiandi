package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder = {"tpcopId"})
@XmlRootElement(name = "KVMessage")
public class AckMessage {
	private String type;
	private String tpcopid;
	
	
	public String getTpcopid() {
		return tpcopid;
	}
	
	@XmlElement(name = "TPCOpId")
	public void setTpcopid(String tpcopid) {
		this.tpcopid = tpcopid;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute(name = "type", required = true)
	public void setType(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return "2PC Acknowledgement message content: tpcopid = "+ this.tpcopid + "\n";
	}
}
