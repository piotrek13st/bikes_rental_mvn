package gui;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import logic.RecordManager;
import logic.RecordNotFoundException;
import model.client.Client;
import model.client.Institution;
import model.client.Person;
import model.date.DatePair;
import model.date.EndBeforStartException;
import model.id.IdModel;
import model.id.Idenifiable;
import model.order.Order;
import model.order.Order.Status;
import model.thing.Bike;
import model.thing.MTBike;
import model.thing.MountainBike;
import model.thing.Thing;
import model.thing.TraditionalBike;

public class GUI {
	final static int PRICE_TRADITIONAL=20;
	final static int PRICE_MOUNTAIN=30;
	final static int PRICE_MTB=50;
	public static IdModel idHandler= IdModel.getInstance();
	
	public static Logger logger = LogManager.getLogger(GUI.class.getName());
	
	public static void main(String[] args) throws RecordNotFoundException {
				
		RecordManager<Bike> bikes=new RecordManager<>();
		RecordManager<Client> clients=new RecordManager<>();
		RecordManager<Order> orders= new RecordManager<>();
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String option;
		
		logger.error("Entering application");
		logger.trace("Trace application");
		
		while(true) {
		System.out.println("Type 'a' for commands");
		option=in.next();
		if(!option.matches("[a-u]")) {
			System.out.println("Use [a-u]");
			continue;
		}
		switch(option.charAt(0)) {
		case 'a':
			showMain();
			break;
		case 'b':
			showRecords(orders);
			break;
		case 'c':
			System.out.format("%-10s%-20s%9s%6s%11s%7s%5s%10s%n", "Id" , "Type", "s.dis[km]"
					,"cal", "a.dis[km]", "func.", "n.de", "model");
			showRecords(bikes);
			break;
		case 'd':
			System.out.format("%-10s%-22s%-22s%18s%n", "Id", "Name", "Email","Phone");
			showRecords(clients);
			break;
		case 'e':
			addNewOrder(orders, clients, bikes);
			break;
			
		case 'f':
			addNewBike(bikes);
			break;
		case 'g':
			addNewClient(clients);
			break;
		case 'h':
			System.out.print("Id: ");
			int idB=in.nextInt();
			Order ord=null;
			if((ord=orders.getRecordById(idB))==null) {
				System.out.println("No such order in database");
				break;
			}
			
			System.out.print("a)CREATING_ORDER\n" +
							"b)CONFIRMED\n" +
							"c)CANCELED\n" +
							"d)IN_REALISATION\n" +
							"e)ENDED\n" + "Status: ");
			switch(in.next().charAt(0)) {
			case 'a':
				ord.setStatus(Status.CREATING_ORDER);
				break;
			case 'b':
				ord.setStatus(Status.CONFIRMED);
				break;
			case 'c':
				ord.setStatus(Status.CANCELED);
				releaseDate(bikes, ord);
				break;
			case 'd':
				ord.setStatus(Status.IN_REALISATION);
				break;
			case 'e':
				ord.setStatus(Status.ENDED);
				releaseDate(bikes, ord);
				break;
			default:
				System.out.println("[a-e] only possible");
			}
			break;
		case 'k':
			removeOrderById(orders, bikes);
			break;
		case 'i':
			removeRecordById(bikes);
			break;
		case 'j':
			removeRecordById(clients);
			break;

		case 'm':
			bikes.sortRecords();
			System.out.println("Bikes sorted by distance to service");
			break;
		case 'l':
			showRecordById(clients);
			break;
		case 't':
			showRecordById(bikes);
			break;
		case 'n':
			clients.sortRecords();
			System.out.println("Clients sorted alphabetically");
			break;
		case 'o':
			orders.sortRecords();
			
		case 'u':
			return;
		case 'p':
			try {
				bikes.saveRecordsToFile("bikes.d");
				clients.saveRecordsToFile("clients.d");
				orders.saveRecordsToFile("orders.d");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Records successfully saved");
			break;
		case 'r':
			try {
				bikes.loadRecordsFromFile("bikes.d");
				clients.loadRecordsFromFile("clients.d");
				orders.loadRecordsFromFile("orders.d");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Records loaded successfully");
			break;
		}	
			
	}
	}
	

	public static void showMain() {
		System.out.format("%-30s", "a) help");								//
		System.out.format("%-30s%n", "b) show all orders");					//
		System.out.format("%-30s", "c) show all bikes");					//
		System.out.format("%-30s%n", "d) show all clients");				//
		System.out.format("%-30s", "e) add order to database");				//?
		System.out.format("%-30s%n","f) add bike to database");				//
		System.out.format("%-30s","g) add client to database");				//
		System.out.format("%-30s%n","h) change order status");				//?
		System.out.format("%-30s","i) remove bike");						//
		System.out.format("%-30s%n","j) remove client");					//
		System.out.format("%-30s","k) remove order by id");					//
		System.out.format("%-30s%n","t) show client by id");				//
		System.out.format("%-30s","l) show order by id");					//
		System.out.format("%-30s%n","m) sort bike database");				//
		System.out.format("%-30s","n) sort client database");				//
		System.out.format("%-30s%n","o) sort order database");				//
		System.out.format("%-30s","p) save databases to file");				//
		System.out.format("%-30s%n","r) load databases from file");			//
		System.out.format("Choose an option: ");

		
	
	}

	public static void showRecords(RecordManager<? extends Idenifiable> b) {
		List<? extends Idenifiable> bikeList = b.getRecords();
		Iterator<? extends Idenifiable> it= bikeList.iterator();
		
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	
	public static boolean removeRecordById(RecordManager<? extends Idenifiable> r) throws RecordNotFoundException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.print("Id: ");
		int idBB=in.nextInt();
		Idenifiable record=r.getRecordById(idBB);
		if(record==null) {
			System.out.println("Record with id=" + idBB + " doesn't exist");
			return false;
		}
		System.out.print("Remove "+ record.toString()+ " [y/n]");
		String confirm=in.next();
		if(confirm.charAt(0)=='y')
			return r.removeRecord(idBB);
		return false;
	}
	
	public static boolean removeOrderById(RecordManager<Order> r, RecordManager<Bike> b) throws RecordNotFoundException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.print("Id: ");
		int idBB=in.nextInt();
		Order record=r.getRecordById(idBB);
		if(record==null) {
			System.out.println("Record with id=" + idBB + " doesn't exist");
			return false;
		}
		System.out.print("Remove "+ record.toString()+ " [y/n]");
		String confirm=in.next();
		if(confirm.charAt(0)=='y') {
			for(Thing t:record.getThingList()) {
				b.getRecordById(t.getId()).releaseReservation(record.getDatePair());			
			}
			return r.removeRecord(idBB);
		}
		return false;
	}
	
	public static void addNewBike(RecordManager<Bike> b) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Bike bike=null;
		
		System.out.println("a) Traditional bike");
		System.out.println("b) Mountain bike");
		System.out.println("c) MTBike ");
		System.out.print("Choose [a-c]: ");
		try {
		String bikeKind=in.nextLine();
		if(bikeKind.matches("[a-c]")) {
			switch(bikeKind.charAt(0)) {
			case 'a':
				bike=new TraditionalBike(idHandler.produceId());				
				bike.setDailyRentalPrice(PRICE_TRADITIONAL);

				break;
			case 'b':
				bike=new MountainBike(idHandler.produceId());
				
				bike.setDailyRentalPrice(PRICE_MOUNTAIN);
				System.out.print("Amount of derailleurs: ");
				((MountainBike)bike).setDerailleurAmount(in.nextInt());
				break;
			case 'c':
				bike=new MTBike(idHandler.produceId());
				
				bike.setDailyRentalPrice(PRICE_MTB);
				System.out.print("Amount of derailleurs: ");
				((MTBike)bike).setDerailleurAmount(in.nextInt());
				System.out.print("Model: ");
				((MTBike)bike).setMtbModel(in.next());	
				break;
				
			default:
				System.out.print("Retry...");
				return;
			}	
				 
		}
		else {
			System.out.println("[a-c] only possible!");
			return;
		}
		System.out.print("Price [pln/day] (traditional=20, mountain=30, mtb=50): ");
		bike.setDailyRentalPrice(in.nextInt());
		System.out.print("Wheel diameter[cal]: ");
		bike.setWheelDiameter(in.nextInt());
		System.out.print("servicing distance[km] (traditional=5000, mountain=2000, mtb=1000): ");
		bike.setServicingDistance(in.nextInt());
		System.out.print("Absolute distance[km] : ");
		bike.setAbsoluteDisctance(in.nextInt());
		System.out.print("Distance after service[km] : ");
		bike.setDistanceAfterService(in.nextInt());
		
		bike.setFunctional(bike.getDistanceAfterService()<bike.getServicingDistance());
		
		System.out.print("confirm [y/n] ");
		String confirm=in.next();
		if(confirm.charAt(0)=='n')
			return;
		b.addRecord(bike);
		return;
	} catch(Exception e) {
		System.out.println("Error! Retry...");
	} finally {
//		if(in!=null)
//			in.close();
	}
		
		
		
	}
	public static Client addNewClient(RecordManager<Client> c) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Client client=null;
		
