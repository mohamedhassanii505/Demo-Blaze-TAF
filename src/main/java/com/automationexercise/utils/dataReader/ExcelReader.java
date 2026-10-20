package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static final  String TEST_DATA_PATH = "src/test/resources/test-data";



    //row and col > 0
    //bad practice
    public static String getExcelData(String excelFileName,String sheetName,int rowNum,int colNum){
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String cellData;
        try{
            workBook = new XSSFWorkbook(TEST_DATA_PATH+excelFileName);
            sheet = workBook.getSheet(sheetName);
            cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
            return cellData;

        }
        catch (Exception e){
            LogsManager.error("Error reading excel file:",excelFileName,e.getMessage());
            return "";

        }
    }
}
