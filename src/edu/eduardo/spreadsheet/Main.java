package edu.eduardo.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

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
		Scanner sc = new Scanner(System.in);
		boolean success = false;
		do {
			System.out.println("1- search");
			System.out.println("2- exit");
			try {
				int op = sc.nextInt();
				sc.nextLine();
				if (op == 1) {
					System.out.println("Enter the name to search:");
					String name = sc.nextLine();
					String result = dataMap.getData(name);
					if (result.isEmpty()) {
						System.out.println("The name is not contained in the file...");
					} else {
						System.out.println(result);
						success = true;
					}
				} else if (op == 2) {
					break;
				} else {
					System.out.println("WARN: unknown option. Try again.");
				}
			} catch (

			InputMismatchException e) {
				System.out.println("WARN: must be an avaiable option. Try again.");
				sc.nextLine();
			}
		} while (!success);
		
		sc.close();
	}

}
