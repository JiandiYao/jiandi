package com.example.sparktutorial.spark;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.api.java.JavaSQLContext;
import org.apache.spark.sql.api.java.JavaSchemaRDD;
import org.apache.spark.sql.api.java.Row;

public class ReadParquet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SparkConf sparkConf = new SparkConf().setMaster("local[4]").setAppName("parquet test");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		JavaSQLContext sqlContext = new JavaSQLContext(jsc);
		
		JavaSchemaRDD parquetFile = sqlContext.parquetFile("textfile/people.parquet");
		
		parquetFile.registerTempTable("parquetFile");
		JavaSchemaRDD teenagers = sqlContext.sql("SELECT name FROM parquetFile");
		List<String> teenagerNames = teenagers.map(new Function<Row, String>() {
		  public String call(Row row) {
			  
			  System.out.println(row.getString(0));
		    return "Name: " + row.getString(0);
		  }
		}).collect();
	}

}
