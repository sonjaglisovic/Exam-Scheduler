package etf_project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Glavna klasa namenjena za rasporedjivanje ispita po terminima i salama
 *
 */
public class Scheduler {

	private List<Exam> examsToSchedule;
	private List<Classroom> classroomsAvailable;
	private List<ScheduledExam> bestSchedule = new ArrayList<>();
	private int numOfDays;
	private final int TERMS_PER_DAY = 4;
	private final double SCALING_FACTOR = 1.2;
	private double bestResult = Double.MAX_VALUE;
	private FileWriter myWriter;
	private boolean oneFound = false;

	public Scheduler(List<Exam> examsToSchedule, List<Classroom> classroomsAvailable, int numOfDays) {
		super();
		this.examsToSchedule = examsToSchedule;
		this.classroomsAvailable = classroomsAvailable;
		this.numOfDays = numOfDays;
	}

	/**
	 * Funkcija najpre inicijalizuje okruzenje i dovodi ga u stanje pogodno za
	 * izvrsavanje algoritma i onda poziva algoritam
	 * 
	 * @throws IOException
	 * 
	 */
	public List<ScheduledExam> doScheduling(String filePath) throws CloneNotSupportedException, IOException {

		File file = new File(filePath);
		myWriter = new FileWriter(file);
		Comparator<Exam> compExam = new Comparator<Exam>() {

			@Override
			public int compare(Exam o1, Exam o2) {
				// TODO Auto-generated method stub

				return o1.getNumOfRegistered() > o2.getNumOfRegistered() ? -1 : 1;
			}

		};
		Comparator<Classroom> comp = new Comparator<Classroom>() {

			@Override
			public int compare(Classroom o1, Classroom o2) {
				// TODO Auto-generated method stub
				return o1.getCapacity() > o2.getCapacity() ? -1 : 1;
			}

		};
		List<ScheduledExam> currentSchedule = new ArrayList<>();
		classroomsAvailable.sort(comp);
		examsToSchedule.sort(compExam);
		doBacktracking(0, 0, examsToSchedule, classroomsAvailable, 0, currentSchedule);
		myWriter.close();
		return bestSchedule;
	}

	/**
	 * Funkcija radi forward checking tj. izbacuje iz opticaja sve termine za
	 * preostale ispite koji ne dolaze u obzir nakon tekuceg rasporedjivanja
	 * 
	 * 
	 * @param exams      Lista ispita koji su ostali za rasporedjivanje
	 * @param classrooms Lista ucionica koje su u opticaju
	 * @param year       Godina na kojoj se odrzava tekuci ispit
	 * @param term       Termin koji se razmatra za tekuci ispit
	 */
	private void doForwardChecking(List<Exam> exams, List<Classroom> classrooms, int year, int term) {

		for (int i = 1; i < exams.size(); i++) {

			for (int j = 0; j < exams.get(0).getNumOfDepartments(); j++)
				exams.get(i).removeTermIfImpossible(exams.get(0).getIthDepartment(j), year, term);

			int termCapacity = 0;
			if (exams.get(i).checkIfAvailable(term)) {
				boolean termToRemove = true;
				for (int j = 0; j < classrooms.size(); j++) {
					if (classrooms.get(j).checkIfAvailable(term)
							&& exams.get(i).isOnComputer() == classrooms.get(j).isHasComputers()) {
						termCapacity += classrooms.get(j).getCapacity();

						if (termCapacity >= exams.get(i).getNumOfRegistered()) {
							termToRemove = false;
							break;
						}
					}
				}
				if (termToRemove)
					exams.get(i).removeTerm(term);
			}
		}
	}

