package model.check;

public interface Checkable<T> {
	boolean check(T obj);
}
