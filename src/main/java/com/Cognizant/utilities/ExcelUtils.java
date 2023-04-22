package com.Cognizant.utilities;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	// ****************************** To read data from excel file***********************************//
	public static String[][] readExcelData() {

		/* To initialize the locater variable with the type */
		String[][] data = null;
		File file;
		FileInputStream fis = null;
		XSSFWorkbook workBook = null;

		try {
			file = new File("./src/test/resources/TestData/ExcelData.xlsx");
			fis = new FileInputStream(file);
			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheetAt(0);

			int rowCount = sheet.getLastRowNum();
			// System.out.println(rowCount);
			int cellCount = sheet.getRow(0).getLastCellNum();
			// System.out.println(cellCount);

			data = new String[rowCount][cellCount];

			for (int i = 1; i <= rowCount; i++) {
				XSSFRow row = sheet.getRow(i);
				for (int j = 0; j < cellCount; j++) {
					XSSFCell cell = row.getCell(j);
					String value = cell.getStringCellValue();
					// System.out.println(value);
					data[i - 1][j] = value;
					// System.out.println(data[i-1][j]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
