package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.File;
import java.util.Objects;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.teach_spreadsheets.courses.Teacher;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Louis Fontaine (fontlo15) This class
 *         allows to write informations of a teacher in spreadsheet Document.
 *         <p>
 *         The position where the fields are is static final attributes.
 *         </p>
 *         spreadsheet Version : 1.0 Last update : 26/05/2018
 */
public class WriteTeacher {

	// All informations fields position
	private final static String NAMEPOSITION = "B2";
	private final static String FIRSTNAMEPOSITION = "F2";
	private final static String ADRESSPOSITION = "B4";
	private final static String POSTCODEPOSITION = "B5";
	private final static String CITYPOSITION = "D5";
	private final static String PERSONALPHONEPOSITION = "B6";
	private final static String MOBILEPHONEPOSITION = "E6";
	private final static String PERSONALMAILPOSITION = "B8";
	private final static String DAUPHINEMAILPOSITION = "B9";
	private final static String STATUSPOSITION = "B11";
	private final static String DAUPHINEPHONEPOSITION = "E11";
	private final static String DESKPOSITION = "H11";
	private final static String SHEETNAME = "Emplois_du_temps";

	private File odfFile = null;
	private SpreadsheetDocument workbook = null;
	private final static Logger LOGGER = LoggerFactory.getLogger(WriteCourses.class);

	public WriteTeacher(File odfFile) throws Exception {
		this.odfFile = Objects.requireNonNull(odfFile);
		this.workbook = SpreadsheetDocument.loadDocument(this.odfFile);
		LOGGER.info("File" + odfFile + "has been correctly loaded");
	}

	/**
	 * Write all informations of teacher in corresponding fields.
	 */
	public void write(Teacher teacher) throws Exception {
		LOGGER.info("Start writing teacher in file : " + odfFile.getName());
		Table sheet = this.workbook.getSheetByName(SHEETNAME);

		System.out.println(sheet.getCellByPosition("A2").getDisplayText());
		System.out.println(sheet.getTableName());

		// Fill fields

		sheet.getCellByPosition(NAMEPOSITION).setStringValue(teacher.getName());
		sheet.getCellByPosition(FIRSTNAMEPOSITION).setStringValue(teacher.getFirstName());
		sheet.getCellByPosition(ADRESSPOSITION).setStringValue(teacher.getAdress());
		sheet.getCellByPosition(POSTCODEPOSITION).setStringValue(teacher.getPostCode());
		sheet.getCellByPosition(CITYPOSITION).setStringValue(teacher.getCity());
		sheet.getCellByPosition(PERSONALPHONEPOSITION).setStringValue(teacher.getPersonalPhone());
		sheet.getCellByPosition(MOBILEPHONEPOSITION).setStringValue(teacher.getMobilePhone());
		sheet.getCellByPosition(PERSONALMAILPOSITION).setStringValue(teacher.getPersonalMail());
		sheet.getCellByPosition(DAUPHINEMAILPOSITION).setStringValue(teacher.getDauphineMail());
		sheet.getCellByPosition(STATUSPOSITION).setStringValue(teacher.getStatus());
		sheet.getCellByPosition(DAUPHINEPHONEPOSITION).setStringValue(teacher.getDauphinePhone());
		sheet.getCellByPosition(DESKPOSITION).setStringValue(teacher.getDesk());

		workbook.save(odfFile);
		LOGGER.info("Teacher has been written successfully in file : " + odfFile.getName());

	}

}
