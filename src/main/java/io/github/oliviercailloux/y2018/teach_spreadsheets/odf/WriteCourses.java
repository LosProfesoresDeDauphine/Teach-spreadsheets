/**
 * 
 */
package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Course;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel Cohen (samuelcohen75) This
 *         class allows to write a year of study as a new odf sheet in an odf
 *         spreadsheet Version : 2.1 Last update : 13/05/2018
 */
public class WriteCourses {
	private File odfFile = null;
	private SpreadsheetDocument workbook = null;
	private CourseSheet courseSheet = null;
	private final static Logger LOGGER = LoggerFactory.getLogger(WriteCourses.class);

	public WriteCourses(File odfFile, CourseSheet courseSheet) throws Exception {
		this.odfFile = Objects.requireNonNull(odfFile);
		this.workbook = SpreadsheetDocument.loadDocument(this.odfFile);
		this.courseSheet = Objects.requireNonNull(courseSheet);
		LOGGER.info("File" + odfFile + "has been correctly loaded");
	}

	/**
	 * This method appends a new sheet corresponding to courses year of study. Then
	 * it writes the year of study sheet template, with all headers informations,
	 * the 2 semester arrays and comment text zone. It also configures cells format.
	 * At the end it calls WriteSemesterCourses to fill the Arrays. We assume that
	 * all the courses of firstSemesterCourses and secondSemesterCourses correspond
	 * to the courses of the same year of study. We assume that the corresponding
	 * sheet doesn't exist yet.
	 */
	public void WriteCoursesOfYear() throws Exception {

		// Design handling
		Font headerBoldFont = new Font("Calibri", StyleTypeDefinitions.FontStyle.BOLD, 12, Color.BLACK);
		Font headerFont = new Font("Calibri", StyleTypeDefinitions.FontStyle.REGULAR, 12, Color.BLACK);
		Font cellContentFont = new Font("Calibri", StyleTypeDefinitions.FontStyle.REGULAR, 11, Color.BLACK);
		Border border1 = new Border(Color.BLACK, 1, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Border border2 = new Border(Color.BLACK, 2, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Color headerColor = new Color(224, 224, 224);

		int firstArraySize = this.courseSheet.getCourseS1().size();
		int secondArraySize = this.courseSheet.getCourseS2().size();

		// Adding new sheeth
		Table sheet = this.workbook.appendSheet(courseSheet.getYearOfStud());
		int yearBegin = Course.getYearBegin();
		int yearEnd = yearBegin + 1;
		// Creating template
		// Header
		writeHeader(headerBoldFont, headerFont, border2, headerColor, sheet, yearBegin, yearEnd);

		// Semester Arrays

		// Arrays cells Format
		// First Array
		setSheetSemesterFormat(1, cellContentFont, border1, border2, firstArraySize, sheet);

		// Second Array
		setSheetSemesterFormat(15, cellContentFont, border1, border2, secondArraySize, sheet);

		// Arrays Header
		String[] arrayHeader = { "Matière", "code Apogée", "Responsable UE " + yearBegin + "-" + yearEnd,
				"Intervenants " + yearBegin + "-" + yearEnd, "COURS", "TD ou cours-TD", "TP ou cours-TP",
				"Nombre de groupes indicatif", "Choix Cours", "Choix TD/CMTD", "Choix TP/CMTP",
				"Nombre de TD/TP souhaités", "Nombre d'années d'enseignement de la matière" };
		fillHeader(1, headerColor, sheet, arrayHeader);
		fillHeader(15, headerColor, sheet, arrayHeader);

		// Comment text zone
		sheet.getCellByPosition("B" + (firstArraySize + 5)).setTextWrapped(true);
		sheet.getCellByPosition("B" + (firstArraySize + 5)).setFont(cellContentFont);
		sheet.getCellByPosition("B" + (firstArraySize + 5)).setStringValue("COMMENTAIRES");
		sheet.getCellRangeByPosition("C" + (firstArraySize + 5), "N" + (firstArraySize + 9)).merge();

		sheet.getCellByPosition("P" + (secondArraySize + 5)).setTextWrapped(true);
		sheet.getCellByPosition("P" + (secondArraySize + 5)).setFont(cellContentFont);
		sheet.getCellByPosition("P" + (secondArraySize + 5)).setStringValue("COMMENTAIRES");
		sheet.getCellRangeByPosition("Q" + (secondArraySize + 5), "AB" + (secondArraySize + 9)).merge();

		this.workbook.save(this.odfFile);

		LOGGER.info("File" + odfFile + "has been correctly saved");

		// To fill the Arrays with courses informations
		WriteSemesterCourses(courseSheet.getCourseS1(), 1, 3);
		WriteSemesterCourses(courseSheet.getCourseS2(), 15, 3);
	}

	/**
	 * This method fills one semester Array by using column and line cursors.
	 * 
	 * @param courseList
	 *            is a list of courses that will be fill in the array
	 * @param col
	 *            is the number of the column where we begin the filling
	 * @param line
	 *            is the number of the line where we begin the filling
	 * @throws Exception
	 */
	private void WriteSemesterCourses(List<Course> coursesList, int col, int line) throws Exception {

		// Design handling
		Border border = new Border(Color.BLACK, 1, StyleTypeDefinitions.SupportedLinearMeasure.PT);
		Table sheet = this.workbook.getSheetByName(coursesList.get(0).getYearOfStud());

		int lineCursor = line;

		// Writing course by row
		for (Course course : coursesList) {
			// Name
			sheet.getCellByPosition(col, lineCursor).setStringValue(course.getName());

			// apogeeCode
			sheet.getCellByPosition(col + 1, lineCursor).setStringValue(course.getapogeeCode());

			// superVisor
			if (course.getSupervisor().equals(""))
				sheet.getCellByPosition(col + 2, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 2, lineCursor).setStringValue(course.getSupervisor());

			// teachers
			if (course.getTeachers().equals(""))
				sheet.getCellByPosition(col + 3, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 3, lineCursor).setStringValue(course.getTeachers());

			// CM hours
			if (course.getCM_Hour() == 0.0)
				sheet.getCellByPosition(col + 4, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 4, lineCursor).setStringValue(course.getCM_Hour() + "h CM");
			// TD and CMTD

			if (course.getCMTD_Hour() == 0.0 && course.getTD_Hour() == 0.0) {
				sheet.getCellByPosition(col + 5, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				if (course.getCMTD_Hour() != 0.0) {
					sheet.getCellByPosition(col + 5, lineCursor).setStringValue(course.getCMTD_Hour() + "h CMTD");
				} else {
					sheet.getCellByPosition(col + 5, lineCursor).setStringValue(course.getTD_Hour() + "h TD");

				}
			}

			// TP and CMTP
			if (course.getCMTP_Hour() == 0.0 && course.getTP_Hour() == 0.0) {
				sheet.getCellByPosition(col + 6, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			} else {
				if (course.getCMTP_Hour() != 0.0) {
					sheet.getCellByPosition(col + 6, lineCursor).setStringValue(course.getCMTP_Hour() + "h CMTP");
				} else {
					sheet.getCellByPosition(col + 6, lineCursor).setStringValue(course.getTP_Hour() + "h TP");

				}
			}

			// groups number
			if (course.getGrpsNumber().equals(""))
				sheet.getCellByPosition(col + 7, lineCursor).setBorders(CellBordersType.DIAGONALBLTR, border);
			else
				sheet.getCellByPosition(col + 7, lineCursor).setStringValue(course.getGrpsNumber());
			lineCursor++;
		}

		this.workbook.save(this.odfFile);

		LOGGER.info("File" + odfFile + "has been correctly saved");

	}

	public void writeHeader(Font headerBoldFont, Font headerFont, Border border2, Color headerColor, Table sheet,
			int yearBegin, int yearEnd) {
		sheet.getCellByPosition("B1").setFont(headerBoldFont);
		sheet.getCellByPosition("B1").setStringValue(courseSheet.getCompleteYearOfStudyName());
		sheet.getCellRangeByPosition("B1", "E1").merge();
		sheet.getCellByPosition("B1").setCellBackgroundColor(headerColor);
		sheet.getCellByPosition("B1").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("B1").setTextWrapped(true);

		sheet.getCellByPosition("G1").setFont(headerFont);
		sheet.getCellByPosition("G1").setStringValue("prévision " + yearBegin + "/" + yearEnd);
		sheet.getCellRangeByPosition("G1", "I1").merge();
		sheet.getCellByPosition("G1").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("G1").setTextWrapped(true);

		sheet.getCellByPosition("J1").setFont(headerFont);
		sheet.getCellByPosition("J1").setStringValue(": " + courseSheet.getStudentNumber() + " étudiants");
		sheet.getCellRangeByPosition("J1", "L1").merge();
		sheet.getCellByPosition("J1").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("J1").setTextWrapped(true);

		sheet.getCellByPosition("B2").setFont(headerBoldFont);
		sheet.getCellByPosition("B2").setStringValue("ENSEIGNEMENTS SEMESTRE " + courseSheet.getFirstSemesterNumber());
		sheet.getCellRangeByPosition("B2", "N2").merge();
		sheet.getCellByPosition("B2").setCellBackgroundColor(headerColor);
		sheet.getCellByPosition("B2").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("B2").setBorders(CellBordersType.ALL_FOUR, border2);
		sheet.getCellByPosition("B2").setTextWrapped(true);
		sheet.getCellByPosition("P2").setFont(headerBoldFont);
		sheet.getCellByPosition("P2")
				.setStringValue("ENSEIGNEMENTS SEMESTRE " + (courseSheet.getFirstSemesterNumber() + 1));
		sheet.getCellRangeByPosition("P2", "AB2").merge();
		sheet.getCellByPosition("P2").setCellBackgroundColor(headerColor);
		sheet.getCellByPosition("P2").setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		sheet.getCellByPosition("P2").setBorders(CellBordersType.ALL_FOUR, border2);
		sheet.getCellByPosition("P2").setTextWrapped(true);
	}

	public void setSheetSemesterFormat(int firstColumnPosition, Font cellContentFont, Border border1, Border border2,
			int firstArraySize, Table sheet) {
		for (int i = 2; i <= firstArraySize + 2; i++) {
			for (int j = firstColumnPosition; j <= firstColumnPosition + 12; j++) {
				sheet.getCellByPosition(j, i).setBorders(CellBordersType.ALL_FOUR, border1);
				sheet.getCellByPosition(j, i).setTextWrapped(true);
				sheet.getCellByPosition(j, i).setFont(cellContentFont);
				sheet.getCellByPosition(j, i).setVerticalAlignment(StyleTypeDefinitions.VerticalAlignmentType.MIDDLE);
				if (j == firstColumnPosition) {
					sheet.getCellByPosition(j, i)
							.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.LEFT);
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.LEFT, border2);
				} else
					sheet.getCellByPosition(j, i)
							.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
				if (j == firstColumnPosition + 7 || j == firstColumnPosition + 12)
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.RIGHT, border2);
				if (i == firstArraySize + 2)
					sheet.getCellByPosition(j, i).setBorders(CellBordersType.BOTTOM, border2);
			}
		}
	}

	public void fillHeader(int firstColumn, Color headerColor, Table sheet, String[] arrayHeader) {
		for (int j = firstColumn; j <= firstColumn + 12; j++) {
			sheet.getCellByPosition(j, 2).setStringValue(arrayHeader[j - firstColumn]);
			sheet.getCellByPosition(j, 2).setCellBackgroundColor(headerColor);
		}
	}
	// Setters & Getters

	public File getODFFile() {
		return odfFile;
	}

	public void setODFFile(File odfFile) {
		this.odfFile = odfFile;
	}

	public SpreadsheetDocument getWorkbook() {
		return workbook;
	}

	public CourseSheet getCourseSheet() {
		return courseSheet;
	}

	public void setCourseSheet(CourseSheet courseSheet) {
		this.courseSheet = courseSheet;
	}

}