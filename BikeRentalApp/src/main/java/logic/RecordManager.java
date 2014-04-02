//package logic;
//
//public class RecordManager<T> implements RecordManagerInterface<T> {
//	//public T get() ;
//	
//}


package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
//

import model.check.Checkable;
import model.id.Idenifiable;


public class RecordManager<T extends Comparable<T> & Idenifiable> implements RecordManagerInterface<T>{

	private List<T> recordList =new ArrayList<>();
	

	@Override
	public void addRecord(T record) {
		recordList.add(record);
	}

	@Override
	public void sortRecords() {	
			Collections.<T>sort(recordList);		
	}

	@Override
	public T getRecordById(long bikeId) throws RecordNotFoundException {
		if(bikeId<0) 
			throw new RecordNotFoundException();
		for(T v: recordList) {
			if(v.getId()==bikeId) {
				return v;
			}
		}
		return null;
	}

	
	@Override
	public List<T> getRecords() {
		return recordList;
	}
	
	@Override
	public void setRecords(Collection<T> col) {
		this.recordList = (List<T>) col;
	}
	
	@Override
	public boolean removeRecord(T record) {
		return recordList.remove(record);
	}

	@Override
	public boolean removeRecord(long recordId) {
		Iterator<T> it =recordList.iterator();
		while(it.hasNext()) {
			if(it.next().getId()==recordId) {
				it.remove();
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean saveRecordsToFile(String filename) throws IOException  {
	 boolean isProperSaved=true;
	 ObjectOutputStream out=null;
	 
	 try{
		out = new ObjectOutputStream(new BufferedOutputStream(
	              new FileOutputStream(filename)));
		
		out.writeObject(recordList);
		
	  } catch(IOException e) {
		  isProperSaved=false;
	  } finally {
		  if(out!=null)
			  out.close();
	  }
		return isProperSaved;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean loadRecordsFromFile(String filename) throws IOException {
		boolean isProperLoaded=true;
		ObjectInputStream in=null;
		try {
			in=new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			recordList= (List<T>)in.readObject();
		}  catch(ClassNotFoundException ex){
		      //fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		      isProperLoaded=true;
	    }  catch(IOException ex){
	    	  //fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
	    	  isProperLoaded=true;
	    } finally {
	    	if(in!=null)
	    		in.close();
	    }
		return isProperLoaded;
	}

	@Override
	public List<T> findAll(Checkable<T> chk) {
		List<T> list=new ArrayList<>();
		for(T obj: recordList) {
			if(chk.check(obj))
				list.add(obj);
		}
		return list;
	}
	
	@Override
	public List<T> findAll(Checkable<T> chk, int max) {
		List<T> list=new ArrayList<>();
		int it=0;
		for(T obj: recordList) {
			if(it == max)
				break;
			if(chk.check(obj)) {
				list.add(obj);
				++it;
			}
		}
		return list;
	}


	


	
	
	
	
}
