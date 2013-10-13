package elcon.mods.agecraft.core.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSaplingDNA extends ItemBlockName {

	public ItemSaplingDNA(int id) {
		super(id);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(player.isSneaking()) {
			//TODO: add information based on DNA
		}
	}
}
