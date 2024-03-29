package com.elearning.studyvocabulary.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.elearning.studyvocabulary.model.entity.Question;
import com.elearning.studyvocabulary.model.entity.Test;
import com.elearning.studyvocabulary.model.entity.Topic;
import com.elearning.studyvocabulary.model.entity.Vocabulary;

public class ReadExcel {
	
	public static List<Question> readExcelQuestion(String excelFilePath, Test test) throws IOException {
        List<Question> listQuestions = new ArrayList<>();
 
        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));
 
        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
 
        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);
        
        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
 
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();
 
            // Read cells and set value for book object
            Question question = new Question();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                case 0:
                    question.setQuestionNumber((String) getCellValue(cell));
                    break;
                case 1:
                    question.setContent((String) getCellValue(cell));
                    break;
                case 2:
                    question.setOption_1((String) getCellValue(cell));
                    break;
                case 3:
                    question.setOption_2((String) getCellValue(cell));
                    break;
                case 4:
                	question.setOption_3((String) getCellValue(cell));
                	break;
                case 5:
                	question.setOption_4((String) getCellValue(cell));
                	break;
                case 6:
                	question.setCorrectAnswer((String) getCellValue(cell));
                	break;
                default:
                    break;
                }
 
            }
            question.setTest(test);
            listQuestions.add(question);
        }
 
        workbook.close();
        inputStream.close();
 
        return listQuestions;
    }
	
	public static List<Vocabulary> readExcelVocab(String excelFilePath, Topic topic) throws IOException {
		List<Vocabulary> listVocabularies = new ArrayList<>();
		 
        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));
 
        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
 
        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);
        
        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }
 
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();
 
            // Read cells and set value for book object
            Vocabulary vocabulary = new Vocabulary();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
	                case 0:
	                	vocabulary.setVocab((String) getCellValue(cell));
	                    break;
	                case 1:
	                	vocabulary.setFromType((String) getCellValue(cell));
	                    break;
	                case 2:
	                	vocabulary.setMeans((String) getCellValue(cell));
	                    break;
	                case 3:
	                	vocabulary.setTranscription((String) getCellValue(cell));
	                    break;
	                case 4:
	                	vocabulary.setExample((String) getCellValue(cell));
	                	break;
	                default:
	                    break;
                }
 
            }
            vocabulary.setTopic(topic);
            listVocabularies.add(vocabulary);
        }
        workbook.close();
        inputStream.close();
        return listVocabularies;
    }
	
	private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
 
        return workbook;
    }
	private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
        case BOOLEAN:
            cellValue = cell.getBooleanCellValue();
            break;
        case FORMULA:
            Workbook workbook = cell.getSheet().getWorkbook();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            cellValue = evaluator.evaluate(cell).getNumberValue();
            break;
        case NUMERIC:
            cellValue = cell.getNumericCellValue();
            break;
        case STRING:
            cellValue = cell.getStringCellValue();
            break;
        case _NONE:
        case BLANK:
        case ERROR:
            break;
        default:
            break;
        }
 
        return cellValue;
    }
}
