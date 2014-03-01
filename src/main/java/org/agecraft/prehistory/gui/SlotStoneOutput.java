package org.agecraft.prehistory.gui;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;

public class SlotStoneOutput extends Slot {

	public SlotStoneOutput(IInventory inv, int par2, int par3, int par4) {
		super(inv, par2, par3, par4);
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			closeScreenClient();
		} else {
			player.openContainer = player.inventoryContainer;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void closeScreenClient() {
		((EntityClientPlayerMP) EQUtilClient.getPlayer()).closeScreen();
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
}
