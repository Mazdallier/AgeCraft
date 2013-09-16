package elcon.mods.agecraft.core.clothing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import elcon.mods.core.ElConCore;

public class ClothingManager {

	public static class ClothingCategoryVersion {
		
		public String name;
		public String version;
		public String updateURL;
		
		public ClothingCategoryVersion(String name, String version, String updateURL) {
			this.name = name;
			this.version = version;
			this.updateURL = updateURL;
		}
	}
	
	public static HashMap<String, ClothingCategoryVersion> localVersions = new HashMap<String, ClothingCategoryVersion>();
	public static HashMap<String, ClothingCategoryVersion> versions = new HashMap<String, ClothingCategoryVersion>();
	public static File clothingDir;
	
	public static void load() {
		
	}
	
	public static void update() {
		File file = new File(ElConCore.minecraftDir, "clothing_version.dat");
		if(file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while((line = reader.readLine()) != null) {
					String[] split = line.split("|");
					if(split.length >= 3) {
						localVersions.put(split[0], new ClothingCategoryVersion(split[0], split[1], split[2]));
					}
				}
				reader.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}		
		for(int i = 0; i < ClothingRegistry.categories.length; i++) {
			if(ClothingRegistry.categories[i] != null) {
				ClothingCategory category = ClothingRegistry.categories[i];
				try {
					File tempFile = new File(ElConCore.minecraftDir, "clothing_version_temp.dat");
					FileUtils.copyURLToFile(new URL(category.updateURL), tempFile);
					try {
						BufferedReader reader = new BufferedReader(new FileReader(tempFile));
						String line;
						while((line = reader.readLine()) != null) {
							String[] split = line.split("|");
							if(split.length >= 3) {
								versions.put(split[0], new ClothingCategoryVersion(split[0], split[1], split[2]));
							}
						}
						reader.close();
					} catch(Exception e) {
						e.printStackTrace();
					}
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
