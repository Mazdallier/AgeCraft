package org.agecraft.core.techtree;

import java.util.ArrayList;
import java.util.HashMap;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.gui.GuiTechTreeComponent;

public class TechTreeClient extends ACComponentClient {

	public static TechTreeClient instance;	
	public static HashMap<String, ArrayList<String>> pages = new HashMap<String, ArrayList<String>>();
	
	public TechTreeClient(ACComponent component) {
		super(component);
		instance = this;
	}
	
	public static boolean hasUnlockedComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
			pages.put(pageName, components);
		} else {
			components = pages.get(pageName);
		}
		return components.contains(name);
	}

	public static boolean canUnlockComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
			pages.put(pageName, components);
		} else {
			components = pages.get(pageName);
		}
		TechTreeComponent component = TechTree.getComponent(pageName, name);
		for(TechTreeComponent parent : component.parents) {
			if(!hasUnlockedComponent(parent.pageName, parent.name)) {
				return false;
			}
		}
		return true;
	}
	
	public static void unlockComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
			pages.put(pageName, components);
		} else {
			components = pages.get(pageName);
		}
		if(!components.contains(name)) {
			components.add(name);
			GuiTechTreeComponent.instance.queueComponent(pageName, name);
		}
	}
	
	public static void lockComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
			pages.put(pageName, components);
		} else {
			components = pages.get(pageName);
		}
		if(components.contains(name)) {
			components.remove(name);
		}
	}
}
