package elcon.mods.agecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.tileentities.TileEntityDNA;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;
import elcon.mods.agecraft.core.tileentities.TileEntityNBT;
import elcon.mods.agecraft.tech.TechTreeClient;
import elcon.mods.agecraft.tech.TechTreeServer;

public class ACPacketHandler implements IPacketHandler, IConnectionHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int packetID = dat.readInt();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			handlePacketClient(packetID, dat);
		} else if(side == Side.SERVER) {
			handlePacketServer(packetID, dat);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void handlePacketClient(int packetID, ByteArrayDataInput dat) {
		World world = Minecraft.getMinecraft().theWorld;
		
		switch(packetID) {
		case 1:
			handleTechTreeComponentPacket(dat);
			return;
		case 2:
			handleAllTechTreeComponentsPacket(dat);
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
				IACPacketHandler packetHandler = component.getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketClient(packetID, dat, world);
				}
			}
		}
	}
	
	private void handlePacketServer(int packetID, ByteArrayDataInput dat) {
		for(ACComponent component : AgeCraft.instance.components) {
			if(component != null) {
				IACPacketHandler packetHandler = component.getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketServer(packetID, dat);
				}
			}
		}
	}

	private void handleTechTreeComponentPacket(ByteArrayDataInput dat) {
		short size = dat.readShort();
		StringBuilder var3 = new StringBuilder();
		for(int var4 = 0; var4 < size; var4++) {
			var3.append(dat.readChar());
		}
		String key = var3.toString();
		boolean unlock = dat.readBoolean();

		if(unlock) {
			TechTreeClient.unlockComponent(key);
		} else {
			TechTreeClient.lockComponent(key);
		}
	}

	public static void sendTechTreeComponentPacket(String key, boolean unlock, EntityPlayerMP player) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(1);
			dos.writeShort(key.length());
			dos.writeChars(key);
			dos.writeBoolean(unlock);
			dos.close();
			packet.channel = "ACTech";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			player.playerNetServerHandler.sendPacketToPlayer(packet);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void handleAllTechTreeComponentsPacket(ByteArrayDataInput dat) {
		short size = dat.readShort();
		ArrayList<String> ll = new ArrayList<String>();
		for(int a = 0; a < size; a++) {
			short ss = dat.readShort();
			StringBuilder var3 = new StringBuilder();
			for(int var4 = 0; var4 < ss; var4++) {
				var3.append(dat.readChar());
			}
			ll.add(var3.toString());
		}
		for(String key : ll) {
			TechTreeClient.unlockComponentStartup(key);
		}
	}

	public static void sendAllTechTreeComponentsPacket(EntityPlayerMP player) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(2);
			ArrayList<String> ll = TechTreeServer.unlockedTechComponents;
			if((ll != null) && (ll.size() > 0)) {
				dos.writeShort(ll.size());
				for(String s : ll) {
					if(s != null) {
						dos.writeShort(s.length());
						dos.writeChars(s);
					}
				}
				dos.close();
				packet.channel = "ACTech";
				packet.data = bos.toByteArray();
				packet.length = bos.size();
				packet.isChunkDataPacket = false;
				player.playerNetServerHandler.sendPacketToPlayer(packet);
			}
		} catch(IOException e) {
			e.printStackTrace();
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
		tile.metadata = dat.readInt();
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	// SERVER
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		try {
			sendAllTechTreeComponentsPacket((EntityPlayerMP) netHandler.getPlayer());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	// SERVER
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		return null;
	}

	@Override
	// CLIENT
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
	}

	@Override
	// CLIENT
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
	}

	@Override
	// BOTH
	public void connectionClosed(INetworkManager manager) {
	}

	@Override
	// CLIENT
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
	}
}
