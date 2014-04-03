package model.id;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//singleton model
public class IdModel {
	private static IdModel instance;
	private IdModel(long id) {
		idCounter=id;
	};
	
	private long idCounter;
	private final static String filename="idCounter.d";
	
	
	public static IdModel getInstance() {
		long id=0;
		if(instance == null) {
			BufferedReader file=null;
				try {
					file = new BufferedReader(new FileReader(filename));
					id=Long.parseLong(file.readLine());
				} catch (FileNotFoundException e) {
					try {
						new File(filename).createNewFile();
						file = new BufferedReader(new FileReader(filename));
					} catch (IOException e1) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if(file!=null)
						try {
							file.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			instance=new IdModel(id);
		}
		return instance;
	}
	
	public long produceId() {
		BufferedWriter out=null;
		try {
			out=new BufferedWriter(new FileWriter(filename));
			++idCounter;
			out.write(new Long(idCounter).toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}		
		}
		return idCounter;
	}
	
	public void clearCounter() {
		idCounter=0;
		BufferedWriter out=null;
		try {
			out=new BufferedWriter(new FileWriter(filename));
			out.write(new Long(idCounter).toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}		
		}
	}
	
}
