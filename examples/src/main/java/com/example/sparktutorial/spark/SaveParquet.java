package com.example.sparktutorial.spark;

import java.util.*;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.api.java.DataType;
import org.apache.spark.sql.api.java.JavaSQLContext;
import org.apache.spark.sql.api.java.JavaSchemaRDD;
import org.apache.spark.sql.api.java.Row;
import org.apache.spark.sql.api.java.StructField;
import org.apache.spark.sql.api.java.StructType;
import org.codehaus.groovy.tools.shell.util.Logger;

import scala.Tuple2;

/**
 * Hello world!
 *
 */
public class SaveParquet 
{
//	Logger logger = Logger.create(App.class);
	
    public static void main( String[] args )
    {
    	SparkConf sparkConf = new SparkConf().setMaster("local[4]").setAppName("Lao gong bu shi ren");
    	JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        JavaSQLContext sqlContext = new JavaSQLContext(jsc);
    	
    	JavaRDD<String> lines = jsc.textFile("textfile/people.txt");
    	
    	JavaPairRDD<String, Integer> pair = lines.mapToPair(new PairFunction<String, String, Integer>(){

			public Tuple2<String, Integer> call(String line) throws Exception {
				
				String[] people = line.split(",");
				return new Tuple2<String, Integer>(people[0], new Integer(people[1]));
			}
    		
    	});
    	
    	List<StructField> fields = new ArrayList<StructField>();
    	
    	fields.add(DataType.createStructField("name", DataType.StringType, true));
    	fields.add(DataType.createStructField("age", DataType.IntegerType, true));
    	
    	StructType schema = DataType.createStructType(fields);
    	
    	
    	JavaRDD<Row> rows = pair.map(new Function<Tuple2<String, Integer>, Row>(){

			public Row call(Tuple2<String, Integer> args) throws Exception {
				
				return Row.create(args._1(), args._2());
			}
    		
    	});
    	
    	JavaSchemaRDD peopleSchemaRDD = sqlContext.applySchema(rows, schema);
    	peopleSchemaRDD.registerTempTable("people");
    	
    	peopleSchemaRDD.saveAsParquetFile("textfile/people.parquet");
    	
    }
}
