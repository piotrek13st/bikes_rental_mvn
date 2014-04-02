package model.thing;

public class MTBike extends MountainBike {

	private static final long serialVersionUID = 4293013585396737585L;

	public MTBike(long id) {
		super(id);
	}
	
	public MTBike() {
		super(0L);
	}

	private String mtbModel;
	
	
	//getters & setters
	public String getMtbModel() {
		return mtbModel;
	}
	public void setMtbModel(String mtbModel) {
		this.mtbModel = mtbModel;
	}
	
	
	@Override
	public String getName() {
		return "MTBike";
	}
	
	@Override
	public String toString() {
		return super.toString()+String.format("%10s", mtbModel);
	}
}
