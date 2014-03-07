package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;

import org.agecraft.core.tileentities.TileEntityAnvil;

import elcon.mods.elconqore.gui.ContainerBasic;
import elcon.mods.elconqore.gui.InventoryBasic;

public class ContainerAnvil extends ContainerBasic implements IContainerTools {

	public EntityPlayer player;
	public TileEntityAnvil anvil;
	public InventoryCraftMatrix craftMatrix;
	public InventoryBasic repairMatrix;
	public InventoryCraftResult craftResult;
	
	public boolean isRepair = false;
	
	public ContainerAnvil(EntityPlayer player, TileEntityAnvil anvil, boolean isRepair) {
		this.player = player;
		this.anvil = anvil;
		this.isRepair = isRepair;
		anvil.container = this;
		craftResult = new InventoryCraftResult();
		
		addSlotToContainer(new SlotTool(anvil, 0, 15, 142, 98));

		if(isRepair) {
			repairMatrix = new InventoryBasic(this, 2, "container.anvil");
			addSlotToContainer(new Slot(repairMatrix, 0, 22, 63));
			addSlotToContainer(new Slot(repairMatrix, 1, 68, 63));
		} else {
			craftMatrix = new InventoryCraftMatrix(this, 5, 5, "container.anvil");
			addSlotToContainer(new SlotCraftingTools(player, this, craftMatrix, craftResult, 0, 138, 35));
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 5; j++) {
					addSlotToContainer(new Slot(craftMatrix, i + j * 5, 8 + i * 18, 26 + j * 18));
				}
			}
		}
		addInventoryPlayer(player.inventory, 8, 122);
		if(isRepair) {
			onCraftMatrixChanged(anvil);
		} else {
			onCraftMatrixChanged(craftMatrix);
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(anvil.xCoord + 0.5D, anvil.yCoord + 0.5D, anvil.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void damageTools() {
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		anvil.container = null;
	}
}
