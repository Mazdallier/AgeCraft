package elcon.mods.agecraft.tech;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;
import elcon.mods.agecraft.ACPacketHandler;
import elcon.mods.agecraft.AgeCraft;

public class TechTreeServer {

	public static ArrayList<String> unlockedTechComponents = new ArrayList<String>();
	
	public static void unlockComponent(String key) {
		if((unlockedTechComponents == null) || (!unlockedTechComponents.contains(key))) {
			if(unlockedTechComponents == null) {
				unlockedTechComponents = new ArrayList();
			}
			TechTreeComponent[] t = TechTree.getTechTreeComponent(key).parents;
			if(t != null) {
				for(int i=0; i<t.length; i++) {
					if(t[i] != null) {
						if(!unlockedTechComponents.contains(t[i].key)) {
							return;
						}
					}
				}	
			}
			unlockedTechComponents.add(key);
			for(int i = 0; i < AgeCraft.proxy.getMCServer().worldServers.length; i++) {
				if(AgeCraft.proxy.getMCServer().worldServerForDimension(i) != null) {
					for(Object o :AgeCraft.proxy.getMCServer().worldServerForDimension(i).playerEntities) {
						EntityPlayerMP player = null;
						if(o instanceof EntityPlayerMP) {
							player = (EntityPlayerMP) o;
							ACPacketHandler.sendTechTreeComponentPacket(key, true, player);
						}	
					}
				}
			}
		}
	}
	
	public static void lockComponent(String key) {
		if((unlockedTechComponents == null) || (unlockedTechComponents.contains(key))) {
			if(unlockedTechComponents == null) {
				unlockedTechComponents = new ArrayList();
				return;
			}
			TechTreeComponent[] t = TechTree.getTechTreeComponent(key).parents;
			for(int i=0; i<t.length; i++) {
				if(!unlockedTechComponents.contains(t[i].key)) {
					return;
				}
			}
			unlockedTechComponents.remove(key);
			for(int i = 0; i < AgeCraft.proxy.getMCServer().worldServers.length; i++) {
				if(AgeCraft.proxy.getMCServer().worldServerForDimension(i) != null) {
					for(Object o :AgeCraft.proxy.getMCServer().worldServerForDimension(i).playerEntities) {
						EntityPlayerMP player = null;
						if(o instanceof EntityPlayerMP) {
							player = (EntityPlayerMP) o;
							ACPacketHandler.sendTechTreeComponentPacket(key, false, player);
						}	
					}
				}
			}
		}
	}
}
