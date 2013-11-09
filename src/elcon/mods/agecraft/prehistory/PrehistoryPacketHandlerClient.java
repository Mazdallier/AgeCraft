package elcon.mods.agecraft.prehistory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.IACPacketHandlerClient;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBarrel;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBed;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityPot;

@SideOnly(Side.CLIENT)
public class PrehistoryPacketHandlerClient implements IACPacketHandlerClient {

	@Override
	public void handlePacketClient(int packetID, ByteArrayDataInput dat, World world) {
		switch(packetID) {
		case 200:
			handleTileEntityCampfire(world, dat);
			break;
		case 201:
			handleTileEntityPot(world, dat);
			break;
		case 202:
			handleTileEntityBed(world, dat);
			break;
		case 203:
			handleTileEntityBarrel(world, dat);
			break;
		}
	}

	private void handleTileEntityCampfire(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCampfire();
			world.setBlockTileEntity(x, y, z, tile);
		}
		
		tile.burnTime = dat.readInt();
		tile.hasFire = dat.readBoolean();
		
		tile.logCount = dat.readInt();
		tile.currentLogIndex = dat.readInt();
		
		tile.frameDirection = dat.readByte();
		tile.frameStage = dat.readByte();
		tile.frameType[0] = dat.readInt();
		tile.frameType[1] = dat.readInt();
		tile.frameType[2] = dat.readInt();
		
		if(dat.readBoolean()) {
			tile.spitStack = new ItemStack(dat.readInt(), 1, dat.readInt());
			if(dat.readBoolean()) {
				try {
					tile.spitStack.setTagCompound((NBTTagCompound) NBTBase.readNamedTag(dat));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			tile.spitStack = null;
		}
		world.markBlockForRenderUpdate(x, y, z);
		world.updateLightByType(EnumSkyBlock.Block, x, y, z);
	}
	
	private void handleTileEntityPot(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityPot tile = (TileEntityPot) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityPot();
			world.setBlockTileEntity(x, y, z, tile);
		}
		
		tile.hasLid = dat.readBoolean();
		
		if(dat.readBoolean()) {
			tile.fluid.setFluid(new FluidStack(dat.readInt(), dat.readInt()));
		} else {
			tile.fluid.setFluid(null);
		}
		
		if(dat.readBoolean()) {
			tile.dust = new ItemStack(dat.readInt(), dat.readInt(), dat.readInt());
			if(dat.readBoolean()) {
				try {
					tile.dust.setTagCompound((NBTTagCompound) NBTBase.readNamedTag(dat));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			tile.dust = null;
		}
		world.markBlockForRenderUpdate(x, y, z);
	}
	
	private void handleTileEntityBed(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityBed tile = (TileEntityBed) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBed();
			world.setBlockTileEntity(x, y, z, tile);
		}
		
		tile.isFoot = dat.readBoolean();
		tile.direction = dat.readByte();
		tile.color = dat.readInt();
		tile.isOccupied = dat.readBoolean();
		
		world.markBlockForRenderUpdate(x, y, z);
	}
	
	private void handleTileEntityBarrel(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityBarrel tile = (TileEntityBarrel) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBarrel();
			world.setBlockTileEntity(x, y, z, tile);
		}
		
		tile.woodType = dat.readInt();
		tile.stickType = dat.readInt();
		tile.hasLid = dat.readBoolean();
		
		if(dat.readBoolean()) {
			tile.fluid.setFluid(new FluidStack(dat.readInt(), dat.readInt()));
		} else {
			tile.fluid.setFluid(null);
		}
		
		if(dat.readBoolean()) {
			tile.stack = new ItemStack(dat.readInt(), dat.readInt(), dat.readInt());
			if(dat.readBoolean()) {
				try {
					tile.stack.setTagCompound((NBTTagCompound) NBTBase.readNamedTag(dat));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			tile.stack = null;
		}
		
		world.markBlockForRenderUpdate(x, y, z);
	}
}
