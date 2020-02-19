package com.optum.topsuat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
/*import org.testng.Reporter;
import org.testng.annotations.Test;*/

public class Testdata  {

	// String Testdatafile = new commonfunctions.Testdata().Testdatafilepath;
	// String Sheetname = "Member";
	// String Rowflag = "Chandra";
	// String filePath="C:\\Users\\smallel1\\Desktop\\ICUETESTDATA.xls";

	String key = "";
	String value = "";
	ArrayList<String> TDHeaderlist = new ArrayList<String>();
	ArrayList<String> TDTempValuelist = new ArrayList<String>();
	ArrayList<String> TDValuelist = new ArrayList<String>();
	HSSFWorkbook workbook = null;
	HSSFSheet sheet = null;
	Row row = null;
	Cell cell = null;
	@SuppressWarnings("rawtypes")
	ArrayList list = new ArrayList();

	// Map<String, String>

	@SuppressWarnings({ "rawtypes" })
	public Map<String, String> ReadTestdata(String filePath, String Sheetname,String Rowflag) {

		// filePath="TestMethodDetails.xls";
		Map<String, String> TestData = new HashMap<String, String>();
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			// Get the workbook instance for XLS file
			workbook = new HSSFWorkbook(file);
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
				}// End of While Cell Iterator
			} // End of IF - Row
				// }// End of While Row Iterator
			// Trim decimals if present
			for (int i = 0; i < TDTempValuelist.size(); i++) {
				String s2 = TDTempValuelist.get(i);
				if (s2 != null) {
					s2 = s2.contains(".0") ? s2.replace(".0", "") : s2;
				}
				TDValuelist.add(s2);
			}
			// Key value Pair in MAP
			if (TDHeaderlist.size() == TDValuelist.size()) {
				for (int i = 0; i < TDHeaderlist.size(); i++) {
					TestData.put(TDHeaderlist.get(i), TDValuelist.get(i));
				} // End OF FOR
				Iterator iter = TestData.entrySet().iterator();
				while (iter.hasNext()) {
					Entry or = (Map.Entry) iter.next();
					// System.out.println( or.getKey() + " : " + or.getValue());
				} // End of WHILE
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

	public ArrayList<String> ReadTestdataHeader() {
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
		return TDHeaderlist;
	} // End of ReadTestdataHeader

	public int GetNumRowFlag(String Rowflag) {
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
	
	public int  GetRowCount( String filePath, String Sheetname) {
        int a=0;
        try {
               System.out.println("Hello World");
           FileInputStream file = new FileInputStream(new File(filePath));
           // Get the workbook instance for XLS file
            workbook = new HSSFWorkbook(file);
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
	
	
	public int GetRowNumforRowFlag(String filePath, String Sheetname,String Rowflag) throws IOException {
		FileInputStream file = new FileInputStream(new File(filePath));
		// Get the workbook instance for XLS file
		workbook = new HSSFWorkbook(file);
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

} // End OF TEST DATA Class

