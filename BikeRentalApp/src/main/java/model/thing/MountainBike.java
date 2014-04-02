package model.thing;

public class MountainBike extends Bike {
	
	private static final long serialVersionUID = -8377857784585777599L;
	private int derailleurAmount;
	
	public MountainBike(long id) {
		super(id);
	}
	
	public MountainBike() {
		super(0L);
	}
	
	//getters & setters
	public int getDerailleurAmount() {
		return derailleurAmount;
	}
	public void setDerailleurAmount(int derailleurAmount) {
		this.derailleurAmount = derailleurAmount;
	}
	
	
	@Override
	public String getName() {
		return "Mountain bike";
	}
	
	@Override
	public String toString() {
		return super.toString()+String.format("%5d",derailleurAmount);
	}
}
