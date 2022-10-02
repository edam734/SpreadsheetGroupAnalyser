package edu.eduardo.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.eduardo.spreadsheet.SpreadsheetAnalyser.ScoreMap;

public class Main {

	enum SheetEnum {
		SCREENWRITING, I_NEED_A_PRODUCER;
	}

	public static void main(String[] args) throws IOException {
		FileInputStream file = new FileInputStream(new File("repo/Gente conhecida .xlsx"));
		Workbook workbook = new XSSFWorkbook(file);

		SpreadsheetAnalyser analyser = new SpreadsheetAnalyser(workbook);
		ScoreMap dataMap = analyser.getGroupingDataFromSheet(0);
		System.out.println(dataMap.getData("ze manel"));
	}

}
