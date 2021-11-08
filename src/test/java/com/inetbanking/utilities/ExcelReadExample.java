package com.inetbanking.utilities;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelReadExample {
	
	public static void main (String[] args) {
		 //Apache poi will provide us the datatypes which will be utlised to read Excel file and store the data.
		XSSFWorkbook ExcelWBook;
		XSSFSheet ExcelWSheet;
		XSSFCell ExcelCell;
		
		String path=System.getProperty("user.dir")+"//Test-Data//2-ExampleData.xlsx";
		String sheetName="Scenario1";
		
		try {
			FileInputStream ExcelFile=new FileInputStream(path);
			ExcelWBook= new XSSFWorkbook(ExcelFile);
			ExcelWSheet= ExcelWBook.getSheet(sheetName);
			ExcelCell=ExcelWSheet.getRow(1).getCell(0);
			
			String cellData=ExcelCell.getStringCellValue();
			System.out.println(cellData);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
