package elcon.mods.agecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothing.ClothingPiece;
import elcon.mods.agecraft.core.clothing.PlayerClothingServer;
import elcon.mods.agecraft.core.tech.TechTreeServer;

public class ACPacketHandler implements IPacketHandler, IConnectionHandler {
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int packetID = dat.readInt();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.SERVER) {
			handlePacketServer(packetID, dat);
		}
	}
	
	private void handlePacketServer(int packetID, ByteArrayDataInput dat) {
		switch(packetID) {
		case 75:
			handleTradePacket(dat);
		}
		
		for(ACComponent component : AgeCraft.instance.components) {
			if(component != null) {
				IACPacketHandler packetHandler = component.getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketServer(packetID, dat);
				}
			}
		}
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				IACPacketHandler packetHandler = Age.ages[i].getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketServer(packetID, dat);
				}
			}
		}
	}
	
	private void handleTradePacket(ByteArrayDataInput dat) {
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dat.readInt());
		EntityPlayer p1 = world.getPlayerEntityByName(dat.readUTF());
		EntityPlayer p2 = world.getPlayerEntityByName(dat.readUTF());
		p1.openGui(AgeCraft.instance, 1, world, (int) p1.posX, (int) p1.posY, (int) p1.posZ);
		p2.openGui(AgeCraft.instance, 1, world, (int) p2.posX, (int) p2.posY, (int) p2.posZ);
	}

	public static Packet getTechTreeComponentPacket(String player, String pageName, String name, boolean unlocked) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(0);
			dos.writeUTF(player);
			dos.writeBoolean(unlocked);
			dos.writeUTF(pageName);
			dos.writeUTF(name);
			dos.close();
			packet.channel = "ACTech";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Packet getTechTreeAllComponentsPacket(String player) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(1);
			if(TechTreeServer.players.containsKey(player)) {
				HashMap<String, ArrayList<String>> pages = TechTreeServer.players.get(player);
				dos.writeInt(pages.size());
				for(String pageName : pages.keySet()) {
					ArrayList<String> components = pages.get(pageName);
					dos.writeUTF(pageName);
					dos.writeInt(components.size());
					for(String name : components) {
						dos.writeUTF(name);
					}
				}
			} else {
				dos.writeInt(0);
			}
			dos.close();
			packet.channel = "ACTech";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Packet getClothingUpdatePacket(PlayerClothing clothing) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(2);
			dos.writeUTF(clothing.player);
			dos.writeInt(clothing.clothingPiecesOwned.size());
			for(int typeID : clothing.clothingPiecesOwned.keySet()) {
				ArrayList<ClothingPiece> pieces = clothing.clothingPiecesOwned.get(typeID);
				dos.writeInt(pieces.size());
				for(ClothingPiece piece : pieces) {
					dos.writeInt(piece.categoryID);
					dos.writeInt(piece.clothingID);
					for(int i = 0; i < 16; i++) {
						dos.writeBoolean(piece.colors[i]);
					}
					if(clothing.clothingPiecesWorn.containsValue(piece)) {
						dos.writeBoolean(true);
						dos.writeInt(clothing.clothingPiecesWornColor.get(typeID));
					} else {
						dos.writeBoolean(false);
					}
				}
			}			
			dos.close();
			packet.channel = "ACClothing";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Packet getClothingAllUpdatePacket() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(3);
			dos.writeInt(PlayerClothingServer.players.size());
			for(PlayerClothing clothing : PlayerClothingServer.players.values()) {
				dos.writeUTF(clothing.player);
				dos.writeInt(clothing.clothingPiecesOwned.size());
				for(int typeID : clothing.clothingPiecesOwned.keySet()) {
					ArrayList<ClothingPiece> pieces = clothing.clothingPiecesOwned.get(typeID);
					dos.writeInt(pieces.size());
					for(ClothingPiece piece : pieces) {
						dos.writeInt(piece.categoryID);
						dos.writeInt(piece.clothingID);
						for(int i = 0; i < 16; i++) {
							dos.writeBoolean(piece.colors[i]);
						}
						if(clothing.clothingPiecesWorn.containsValue(piece)) {
							dos.writeBoolean(true);
							dos.writeInt(clothing.clothingPiecesWornColor.get(typeID));
						} else {
							dos.writeBoolean(false);
						}
					}
				}
			}
			dos.close();
			packet.channel = "ACClothing";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	// SERVER
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		PacketDispatcher.sendPacketToPlayer(getTechTreeAllComponentsPacket(netHandler.getPlayer().username), player);
		ACLog.info("[TechTree] Send all components to " + netHandler.getPlayer().username);
		
		PlayerClothingServer.createDefaultClothing(netHandler.getPlayer().username);
		PacketDispatcher.sendPacketToPlayer(getClothingAllUpdatePacket(), player);
		PacketDispatcher.sendPacketToAllPlayers(getClothingUpdatePacket(PlayerClothingServer.getPlayerClothing(netHandler.getPlayer().username)));
		ACLog.info("[Clothing] Send all clothing to " + netHandler.getPlayer().username);
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
