package org.agecraft.core.clothing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.agecraft.AgeCraft;
import org.agecraft.core.clothing.ClothingRegistry.ClothingType;
import org.apache.commons.io.FileUtils;

import elcon.mods.elconqore.ElConQore;

public class ClothingUpdater implements Runnable {

	public static class ClothingCategoryVersion {

		public String name;
		public String version;

		public ClothingCategoryVersion(String name, String version) {
			this.name = name;
			this.version = version;
		}
	}
	
	public static ClothingUpdater instance;

	public HashMap<String, ClothingCategoryVersion> localVersions = new HashMap<String, ClothingCategoryVersion>();
	public HashMap<String, ClothingCategoryVersion> versions = new HashMap<String, ClothingCategoryVersion>();
	public LinkedList<ClothingCategory> downloadCategories = new LinkedList<ClothingCategory>();
	public LinkedList<ClothingCategory> localCategories = new LinkedList<ClothingCategory>();
	public LinkedList<String> previousLines = new LinkedList<String>();
	public File clothingDir;

	public ClothingUpdater(File clothingDir) {
		this.clothingDir = clothingDir;
		
		instance = this;
	}
	
	public void loadLocalCategories() {
		File file = new File(ElConQore.minecraftDir, "config/clothing-categories.txt");
		if(file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while((line = reader.readLine()) != null) {
					if(line.length() > 0 && !line.startsWith("#")) {
						String[] split = line.split("=");
						if(ClothingRegistry.getClothingCategory(split[0]) == null) {
							ClothingCategory category = new ClothingCategory(split[0], split[1], split[2]);
							ClothingRegistry.registerClothingCategory(category);
							localCategories.add(category);
						}
					}
				}
				reader.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("### Clothing Categories ### \n");
				writer.write("# Format: <name>=<version url>=<update url> \n");
				writer.write("# \n");
				writer.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveLocalCategories() {
		File file = new File(ElConQore.minecraftDir, "config/clothing-categories.txt");
		try {
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write("### Clothing Categories ### \n");
			writer.write("# Format: <name>=<version url>=<update url> \n");
			writer.write("# \n");
			for(ClothingCategory category : localCategories) {
				writer.write(category.name + "=" + category.versionURL + "=" + category.updateURL);
				writer.write("\n");
			}
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadCateogries(List<ClothingCategory> categories) {
		for(ClothingCategory category : categories) {
			try {
				AgeCraft.log.info("[Clothing] Updating category " + category.name + " to version " + versions.get(category.name).version);
				File clothingZip = new File(clothingDir, category.name + ".zip");
				clothingZip.createNewFile();
				FileUtils.copyURLToFile(new URL(category.updateURL), clothingZip);
				AgeCraft.log.info("[Clothing] Downloaded " + clothingZip.getName() + " from " + category.updateURL);
				if(clothingZip.exists()) {
					extractZip(clothingZip, new File(clothingDir, File.separator + category.name), true);
					AgeCraft.log.info("[Clothing] Extracted " + clothingZip.getName() + " to " + new File(clothingDir, File.separator + category.name).getAbsolutePath());
				}
				clothingZip.delete();
				previousLines.add(category.name + "=" + versions.get(category.name).version);
			} catch(Exception e) {
				e.printStackTrace();
			}
			for(String url : category.expansionURLs) {
				try {
					File clothingZip = new File(clothingDir, category.name + ".zip");
					clothingZip.createNewFile();
					FileUtils.copyURLToFile(new URL(url), clothingZip);
					AgeCraft.log.info("[Clothing] Downloaded expansion " + clothingZip.getName() + " from " + url);
					if(clothingZip.exists()) {
						extractZip(clothingZip, new File(clothingDir, File.separator + category.name), false);
						AgeCraft.log.info("[Clothing] Extracted expansion " + clothingZip.getName() + " to " + new File(clothingDir, File.separator + category.name).getAbsolutePath());
					}
					clothingZip.delete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(!previousLines.isEmpty()) {
			try {
				File file = new File(ElConQore.minecraftDir, "clothing_versions.dat");
				if(file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				for(String line : previousLines) {
					writer.write(line);
					writer.write("\n");
				}
				writer.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
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
				for(ClothingType type : ClothingRegistry.types.values()) {
					if(type != null) {
						File typeDir = new File(dirs[i], File.separator + type.name);
						if(typeDir.exists()) {
							File[] files = typeDir.listFiles();
							for(int k = 0; k < files.length; k++) {
								String[] split = files[k].getName().replaceAll(".png", "").split("_");
								String clothingColor = split[split.length - 1];
								String clothingName = files[k].getName().replaceAll("_" + clothingColor + ".png", "");
								Clothing clothing = category.getClothing(type, clothingName);
								if(clothing == null) {
									clothing = new Clothing(clothingName, type, category);
									category.registerClothing(clothing);
									clothingCount++;
								}
								clothing.enableColor(Integer.parseInt(clothingColor));
								colorCount++;
							}
						}
					}
				}
				File configFile = new File(dirs[i], category.name + ".config");
				if(configFile.exists()) {
					try {
						BufferedReader reader = new BufferedReader(new FileReader(configFile));
						String line = "";
						while((line = reader.readLine()) != null) {
							if(!line.startsWith("#") && line.length() > 1) {
								String[] split = line.split("=");
								if(split[0].equals("lockedByDefault")) {
									category.defaultUnlocked = split[1].equals("false");
								} else if(split[0].equals("hideIfLocked")) {
									category.hideIfLocked = split[1].equals("true");
								} else {
									String[] clothingSplit = split[0].split("\\.");
									Clothing clothing = category.clothing.get(ClothingRegistry.getClothingType(clothingSplit[0])).get(clothingSplit[1]);
									clothing.price = Integer.parseInt(split[1]);
									clothing.defaultUnlocked = split[2].equals("false");
									clothing.hideIfLocked = split[3].equals("true");
								}
							}
						}
						reader.close();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				AgeCraft.log.info("[Clothing] Loaded clothing category: " + category.name);
			}
			AgeCraft.log.info("[Clothing] Loaded " + categoryCount + " categories with " + clothingCount + " clothing pieces with a total of " + colorCount + " colors. (Average: " + (clothingCount == 0 ? 0 : (colorCount / clothingCount)) + " colors/clothing piece)");
		}
	}

	public void update() {
		if(!clothingDir.exists()) {
			clothingDir.mkdirs();
		}
		File file = new File(ElConQore.minecraftDir, "clothing_versions.dat");
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
		LinkedList<String> versionURLsChecked = new LinkedList<String>();
		for(ClothingCategory category : ClothingRegistry.categories.values()) {
			if(category != null) {
				AgeCraft.log.info("[Clothing] Checking version for category: " + category.name);
				if(!versionURLsChecked.contains(category.versionURL)) {
					try {
						File tempFile = new File(ElConQore.minecraftDir, "clothing_version_temp.dat");
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
		previousLines.clear();
		LinkedList<String> lines = new LinkedList<String>();
		for(ClothingCategory category : downloadCategories) {
			try {
				AgeCraft.log.info("[Clothing] Updating category " + category.name + " to version " + versions.get(category.name).version);
				File clothingZip = new File(clothingDir, category.name + ".zip");
				clothingZip.createNewFile();
				FileUtils.copyURLToFile(new URL(category.updateURL), clothingZip);
				AgeCraft.log.info("[Clothing] Downloaded " + clothingZip.getName() + " from " + category.updateURL);
				if(clothingZip.exists()) {
					extractZip(clothingZip, new File(clothingDir, File.separator + category.name), true);
					AgeCraft.log.info("[Clothing] Extracted " + clothingZip.getName() + " to " + new File(clothingDir, File.separator + category.name).getAbsolutePath());
				}
				clothingZip.delete();
				lines.add(category.name + "=" + versions.get(category.name).version);
			} catch(Exception e) {
				e.printStackTrace();
			}
			for(String url : category.expansionURLs) {
				try {
					File clothingZip = new File(clothingDir, category.name + ".zip");
					clothingZip.createNewFile();
					FileUtils.copyURLToFile(new URL(url), clothingZip);
					AgeCraft.log.info("[Clothing] Downloaded expansion " + clothingZip.getName() + " from " + url);
					if(clothingZip.exists()) {
						extractZip(clothingZip, new File(clothingDir, File.separator + category.name), false);
						AgeCraft.log.info("[Clothing] Extracted expansion " + clothingZip.getName() + " to " + new File(clothingDir, File.separator + category.name).getAbsolutePath());
					}
					clothingZip.delete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(!lines.isEmpty()) {
			previousLines.addAll(lines);
			try {
				File file = new File(ElConQore.minecraftDir, "clothing_versions.dat");
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

	private void extractZip(File zip, File dest, boolean deleteDest) {
		byte[] buffer = new byte[1024];
		try {
			if(deleteDest && dest.exists()) {
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
		AgeCraft.log.info("[Clothing] Loading clothing...");
		loadLocalCategories();
		update();
		download();
		load();
	}
}
