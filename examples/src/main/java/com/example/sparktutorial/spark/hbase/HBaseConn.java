package com.example.sparktutorial.spark.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.spark.SparkContext;

import com.tutorial.hbase.Util.Constant;

public class HBaseConn {
	public static void main(String[] args){
		SparkContext sc = new SparkContext(Constant.SPARK_MASTER, "Test");
		String tableName = Constant.TABLE_NAME;
		HBaseConfiguration conf = HBaseConfiguration.create();
		conf.addResource(new Path(""));
		
		
		
	}
}
