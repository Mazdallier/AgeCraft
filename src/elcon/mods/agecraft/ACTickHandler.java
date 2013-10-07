package elcon.mods.agecraft;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ACTickHandler implements ITickHandler {
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.contains(TickType.SERVER)) {
			for(WorldServer world : FMLCommonHandler.instance().getMinecraftServerInstance().worldServers) {
				for(EntityPlayer player : (List<EntityPlayer>) world.playerEntities) {
					if(player.inventoryContainer != null && player.inventoryContainer instanceof ContainerPlayer) {
						player.openGui(AgeCraft.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return null;
	}

}
