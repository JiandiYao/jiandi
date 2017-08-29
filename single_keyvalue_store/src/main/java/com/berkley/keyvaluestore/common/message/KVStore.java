package com.berkley.keyvaluestore.common.message;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KVStore")

public class KVStore {
	
	private List<KVPair> kVPairs;
	
	public List<KVPair> getkVPairs() {
		return kVPairs;
	}

	@XmlElement (name = "KVPair")
	public void setkVPairs(List<KVPair> kVPairs) {
		this.kVPairs = kVPairs;
	}


	

}
