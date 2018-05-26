package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Choice;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

public class WritePref {

	/**
	 * Assuming that the tables of coursePref starts at cells STARTCELL1 and
	 * STARTCELL2 for each sheet
	 */
	private final static String STARTCELL1 = "J4";
	/**
	 * Assuming that the tables of coursePref starts at cells STARTCELL1 and
	 * STARTCELL2 for each sheet
	 */
	private final static String STARTCELL2 = "X4";

	private File odfFile = null;
	private SpreadsheetDocument workbook = null;
	private CourseSheet courseSheet = null;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(WriteCourses.class);

	public WritePref(File odfFile, CourseSheet courseSheet) throws Exception {
		this.odfFile = Objects.requireNonNull(odfFile);
		this.workbook = SpreadsheetDocument.loadDocument(this.odfFile);
		this.courseSheet = Objects.requireNonNull(courseSheet);
		LOGGER.info("File" + odfFile + "has been correctly loaded");
	}

	/**
	 * This method writes the courses preferences for one semester
	 * 
	 * @param coursesPref
	 *            is a list of {@link CoursePref} that will be fill in the
	 *            spreadsheet
	 * @param startCellPosition
	 *            the starting position of the array of {@link CoursePref}, of
	 *            one semester
	 */
	private void writeSemesterCoursesPref(List<CoursePref> coursesPref,
			String startCellPosition) throws Exception {

		// Design handling
		Border border = new Border(Color.BLACK, 1,
				StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Table sheet = this.workbook.getSheetByName(courseSheet.getYearOfStud());

		Cell currentCell = sheet.getCellByPosition(startCellPosition);

		int startCellColumnIndex = currentCell.getColumnIndex();
		int currentRow = currentCell.getRowIndex();

		// Writing coursePref by row
		for (CoursePref coursePref : coursesPref) {
			// CM Choice
			currentCell = sheet.getCellByPosition(startCellColumnIndex,
					currentRow);
			if (coursePref.getCmChoice() == Choice.NA) {
				currentCell.setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				currentCell.setStringValue(coursePref.getCmChoice().toString());
			}

			// TD Choice
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 1,
					currentRow);
			if (coursePref.getTdChoice() == Choice.NA) {
				currentCell.setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				currentCell.setStringValue(coursePref.getTdChoice().toString());
			}

			// TP Choice
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 2,
					currentRow);
			if (coursePref.getTpChoice() == Choice.NA) {
				currentCell.setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				currentCell.setStringValue(coursePref.getTpChoice().toString());
			}

			// Groups number
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 3,
					currentRow);
			currentCell.setStringValue("CM : " + coursePref.getNbrGrpCm()
					+ ", TD : " + coursePref.getNbrGrpTd() + ", TP : "
					+ coursePref.getNbrGrpTp());

			// Experience
			currentCell = sheet.getCellByPosition(startCellColumnIndex + 4,
					currentRow);
			currentCell.setStringValue(String.valueOf(coursePref.getNbrExp()));

			currentRow++;

		}

		this.workbook.save(this.odfFile);

		LOGGER.info("File" + odfFile + "has been correctly saved");

	}

	/**
	 * This method writes all the course preferences of a sheet for both
	 * semester.
	 */
	public void writeSheetCoursesPref() throws Exception {

		writeSemesterCoursesPref(courseSheet.getCoursePrefS1(), STARTCELL1);
		writeSemesterCoursesPref(courseSheet.getCoursePrefS2(), STARTCELL2);

	}
}