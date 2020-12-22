package etf_project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONFileReader {

	static JSONFileReader instance;

	private JSONFileReader() {
	}

	final int ACCREDITATION_SIGN_START = 0;
	final int ACCREDITATION_SIGN_END = 2;
	final int DEPARTMENT_SIGN = 2;
	final int DESK_SIGN_START = 3;
	final int DESK_SIGN_END = 5;
	final int SCHOOL_YEAR_SIGN = 5;
	final int SUBJECT_CODE_START = 6;

	public List<Exam> readExams(String filePath, int numOfDays) {
		List<Exam> exams = new ArrayList<>();

		JSONTokener myTokener;
		try {
			myTokener = new JSONTokener(new FileReader(filePath));
			JSONObject termInfoJSON = new JSONObject(myTokener);
			JSONArray examList = termInfoJSON.getJSONArray("ispiti");
			for (int i = 0; i < examList.length(); i++) {
				JSONObject newExamJSON = examList.getJSONObject(i);
				String examCode = newExamJSON.getString("sifra");
				int accreditation = Integer
						.parseInt(examCode.substring(ACCREDITATION_SIGN_START, ACCREDITATION_SIGN_END));
				String department = examCode.substring(DEPARTMENT_SIGN, DEPARTMENT_SIGN + 1);
				int desk = Integer.parseInt(examCode.substring(DESK_SIGN_START, DESK_SIGN_END));
				int schoolYear = Integer.parseInt(examCode.substring(SCHOOL_YEAR_SIGN, SCHOOL_YEAR_SIGN + 1));
				String subject = examCode.substring(SUBJECT_CODE_START);
				boolean onComputer = newExamJSON.getInt("racunari") == 0 ? false : true;
				JSONArray departmentList = newExamJSON.getJSONArray("odseci");
				String[] departmentSign = new String[departmentList.length()];
				for (int j = 0; j < departmentList.length(); j++) {
					departmentSign[j] = departmentList.getString(j);
				}
				Exam newExam = new Exam(numOfDays, accreditation, department, desk, schoolYear, subject,
						newExamJSON.getInt("prijavljeni"), onComputer, departmentSign, examCode);
				exams.add(newExam);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exams;
	}

	public List<Classroom> readClassRooms(String filePath, int numOfDays) {
		List<Classroom> classrooms = new ArrayList<>();
		try {
			JSONTokener myTokener = new JSONTokener(new FileReader(filePath));
			JSONArray newObject = new JSONArray(myTokener);
			for (int i = 0; i < newObject.length(); i++) {
				boolean hasComputers = newObject.getJSONObject(i).getInt("racunari") == 0 ? false : true;
				boolean belongsToETF = newObject.getJSONObject(i).getInt("etf") == 0 ? false : true;
				Classroom newCR = new Classroom(numOfDays, newObject.getJSONObject(i).getString("naziv"),
						newObject.getJSONObject(i).getInt("kapacitet"), hasComputers,
						newObject.getJSONObject(i).getInt("dezurni"), belongsToETF);
				classrooms.add(newCR);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return classrooms;
	}

	public static JSONFileReader getInstance() {
		if (instance == null) {
			instance = new JSONFileReader();
		}
		return instance;
	}

	public int readNumOfDays(String filePath) {
		int numOfDays = 0;
		JSONTokener myTokener;
		try {
			myTokener = new JSONTokener(new FileReader(filePath));
			JSONObject termInfoJSON = new JSONObject(myTokener);
			numOfDays = termInfoJSON.getInt("trajanje_u_danima");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return numOfDays;
	}

}
