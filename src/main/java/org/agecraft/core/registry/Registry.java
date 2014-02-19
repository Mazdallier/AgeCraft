package org.agecraft.core.registry;

import org.agecraft.AgeCraft;

public class Registry<T> {
	
	private T[] registered;
	
	public Registry(int maxSize) {
		this.registered = (T[]) new Object[maxSize];
	}
	
	public T[] getAll() {
		return registered;
	}
	
	public T get(int index) {
		return registered[index];
	}
	
	public void setAll(T[] registered) {
		this.registered = registered;
	}
	
	public void set(int index, T value) {
		if(registered[index] != null) {
			AgeCraft.log.warn("[" + getClass().getSimpleName() + "] Overriding " + registered[index].toString() + " with " + value.toString() + " at " + index);
		}
		registered[index] = value;
	}
}
