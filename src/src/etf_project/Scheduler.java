package etf_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

	private List<Exam> examsToSchedule;
	private List<Classroom> classroomsAvailable;
	private List<ScheduledExam> bestSchedule = new ArrayList<>();
	private int numOfDays;
	private final int TERMS_PER_DAY = 4;
	private final double SCALING_FACTOR = 1.2;
	private double bestResult = Double.MAX_VALUE;

	public Scheduler(List<Exam> examsToSchedule, List<Classroom> classroomsAvailable, int numOfDays) {
		super();
		this.examsToSchedule = examsToSchedule;
		this.classroomsAvailable = classroomsAvailable;
		this.numOfDays = numOfDays;
	}

	public List<ScheduledExam> doScheduling() throws CloneNotSupportedException {

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
				return (o1.isBelongsToETF() && !o2.isBelongsToETF()
						|| (o1.isBelongsToETF() == o1.isBelongsToETF() && o1.getStaff() < o2.getStaff())
						|| (o1.isBelongsToETF() == o2.isBelongsToETF() && o1.getStaff() == o2.getStaff()
								&& o1.getCapacity() > o2.getCapacity())) ? -1 : 1;
			}

		};
		List<ScheduledExam> currentSchedule = new ArrayList<>();
		classroomsAvailable.sort(comp);
		examsToSchedule.sort(compExam);
		doBacktracking(0, 0, examsToSchedule, classroomsAvailable, 0, currentSchedule);
		return bestSchedule;
	}

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

	private List<Classroom> findBestClassroomCombination(int term, List<Classroom> classrooms, Exam currentExam)
			throws CloneNotSupportedException {
		List<Classroom> localClassroomsCopy = new ArrayList<>();
		for (int i = 0; i < classrooms.size(); i++) {
			if (classrooms.get(i).checkIfAvailable(term)
					&& currentExam.isOnComputer() == classrooms.get(i).isHasComputers())
				localClassroomsCopy.add(classrooms.get(i).clone());
		}
		Comparator<Classroom> comp = new Comparator<Classroom>() {

			@Override
			public int compare(Classroom o1, Classroom o2) {
				// TODO Auto-generated method stub
				return o1.getCapacity() > o2.getCapacity() ? 1 : -1;
			}
		};
		localClassroomsCopy.sort(comp);
		int capacityAvail = 0;
		for (int i = 0; i < localClassroomsCopy.size(); i++) {
			capacityAvail += localClassroomsCopy.get(i).getCapacity();
		}
		int maxConsecutive = 0;
		int currentSum = 0;
		List<Classroom> classroomsToRemove = new ArrayList<>();
		List<Classroom> bestToRemove = new ArrayList<>();
		for (int i = 0; i < localClassroomsCopy.size(); i++) {
			if ((currentSum + localClassroomsCopy.get(i).getCapacity()) <= (capacityAvail
					- currentExam.getNumOfRegistered())) {
				currentSum += localClassroomsCopy.get(i).getCapacity();
				classroomsToRemove.add(localClassroomsCopy.get(i));
			} else {
				currentSum = localClassroomsCopy.get(i).getCapacity();
				if (currentSum > maxConsecutive) {
					maxConsecutive = currentSum;

					bestToRemove = new ArrayList<>();
					for (int j = 0; j < classroomsToRemove.size(); j++) {
						bestToRemove.add(classroomsToRemove.get(j).clone());
					}
				}

				classroomsToRemove = new ArrayList<>();
				if (localClassroomsCopy.get(i).getCapacity() >= (capacityAvail - currentExam.getNumOfRegistered())) {
					currentSum = 0;
				} else {
					currentSum = localClassroomsCopy.get(i).getCapacity();
					classroomsToRemove.add(localClassroomsCopy.get(i));
				}
			}
		}
		if (currentSum > maxConsecutive) {
			maxConsecutive = currentSum;
			bestToRemove = new ArrayList<>();
			for (int j = 0; j < classroomsToRemove.size(); j++) {
				bestToRemove.add(classroomsToRemove.get(j).clone());
			}
		}
		return bestToRemove;
	}

	private boolean checkIfClassroomInList(List<Classroom> classrooms, Classroom CrToCompare) {
		for (int i = 0; i < classrooms.size(); i++) {
			if (CrToCompare.getClassroomName().compareTo(classrooms.get(i).getClassroomName()) == 0)
				return true;
		}
		return false;
	}

	private void doBacktracking(int numOfNoETFClassrooms, int numOfStaffRequired, List<Exam> examsToSch,
			List<Classroom> classroomAvailable, int numOfExamScheduled, List<ScheduledExam> scheduled)
			throws CloneNotSupportedException {
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

				List<Classroom> removeList = findBestClassroomCombination(i, classroomAvailable, examsToSch.get(0));

				for (int j = 0; j < classroomAvailable.size(); j++) {
					if (capacityBooked >= examsToSch.get(0).getNumOfRegistered())
						break;
					if (!checkIfClassroomInList(removeList, classroomAvailable.get(j))
							&& classroomAvailable.get(j).checkIfAvailable(i)
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
