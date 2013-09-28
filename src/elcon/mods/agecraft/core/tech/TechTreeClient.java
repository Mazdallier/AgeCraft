package elcon.mods.agecraft.core.tech;

import java.util.ArrayList;
import java.util.HashMap;

import elcon.mods.agecraft.core.tech.gui.GuiTechTreeComponent;

public class TechTreeClient {

	public static HashMap<String, ArrayList<String>> pages = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<String>> unlocked = new HashMap<String, ArrayList<String>>();
	
	public static boolean hasUnlockedComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = pages.get(pageName);
		}
		return components.contains(name);
	}

	public static boolean canUnlockComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = pages.get(pageName);
		}
		if(components.contains(name)) {
			TechTreeComponent component = TechTree.getComponent(pageName, name);
			for(TechTreeComponent parent : component.parents) {
				if(!hasUnlockedComponent(parent.pageName, parent.name)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static void unlockComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = pages.get(pageName);
		}
		if(!components.contains(name)) {
			components.add(name);
			GuiTechTreeComponent.instance.queueComponent(pageName, name);
			addUnlock(pageName, name);
		}
	}
	
	public static void lockComponent(String pageName, String name) {
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = pages.get(pageName);
		}
		if(components.contains(name)) {
			components.remove(name);
		}
	}
	
	public static boolean hasUnlock(String pageName, String name) {
		ArrayList<String> components;
		if(!unlocked.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = unlocked.get(pageName);
		}
		return components.contains(name);
	}
	
	public static void addUnlock(String pageName, String name) {
		ArrayList<String> components;
		if(!unlocked.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = unlocked.get(pageName);
		}
		if(!components.contains(name)) {
			components.add(name);
		}
	}
	
	public static void removeUnlock(String pageName, String name) {
		ArrayList<String> components;
		if(!unlocked.containsKey(pageName)) {
			components = new ArrayList<String>();
		} else {
			components = unlocked.get(pageName);
		}
		if(components.contains(name)) {
			components.remove(name);
		}
	}
}
