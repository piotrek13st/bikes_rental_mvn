package model.thing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.check.Checkable;
import model.date.DatePair;
import model.date.EndBeforStartException;
import model.id.Idenifiable;

public abstract class Thing implements Idenifiable, Serializable {

	private static final long serialVersionUID = 1166863320651410201L;
	private final long id;
	private float dailyRentalPrice;		//pln/day
	private boolean isFunctional=true;	
	
	List<DatePair> datesOfReservations = new ArrayList<>();
	
	public Thing(long id) {
		this.id=id;
	}
	
	public Thing() {
		this(0);
	}
	
	abstract public String getName();
	
	
	@Override
	public long getId() {
		return id;
	}

	public float getDailyRentalPrice() {
		return dailyRentalPrice;
	}
	public void setDailyRentalPrice(float dailyRentalPrice) {
		this.dailyRentalPrice = dailyRentalPrice;
	}
	
	/**
	 * 
	 * @return false if thing needs a service
	 */
	public boolean isFunctional() {
		return isFunctional;
	}
	
	public void setFunctional(boolean isFunctional) {
		this.isFunctional = isFunctional;
	}
	
	/**
	 * 
	 * @param from Date that represents time that reservation starts
	 * @param to Date that represents time that reservation end
	 * @return	true if Reservation date is correctly added or false while dates are overlapping with other reservations
	 */
	public boolean addReservation(Date from, Date to) {
		boolean isAdded=isAvailable(from, to);
		if(isAdded==true)
			try {
				isAdded=datesOfReservations.add(new DatePair(from, to));
			} catch (EndBeforStartException e) {
				return false;
			}
		return isAdded;
	}
	
	public boolean addReservation(DatePair p) {
		return datesOfReservations.add(p);
	}
	
	public boolean releaseReservation(DatePair pair) {
		return datesOfReservations.remove(pair);
	}
	
	
	public boolean isAvailable(Date from, Date to) {
		try{
			for(DatePair p: datesOfReservations) {
				if(p.isOverlapping(from, to))
					return false;
			}
		} catch (EndBeforStartException e) {
			return false;
		}
		
		return true;
	}

	public List<DatePair> getDatesOfReservations() {
		return datesOfReservations;
	}


	
}
