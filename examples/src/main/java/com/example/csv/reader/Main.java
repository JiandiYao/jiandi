package com.example.csv.reader;

public class Main {

	public static void main(String[] args) {
		String file = "./target/from/Merged_CSV_File_TPK_IQC_12_15.csv";
		ExcelReader reader = new ExcelReader(file);
		System.out.println(reader.getRowNumber());
		
	}

}
