package elcon.mods.agecraft.schematic;

import java.io.File;
import java.util.HashMap;

import net.minecraft.world.World;

public class SchematicAPI {

	public static HashMap<String, HashMap<String, Schematic>> schematics = new HashMap<String, HashMap<String, Schematic>>();
	
	public static Schematic createSchematicFromWorld(String mod, String name, File dir, String fileName, boolean useMCDir, World world, int x, int y, int z) {
		Schematic schematic = new Schematic(mod, name, dir, fileName, useMCDir);
		schematic.createFromWorld(world, x, y, z);
		return schematic;
	}
	
	public static Schematic createSchematicFromWorld(String mod, String name, String dirName, String path, boolean useMCDir, World world, int x, int y, int z) {
		Schematic schematic = new Schematic(mod, name, dirName, path, useMCDir);
		schematic.createFromWorld(world, x, y, z);
		return schematic;
	}
	
	public static Schematic createSchematicFromWorld(String mod, String name, World world, int x, int y, int z) {
		Schematic schematic = new Schematic(mod, name);
		schematic.createFromWorld(world, x, y, z);
		return schematic;
	}
	
	public static void addSchematic(String mod, String name, Schematic schematic) {
		if(mod == null || name == null || mod.isEmpty() || name.isEmpty() || schematic == null) {
			return;
		}
		HashMap<String, Schematic> oldSchematics = schematics.get(mod);
		if(oldSchematics == null) {
			oldSchematics = new HashMap<String, Schematic>();
		}
		oldSchematics.put(name, schematic);
		schematics.put(mod, oldSchematics);
	}
	
	public static void removeSchematic(String mod, String name) {
		if(mod == null || name == null || mod.isEmpty() || name.isEmpty()) {
			return;
		}
		HashMap<String, Schematic> oldSchematics = schematics.get(mod);
		oldSchematics.remove(name);
		if(oldSchematics.size() <= 0 || oldSchematics.isEmpty()) {
			 schematics.remove(mod);
		} else {
			schematics.put(mod, oldSchematics);
		}
	}
	
	public static Schematic getSchematic(String mod, String name) {
		if(mod == null || name == null || mod.isEmpty() || name.isEmpty() || !schematics.containsKey(mod)) {
			return null;
		}
		HashMap<String, Schematic> oldSchematics = schematics.get(mod);
		if(!oldSchematics.containsKey(name)) {
			return null;
		}
		return oldSchematics.get(name);
	}
}
