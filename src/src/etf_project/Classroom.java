package etf_project;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private final int TERMS_PER_DAY = 4;
	private String classroomName;
	private int capacity;
	private boolean hasComputers;
	private int staff;
	private boolean belongsToETF;
	private List<Integer> capacityPerTerm;
	
	public Classroom(int numOfDays,String classroomName, int capacity, boolean hasComputers, int staff, boolean belongsToETF) {
		super();
		this.classroomName = classroomName;
		this.capacity = capacity;
		this.hasComputers = hasComputers;
		this.staff = staff;
		this.belongsToETF = belongsToETF;
		capacityPerTerm = new ArrayList<>();
		for(int i = 0; i < numOfDays*TERMS_PER_DAY; i++) {
			capacityPerTerm.add(capacity);
		}
	}
	
	public int bookClassroom(int term, int requiredCapacity) {
		int capacityAvailable = capacityPerTerm.get(term-1);
		if(capacityAvailable >= requiredCapacity)
		{
			capacityPerTerm.set(term-1, capacityAvailable-requiredCapacity);
			return requiredCapacity;
		}
		capacityPerTerm.set(term-1, 0);
		return capacityAvailable;
	}


	public String getClassroomName() {
		return classroomName;
	}


	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public boolean isHasComputers() {
		return hasComputers;
	}


	public void setHasComputers(boolean hasComputers) {
		this.hasComputers = hasComputers;
	}


	public int getStaff() {
		return staff;
	}


	public void setStaff(int staff) {
		this.staff = staff;
	}


	public boolean isBelongsToETF() {
		return belongsToETF;
	}


	public void setBelongsToETF(boolean belongsToETF) {
		this.belongsToETF = belongsToETF;
	}
	
	
	
}
