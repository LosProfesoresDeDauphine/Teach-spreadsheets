package fr.losprofesoresdedauphine.courses;
public class Main {

	public static void main(String[] args) {
		Course c=new Course("samuel","14","2008");
		CoursePref cp = new CoursePref(c, "Samuel");
		cp.setCmChoice(Choice.A);
		cp.setTdChoice(Choice.B);
		cp.setCmtdChoice(Choice.C);
		cp.setTpChoice(Choice.ABSENT);
		//CoursePref cp1 = new CoursePref(c, "Samuel", Choice.A, Choice.B, Choice.C, Choice.ABSENT);
		System.out.println(c.getName() + " CM : " + cp.getCmChoice());
		System.out.println(c.getName() + " TD : " + cp.getTdChoice());
		System.out.println(c.getName() + " CMTD : " + cp.getCmtdChoice());
		System.out.println(c.getName() + " TP : " + cp.getTpChoice());
		System.out.println("\n" + cp.toString());
		//System.out.println("\n" + cp1.toString());
		
		System.out.println("\n" + c);
		// TODO Auto-generated method stub

	}

}