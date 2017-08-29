package com.berkley.keyvaluestore.common.message;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"Message"})
@XmlRootElement(name = "KVCache")

public class KVCache {

	private String set;

	public String getSet() {
		return set;
	}

	@XmlElementWrapper(name="Set" )
	public void setSet(String set) {
		this.set = set;
	}
	
}
