package etf_project;

import java.util.ArrayList;
import java.util.List;

public class ScheduledExam implements Cloneable {

	private Exam exam;
	int examTerm;
	private List<Classroom> listOfClassrooms;

	public ScheduledExam(Exam exam, List<Classroom> listOfClassrooms, int examTerm) {
		super();
		this.exam = exam;
		this.examTerm = examTerm;
		this.listOfClassrooms = listOfClassrooms;
	}

	public ScheduledExam clone() throws CloneNotSupportedException {
		ScheduledExam myClone = (ScheduledExam) super.clone();
		myClone.exam = exam.clone();
		myClone.listOfClassrooms = new ArrayList<>();
		for (int i = 0; i < listOfClassrooms.size(); i++) {
			myClone.listOfClassrooms.add(listOfClassrooms.get(i).clone());
		}
		return myClone;
	}

	public String getExam() {
		return exam.getExamCode();
	}

	public int getTerm() {
		return examTerm;
	}

	public List<Classroom> getClassrooms() {
		return listOfClassrooms;
	}
}
