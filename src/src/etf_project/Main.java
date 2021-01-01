package etf_project;

import java.io.IOException;
import java.util.List;

/**
 * 
 * Klasa Main predstavlja glavnu klasu programa iz nje se pozivaju sve metode
 * neophodne za obradu ulaznih fajlova
 */
public class Main {

	/**
	 * 
	 * Metoda main prima 1 parametar to su argumenti komandne linije, pri pokretanju
	 * programa potrebno je navesti 4 argumenta 1. Naziv ulaznog fajla u JSON
	 * formatu koji sadrzi informacije o trajanju ispitnog roka i ispitima 2. Naziv
	 * ulaznog fajla u JSON formatu koji sadrzi o salama koje su na raspolaganju za
	 * taj ispitni rok 3. Naziv izlaznog fajla sa ekstenzijom .csv u koji ce biti
	 * ispisan generisani raspored ispita 4. Naziv izlaznog .txt fajla u koji se
	 * smestaju koraci algoritma
	 * 
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 4) {
				System.out.println("You need to specify 4 command line arguments but there are only " + args.length);
				return;
			}
			JSONFileReader myReader = JSONFileReader.getInstance();
			int termDuration = myReader.readNumOfDays(args[0]);
			List<Classroom> classroomList = myReader.readClassRooms(args[1], termDuration);
			List<Exam> examList = myReader.readExams(args[0], termDuration);
			Scheduler myScheduler = new Scheduler(examList, classroomList, termDuration);
			List<ScheduledExam> finalSchedule = myScheduler.doScheduling(args[3]);
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
