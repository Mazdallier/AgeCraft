package elcon.mods.agecraft.core.clothing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.core.clothing.ClothingRegistry.ClothingType;
import elcon.mods.core.ElConCore;

public class ClothingUpdater implements Runnable {

	public static class ClothingCategoryVersion {

		public String name;
		public String version;

		public ClothingCategoryVersion(String name, String version) {
			this.name = name;
			this.version = version;
		}
	}

	public HashMap<String, ClothingCategoryVersion> localVersions = new HashMap<String, ClothingCategoryVersion>();
	public HashMap<String, ClothingCategoryVersion> versions = new HashMap<String, ClothingCategoryVersion>();
	public ArrayList<ClothingCategory> downloadCategories = new ArrayList<ClothingCategory>();
	public File clothingDir;

	public ClothingUpdater(File clothingDir) {
		this.clothingDir = clothingDir;
	}	
	
	public void load() {
		if(clothingDir.exists()) {
			int categoryCount = 0;
			int clothingCount = 0;
			int colorCount = 0;
			File[] dirs = clothingDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			for(int i = 0; i < dirs.length; i++) {
				ClothingCategory category = ClothingRegistry.getClothingCategory(dirs[i].getName());
				categoryCount++;
				for(int j = 0; j < ClothingRegistry.types.length; j++) {
					if(ClothingRegistry.types[j] != null) {
						ClothingType type = ClothingRegistry.types[j];
						File typeDir = new File(dirs[i], File.separator + type.name);
						if(typeDir.exists()) {
							File[] files = typeDir.listFiles();
							for(int k = 0; k < files.length; k++) {
								String[] split = files[k].getName().replaceAll(".png", "").split("_");
								String clothingColor = split[split.length - 1];
								String clothingName = files[k].getName().replaceAll("_" + clothingColor + ".png", "");
								Clothing clothing = category.getClothing(type, clothingName);
								if(clothing == null) {
									clothing = new Clothing(category.getNextClothingID(type), clothingName, type, category);
									category.registerClothing(clothing);
									clothingCount++;									
								}
								clothing.enableColor(Integer.parseInt(clothingColor));
								colorCount++;
							}
						}
					}
				}
				ACLog.info("[Clothing] Loaded clothing category: " + category.name);
			}
			ACLog.info("[Clothing] Loaded " + categoryCount + " categories with " + clothingCount + " clothing types with a total of " + colorCount + " colors. (Average: " + (clothingCount == 0 ? 0 :(colorCount / clothingCount)) + " colors/clothing type)");
		}
	}

	public void update() {
		if(!clothingDir.exists()) {
			clothingDir.mkdirs();
		}
		File file = new File(ElConCore.minecraftDir, "clothing_versions.dat");
		if(file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while((line = reader.readLine()) != null) {
					if(line.length() > 1) {
						String[] split = line.split("=");
						if(split.length >= 2) {
							localVersions.put(split[0], new ClothingCategoryVersion(split[0], split[1]));
						}
					}
				}
				reader.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		ArrayList<String> versionURLsChecked = new ArrayList<String>();
		for(int i = 0; i < ClothingRegistry.categories.length; i++) {
			if(ClothingRegistry.categories[i] != null) {
				ClothingCategory category = ClothingRegistry.categories[i];
				ACLog.info("[Clothing] Checking version for category: " + category.name);
				if(!versionURLsChecked.contains(category.versionURL)) {
					try {
						File tempFile = new File(ElConCore.minecraftDir, "clothing_version_temp.dat");
						FileUtils.copyURLToFile(new URL(category.versionURL), tempFile);
						try {
							BufferedReader reader = new BufferedReader(new FileReader(tempFile));
							String line;
							while((line = reader.readLine()) != null) {
								if(line.length() > 1) {
									String[] split = line.split("=");
									if(split.length >= 2) {
										versions.put(split[0], new ClothingCategoryVersion(split[0], split[1]));
									}
								}
							}
							reader.close();
							tempFile.delete();
							versionURLsChecked.add(category.versionURL);
						} catch(Exception e) {
							e.printStackTrace();
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(versions.containsKey(category.name) && (!localVersions.containsKey(category.name) || shouldUpdate(localVersions.get(category.name), versions.get(category.name)))) {
					downloadCategories.add(category);
				}
			}
		}
	}
	
	public void download() {
		ArrayList<String> lines = new ArrayList<String>();
		for(ClothingCategory category : downloadCategories) {
			try {
				ACLog.info("[Clothing] Updating category " + category.name + " to version " + versions.get(category.name));
				File clothingZip = new File(clothingDir, category.name + ".zip");
				clothingZip.createNewFile();
				FileUtils.copyURLToFile(new URL(category.updateURL), clothingZip);
				ACLog.info("[Clothing] Downloaded " + clothingZip.getName() + " from " + category.updateURL);
				if(clothingZip.exists()) {
					extractZip(clothingZip, new File(clothingDir, File.separator + category.name));
					ACLog.info("[Clothing] Extracted " + clothingZip.getName() + " to " + new File(clothingDir, File.separator + category.name).getAbsolutePath());
				}
				clothingZip.delete();
				lines.add(category.name + "=" + versions.get(category.name).version);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(!lines.isEmpty()) {
			try {
				File file = new File(ElConCore.minecraftDir, "clothing_versions.dat");
				if(file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				for(String line : lines) {
					writer.write(line);
					writer.write("\n");
				}
				writer.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean shouldUpdate(ClothingCategoryVersion localVersion, ClothingCategoryVersion version) {
		boolean update = false;
		String[] localSplit = localVersion.version.split("\\.");
		String[] split = version.version.split("\\.");
		for(int i = 0; i < split.length; i++) {
			if(localSplit.length > i) {
				update = true;
			} else {
				int localNumber = Integer.parseInt(localSplit[i]);
				int number = Integer.parseInt(split[i]);
				if(number > localNumber) {
					update = true;
				}
			}
		}
		return update;
	}

	private void extractZip(File zip, File dest) {
		byte[] buffer = new byte[1024];
		try {
			if(dest.exists()) {
				dest.delete();
			}
			dest.mkdirs();
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
			ZipEntry ze = zis.getNextEntry();

			while(ze != null) {
				String fileName = ze.getName();
				File newFile = new File(dest, fileName);
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void run() {
		ACLog.info("[Clothing] Loading clothing...");
		update();
		download();
		load();
	}
	
	public void excecute() {
		new Thread(this).start();
	}
}
