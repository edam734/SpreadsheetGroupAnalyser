package edu.eduardo.spreadsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;

public class SpreadsheetAnalyser {

	Workbook workbook;

	public SpreadsheetAnalyser(Workbook workbook) {
		super();
		this.workbook = workbook;
	}

	public ScoreMap getGroupingDataFromSheet(int n) {
		Sheet sheet = workbook.getSheetAt(n);

		ScoreMap data = new ScoreMap();
		data.init();

		for (Row row : sheet) {
			for (Cell cell : row) {
				CellType type = cell.getCellTypeEnum();
				if (CellType.STRING.equals(type)) {
					CellAddress address = cell.getAddress();
					int score = address.getColumn();
					boolean isData = address.getRow() > 3;
					if (isData && score < 6) {
						data.putData(score, cell.getStringCellValue());
					}
				}
			}
		}
		return data;
	}

	static class ScoreMap {
		Map<Integer, List<String>> data;

		public ScoreMap(Map<Integer, List<String>> data) {
			super();
			this.data = data;
		}

		public ScoreMap() {
			super();
			data = new HashMap<>();
		}

		public void init() {
			data.put(0, new ArrayList<>()); // Mau/Inútil
			data.put(1, new ArrayList<>()); // Meh
			data.put(2, new ArrayList<>()); // Bom
			data.put(3, new ArrayList<>()); // Óptimo
			data.put(4, new ArrayList<>()); // Actrizes
			data.put(5, new ArrayList<>()); // Produtores
			data.put(6, new ArrayList<>()); // Importantes
		}

		public void putData(Integer score, String value) {
			data.computeIfPresent(score, (key, val) -> {
				val.add(value);
				return val;
			});

		}

		public String getData(String value) {
			StringBuilder sb = new StringBuilder();
			Set<Integer> scores = data.keySet();
			boolean found = false;
			int key = 0;
			while (!found && key < scores.size()) {
				List<String> people = data.get(key);
				if (people.contains(value)) {
					sb.append(value).append(" : ").append(getCategory(key));
					found = true;
				} else {
					key++;
				}
			}

			return sb.toString();
		}

		private String getCategory(int index) {
			return Category.values()[index].getStr();
		}

		@Override
		public String toString() {
			return "ScoreMap [data=" + data + "]";
		}

		public enum Category {
			HORRIBLE("Péssimo"),

			MEH("Meh"),

			GOOD("Bom"),

			BEST("Óptimo"),

			ACTRESS("Actrizes"),

			PRODUCERS("Produtores"),

			IMPORTANT("Importantes");

			String str;

			private Category(String str) {
				this.str = str;
			}

			public String getStr() {
				return str;
			}

		}

	}

}
