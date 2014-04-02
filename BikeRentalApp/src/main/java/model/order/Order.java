package model.order;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.client.Client;
import model.date.DatePair;
import model.id.Idenifiable;
import model.thing.Thing;


public class Order implements Comparable<Order>, Idenifiable, Serializable {

	private final long id;
	private Status status=Status.CREATING_ORDER;
	private Client client;
	private List<Thing> thingList= new ArrayList<Thing>();

	private Date madedOrderDate;
	private DatePair datePair;
	float price;
	private static final long serialVersionUID = 2348982526506611759L;
	
	public enum Status{
		CREATING_ORDER,
		CONFIRMED,
		CANCELED,
		IN_REALISATION,
		ENDED
	}
	
	public Order() {
		this(0L);
	};
	
	public Order(long id) {
		this.id=id;
	}
	
	@Override
	public int compareTo(Order o) {
		return this.madedOrderDate.compareTo(o.madedOrderDate);
	}
	
	
	public float calculatePrice() {
		switch(status) {
		case IN_REALISATION:
		case ENDED:
		case CONFIRMED:
			float sum2=0f;
			for(Thing t: thingList) {
				sum2+=t.getDailyRentalPrice();
			}
			Date startAtDate=datePair.getStartAtDate();
			Date endedAtDate=datePair.getEndAtDate();
		
			@SuppressWarnings("deprecation")
			long min2=endedAtDate.getTime()-startAtDate.getTime();
			price= (float)min2/1000/60/60/24*sum2;
			break;
		default:
			price=0f;
		}
		return price;
		
	}
	
	public void addThing(Thing thing) {
		thingList.add(thing);
	}
	
	public void removeThing(int thingId) {
		Iterator<Thing> it=thingList.iterator();
		while(it.hasNext()) 
			if(it.next().getId()==thingId) {
				it.remove();
				return;
			}
	}
	
	public void confirmOrder() {
		madedOrderDate= new Date(System.currentTimeMillis());
		status=Status.CONFIRMED;
	}
	

	public DatePair getDatePair() {
		return datePair;
	}

	public void setDatePair(DatePair datePair) {
		this.datePair = datePair;
	}

	public void setClient(Client client) {
		this.client=client;
	}
	
	public Client getClient() {
		return client;
	}
	
	@Override
	public long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public List<Thing> getThingList() {
		return thingList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		StringBuilder builder= new StringBuilder();
		builder.append(String.format("Order id: %d\tprice: %.2f\n",id , calculatePrice()));
		builder.append(client.toString() +"\n");
		builder.append("Status :" + status +"\n");
		if(madedOrderDate!=null)
			builder.append("Creating date: "+ madedOrderDate.toGMTString()+"\n");
		builder.append("Reservation time: " + datePair.toString() +"\n");
		
		builder.append(String.format("%-10s%-20s%9s%6s%11s%7s%5s%10s%n", "Id" , "Type", "s.dis[km]"
				,"cal", "a.dis[km]", "func.", "n.de", "model"));
		for(Thing t: thingList) {
			builder.append(t.toString()+"\n");
		}
		
		return builder.toString();
		
	
	}

	
	
}
