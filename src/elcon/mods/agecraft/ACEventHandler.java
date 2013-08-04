package elcon.mods.agecraft;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.ICraftingHandler;
import elcon.mods.agecraft.tech.TechTreeServer;

public class ACEventHandler implements ICraftingHandler {

	@ForgeSubscribe
	public void worldLoad(WorldEvent.Load event) {
		if(event.world.isRemote) {
			return;
		}
		ACSaveHandler sm = new ACSaveHandler(event.world.getSaveHandler(), event.world);
		sm.loadTechTree();
	}

	@ForgeSubscribe
	public void worldSave(WorldEvent.Save event) {
		if(event.world.isRemote) {
			return;
		}
		ACSaveHandler sm = new ACSaveHandler(event.world.getSaveHandler(), event.world);
		sm.saveTechTree();
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		if(item.itemID == Item.paper.itemID) {
			TechTreeServer.unlockComponent("test");
		} else if(item.itemID == Block.planks.blockID) {
			TechTreeServer.unlockComponent("test2");
		} else if(item.itemID == Item.book.itemID) {
			TechTreeServer.unlockComponent("test3");
		} else if(item.itemID == Block.bookShelf.blockID) {
			TechTreeServer.unlockComponent("test4");
		} else if(item.itemID == Block.enchantmentTable.blockID) {
			TechTreeServer.unlockComponent("test5");
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
	}
}
