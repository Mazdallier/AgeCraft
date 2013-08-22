package elcon.mods.agecraft;

import java.io.File;

import net.minecraft.world.World;

public class ACUtil {

	public static int getFirstUncoveredBlock(World world, int x, int z) {
		int i;
		for(i = 48; !world.isAirBlock(x + 3, i + 1, z + 3); i++) {
			;
		}
		return i;
	}

	public static String firstUpperCase(String s) {
		return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}

	public static String getFileExtension(File file) {
		return getFileExtension(file.getName());
	}
	
	public static String getFileExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if(i > p) {
			return fileName.substring(i + 1);
		}
		return "";
	}
}
