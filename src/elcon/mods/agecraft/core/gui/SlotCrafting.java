package elcon.mods.agecraft.core.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class SlotCrafting extends Slot {

	private InventoryCraftMatrix craftMatrix;
	private EntityPlayer player;
	private int amountCrafted;

	public SlotCrafting(EntityPlayer player, InventoryCraftMatrix craftMatrix, IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		this.player = player;
		this.craftMatrix = craftMatrix;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if(getHasStack()) {
			amountCrafted += Math.min(amount, getStack().stackSize);
		}
		return super.decrStackSize(amount);
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		amountCrafted += amount;
		onCrafting(stack);
	}

	@Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(player.worldObj, player, amountCrafted);
		amountCrafted = 0;
		if(stack.itemID == Block.workbench.blockID) {
			player.addStat(AchievementList.buildWorkBench, 1);
		} else if(stack.itemID == Item.pickaxeWood.itemID) {
			player.addStat(AchievementList.buildPickaxe, 1);
		} else if(stack.itemID == Block.furnaceIdle.blockID) {
			player.addStat(AchievementList.buildFurnace, 1);
		} else if(stack.itemID == Item.hoeWood.itemID) {
			player.addStat(AchievementList.buildHoe, 1);
		} else if(stack.itemID == Item.bread.itemID) {
			player.addStat(AchievementList.makeBread, 1);
		} else if(stack.itemID == Item.cake.itemID) {
			player.addStat(AchievementList.bakeCake, 1);
		} else if(stack.itemID == Item.pickaxeStone.itemID) {
			player.addStat(AchievementList.buildBetterPickaxe, 1);
		} else if(stack.itemID == Item.swordWood.itemID) {
			player.addStat(AchievementList.buildSword, 1);
		} else if(stack.itemID == Block.enchantmentTable.blockID) {
			player.addStat(AchievementList.enchantments, 1);
		} else if(stack.itemID == Block.bookShelf.blockID) {
			player.addStat(AchievementList.bookcase, 1);
		}
	}

	@Override
	public void onPickupFromSlot(EntityPlayer currentPlayer, ItemStack stack) {
		GameRegistry.onItemCrafted(currentPlayer, stack, craftMatrix);
		onCrafting(stack);
		for(int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
			ItemStack stackInSlot = craftMatrix.getStackInSlot(i);
			if(stackInSlot != null) {
				craftMatrix.decrStackSize(i, 1);
				if(stackInSlot.getItem().hasContainerItem()) {
					ItemStack stackContainer = stackInSlot.getItem().getContainerItemStack(stackInSlot);
					if(stackContainer.isItemStackDamageable() && stackContainer.getItemDamage() > stackContainer.getMaxDamage()) {
						MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(currentPlayer, stackContainer));
						stackContainer = null;
					}
					if(stackContainer != null && (!stackInSlot.getItem().doesContainerItemLeaveCraftingGrid(stackInSlot) || !player.inventory.addItemStackToInventory(stackContainer))) {
						if(craftMatrix.getStackInSlot(i) == null) {
							craftMatrix.setInventorySlotContents(i, stackContainer);
						} else {
							currentPlayer.dropPlayerItem(stackContainer);
						}
					}
				}
			}
		}
	}
}
