package com.example.sparktutorial.hbase.Util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;

public class HBaseConfig {
	
	public static Configuration create(Scan scan, String entityName){
		Configuration conf = create(scan);
		conf.set(TableInputFormat.INPUT_TABLE, entityName);
		return conf;
	}
	
	public static Configuration create(Scan scan){
		Configuration conf = create();
//		conf.set(TableInputFormat.SCAN, scan);
//		conf.set(TableInputFormat.SCAN, scan);
		return conf;
	}

	public static Configuration create(){
		Configuration hBaseConf=HBaseConfiguration.create();
		
		hBaseConf.set("hbase.regionserver.port", "60020");
	    hBaseConf.set("hbase.master", "10.0.1.14:9000*");
	    hBaseConf.set("hbase.zookeeper.quorum","10.0.1.14");
	    hBaseConf.set("hbase.zookeeper.property.clientPort", "2181");
	    
	    try {
			HBaseAdmin.checkHBaseAvailable(hBaseConf);
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    return hBaseConf;
	}
	
}
