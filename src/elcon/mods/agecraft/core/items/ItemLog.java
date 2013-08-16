package elcon.mods.agecraft.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemLog extends ItemBlockExtendedMetadata {

	public ItemLog(int id) {
		super(id);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		if(player.capabilities.isCreativeMode) {
			return super.onItemUse(stack, player, world, x, y, z, side, xx, yy, zz);
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return TreeRegistry.trees[meta].log;
	}
}
