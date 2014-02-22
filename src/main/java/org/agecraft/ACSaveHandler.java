package org.agecraft;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.nbt.NBTTagCompound;
import elcon.mods.elconqore.IEQSaveHandler;

public class ACSaveHandler implements IEQSaveHandler {

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
				//TechTreeServer.players = (HashMap<String, HashMap<String, ArrayList<String>>>) in.readObject();
				AgeCraft.log.info("[TechTree] Loaded the Tech Tree");
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(fileName.equals("clothing")) {
			try {
				//PlayerClothingServer.players = (HashMap<String, PlayerClothing>) in.readObject();
				AgeCraft.log.info("[Clothing] Loaded the clothing");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void save(String fileName, File file, ObjectOutputStream out) {
		if(fileName.equals("tech_tree")) {
			try {
				//out.writeObject(TechTreeServer.players);
				AgeCraft.log.info("[TechTree] Saved the Tech Tree");
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(fileName.equals("clothing")) {
			try {
				//out.writeObject(PlayerClothingServer.players);
				AgeCraft.log.info("[Clothing] Saved the clothing");
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
