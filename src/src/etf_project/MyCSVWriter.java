package etf_project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MyCSVWriter {

	List<ScheduledExam> schedule;
	private int numOfDays;
	List<Classroom> classroomList;
	private final int TERMS_PER_DAY = 4;
	HashMap<Integer, String> termToTime = new HashMap<>();

	public MyCSVWriter(List<ScheduledExam> schedule, int numOfDays, List<Classroom> classroomList) {
		super();
		this.classroomList = classroomList;
		this.numOfDays = numOfDays;
		this.schedule = schedule;

		termToTime.put(0, "8:00");
		termToTime.put(1, "11:30");
		termToTime.put(2, "15:00");
		termToTime.put(3, "18:30");

	}

	public String findClassroomIfExists(int term, String classroomName) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getTerm() == term) {
				List<Classroom> classrooms = schedule.get(i).getClassrooms();
				for (int j = 0; j < classrooms.size(); j++) {
					if (classrooms.get(j).getClassroomName().compareTo(classroomName) == 0)
						return schedule.get(i).getExam();
				}
			}
		}

		return null;
	}

	public void writeToFile(String filePath) throws IOException {

		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter dataOut = new OutputStreamWriter(fos);

		Comparator<ScheduledExam> comp = new Comparator<ScheduledExam>() {

			@Override
			public int compare(ScheduledExam o1, ScheduledExam o2) {

				return o1.getTerm() < o2.getTerm() ? 1 : 0;
			}
		};

		schedule.sort(comp);
		for (int i = 0; i < numOfDays; i++) {
			dataOut.write("Dan " + (i + 1));
			for (int j = 0; j < classroomList.size(); j++) {
				dataOut.write("," + classroomList.get(j).getClassroomName());
			}
			dataOut.write("\n");
			for (int j = 0; j < TERMS_PER_DAY; j++) {
				dataOut.write(termToTime.get(j));
				for (int k = 0; k < classroomList.size(); k++) {
					String toApped = findClassroomIfExists(i * TERMS_PER_DAY + j,
							classroomList.get(k).getClassroomName());

					if (toApped == null)
						dataOut.write("," + "x");
					else {
						toApped = new String(toApped.getBytes(), StandardCharsets.UTF_8);
						dataOut.write("," + toApped);
					}
				}
				dataOut.write("\n");
			}
			dataOut.write("\n");

		}
		dataOut.flush();
		dataOut.close();
		fos.close();

	}

}
