package model.client;

public class Institution extends Client {
	

	String institutionName;
	
	private static final long serialVersionUID = 2275781237589010205L;
	
	public Institution(long id) {
		super(id);
	}
	public Institution(long id, String phoneNumber, String institutionName) {
		super(id, phoneNumber);
		this.institutionName=institutionName;
	}
	public Institution() {
		super(0L);
	}
	
	public Institution(long id, String phoneNumber, String institutionName, String email) {
		super(id, phoneNumber, email);
		this.institutionName=institutionName;
	}
	
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	@Override
	public String getClientName() {
		return institutionName;
	}

}
