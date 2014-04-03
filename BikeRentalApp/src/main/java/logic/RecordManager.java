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



import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import model.check.Checkable;
import model.id.Idenifiable;


public class RecordManager<T extends Comparable<T> & Idenifiable> implements RecordManagerInterface<T>{

	private List<T> recordList =new ArrayList<>();
	
	private static Logger logger=LogManager.getLogger(RecordManager.class.getName());
	
	public RecordManager() {
		logger.info("Creating RecordManager<>" );
	}

	@Override
	public void addRecord(T record) {
		logger.debug("addRecord() start: size=" +recordList.size());
		recordList.add(record);
		logger.debug("addRecord() return: size=" +recordList.size());
	}

	@Override
	public void sortRecords() {
			logger.debug("sortRecords() start");
			Collections.<T>sort(recordList);
			logger.debug("sortRecords() return");
	}

	@Override
	public T getRecordById(long bikeId) throws RecordNotFoundException {
		logger.debug("getRecordById("+bikeId+") start");
		if(bikeId<0) 
			throw new RecordNotFoundException();
		for(T v: recordList) {
			if(v.getId()==bikeId) {
				logger.debug("sortRecords() return="+v.getClass().getSimpleName());
				return v;
			}
		}
		logger.debug("sortRecords() return=null");
		return null;
	}

	
	@Override
	public List<T> getRecords() {
		logger.debug("getRecords(): size="+recordList.size());
		return recordList;
	}
	
	@Override
	public void setRecords(Collection<T> col) {
		logger.debug("setRecords()");
		this.recordList = (List<T>) col;
	}
	
	@Override
	public boolean removeRecord(T record) {
		logger.debug("removeRecord: " +record.getClass().getSimpleName());
		return recordList.remove(record);
	}

	@Override
	public boolean removeRecord(long recordId) {
		logger.debug("getRecordById("+recordId+") start: size="+recordList.size());
		Iterator<T> it =recordList.iterator();
		while(it.hasNext()) {
			if(it.next().getId()==recordId) {
				it.remove();
				logger.debug("getRecordById("+recordId+") return=true, size="+recordList.size());
				return true;
			}
		}
		logger.debug("getRecordById("+recordId+") return=false, size="+recordList.size());
		return false;
	}


	@Override
	public boolean saveRecordsToFile(String filename) throws IOException  {
		logger.debug("saveRecordsFromFile("+filename+") start");
		boolean isProperSaved=true;
	 ObjectOutputStream out=null;
	 
	 try{
		out = new ObjectOutputStream(new BufferedOutputStream(
	              new FileOutputStream(filename)));
		
		out.writeObject(recordList);
		
	  } catch(IOException e) {
		  logger.catching(e);
		  isProperSaved=false;
	  } finally {
		  if(out!=null)
			  out.close();
	  }
	 logger.debug("loadRecordsFromFile() return=" +isProperSaved);
		return isProperSaved;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean loadRecordsFromFile(String filename) throws IOException {
		logger.debug("loadRecordsFromFile("+filename+") start");
		boolean isProperLoaded=true;
		ObjectInputStream in=null;
		try {
			in=new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
			recordList= (List<T>)in.readObject();
		}  catch(ClassNotFoundException ex){
			  logger.catching(ex);	  
		      isProperLoaded=true;
	    }  catch(IOException ex){
	    	 logger.catching(ex);
	    	  isProperLoaded=true;
	    } finally {
	    	if(in!=null)
	    		in.close();
	    }
		logger.debug("loadRecordsFromFile() return=" +isProperLoaded);
		return isProperLoaded;
	}

	@Override
	public List<T> findAll(Checkable<T> chk) {
		logger.debug("findAll("+chk.getClass().getSimpleName()+") start");
		List<T> list=new ArrayList<>();
		for(T obj: recordList) {
			if(chk.check(obj))
				list.add(obj);
		}
		logger.debug("findAll(): "+list.size() +" elements founded");
		return list;
	}
	
	@Override
	public List<T> findAll(Checkable<T> chk, int max) {
		logger.debug("findAll("+chk.getClass().getSimpleName()+"," +max+") start");
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
		logger.debug("findAll(): "+list.size() +" elements founded");
		return list;
	}


	


	
	
	
	
}
