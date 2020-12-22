package etf_project;

import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			JSONFileReader myReader = JSONFileReader.getInstance();
			int termDuration = myReader.readNumOfDays(args[0]);
			List<Classroom> classroomList = myReader.readClassRooms(args[1], termDuration);
			List<Exam> examList = myReader.readExams(args[0], termDuration);
			Scheduler myScheduler = new Scheduler(examList, classroomList, termDuration);
			List<ScheduledExam> finalSchedule = myScheduler.doScheduling();
			MyCSVWriter myWriter = new MyCSVWriter(finalSchedule, termDuration, classroomList);
			myWriter.writeToFile(args[2]);
		} catch (CloneNotSupportedException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
