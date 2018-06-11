package io.github.oliviercailloux.y2018.teach_spreadsheets.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Choice;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CoursePref;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.CourseSheet;
import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;
import io.github.oliviercailloux.y2018.teach_spreadsheets.csv.CsvFileReader;
import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.ReadCourses;
import io.github.oliviercailloux.y2018.teach_spreadsheets.odf.WriteSpreadSheet;

public class TeachSpreadSheetController {

	private final static String template = "Saisie_voeux_dauphine_Template.ods";

	private InputStream source;

	private OutputStream destination;

	private List<CourseSheet> courseSheetList;

	public TeachSpreadSheetController(InputStream source, OutputStream destination, List<CourseSheet> courseSheetList) {
		this.source = source;
		this.destination = destination;
		this.courseSheetList = courseSheetList;
	}

	public List<String> getYearNames() {
		List<String> yearNames = new ArrayList<>();

		for (CourseSheet courseSheet : courseSheetList) {
			yearNames.add(courseSheet.getYearOfStud());
		}
		return yearNames;
	}

	public CourseSheet getCourseSheetByYear(String yearName) {
		for (CourseSheet courseSheet : courseSheetList) {
			if (courseSheet.getYearOfStud().equals(yearName)) {
				return courseSheet;
			}
		}
		return null;
	}

	public List<String> getCoursesName(String yearName, int semester) {
		List<String> coursesName = new ArrayList<>();
		CourseSheet courseSheet = getCourseSheetByYear(yearName);
		if (semester % 2 == 0) {
			for (CoursePref coursePref : courseSheet.getCoursePrefS1()) {
				coursesName.add(coursePref.getCourse().getName());
			}
		} else {
			for (CoursePref coursePref : courseSheet.getCoursePrefS2()) {
				coursesName.add(coursePref.getCourse().getName());
			}
		}
		return coursesName;
	}

	public InputStream getSource() {
		return source;
	}

	public void setSource(InputStream source) {
		this.source = source;
	}

	public OutputStream getDestination() {
		return destination;
	}

	public void setDestination(OutputStream destination) {
		this.destination = destination;
	}

	public List<CourseSheet> getCourseSheetList() {
		return courseSheetList;
	}

	public void setCourseSheetList(List<CourseSheet> courseSheetList) {
		this.courseSheetList = courseSheetList;
	}

	public static void main(String[] args) throws Exception {
		// csv file of a teacher info
		String teacherPath = "C:\\Users\\lf947\\Documents\\JAVA\\L3_MIAGE\\JAVA\\WORKSPACE_1\\Projets\\Teach-spreadsheets\\src\\test\\resources\\io\\github\\oliviercailloux\\y2018\\teach_spreadsheets\\odf\\oneTeacherTest.csv";
		// file with all the courses
		String spreadSheetInitialPath = "C:\\Users\\lf947\\Documents\\JAVA\\L3_MIAGE\\JAVA\\WORKSPACE_1\\Projets\\Teach-spreadsheets\\src\\main\\resources\\io\\github\\oliviercailloux\\y2018\\teach_spreadsheets\\odf\\Saisie_voeux_dauphine.ods";
		// file with the template
		String spreadSheetInputPath = "C:\\Users\\lf947\\Documents\\JAVA\\L3_MIAGE\\JAVA\\WORKSPACE_1\\Projets\\Teach-spreadsheets\\src\\test\\resources\\io\\github\\oliviercailloux\\y2018\\teach_spreadsheets\\odf\\Saisie_voeux_dauphine_WriteTeacher.ods";
		// copie from the template
		String spreadSheetOutputPath = "C:\\Users\\lf947\\Documents\\JAVA\\L3_MIAGE\\JAVA\\WORKSPACE_1\\Projets\\Saisie_voeux_dauphine.ods";

		Teacher teacher = null;
		// Get teacher
		try (FileReader fileReader = new FileReader(teacherPath)) {
			teacher = CsvFileReader.readTeacherFromCSVfile(fileReader);
		}
		// Read courses from spreadsheet
		List<CourseSheet> courseSheets = new ArrayList<>();
		try (FileInputStream coursesInputStream = new FileInputStream(spreadSheetInitialPath)) {
			try (ReadCourses courseReader = new ReadCourses(coursesInputStream)) {
				courseSheets = courseReader.readCourseSheets();

				// Set course preferences

				courseSheets.get(3).getCoursePrefS1().get(0).setCmChoice(Choice.A);
				// Write spreadsheet
			}
		}
		try (FileInputStream templateInputStream = new FileInputStream(spreadSheetInputPath)) {
			try (FileOutputStream outputStream = new FileOutputStream(spreadSheetOutputPath)) {
				WriteSpreadSheet.writeSpreadSheet(templateInputStream, outputStream, courseSheets, teacher);
			}
		}
	}

}
