package logic;


import java.io.IOException;
import java.util.Collection;
import model.check.Checkable;

public interface RecordManagerInterface<T> {
	public void addRecord(T obj) throws RecordExistsException;
	public void sortRecords();
	
	public T getRecordById(long id) throws RecordNotFoundException;
	
	public Collection<T> getRecords() throws RecordNotFoundException;
	public void setRecords(Collection<T> col);
	
	public boolean removeRecord(T obj);
	public boolean removeRecord(long id);
	
	public Collection<T> findAll(Checkable<T> chk);
	public Collection<T> findAll(Checkable<T> chk, int max);
	
	public boolean saveRecordsToFile(String filename) throws IOException;
	public boolean loadRecordsFromFile(String filename) throws IOException;
	
}
