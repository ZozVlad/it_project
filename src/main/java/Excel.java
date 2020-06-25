package main.java;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.models.Prospect;

public class Excel {
	private static Map<String, Object[]> data = new TreeMap<String, Object[]>();

	public static void setData(Object[] object){
		data.put(Integer.toString(data.size()), object);
	}

	public static Object[] getData(int index) {
		return data.get(Integer.toString(index));
	}

	public static int getCountRows(){
		return data.size();
	}
	public static int getColumnSize(String workbookName){
		int size = 0;
		try {
            FileInputStream file = new FileInputStream(new File(workbookName));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    size++;
                }
            }
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return size;
	}
	public static int getRowSize(String workbookName){
		int size = 0;
		try {
			FileInputStream file = new FileInputStream(new File(workbookName));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				size++;
			}
			file.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	public static ArrayList<String> getFirstRow(String workbookName){
		ArrayList<String> str = new ArrayList<String>();
		try {
            FileInputStream file = new FileInputStream(new File(workbookName));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC:
                        	str.add(cell.toString());
//                            System.out.printf("%-20s", cell.getNumericCellValue());
                            break;
                        case STRING:
                        	str.add(cell.toString());
//                            System.out.printf("%-20s", cell.getStringCellValue());
                            break;
					default:
						break;
                    }
                }
            }
            file.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return str;
	}

	public static void writingToExcel(String workbookName, String sheetName) {
		//Blank workbook
        @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet(sheetName);

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(workbookName));
            workbook.write(out);
            out.close();
            System.out.println("Excel.xlsx written successfully on disk.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void readingFromExcel(boolean fromSecondRow, String workbookName, ArrayList<String> choiceFromComboBox) {
		FileInputStream file = null;
		try {
			String target = "";
			file = new FileInputStream(new File(workbookName));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowStart = 0;
            if(fromSecondRow)
            	rowStart = 1;
			int lastColumn = getColumnSize(workbookName);
			int rowEnd = getRowSize(workbookName);
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row r = sheet.getRow(rowNum);
				if (r == null) {
					// This whole row is empty
					// Handle it as needed
					continue;
				}

            	Prospect p = new Prospect();

				for (int cn = 0; cn < lastColumn; cn++) {
					// Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					r.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					Cell c = r.getCell(cn);
					DataFormatter formatter = new DataFormatter();
					
					if (c == null) {
						// The spreadsheet is empty in this cell
						target = "";
					} else {
						// Do something useful with the cell's contents
						target = formatter.formatCellValue(c);
//						target = c.toString();
					}
                    switch (choiceFromComboBox.get(cn)) {
					case "EMAIL(required)":
						p.setEmail(target);
						break;
					case "FIRSTNAME":
						p.setFirstName(target);
						break;
					case "LASTNAME":
						p.setLastName(target);
						break;
					case "FULLNAME":
						p.setFullName(target);
						break;
					case "COMPANY":
						p.setCompany(target);
						break;
					case "PHONE":
						p.setPhone(target);
						break;
					case "ADDRESS":
						p.setAddress(target);
						break;
					case "CITY":
						p.setCity(target);
						break;
					case "SNIPPET1":
						p.setSnippet1(target);
						break;
					case "SNIPPET2":
						p.setSnippet2(target);
						break;
					case "SNIPPET3":
						p.setSnippet3(target);
						break;
					case "SNIPPET4":
						p.setSnippet4(target);
						break;
					case "SNIPPET5":
						p.setSnippet5(target);
						break;
					default:
						break;
					}
				}
                AddCampaignController.prospects.add(p);
			}

	        file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
