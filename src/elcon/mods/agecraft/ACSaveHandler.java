package elcon.mods.agecraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.ISaveHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import elcon.mods.agecraft.tech.TechTreeServer;

public class ACSaveHandler {
	
	public ISaveHandler saveHandler;
	public World world;

	public ACSaveHandler(ISaveHandler saveHandler, World world) {
		this.saveHandler = saveHandler;
		this.world = world;
	}
	
	public void loadTechTree() {
		if ((world.provider.dimensionId == 0) && (!saveHandler.getWorldDirectoryName().equalsIgnoreCase("none"))) {
			try {
				TechTreeServer.unlockedTechComponents.clear();
				File file = getSaveFile(saveHandler, world, "tech_tree.dat");
				if (file != null) {
					try {
						TechTreeServer.unlockedTechComponents.clear();
						loadTechTree(file);
					} catch (Exception e) {
						e.printStackTrace();
						File file2 = new File(new StringBuilder().append(file.getAbsolutePath()).append(".bak").toString());
						if(file2.exists()) {
							TechTreeServer.unlockedTechComponents.clear();
							loadTechTree(file2);
						} else {
							TechTreeServer.unlockedTechComponents.clear();
							file.createNewFile();
							saveTechTree();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void saveTechTree() {
		if (this.world.provider.dimensionId == 0) {
			try {
				FileOutputStream fos = new FileOutputStream(getSaveFile(saveHandler, world, "tech_tree.dat", false).getAbsolutePath());
				GZIPOutputStream gzos = new GZIPOutputStream(fos);
				ObjectOutputStream out = new ObjectOutputStream(gzos);
				out.writeObject(TechTreeServer.unlockedTechComponents);
				out.flush();
				out.close();
				gzos.close();
				fos.close();
				copyFile(getSaveFile(saveHandler, world, "tech_tree.dat", false), getSaveFile(saveHandler, world, "tech_tree.dat", true));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void loadTechTree(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		GZIPInputStream gzis = new GZIPInputStream(fis);
		ObjectInputStream in = new ObjectInputStream(gzis);
		List<String> loaded = (List<String>) in.readObject();

		TechTreeServer.unlockedTechComponents.clear();
		
		for (String key : loaded) {
			TechTreeServer.unlockedTechComponents.add(key);
		}
		ACLog.info("Loaded the Tech Tree");

		in.close();
		gzis.close();
		fis.close();
	}

	public static File getSaveFile(ISaveHandler saveHandler, World world, String name) {
		File worldDir = new File(saveHandler.getWorldDirectoryName());
		IChunkLoader loader = saveHandler.getChunkLoader(world.provider);
		if ((loader instanceof AnvilChunkLoader)) {
			worldDir = ((AnvilChunkLoader) loader).chunkSaveLocation;
		}
		File file = new File(worldDir, name);
		return file;
	}

	public File getSaveFile(ISaveHandler saveHandler, World world, String name, boolean backup) throws Exception {
		File worldDir = new File(saveHandler.getWorldDirectoryName());
		IChunkLoader loader = saveHandler.getChunkLoader(world.provider);
		if ((loader instanceof AnvilChunkLoader)) {
			worldDir = ((AnvilChunkLoader) loader).chunkSaveLocation;
		}
		return new File(worldDir, new StringBuilder().append(name).append(backup ? ".bak" : "").toString());
	}

	public void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0L, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
}
