package etf_project;

<<<<<<< refs/remotes/origin/main


public class Classroom implements Cloneable{
	
    private final int TERMS_PER_DAY = 4;
=======
/**
 * 
 * Klasa namenjena za cuvanje informacija o ucionicama i njihovoj trenutnoj
 * zauzetosti po terminima
 * 
 *
 */
public class Classroom implements Cloneable {

	private final int TERMS_PER_DAY = 4;
>>>>>>> Finished
	private String classroomName;
	private int capacity;
	private boolean hasComputers;
	private int staff;
	private boolean belongsToETF;
	private boolean[] booked;
<<<<<<< refs/remotes/origin/main
	
	
	public Classroom(int numOfDays,String classroomName, int capacity, boolean hasComputers, int staff, boolean belongsToETF) {
=======

	public Classroom(int numOfDays, String classroomName, int capacity, boolean hasComputers, int staff,
			boolean belongsToETF) {
>>>>>>> Finished
		super();
		this.classroomName = classroomName;
		this.capacity = capacity;
		this.hasComputers = hasComputers;
		this.staff = staff;
		this.belongsToETF = belongsToETF;
<<<<<<< refs/remotes/origin/main
		booked = new boolean[numOfDays*TERMS_PER_DAY];
		for(int i = 0; i < numOfDays*TERMS_PER_DAY; i++) {
			booked[i] = true;
		}
	}
	
	public Classroom clone() throws CloneNotSupportedException {
		Classroom newCR = (Classroom) super.clone();
		newCR.booked = (boolean[]) booked.clone();
		return newCR;
	}
	
	public void bookClassroom(int term) {
		booked[term] = false;
	}

	public boolean checkIfAvailable(int term)
	{
		return booked[term];
	}
	public String getClassroomName() {
		return classroomName;
=======
		booked = new boolean[numOfDays * TERMS_PER_DAY];
		for (int i = 0; i < numOfDays * TERMS_PER_DAY; i++) {
			booked[i] = true;
		}
	}

	public Classroom clone() throws CloneNotSupportedException {
		Classroom newCR = (Classroom) super.clone();
		newCR.booked = (boolean[]) booked.clone();
		return newCR;
>>>>>>> Finished
	}

	public void bookClassroom(int term) {
		booked[term] = false;
	}

<<<<<<< refs/remotes/origin/main
=======
	public boolean checkIfAvailable(int term) {
		return booked[term];
	}

	public String getClassroomName() {
		return classroomName;
	}

>>>>>>> Finished
	public int getCapacity() {
		return capacity;
	}

<<<<<<< refs/remotes/origin/main

=======
>>>>>>> Finished
	public boolean isHasComputers() {
		return hasComputers;
	}

<<<<<<< refs/remotes/origin/main

=======
>>>>>>> Finished
	public int getStaff() {
		return staff;
	}

<<<<<<< refs/remotes/origin/main

=======
>>>>>>> Finished
	public boolean isBelongsToETF() {
		return belongsToETF;
	}

<<<<<<< refs/remotes/origin/main

=======
>>>>>>> Finished
}
