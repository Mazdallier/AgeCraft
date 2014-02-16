package org.agecraft;

import java.util.ArrayList;

import org.agecraft.core.RankManager;
import org.agecraft.core.tech.TechTree;
import org.agecraft.core.tech.TechTreeComponent;
import org.agecraft.core.tech.TechTreeServer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.ICraftingHandler;

public class ACEventHandler implements ICraftingHandler {

	@ForgeSubscribe
	public void onPlayerNameFormat(PlayerEvent.NameFormat event) {
		if(RankManager.hasRank(event.username)) {
			event.displayname = RankManager.getPrefix(event.username) + event.displayname + RankManager.getPostfix(event.username) + EnumChatFormatting.RESET;
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
