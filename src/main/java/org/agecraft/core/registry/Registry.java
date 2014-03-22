package org.agecraft.core.registry;

import java.util.HashMap;

import org.agecraft.AgeCraft;

public class Registry<T> {
	
	public static HashMap<String, Registry> registries = new HashMap<String, Registry>();
	
	private Object[] idToObject;
	private HashMap<String, T> nameToObject;
	
	public Registry(int maxSize) {
		idToObject = new Object[maxSize];
		nameToObject = new HashMap<String, T>();
		
		registries.put(getClass().getSimpleName().replaceAll("Registry", "").toLowerCase(), this);
	}
	
	public Object[] getAll() {
		return idToObject;
	}
	
	public T get(int index) {
		return (T) idToObject[index];
	}
	
	public T get(String name) {
		return nameToObject.get(name);
	}
	
	public void setAll(T[] registered) {
		idToObject = registered;
		nameToObject.clear();
		for(int i = 0; i < registered.length; i++) {
			if(registered[i] != null) {
				nameToObject.put(registered[i].toString(), registered[i]);
			}
		}
	}
	
	public void set(int index, T value) {
		if(idToObject[index] != null) {
			AgeCraft.log.warn("[" + getClass().getSimpleName() + "] Overriding " + idToObject[index].toString() + " with " + value.toString() + " at " + index);
		}
		idToObject[index] = value;
		nameToObject.put(value.toString(), value);
	}
}