	private void doBacktracking(int numOfNoETFClassrooms, int numOfStaffRequired, List<Exam> examsToSch,
			List<Classroom> classroomAvailable, int numOfExamScheduled, List<ScheduledExam> scheduled)
			throws CloneNotSupportedException, IOException {
		if ((numOfStaffRequired + numOfNoETFClassrooms * SCALING_FACTOR) >= bestResult)
			return;
		if (examsToSch.size() == 0) {
			if ((numOfStaffRequired + numOfNoETFClassrooms * SCALING_FACTOR) < bestResult) {
				bestResult = numOfStaffRequired + numOfNoETFClassrooms * SCALING_FACTOR;
				bestSchedule = new ArrayList<>();
				for (int i = 0; i < scheduled.size(); i++) {
					bestSchedule.add(scheduled.get(i).clone());
				}
			}
			oneFound = true;
			return;
		}

		List<Exam> exams = new ArrayList<>();
		for (int i = 0; i < examsToSch.size(); i++) {
			exams.add(examsToSch.get(i).clone());
		}

		List<Classroom> classrooms = new ArrayList<>();
		for (int i = 0; i < classroomAvailable.size(); i++) {
			classrooms.add(classroomAvailable.get(i).clone());
		}

		List<ScheduledExam> alreadyScheduled = new ArrayList<>();
		for (int i = 0; i < scheduled.size(); i++) {
			alreadyScheduled.add(scheduled.get(i).clone());
		}

		boolean singleTerm = false;
		int numOfIteration = numOfExamScheduled >= numOfDays * TERMS_PER_DAY ? numOfDays * TERMS_PER_DAY
				: numOfExamScheduled + 1;

		for (int i = 0; i < numOfIteration || (!singleTerm && i < numOfDays * TERMS_PER_DAY); i++) {
			if (examsToSch.get(0).checkIfAvailable(i)) {
				ScheduledExam newExam;
				singleTerm = true;
				Exam exam = examsToSch.get(0);
				List<Classroom> classroomsForExam = new ArrayList<>();
				int numOfStaff = 0;
				int numOfNoETF = 0;
				int capacityBooked = 0;

				for (int j = 0; j < classroomAvailable.size(); j++) {
					if (capacityBooked >= examsToSch.get(0).getNumOfRegistered())
						break;
					if (classroomAvailable.get(j).checkIfAvailable(i)
							&& classroomAvailable.get(j).isHasComputers() == examsToSch.get(0).isOnComputer()) {
						classroomAvailable.get(j).bookClassroom(i);
						numOfStaff += classroomAvailable.get(j).getStaff();
						numOfNoETF = classroomAvailable.get(j).isBelongsToETF() ? numOfNoETF : numOfNoETF + 1;
						capacityBooked += classroomAvailable.get(j).getCapacity();
						classroomsForExam.add(classroomAvailable.get(j));
					}
				}
				newExam = new ScheduledExam(exam, classroomsForExam, i);
				scheduled.add(newExam);
				doForwardChecking(examsToSch, classroomAvailable, examsToSch.get(0).getSchoolYear(), i);
				String toWrite = new String(examsToSch.get(0).getExamCode().getBytes(), StandardCharsets.UTF_8);

				myWriter.write("Ispit ");
				myWriter.write(toWrite);
				myWriter.write(" pokusava da se rasporedi u termin " + i + "\n");
				examsToSch.remove(0);
				doBacktracking(numOfNoETFClassrooms + numOfNoETF, numOfStaffRequired + numOfStaff, examsToSch,
						classroomAvailable, numOfExamScheduled + 1, scheduled);

				examsToSch = new ArrayList<>();
				for (int j = 0; j < exams.size(); j++) {
					examsToSch.add(exams.get(j).clone());
				}

				classroomAvailable = new ArrayList<>();
				for (int j = 0; j < classrooms.size(); j++) {
					classroomAvailable.add(classrooms.get(j).clone());
				}

				scheduled = new ArrayList<>();
				for (int j = 0; j < alreadyScheduled.size(); j++) {
					scheduled.add(alreadyScheduled.get(j).clone());
				}
			}
		}

	}
}
