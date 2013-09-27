package elcon.mods.agecraft;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import elcon.mods.agecraft.core.tech.TechTreeServer;
import elcon.mods.core.IElConSaveHandler;

public class ACSaveHandler implements IElConSaveHandler {

	@Override
	public String[] getSaveFiles() {
		return new String[]{"tech_tree"};
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
				ACLog.info("Loaded the Tech Tree");
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
				ACLog.info("Saved the Tech Tree");
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
