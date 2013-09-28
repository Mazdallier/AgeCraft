package elcon.mods.agecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
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
		for(ACComponent component : AgeCraft.instance.components) {
			if(component != null) {
				IACPacketHandler packetHandler = component.getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketServer(packetID, dat);
				}
			}
		}
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

	@Override
	// SERVER
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		PacketDispatcher.sendPacketToPlayer(getTechTreeAllComponentsPacket(netHandler.getPlayer().username), player);
		ACLog.info("[TechTree] Send all components to " + netHandler.getPlayer().username);
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
