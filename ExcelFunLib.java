package com.optum.topsuat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelFunLib 
{
	public static String key = "";
	public static String value = "";
	public static ArrayList<String> TDHeaderlist = new ArrayList<String>();
	public static ArrayList<String> TDTempValuelist = new ArrayList<String>();
	public static ArrayList<String> TDValuelist = new ArrayList<String>();
	public static ArrayList<String> CS_Fields=new ArrayList<String>();
	public static XSSFWorkbook workbook = null;
	public static Sheet sheet = null;
	public static Row row = null;
	public static Cell cell = null;
	public static String CellData,CellData_Up;//Added
	public static int col_index_header,rowNumStart_Cust;
	@SuppressWarnings("rawtypes")
	ArrayList list = new ArrayList();

	// Map<String, String>

	@SuppressWarnings({ "rawtypes" })
	public static Map<String, String> ReadTestdata(String filePath, String Sheetname,String Rowflag) {

		// filePath="TestMethodDetails.xls";
		Map<String, String> TestData = new HashMap<String, String>();
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			// Get the workbook instance for XLS file
			workbook = new XSSFWorkbook(file);
			// Get first sheet from the workbook
			sheet = workbook.getSheet(Sheetname);
			// Read Header in Test Data sheet
			TDHeaderlist = ReadTestdataHeader();
			// sheet.getRow(GetNumRowFlag(Rowflag));
			// Iterate through each rows from first sheet
			// Iterator<Row> rowIterator = sheet.iterator();
			// while (rowIterator.hasNext()) {
			int Flagrownum = GetNumRowFlag(Rowflag);
			row = sheet.getRow(Flagrownum);
			// row = rowIterator.next();
			if (row.getRowNum() == Flagrownum) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					// System.out.println("Cell Type: "+cell.getCellType());
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						Double a = cell.getNumericCellValue();
						key = a.toString();
						break;
					case Cell.CELL_TYPE_STRING:
						key = cell.getStringCellValue();
						break;
					// case Cell.CELL_TYPE_BOOLEAN:
					// System.out.print(cell.getBooleanCellValue() + "\t\t");
					// key = cell.getBooleanCellValue();
					// list.add(cell.getBooleanCellValue());
					// break;
					} // End of Switch - Cell
						// System.out.println("After key Current row number :"+row.getRowNum());
						// System.out.println("key value :"+key);
					// Adding Cloumn values to a List
					TDTempValuelist.add(key);
					
					System.out.println("TD TEMP Value List" +TDTempValuelist);
				}// End of While Cell Iterator
			} // End of IF - Row
				// }// End of While Row Iterator
			// Trim decimals if present
			for (int i = 0; i < TDTempValuelist.size(); i++) {
				String s2 = TDTempValuelist.get(i);
				System.out.println("Value of s2" +s2);
				if (s2 != null) {
					s2 = s2.contains(".0") ? s2.replace(".0", "") : s2;
				}
				TDValuelist.add(s2);
				
				System.out.println("Value of TDValuelist:" +TDValuelist);
			}
			// Key value Pair in MAP
			if (TDHeaderlist.size() == TDValuelist.size()) {
				for (int i = 0; i < TDHeaderlist.size(); i++) {
					System.out.println("Value of i in TDHeaderlist: " +TDHeaderlist.get(i));
					System.out.println("Value of i in TDValuelist: " +TDValuelist.get(i));
					TestData.put(TDHeaderlist.get(i), TDValuelist.get(i));
				} // End OF FOR
				Iterator iter = TestData.entrySet().iterator();
				while (iter.hasNext()) {
					Entry or = (Map.Entry) iter.next();
					// System.out.println( or.getKey() + " : " + or.getValue());
				} // End of WHILE
				System.out.println();
				System.out.println("Reading TestData complete");
			} // END of IF
			else {
				System.out
						.println("Column count in not equal for header and data. "
								+ "Header contains :"
								+ TDHeaderlist.size()
								+ " and column contains : "
								+ TDValuelist.size());
			} // End of Else
			// file.close();
		} // End of Try block
		catch (IOException e) {
			//Reporter.log(" IO Exception in reading Test Data sheet:" + e);
			e.printStackTrace();
		} // End of Catch
		return TestData; // Key value Pair in MAP
	} // End of ReadTestdata method}

	public static  ArrayList<String> ReadTestdataHeader() {
		// Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if (row.getRowNum() == 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					key = cell.getStringCellValue();
					TDHeaderlist.add(key);
				} // End of While - Cell
				//System.out.println("After key Current row number :"
				//		+ row.getRowNum());
				//System.out.println("key value :" + key);
				// Row Flag start

			} // End of IF
		} // End of of While - Row
		System.out.println(TDHeaderlist);
		return TDHeaderlist;
	} // End of ReadTestdataHeader

	public static int GetNumRowFlag(String Rowflag) {
		int Rowflagnum = 0;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			// System.out.println("ROw No:"+ row.getRowNum );
			if (row.getRowNum() != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					key = cell.getStringCellValue();
					System.out.println("Value of key" +key);
					if (key.equals(Rowflag)) {

						Rowflagnum = row.getRowNum();
						System.out.println("Row number is" +Rowflagnum);
						return Rowflagnum;
					}

				}// End of While -Cell Iterator
			} // End of If - Row Iterator
		} // End of While - Row Iterator
		//System.out.println("Test data read from Row num: " + Rowflagnum);
		return Rowflagnum;
	} // End of GetNumRowFlag
	
	public static int  GetRowCount( String filePath, String Sheetname) {
        int a=0;
        try {
               System.out.println("Hello World");
           FileInputStream file = new FileInputStream(new File(filePath));
           // Get the workbook instance for XLS file
            workbook = new XSSFWorkbook(file);
           // Get first sheet from the workbook
           sheet = workbook.getSheet(Sheetname);
           a= sheet.getLastRowNum();
           }
        catch (IOException e) {
               // Reporter.log(" IO Exception in reading Test Data sheet:" +e);
                e.printStackTrace();
                a=0;
                       } // End of Catch
        return a;
        
       } // END of GetRowCount
	
	
	public static int GetRowNumforRowFlag(String filePath, String Sheetname,String Rowflag) throws IOException {
		FileInputStream file = new FileInputStream(new File(filePath));
		// Get the workbook instance for XLS file
		workbook = new XSSFWorkbook(file);
		// Get first sheet from the workbook
		sheet = workbook.getSheet(Sheetname);
		int Rowflagnum = 0;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			// System.out.println("ROw No:"+ row.getRowNum );
			if (row.getRowNum() != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					key = cell.getStringCellValue();
					if (key.equals(Rowflag)) {

						Rowflagnum = row.getRowNum();
						return Rowflagnum;
					}

				}// End of While -Cell Iterator
			} // End of If - Row Iterator
		} // End of While - Row Iterator
		//System.out.println("Test data read from Row num: " + Rowflagnum);
		return Rowflagnum;
	} // End of GetNumRowFlag

	public static ArrayList<String> getCellData(String filepath, String sheetName, String rowFlagName, int colCount_Header ) throws Exception
	{
		FileInputStream ExcelFile = new FileInputStream(filepath);
		 
		// Access the required test data sheet

		workbook = new XSSFWorkbook(ExcelFile);

		sheet = workbook.getSheet(sheetName);
		
		 for (Row row : sheet) {
		        for (Cell cell : row) {
		            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
		                if (cell.getRichStringCellValue().getString().trim().equals(rowFlagName)) {
		                    //return row.getRowNum();  
		                	CellData_Up=cell.getRichStringCellValue().getString();
		                	
		                	//System.out.println(CellData_Up);
		                	
		                	rowNumStart_Cust=GetRowNumforRowFlag(filepath,sheetName,rowFlagName);
		                	
		                	//System.out.println("Starting row is" +rowNumStart_Cust);
		                	
		                	
		                	
		                
		            }
		        }
		    }    
		        
		        
	
			}
		 
		 		if(CS_Fields.isEmpty())
			 		{
			 			for(int i=rowNumStart_Cust;i<=sheet.getLastRowNum();i++)
			 	     	{
			 			
			 			//System.out.println("In if condition");
			 			//System.out.println("Value of i" +i);
			 			//System.out.println("colCount_Header" +colCount_Header);
			 			String val=sheet.getRow(i).getCell(colCount_Header).getStringCellValue();
			 			System.out.println(val);
			 			CS_Fields.add(val);
			 			
			 			
			 	     	}
			 		}
			 		else
			 		{
			 			System.out.println();
			 			CS_Fields.clear();
			 			for(int i=rowNumStart_Cust;i<=sheet.getLastRowNum();i++)
			 	     	{
			 			
			 			//System.out.println("Value of i" +i);
			 			//System.out.println("colCount_Header" +colCount_Header);
			 			String val=sheet.getRow(i).getCell(colCount_Header).getStringCellValue();
			 			//System.out.println(val);
			 			CS_Fields.add(val);
			 	     	
			 			
			 		}
	        	
     	}

		
		return CS_Fields;
		
	}

	public static int getColCount(String filepath, String sheetName, String header_val) throws Exception
	{
		FileInputStream ExcelFile = new FileInputStream(filepath);
		// System.out.println(filepath);
		 System.out.println(sheetName);
		// Access the required test data sheet
		workbook = new XSSFWorkbook(ExcelFile);
		sheet = workbook.getSheet(sheetName);
		Row header_row=sheet.getRow(0);
		int tol_cell=header_row.getLastCellNum();
		System.out.println(header_row.getCell(0).getStringCellValue());
		System.out.println("Total value of cells" +tol_cell);
		for(int i=0;i<tol_cell;i++)
		{
				
				  // if (cell!=null && cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getRichStringCellValue().getString().trim().equals(header_val))
					   
					     Cell header_cell = header_row.getCell(i);
					     String header = header_cell.getStringCellValue();
					     System.out.println("Header value" +header);
					     if(header.equalsIgnoreCase(header_val))
					     {
					    	 System.out.println("Inside col index");
		                	col_index_header=header_cell.getColumnIndex();
		                	System.out.println("col_index_header: " +col_index_header);
				   	}
		    }
			   
		
		
		return col_index_header;
	}

	public static void writeinExcel(String filepath, String sheetName, String result, int romNum, int colval) throws Exception 
	{
		
		FileInputStream ExcelFile = new FileInputStream(filepath); 
		workbook = new XSSFWorkbook(ExcelFile);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		try{
			 
  			Row Row  = sheet.getRow(romNum);

			Cell Cell = Row.getCell(colval, Row.RETURN_BLANK_AS_NULL);

			if (Cell == null) 
			{
				
				if(result.equalsIgnoreCase("0.00"))
				{

					Cell = Row.createCell(colval);

					Cell.setCellValue(" ");
				}
				
				else
				{
					Cell = Row.createCell(colval);
					Cell.setCellValue(result);
				}
				
			}

			
		
		else 
		{
			
			if(result.equalsIgnoreCase("0.00"))
			{

				Cell = Row.createCell(colval);

				Cell.setCellValue(" ");
			}
			
			else
			{
				Cell = Row.createCell(colval);
				Cell.setCellValue(result);
			}

	}

  // Constant variables Test Data path and Test Data file name

  				FileOutputStream fileOut = new FileOutputStream(filepath);

  				workbook.write(fileOut);

  				fileOut.flush();

					fileOut.close();

				}catch(Exception e){

					throw (e);

			}
		
		
		
		
	}
	
	
	public static void writeinExcel_arr(String filepath, String sheetName,ArrayList<String> result, int romNum, int colval) throws Exception 
	{
		
		System.out.println("Inside write in excel");
		
		FileInputStream ExcelFile = new FileInputStream(filepath); 
		workbook = new XSSFWorkbook(ExcelFile);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		try{
			 
  			Row Row  = sheet.getRow(romNum);

			Cell Cell = Row.getCell(colval, Row.RETURN_BLANK_AS_NULL);
			for (int i=0;i<result.size();i++)
			{
				System.out.println("value of result i" +result.get(i));
				if (Cell == null) 
				{
				
					Cell = Row.createCell(colval);

					Cell.setCellValue(result.toString());
				}
				
				else
				{
					Cell = Row.createCell(colval);
					Cell.setCellValue(result.toString());
				}
	

	}

  // Constant variables Test Data path and Test Data file name

  				FileOutputStream fileOut = new FileOutputStream(filepath);

  				workbook.write(fileOut);

  				fileOut.flush();

					fileOut.close();

				}catch(Exception e){

					throw (e);

			}
		
		
		
		
	}
	
	
}
