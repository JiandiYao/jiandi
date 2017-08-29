package com.example.sparktutorial.hbase;


import java.io.IOException;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import com.tutorial.hbase.Util.HBaseConfig;

public class HBasePut {

	private static Logger logger = Logger.getLogger(HBasePut.class.getName());
	
	private static final byte[] CONTENT_INFO = Bytes.toBytes("ContentInfo");
	
	public static void main(String[] args) {
		
		
	    Configuration hBaseConf = HBaseConfig.create();
	    
	    try {
			HTable table=new HTable(hBaseConf,"test_table");
			Put put = new Put(Bytes.toBytes("test_key"));
			put.add(CONTENT_INFO, Bytes.toBytes("payload"), Bytes.toBytes("Lao Gong is a pig"));
			table.put(put);
			
			Get get=new Get(Bytes.toBytes("test_key"));
			Result result =table.get(get);
			byte[] value=result.getValue(CONTENT_INFO,Bytes.toBytes("payload"));
			
			logger.info(new String(value));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
}
