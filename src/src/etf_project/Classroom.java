package etf_project;

/**
 * 
 * Klasa namenjena za cuvanje informacija o ucionicama i njihovoj trenutnoj
 * zauzetosti po terminima
 * 
 *
 */
public class Classroom implements Cloneable {

	private final int TERMS_PER_DAY = 4;
	private String classroomName;
	private int capacity;
	private boolean hasComputers;
	private int staff;
	private boolean belongsToETF;
	private boolean[] booked;

	public Classroom(int numOfDays, String classroomName, int capacity, boolean hasComputers, int staff,
			boolean belongsToETF) {
		super();
		this.classroomName = classroomName;
		this.capacity = capacity;
		this.hasComputers = hasComputers;
		this.staff = staff;
		this.belongsToETF = belongsToETF;
		booked = new boolean[numOfDays * TERMS_PER_DAY];
		for (int i = 0; i < numOfDays * TERMS_PER_DAY; i++) {
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

	public boolean checkIfAvailable(int term) {
		return booked[term];
	}

	public String getClassroomName() {
		return classroomName;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isHasComputers() {
		return hasComputers;
	}

	public int getStaff() {
		return staff;
	}

	public boolean isBelongsToETF() {
		return belongsToETF;
	}

}
