package org.agecraft.prehistory;

import org.agecraft.IACPacketHandlerClient;
import org.agecraft.prehistory.tileentities.TileEntityBarrel;
import org.agecraft.prehistory.tileentities.TileEntityBed;
import org.agecraft.prehistory.tileentities.TileEntityBox;
import org.agecraft.prehistory.tileentities.TileEntityCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPot;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		case 204:
			handleTileEntityBox(world, dat);
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
		
		try {
			tile.spitStack = Packet.readItemStack(dat);
		} catch(Exception e) {
			e.printStackTrace();
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
			FluidStack fluidStack = new FluidStack(dat.readInt(), dat.readInt());
			tile.fluid.setFluid(fluidStack);
			if(dat.readBoolean()) {
				try {
					fluidStack.tag = (NBTTagCompound) NBTBase.readNamedTag(dat);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			tile.fluid.setFluid(null);
		}
		try {
			tile.dust = Packet.readItemStack(dat);
		} catch(Exception e) {
			e.printStackTrace();
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
			FluidStack fluidStack = new FluidStack(dat.readInt(), dat.readInt());
			tile.fluid.setFluid(fluidStack);
			if(dat.readBoolean()) {
				try {
					fluidStack.tag = (NBTTagCompound) NBTBase.readNamedTag(dat);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			tile.fluid.setFluid(null);
		}
		try {
			tile.stack = Packet.readItemStack(dat);
		} catch(Exception e) {
			e.printStackTrace();
		}		
		world.markBlockForRenderUpdate(x, y, z);
	}
	
	private void handleTileEntityBox(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityBox tile = (TileEntityBox) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBox();
			world.setBlockTileEntity(x, y, z, tile);
		}
		
		tile.woodType = dat.readInt();
		tile.hasLid = dat.readBoolean();
		
		for(int i = 0; i < tile.stacks.length; i++) {
			try {
				tile.stacks[i] = Packet.readItemStack(dat);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
