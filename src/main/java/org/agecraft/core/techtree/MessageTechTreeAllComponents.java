package org.agecraft.core.techtree;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class MessageTechTreeAllComponents extends EQMessage {

	public HashMap<String, ArrayList<String>> pages;
	
	public MessageTechTreeAllComponents() {
		pages = new HashMap<String, ArrayList<String>>();
	}
	
	public MessageTechTreeAllComponents(String player) {
		this();
		if(TechTreeServer.players.containsKey(player)) {
			pages.putAll(TechTreeServer.players.get(player));
		}
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeInt(pages.size());
		for(String pageName : pages.keySet()) {
			ArrayList<String> components = pages.get(pageName);
			writeString(target, pageName);
			target.writeInt(components.size());
			for(String name : components) {
				writeString(target, name);
			}
		}
	}

	@Override
	public void decodeFrom(ByteBuf source) {
		int pageCount = source.readInt();
		pages = new HashMap<String, ArrayList<String>>(pageCount);
		for(int i = 0; i < pageCount; i++) {
			String pageName = readString(source);
			int componentCount = source.readInt();
			ArrayList<String> components = new ArrayList<String>(componentCount);
			for(int j = 0; j < componentCount; j++) {
				components.add(readString(source));
			}
			pages.put(pageName, components);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		for(String page : pages.keySet()) {
			ArrayList<String> components = pages.get(page);
			for(String component : components) {
				TechTreeClient.unlockComponent(page, component);
			}
		}
	}
}
