package etf_project;

/**
 * 
 * Klasa Exam sluzi za vodjenje evidencije o rasporedjenim i nerasporedjenim
 * ispitima, cuva se informacija da li svaki od termina dolazi u obzir za taj
 * ispit, cuvaju se informacije o ispitu dobijene citanjem iz ulaznog fajla
 *
 */

public class Exam implements Cloneable {

	private final int EXAMS_PER_DAY = 4;
	private boolean[] flags;
	private int accreditation;
	private String department;
	private int desk;
	private int schoolYear;
	private String subject;
	private int numOfRegistered;
	private boolean onComputer;
	private String[] departmentSign;
	private String examCode;

	public Exam(int numOfDays, int accreditation, String department, int desk, int schoolYear, String subject,
			int numOfRegistered, boolean onComputer, String[] departmentSign, String examCode) {

		super();
		this.accreditation = accreditation;
		this.department = department;
		this.desk = desk;
		this.schoolYear = schoolYear;
		this.subject = subject;
		this.numOfRegistered = numOfRegistered;
		this.onComputer = onComputer;
		this.departmentSign = departmentSign;
		this.examCode = examCode;
		flags = new boolean[numOfDays * EXAMS_PER_DAY];
		for (int i = 0; i < numOfDays * EXAMS_PER_DAY; i++) {
			flags[i] = true;
		}

	}

	public int getNumOfDepartments() {
		return departmentSign.length;
	}

	public String getIthDepartment(int i) {
		return departmentSign[i];
	}

	/**
	 * Metoda za pravljenje duboke kopije objekata klase Exam
	 */
	public Exam clone() throws CloneNotSupportedException {
		Exam newExam = (Exam) super.clone();
		newExam.flags = (boolean[]) flags.clone();
		return newExam;
	}

	public boolean checkIfAvailable(int term) {
		return flags[term];
	}

	public void removeTerm(int term) {
		flags[term] = false;
	}

	/**
	 * Metoda sluzi da izbaci iz opticaja termine koje ne dolaze u obzir zbog nekoga
	 * ogranicenja Na primer vec postoji ispit u istoj ili u susdnoj godini sa istog
	 * smera tog dana
	 * 
	 * @param department odsek za koji se razmatraju ogranizenja
	 * @param year       godina na kojoj se razmatra ogranicenje
	 * @param term       termin za koji se razmatra ogranicenje
	 */

	public void removeTermIfImpossible(String department, int year, int term) {
		boolean foundMatch = false;
		for (int i = 0; i < departmentSign.length; i++) {
			if (departmentSign[i].compareTo(department) == 0) {
				foundMatch = true;
				break;
			}
		}
		if (foundMatch && year == schoolYear) {
			for (int i = term - term % EXAMS_PER_DAY; i < term + (EXAMS_PER_DAY - term % EXAMS_PER_DAY); i++) {
				removeTerm(i);
			}
		} else if (foundMatch && (year == (schoolYear - 1) || year == (schoolYear + 1)))
			removeTerm(term);
		else if ((year == 1 && department.compareTo("СИ") != 0 && schoolYear == 2
				&& this.department.compareTo("СИ") != 0)
				|| (year == 2 && department.compareTo("СИ") != 0 && schoolYear == 1
						&& this.department.compareTo("СИ") != 0))
			removeTerm(term);
	}

	public String getExamCode() {
		return examCode;
	}

	public int getAccreditation() {
		return accreditation;
	}

	public String getDepartment() {
		return department;
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

	public String getSubject() {
		return subject;
	}

	public int getNumOfRegistered() {
		return numOfRegistered;
	}

	public boolean isOnComputer() {
		return onComputer;
	}

	public String[] getDepartmentSign() {
		return departmentSign;
	}

}
