package model.client;

public class Person extends Client {
	
	private String name;
	private String surname;
	
	private static final long serialVersionUID = -7119062341912785804L;
	
	public Person(long id) {
		super(id);
		logger.info("Creating Person: " + " id=" +id);
		}
	public Person(long id, String phoneNumber, String name, String surname) {
		super(id, phoneNumber);
		this.name=name;
		this.surname=surname;
		logger.info("Creating Institution: " + " id=" +id);
	}
	public Person() {
		this(0L);
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setSurname(String surname) {
		this.surname=surname;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	@Override
	public String getClientName() {
		return name + " " + surname;
	}

}
