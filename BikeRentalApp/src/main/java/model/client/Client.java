package model.client;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.check.Checkable;
import model.id.Idenifiable;


abstract public class Client implements Comparable<Client>, Checkable<Client>, Idenifiable, Serializable{
	
	private final long id;
	private String phoneNumber;
	private String email;
	
	private static final long serialVersionUID = 61528275354802521L;
	
	protected static Logger logger= LogManager.getLogger(Client.class.getSimpleName());
	
	public Client(long id) {
		this.id=id;
	}
	
	public Client(long id, String phoneNumber) {
		this(id);
		this.phoneNumber=phoneNumber;
	}
	
	public Client() {
		this(0L);
	}
	
	public Client(long id, String phoneNumber, String email) {
		this(id, phoneNumber);
		this.email=email;
	}
	
	@Override
	public int compareTo(Client o) {
		return this.getClientName().compareToIgnoreCase(o.getClientName());
	}
	
	
	/**check if clients have the same email address. If any has email equals null  
	 * then clients are compared by name
	 * 
	 * @param obj - client to compare with
	 * @return true if equals 
	 */
	@Override
	public boolean check(Client obj) {
		if(email!=null && obj.email!=null) {
			return email.equalsIgnoreCase(obj.email);
		}
		return getClientName().equalsIgnoreCase(obj.getClientName());
	}
	
	abstract public String getClientName();
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber=phoneNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		if((this.getClientName().equalsIgnoreCase(((Client)obj).getClientName()) && 
				this.phoneNumber.equals(((Client)obj).getPhoneNumber())))
			return true;
		else
			return false;
		
	}
	
	@Override
	public String toString() {
		return String.format("%-10s%-22s%-22s%18s", new Long(id).toString()+":", getClientName(), getEmail(), getPhoneNumber());
	}
	
}
