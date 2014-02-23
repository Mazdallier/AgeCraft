package org.agecraft.core.techtree;

import java.util.ArrayList;
import java.util.HashMap;

import org.agecraft.core.AgeCraftCore;

public class TechTreeServer {

	public static HashMap<String, HashMap<String, ArrayList<String>>> players = new HashMap<String, HashMap<String, ArrayList<String>>>();

	public static boolean hasUnlockedComponent(String player, String pageName, String name) {
		HashMap<String, ArrayList<String>> pages;
		if(!players.containsKey(player)) {
			pages = new HashMap<String, ArrayList<String>>();
			players.put(player, pages);
		} else {
			pages = players.get(player);
		}
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
			pages.put(pageName, components);
		} else {
			components = pages.get(pageName);
		}
		return components.contains(name);
	}
	
	public static void unlockComponent(String player, String pageName, String name) {
		HashMap<String, ArrayList<String>> pages;
		if(!players.containsKey(player)) {
			pages = new HashMap<String, ArrayList<String>>();
			players.put(player, pages);
		} else {
			pages = players.get(player);
		}
		ArrayList<String> components;
		if(!pages.containsKey(pageName)) {
			components = new ArrayList<String>();
			pages.put(pageName, components);
		} else {
			components = pages.get(pageName);
		}
		if(!components.contains(name)) {
			TechTreeComponent component = TechTree.getComponent(pageName, name);
			for(TechTreeComponent parent : component.parents) {
				if(!hasUnlockedComponent(player, parent.pageName, parent.name)) {
					return;
				}
			}
			components.add(name);
		}
		AgeCraftCore.instance.packetHandler.sendToAllPlayers(new MessageTechTreeComponent(player, pageName, name, true));
	}
	
	public static void lockComponent(String player, String pageName, String name) {
		HashMap<String, ArrayList<String>> pages;
		if(!players.containsKey(player)) {
			pages = new HashMap<String, ArrayList<String>>();
			players.put(player, pages);
		} else {
			pages = players.get(player);
		}
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
		AgeCraftCore.instance.packetHandler.sendToAllPlayers(new MessageTechTreeComponent(player, pageName, name, false));
	}
}
