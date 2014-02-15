package org.agecraft.core.tech;

import java.util.ArrayList;
import java.util.HashMap;

import org.agecraft.ACPacketHandler;

import cpw.mods.fml.common.network.PacketDispatcher;

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
		PacketDispatcher.sendPacketToAllPlayers(ACPacketHandler.getTechTreeComponentPacket(player, pageName, name, true));
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
		PacketDispatcher.sendPacketToAllPlayers(ACPacketHandler.getTechTreeComponentPacket(player, pageName, name, false));
	}
}
