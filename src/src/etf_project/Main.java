package etf_project;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Main {

	public static void main(String[] args){
		System.setProperty("file.encoding", "Cp1250");
		JSONFileReader myReader = JSONFileReader.getInstance();
		int termDuration = myReader.readNumOfDays(args[0]);
		List<Classroom> classroomList = myReader.readClassRooms(args[1], termDuration);
		List<Exam> examList = myReader.readExams(args[0], termDuration);
	}
	
}
