package com.example.csv.reader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelReader {
	private String file;
	public ExcelReader(){
		
	}
	public ExcelReader(String file){
		this.file = file;
	}
	public int getRowNumber(){
		return read().getPhysicalNumberOfRows();
	}
	
	public HSSFSheet read(){
		HSSFSheet sheet = null;
		HSSFWorkbook wb = null;
		try{
			
			FileInputStream in = new FileInputStream(new File(file));
			
//			POIFSDocument doc = new POIFSDocument(null, in);
//			POIFSFileSystem fs = new POIFSFileSystem();
//			
//			fs.createDocumentInputStream("Calling2015a.xls");
			
			wb = new HSSFWorkbook(in);

			sheet = wb.getSheetAt(0);
			
			/*
		    HSSFRow row;
		    HSSFCell cell;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    
		    for(int i = 1; i < 148; i++){
		    	row = sheet.getRow(i);
		    	
		    }
		    
		    
		  */
		    
//		    int cols = 0; // No of columns
//		    int tmp = 0;
//
//		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
//		    for(int i = 0; i < 10 || i < rows; i++) {
//		        row = sheet.getRow(i);
//		        if(row != null) {
//		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//		            if(tmp > cols) cols = tmp;
//		        }
//		    }
//
//		    for(int r = 0; r < rows; r++) {
//		        row = sheet.getRow(r);
//		        if(row != null) {
//		            for(int c = 0; c < cols; c++) {
//		                cell = row.getCell((short)c);
//		                if(cell != null) {
//		                    // Your code here
//		                }
//		            }
//		        }
//		    }
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
		return sheet;
	}
}
