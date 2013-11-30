package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import elcon.mods.agecraft.ACPacketHandler;
import elcon.mods.agecraft.core.PlayerTradeManager.PlayerTrade;
import elcon.mods.core.gui.ContainerBasic;

public class ContainerPlayerTrade extends ContainerBasic {

	public PlayerTrade trade;
	
	public InventoryPlayerTrade inventoryPlayer1;
	public InventoryPlayerTrade inventoryPlayer2;

	public ContainerPlayerTrade otherContainer;
	
	public byte current;
	public boolean closed = false;

	public ContainerPlayerTrade(InventoryPlayer inventoryPlayer, PlayerTrade trade, byte current) {
		this.trade = trade;
		this.current = current;

		inventoryPlayer1 = new InventoryPlayerTrade(this, true);
		inventoryPlayer2 = new InventoryPlayerTrade(this, false);

		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < 3; i++) {
				addSlotToContainer(new SlotTrade(inventoryPlayer1, i + j * 3, 8 + i * 18, 18 + j * 18, true));
				
			}
		}
		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < 3; i++) {
				addSlotToContainer(new SlotTrade(inventoryPlayer2, i + j * 3, 116 + i * 18, 18 + j * 18, false));
			}
		}
		addInventoryPlayer(inventoryPlayer, 8, 86);
	}
	
	public void checkBothAccepted(EntityPlayerMP p1, EntityPlayerMP p2) {
		if(trade.accepted1 && trade.accepted2) {
			for(int i = 0; i < inventoryPlayer1.getSizeInventory(); i++) {
				ItemStack stack = inventoryPlayer1.getStackInSlotOnClosing(i);
				if(stack != null) {
					if(!p2.inventory.addItemStackToInventory(stack)) {
						p2.dropPlayerItem(stack);
					}
				}
			}
			for(int i = 0; i < inventoryPlayer2.getSizeInventory(); i++) {
				ItemStack stack = inventoryPlayer2.getStackInSlotOnClosing(i);
				if(stack != null) {
					if(!p1.inventory.addItemStackToInventory(stack)) {
						p1.dropPlayerItem(stack);
					}
				}
			}
			p1.closeScreen();
		}
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		super.onCraftMatrixChanged(inventory);
		if(otherContainer != null && inventory instanceof InventoryPlayerTrade && ((InventoryPlayerTrade) inventory).canAccess) {
			trade.accepted1 = false;
			trade.accepted2 = false;
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(trade.dimensionID);
			EntityPlayerMP p1 = (EntityPlayerMP) world.getPlayerEntityByName(trade.player1);
			EntityPlayerMP p2 = (EntityPlayerMP) world.getPlayerEntityByName(trade.player2);
			PacketDispatcher.sendPacketToPlayer(ACPacketHandler.getPlayerTradeAcceptChangePacket(trade.accepted1, trade.accepted2), (Player) p1);
			PacketDispatcher.sendPacketToPlayer(ACPacketHandler.getPlayerTradeAcceptChangePacket(trade.accepted1, trade.accepted2), (Player) p2);
			ItemStack[] stacks = new ItemStack[9];
			for(int i = 0; i < 9; i++) {
				stacks[i] = (ItemStack) inventoryItemStacks.get(i);
				SlotTrade slot = (SlotTrade) otherContainer.getSlot(9 + i);
				slot.preventChange = true;
				slot.putStack(stacks[i]);
				slot.preventChange = false;
			}
			otherContainer.detectAndSendChanges();
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		for(int i = 0; i < inventoryPlayer1.getSizeInventory(); i++) {
			ItemStack stack = inventoryPlayer1.getStackInSlotOnClosing(i);
			if(stack != null) {
				System.out.println("droppped: " + stack);
				if(!player.inventory.addItemStackToInventory(stack)) {
					player.dropPlayerItem(stack);
				}
			}
		}
		if(otherContainer != null && !closed) {
			closed = true;
			FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(trade.dimensionID).getPlayerEntityByName(current == 0 ? trade.player2 : trade.player1).closeScreen();
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotID);
		if(slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if(slotID >= 0 && slotID < 17) {
				if(!mergeItemStack(stackInSlot, 18, 54, false)) {
					return null;
				}
			} else if(slotID >= 18 && slotID < 45) {
				if(!mergeItemStack(stackInSlot, 45, 54, false)) {
					return null;
				}
			} else if(slotID >= 45 && slotID < 54) {
				if(!mergeItemStack(stackInSlot, 18, 45, false)) {
					return null;
				}
			} else if(!mergeItemStack(stackInSlot, 18, 54, false)) {
				return null;
			}
			if(stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if(stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
