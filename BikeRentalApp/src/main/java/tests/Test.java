/*package tests;

import java.util.Date;

import model.date.DatePair;
import model.date.EndBeforStartException;


public class Test {

	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] argc) {
		IdModel mod = IdModel.getInstance();
		TraditionalBike t= new TraditionalBike(mod.produceId());
		System.out.println("Bike: " + t.getId());
		System.out.println("Produce: " +mod.produceId());
		
		Scanner in =new Scanner(System.in);
		int x=in.nextInt();
		int y=in.nextInt();
		int z=in.nextInt();
		System.out.println(x+" "+y+" "+z);
		
		int i=0;
		Integer t[]= new Integer[4];
		String clientKind="2001:10:3:4";
		for(String s:clientKind.split(":")) {
			t[i]=Integer.parseInt(s);
			++i;
		}
		Date t1=new Date(11, 10, 10, 10, 9);
		Date t2=new Date(11, 10, 10, 10, 10);
		
		Date t3=new Date(11,10,10 ,10 ,12);
		Date t4=new Date(11,10,10,10,13);
		
		try {
			DatePair p1=new DatePair(t1,t2);
			DatePair p2=new DatePair(t3, t4);
			DatePair p3=new DatePair(t1, t3);
			DatePair p4=new DatePair(t2,t4);
			
			System.out.println("p1-p1 " + p1.isOverlapping(p1));
			System.out.println("p1->p2 " + p1.isOverlapping(p2));
			System.out.println("p2->p1 " + p2.isOverlapping(p1));
			System.out.println("p2->p3 " + p2.isOverlapping(p3));
			System.out.println("p3->p2 " + p3.isOverlapping(p2));
			System.out.println("p3->p4 " + p3.isOverlapping(p4));
			System.out.println("p4->p3 " + p4.isOverlapping(p3));
		} catch (EndBeforStartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
*/