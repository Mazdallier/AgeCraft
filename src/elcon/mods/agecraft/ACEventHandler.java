package elcon.mods.agecraft;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.ICraftingHandler;
import elcon.mods.agecraft.core.RankManager;
import elcon.mods.agecraft.core.tech.TechTree;
import elcon.mods.agecraft.core.tech.TechTreeComponent;
import elcon.mods.agecraft.core.tech.TechTreeServer;

public class ACEventHandler implements ICraftingHandler {

	@ForgeSubscribe
	public void onPlayerNameFormat(PlayerEvent.NameFormat event) {
		if(RankManager.hasRank(event.username)) {
			event.displayname = RankManager.getPrefix(event.username) + event.displayname + RankManager.getPostfix(event.username);
		}
	}
	
	@Override
	public void onCrafting(EntityPlayer player, ItemStack stack, IInventory craftMatrix) {
		if(!player.worldObj.isRemote) {
			for(ArrayList<TechTreeComponent> components : TechTree.pages.values()) {
				for(TechTreeComponent component : components) {
					if(ACUtil.areItemStacksEqualNoSizeNoTags(component.stack, stack)) {
						TechTreeServer.unlockComponent(player.username, component.pageName, component.name);
					}
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack stack) {
	}
}
