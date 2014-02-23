package org.agecraft.core.clothing;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.LinkedList;

import org.agecraft.AgeCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.network.EQMessage;

public class MessageClothingList extends EQMessage {

	public ArrayList<ClothingCategory> categories;
	
	public MessageClothingList() {
		categories = new ArrayList<ClothingCategory>();
		categories.addAll(ClothingRegistry.categories.values());
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeInt(categories.size());
		for(ClothingCategory category : categories) {
			writeString(target, category.name);
			writeString(target, category.versionURL);
			writeString(target, category.updateURL);
			target.writeInt(category.expansionURLs.size());
			for(String url : category.expansionURLs) {
				writeString(target, url);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void decodeFrom(ByteBuf source) {
		for(ClothingCategory category : ClothingRegistry.categories.values()) {
			category.enabled = false;
		}
		int size = source.readInt();
		final LinkedList<ClothingCategory> categories = new LinkedList<ClothingCategory>();
		for(int i = 0; i < size; i++) {
			String name = readString(source);
			String versionURL = readString(source);
			String updateURL = readString(source);
			int expansions = source.readInt();
			ArrayList<String> expansionURLs = new ArrayList<String>();
			for(int j = 0; j < expansions; j++) {
				expansionURLs.add(readString(source));
			}
			if(ClothingRegistry.getClothingCategory(name) == null) {
				ClothingCategory category = new ClothingCategory(name, versionURL, updateURL);
				category.expansionURLs.addAll(expansionURLs);
				ClothingRegistry.registerClothingCategory(category);
				ClothingUpdater.instance.localCategories.add(category);
				categories.add(category);
				AgeCraft.log.info("[Clothing] Category: " + name + " can't be found locally, so downloading it");
			} else {
				ClothingRegistry.getClothingCategory(name).enabled = true;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		new Thread() {
			@Override
			public void run() {
				ClothingUpdater.instance.saveLocalCategories();
				ClothingUpdater.instance.downloadCateogries(categories);
				PlayerClothingClient.updatePlayerClothingAll();
			}
		};
	}
}