		System.out.println("a) Person");
		System.out.println("b) Institution");
		System.out.print("Choose [a-b]: ");
		try {
		String clientKind=in.nextLine();
		if(clientKind.matches("[a-b]")) {
			switch(clientKind.charAt(0)) {
			case 'a':
				client=new Person(idHandler.produceId());				
				System.out.print("Surname: ");
				((Person)client).setSurname(in.nextLine());
				System.out.print("Name: ");
				((Person)client).setName(in.nextLine());
				break;
			case 'b':
				client=new Institution(idHandler.produceId());
				System.out.print("Name: ");
				((Institution)client).setInstitutionName(in.nextLine());
				break;			
				
			default:
				System.out.print("Retry...");
				return null;
			}	
				 
		}
		else {
			System.out.println("[a-b] only possible!");
			return null;
		}
		System.out.print("Email: ");
		client.setEmail(in.nextLine());
		System.out.print("Phone number: ");
		client.setPhoneNumber(in.nextLine());
				
		System.out.print("confirm [y/n] ");
		String confirm=in.nextLine();
		if(confirm.charAt(0)=='n')
			return null;
		c.addRecord(client);
		return client;
	} catch(Exception e) {
		System.out.println("Error! Retry...");
		return null;
	} 
	}
	
	public static void addNewOrder(RecordManager<Order> o_rec, RecordManager<Client> c_rec, RecordManager<Bike> b_rec) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Order order=new Order(idHandler.produceId());
		
		System.out.println("a) Client from database");
		System.out.println("b) New Client");
		System.out.print("Choose [a-b]: ");
		
		try {
		Client client=null;
			
		String clientKind=in.nextLine();
		if(clientKind.matches("[a-b]")) {
			switch(clientKind.charAt(0)) {
			case 'a':
				System.out.println("a) Person name");
				System.out.println("b) Institution name");
				System.out.println("c) skip");
				System.out.print("Find by [a-c]: ");
				clientKind=in.nextLine();
				if(clientKind.matches("[a-c]")) {
					switch(clientKind.charAt(0)) {
					case 'a':
						client=new Person();				
						System.out.print("Surname: ");
						((Person)client).setSurname(in.nextLine());
						System.out.print("Name: ");
						((Person)client).setName(in.nextLine());
						break;
					case 'b':
						client=new Institution();
						System.out.print("Name: ");
						((Institution)client).setInstitutionName(in.nextLine());
						break;
					case 'c':
						client=new Client() {
							private static final long serialVersionUID = 1L;
							@Override
							public String getClientName() {
								return "";
							}					
						};
					}
				} else {
					System.out.println("[a-c] only possible!");
					return;
				}
				System.out.print("Email (type 'a' to skip): ");
				clientKind=in.nextLine();
				if(!clientKind.matches("a")) {
					client.setEmail(clientKind);
				}
				List<Client> lc=c_rec.findAll(client);
				
				if(lc.isEmpty()) {
					System.out.println("Client not found!");
					return;
				}
				
				for(Client it: lc) {
					System.out.println(it.toString());
				}
				
				System.out.print("Type id: ");
				if((client=c_rec.getRecordById(in.nextInt()))==null) {
					System.out.println("Client not found!");
					return;
				}
				break;												
			case 'b':
				if((client=addNewClient(c_rec))==null)
					return;
				break;
			default:
				System.out.print("Retry...");
				return;
			}				 
		}
		else {
			System.out.println("[a-b] only possible!");
			return;
		}
		order.setClient(client);
		//If get here that means that client is set
		in.nextLine();
		System.out.println("Type the realisation date (yy:mm:dd:hh): ");
		System.out.print("From: ");
		clientKind=in.nextLine();
		int i=0;
		Integer t[]= new Integer[4];
		for(String s:clientKind.split(":")) {
			t[i]=Integer.parseInt(s);
			++i;
		}
		@SuppressWarnings("deprecation")
		Date t1= new Date(Date.UTC(100+t[0], t[1]-1, t[2], t[3], 0,0));
		
		System.out.print("To: ");
		clientKind=in.nextLine();
		i=0;
		for(String s:clientKind.split(":")) {
			t[i]=Integer.parseInt(s);
			++i;
		}
		
		@SuppressWarnings("deprecation")
		Date t2= new Date(Date.UTC(100+t[0], t[1]-1, t[2], t[3], 0,0));
		
		DatePair pair=null;
		try {
		  pair= new DatePair(t1, t2);
		} catch(EndBeforStartException e) {
			System.out.println("Date \"from\" is before the \"to\"");
		}
		
		order.setDatePair(pair);
		
		while(true) {
		System.out.println("a) add a bike");	
		System.out.println("b) confirm");
		System.out.println("c) quit without saving");
		clientKind=in.nextLine();
		if(clientKind.matches("[a-c]")) {
			switch(clientKind.charAt(0)) {
			case 'a':
				System.out.println("a) Traditional");
				System.out.println("b) Mountain bike");
				System.out.println("c) MTB");
				clientKind=in.nextLine();
				if(clientKind.matches("[a-c]")) {
					
					Bike bik=null;
					System.out.print("Count: ");
					i=Integer.parseInt(in.nextLine());
					switch(clientKind.charAt(0)) {
					case 'a':
						bik=new TraditionalBike();
						break;				
					case 'b':
						bik=new MountainBike();
						break;
					case 'c':
						bik=new MTBike();
						break;
					}
					bik.addReservation(pair);
					List<Bike> lb= b_rec.findAll(bik, i);	//find i bikes
					
					if(lb.size()<i) {
						System.out.println("There are only " + lb.size() + " bikes for that criteria");
					} else System.out.println("Searching successfully completed");
					System.out.print("Accept [y/n]: ");
					if(in.nextLine().charAt(0)!='y')
						continue;
					
					order.getThingList().addAll(lb);
					
				} else {
					System.out.println("[a-c] only possible");
					return;
				}
				break;
			case 'b':
				System.out.println(order.toString());
				System.out.print("Confirm [y/n]: ");
				if(in.nextLine().charAt(0)=='y') {
					
					for(Thing b: order.getThingList() ) {
						b.addReservation(pair);
					}
					order.confirmOrder();
					o_rec.addRecord(order);
					return;
				} 
				break;
			case 'c':
				return;
			} //end of switch
		} else {
			System.out.println("[a-c] only possible");
			return;
		}
		}

	} catch(Exception e) {
		System.out.println("Error! Retry...");
	} 
	}
	
	public static void showRecordById(RecordManager<? extends Idenifiable> b) throws RecordNotFoundException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		try {
		System.out.println("Id: ");
		int id=in.nextInt();
		Idenifiable obj=b.getRecordById(id);
		if(obj==null) {
			System.out.println("Record with that id doesn't exist");
		} else {
			System.out.println(obj.toString());
		}
		} finally {
//			if(in!=null)
//				in.close();
		}
	}
	
	public static void releaseDate(RecordManager<Bike> b, Order ord) throws RecordNotFoundException {
		for(Thing t:ord.getThingList()) {
			b.getRecordById(t.getId()).releaseReservation(ord.getDatePair());			
		}
	}
}

