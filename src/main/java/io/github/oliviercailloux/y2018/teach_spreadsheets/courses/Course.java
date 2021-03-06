package io.github.oliviercailloux.y2018.teach_spreadsheets.courses;

import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * @author Tuan Nam Davaux (tuannamdavaux), Samuel COHEN (samuelcohen75)
 * @contributors Louis Fontaine, Victor CHEN
 * 
 *               <p>
 *               <b> Contract 1 This class makes it possible to store a course :
 *               Name, Peak Code, CM hours, TD hours, CMTD hours, TP hours, CM
 *               groups / TD hours / etc., and the year of studies in which the
 *               course is inserted package
 *               io.github.oliviercailloux.y2018.teach_spreadsheets.java; (L3
 *               Computing, M1 Mathematics, L3 learning,...) </b>
 *               </p>
 */
public class Course {
	private static int yearBegin = 2017;
	private String name = "", apogeeCode = "", yearOfStud = "";
	private double cmH = 0.0, tdH = 0.0, cmtdH = 0.0, tpH = 0.0, cmtpH = 0.0;

	private int semester = 0;

	private String grpsNumber = "";

	private String teachers = "", supervisor = "";

	public Course(String name, String apogeeCode, String yearOfStud, String supervisor, String teachers, int semester) {
		this.name = Objects.requireNonNull(name);
		this.apogeeCode = Objects.requireNonNull(apogeeCode);
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
		this.supervisor = Objects.requireNonNull(supervisor);
		this.teachers = Objects.requireNonNull(teachers);
		Preconditions.checkArgument(semester >= 1 && semester <= 6);
		this.semester = semester;

	}

	public Course() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, apogeeCode, yearOfStud, supervisor, teachers, grpsNumber, cmH, tdH, cmtdH, tpH,
				cmtpH);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (apogeeCode == null) {
			if (other.apogeeCode != null)
				return false;
		} else if (!apogeeCode.equals(other.apogeeCode))
			return false;
		if (Double.doubleToLongBits(cmH) != Double.doubleToLongBits(other.cmH))
			return false;
		if (Double.doubleToLongBits(cmtdH) != Double.doubleToLongBits(other.cmtdH))
			return false;
		if (Double.doubleToLongBits(cmtpH) != Double.doubleToLongBits(other.cmtpH))
			return false;
		if (grpsNumber == null) {
			if (other.grpsNumber != null)
				return false;
		} else if (!grpsNumber.equals(other.grpsNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (supervisor == null) {
			if (other.supervisor != null)
				return false;
		} else if (!supervisor.equals(other.supervisor))
			return false;
		if (Double.doubleToLongBits(tdH) != Double.doubleToLongBits(other.tdH))
			return false;
		if (teachers == null) {
			if (other.teachers != null)
				return false;
		} else if (!teachers.equals(other.teachers))
			return false;
		if (Double.doubleToLongBits(tpH) != Double.doubleToLongBits(other.tpH))
			return false;
		if (yearOfStud == null) {
			if (other.yearOfStud != null)
				return false;
		} else if (!yearOfStud.equals(other.yearOfStud))
			return false;
		return true;
	}

	public void set(int index, String data) {
		assert data != null;

		switch (index) {
		case 0:
			setName(data);
			break;
		case 1:
			setapogeeCode(data);
			break;
		case 2:
			setYearOfStud(data);
			break;
		case 3:
			setCM_Hour(Double.parseDouble(data));
			break;
		case 4:
			setTD_Hour(Double.parseDouble(data));
			break;
		case 5:
			setCMTD_Hour(Double.parseDouble(data));
			break;
		case 6:
			setTP_Hour(Double.parseDouble(data));
			break;
		case 7:
			setGrpsNumber(data);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {
		return "name : " + this.name + "  code : " + this.apogeeCode + "  year: " + this.yearOfStud + "\nCM hours: "
				+ this.cmH + "  TD hours: " + this.tdH + "  CMTD hour: " + this.cmtdH + "\nTP hours: " + this.tpH
				+ "  Groups number: " + this.grpsNumber + "\nResponsable: " + this.getSupervisor() + "\nIntervenants: "
				+ this.getTeachers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public String getapogeeCode() {
		return apogeeCode;
	}

	public void setapogeeCode(String apogeeCode) {
		this.apogeeCode = Objects.requireNonNull(apogeeCode);
	}

	public String getYearOfStud() {
		return yearOfStud;
	}

	public void setYearOfStud(String yearOfStud) {
		this.yearOfStud = Objects.requireNonNull(yearOfStud);
	}

	public double getCM_Hour() {
		return cmH;
	}

	public void setCM_Hour(double cmH) {
		if (cmH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmH = cmH;
	}

	public double getTD_Hour() {
		return tdH;
	}

	public void setTD_Hour(double tdH) {
		if (tdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.tdH = tdH;
	}

	public double getCMTD_Hour() {
		return cmtdH;
	}

	public void setCMTD_Hour(double cmtdH) {
		if (cmtdH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmtdH = cmtdH;
	}

	public double getTP_Hour() {
		return tpH;
	}

	public void setTP_Hour(double tpH) {
		if (tpH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.tpH = tpH;
	}

	public double getCMTP_Hour() {
		return cmtpH;
	}

	public void setCMTP_Hour(double cmtpH) {
		if (cmtpH < 0)
			throw new IllegalArgumentException("Negative number of hours !");
		this.cmtpH = cmtpH;
	}

	public String getGrpsNumber() {
		return this.grpsNumber;
	}

	public void setGrpsNumber(String grpsNumber) {
		this.grpsNumber = grpsNumber;
	}

	public String getTeachers() {
		return teachers;
	}

	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public static int getYearBegin() {
		return yearBegin;
	}

	public static void setYearBegin(int yearBegin) {
		Course.yearBegin = yearBegin;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		Preconditions.checkArgument(semester >= 1 && semester <= 6);
		this.semester = semester;
	}

}