package etf_project;

import java.util.ArrayList;
import java.util.List;

public class Exam {
	
    private final int EXAMS_PER_DAY = 4;
	private List<Boolean> flags; 
	private int accreditation;
	private String department;
	private int desk;
	private int schoolYear;
	private String subject;
	private int numOfRegistered;
	private boolean  onComputer;
	private String[] departmentSign;
	
	public Exam(int numOfDays, int accreditation, String department, int desk, int schoolYear, String subject, int numOfRegistered,
			boolean onComputer, String[] departmentSign) {
		
		super();
		this.accreditation = accreditation;
		this.department = department;
		this.desk = desk;
		this.schoolYear = schoolYear;
		this.subject = subject;
		this.numOfRegistered = numOfRegistered;
		this.onComputer = onComputer;
		this.departmentSign = departmentSign;
		flags = new ArrayList<>();
		for(int i = 0; i < numOfDays*EXAMS_PER_DAY; i++) {
			flags.add(true);
		}
		
	}

	public boolean checkIfAvailable(int term) {
		return flags.get(term) == true;
	}
	
	public void removeTerm(int term) {
		flags.set(term, false);
	}
	
	public void removeDay(int day) {		
		for(int i = (day-1)*EXAMS_PER_DAY; i < day*EXAMS_PER_DAY; i++) {
			 removeTerm(i);
		}
	}

	public int getAccreditation() {
		return accreditation;
	}

	public void setAccreditation(int accreditation) {
		this.accreditation = accreditation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getDesk() {
		return desk;
	}

	public void setDesk(int desk) {
		this.desk = desk;
	}

	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getNumOfRegistered() {
		return numOfRegistered;
	}

	public void setNumOfRegistered(int numOfRegistered) {
		this.numOfRegistered = numOfRegistered;
	}

	public boolean isOnComputer() {
		return onComputer;
	}

	public void setOnComputer(boolean onComputer) {
		this.onComputer = onComputer;
	}

	public String[] getDepartmentSign() {
		return departmentSign;
	}

	
	
	
	
}
