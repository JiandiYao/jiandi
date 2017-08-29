package com.example.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperRe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sourceFileName = "ClosingFactSheet.jrxml";  
		try {
			JasperCompileManager.compileReportToFile(sourceFileName);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
