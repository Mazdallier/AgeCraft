package org.agecraft;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.agecraft.core.clothing.PlayerClothing;
import org.agecraft.core.clothing.PlayerClothingServer;
import org.agecraft.core.tech.TechTreeServer;

import net.minecraft.nbt.NBTTagCompound;
import elcon.mods.core.IECSaveHandler;

public class ACSaveHandler implements IECSaveHandler {

	@Override
	public String[] getSaveFiles() {
		return new String[]{"tech_tree", "clothing"};
	}

	@Override
	public SaveFileType getSaveFileType(String fileName) {
		return SaveFileType.OBJECT;
	}

	@Override
	public void load(String fileName, File file, ObjectInputStream in) {
		if(fileName.equals("tech_tree")) {
			try {
				TechTreeServer.players = (HashMap<String, HashMap<String, ArrayList<String>>>) in.readObject();
				ACLog.info("[TechTree] Loaded the Tech Tree");
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(fileName.equals("clothing")) {
			try {
				PlayerClothingServer.players = (HashMap<String, PlayerClothing>) in.readObject();
				ACLog.info("[Clothing] Loaded the clothing");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void save(String fileName, File file, ObjectOutputStream out) {
		if(fileName.equals("tech_tree")) {
			try {
				out.writeObject(TechTreeServer.players);
				ACLog.info("[TechTree] Saved the Tech Tree");
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(fileName.equals("clothing")) {
			try {
				out.writeObject(PlayerClothingServer.players);
				ACLog.info("[Clothing] Saved the clothing");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void loadNBT(String fileName, File file, NBTTagCompound nbt) {
		
	}

	@Override
	public void saveNBT(String fileName, File file, NBTTagCompound nbt) {
		
	}
}
