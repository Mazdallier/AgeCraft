package elcon.mods.agecraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.tech.TechTreeClient;
import elcon.mods.agecraft.core.tileentities.TileEntityDNA;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;
import elcon.mods.agecraft.core.tileentities.TileEntityNBT;

@SideOnly(Side.CLIENT)
public class ACPacketHandlerClient implements IPacketHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int packetID = dat.readInt();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			handlePacketClient(packetID, dat);
		}
	}
	
	private void handlePacketClient(int packetID, ByteArrayDataInput dat) {
		World world = (World) Minecraft.getMinecraft().theWorld;
		
		switch(packetID) {
		case 0:
			handleTechTreeComponent(dat);
			return;
		case 1:
			handleTechTreeAllComponents(dat);
			return;
		case 90:
			handleTileEntityNBT(world, dat);
			break;
		case 91:
			handleTileEntityDNA(world, dat);
			break;
		case 92:
			handleTileEntityMetadata(world, dat);
			break;
		}	
		
		for(ACComponent component : AgeCraft.instance.components) {
			if(component != null) {
				IACPacketHandlerClient packetHandler = component.getPacketHandlerClient();
				if(packetHandler != null) {
					packetHandler.handlePacketClient(packetID, dat, world);
				}
			}
		}
	}
	
	private void handleTechTreeComponent(ByteArrayDataInput dat) {
		boolean unlocked = dat.readBoolean();
		String player = dat.readUTF();
		if(Minecraft.getMinecraft().thePlayer.username.equals(player)) {
			if(unlocked) {
				TechTreeClient.unlockComponent(dat.readUTF(), dat.readUTF());
			} else {
				TechTreeClient.lockComponent(dat.readUTF(), dat.readUTF());
			}
		}
	}
	
	private void handleTechTreeAllComponents(ByteArrayDataInput dat) {
		int pages = dat.readInt();
		for(int i = 0; i < pages; i++) {
			String pageName = dat.readUTF();
			int components = dat.readInt();
			for(int j = 0; j < components; j++) {
				TechTreeClient.unlockComponent(pageName, dat.readUTF());
			}
		}
	}
	
	private void handleTileEntityNBT(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		TileEntityNBT tile = (TileEntityNBT) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = (TileEntityNBT) Block.blocksList[world.getBlockId(x, y, z)].createTileEntity(world, world.getBlockMetadata(x, y, z));
			world.setBlockTileEntity(x, y, z, tile);
		}
		NBTTagCompound nbt = new NBTTagCompound();
		try {
			NBTBase nbtbase;
			while((nbtbase = NBTBase.readNamedTag(dat)).getId() != 0) {
				nbt.setTag(nbtbase.getName(), nbtbase);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		tile.nbt = nbt;
		world.markBlockForUpdate(x, y, z);
	}
	
	private void handleTileEntityDNA(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		TileEntityDNA tile = (TileEntityDNA) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = (TileEntityDNA) Block.blocksList[world.getBlockId(x, y, z)].createTileEntity(world, world.getBlockMetadata(x, y, z));
			world.setBlockTileEntity(x, y, z, tile);
		}
		NBTTagCompound nbt = new NBTTagCompound();
		try {
			NBTBase nbtbase;
			while((nbtbase = NBTBase.readNamedTag(dat)).getId() != 0) {
				nbt.setTag(nbtbase.getName(), nbtbase);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		tile.nbt = nbt;
		tile.getDNA().readFromNBT(tile.nbt);
		world.markBlockForUpdate(x, y, z);
	}
	
	private void handleTileEntityMetadata(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityMetadata();
			world.setBlockTileEntity(x, y, z, tile);
		}
		tile.setTileMetadata(dat.readInt());
		world.markBlockForUpdate(x, y, z);
	}
}
