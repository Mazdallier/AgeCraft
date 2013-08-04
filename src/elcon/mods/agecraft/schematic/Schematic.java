package elcon.mods.agecraft.schematic;

import java.io.File;
import java.io.FileInputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Schematic {

	public String mod;
	public String name;
	public File dir;
	public String fileName;
	public boolean useMCDir = true;

	public NBTTagCompound nbt;

	public int[] blocks;
	public int[] data;

	public short length;
	public short width;
	public short height;
	public short[] sizes;

	public NBTTagList nbtEntities;
	public NBTTagList nbtTileEntities;

	public Schematic(String mod, String name, File dir, String fileName, boolean useMCDir) {
		this.mod = mod;
		this.name = name;
		this.dir = dir;
		this.fileName = fileName;
		this.useMCDir = useMCDir;

		blocks = null;
		data = null;
		length = 0;
		width = 0;
		height = 0;
		sizes = new short[]{length, width, height};

		SchematicAPI.addSchematic(mod, name, this);
	}

	public Schematic(String mod, String name, String dirName, String path, boolean useMCDir) {
		this(mod, name, new File(useMCDir ? Minecraft.getMinecraft().mcDataDir + dirName : dirName), path, useMCDir);
	}

	public Schematic(String mod, String name) {
		this(mod, name, new File(Minecraft.getMinecraft().mcDataDir + "/mods/" + mod + "/schematics/"), name, true);
	}
	
	public void createFromWorld(World world, int x, int y, int z) {
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < height; j++) {
				for(int k = 0; k < width; k++) {
					blocks[i + (j * height) + (k * width)] = world.getBlockId(x + i, y + j, z + k);
					data[i + (j * height) + (k * width)] = world.getBlockMetadata(x + i, y + j, z + k);
				}
			}
		}
	}

	public void generate(World world, int x, int y, int z, boolean generateAir) {
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < height; j++) {
				for(int k = 0; k < width; k++) {
					if(blocks[i + (j * height) + (k * width)] > 0 || (blocks[i + (j * height) + (k * width)] == 0 && generateAir)) {
						world.setBlock(x + i, y + j, z + k, blocks[i + (j * height) + (k * width)], data[i + (j * height) + (k * width)], 1);
					}
				}
			}
		}

		Chunk chunk;
		for(int i = 0; i < nbtEntities.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) nbtEntities.tagAt(i);

			Entity entity = EntityList.createEntityFromNBT(tag, world);

			chunk = world.getChunkFromBlockCoords((int) entity.posX, (int) entity.posZ);
			chunk.hasEntities = true;

			if(entity != null) {
				chunk.addEntity(entity);
			}
		}
		chunk = null;
		for(int i = 0; i < nbtEntities.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) nbtEntities.tagAt(i);
			TileEntity tileEntity = TileEntity.createAndLoadEntity(tag);

			chunk = world.getChunkFromBlockCoords((int) tileEntity.xCoord, (int) tileEntity.zCoord);
			chunk.hasEntities = true;
			
			if(tileEntity != null) {
				chunk.addTileEntity(tileEntity);
			}
		}
	}

	public void loadSchematic() {
		try {
			File schematic = new File(dir, fileName.contains(".schematic") ? fileName : fileName + ".schematic");

			FileInputStream fis = new FileInputStream(schematic);
			nbt = CompressedStreamTools.readCompressed(fis);

			if(nbt != null) {
				blocks = nbt.getIntArray("Blocks");
				data = nbt.getIntArray("Data");

				length = nbt.getShort("Length");
				width = nbt.getShort("Width");
				height = nbt.getShort("Height");
				sizes = new short[]{length, width, height};

				nbtEntities = nbt.getTagList("Entities");
				nbtTileEntities = nbt.getTagList("TileEntities");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void saveSchematic() {
		try {
			File schematic = new File(dir, fileName.contains(".schematic") ? fileName : fileName + ".schematic");

			if(!dir.exists()) {
				dir.mkdir();
			}
			if(!schematic.exists()) {
				schematic.createNewFile();
			}
			if(nbt == null) {
				nbt = new NBTTagCompound();

				nbt.setIntArray("Blocks", blocks);
				nbt.setIntArray("Data", data);

				nbt.setShort("Length", length);
				nbt.setShort("Width", width);
				nbt.setShort("Height", height);

				nbt.setTag("Entities", nbtEntities);
				nbt.setTag("TileEntities", nbtTileEntities);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
