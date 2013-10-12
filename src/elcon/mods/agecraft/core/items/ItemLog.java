package elcon.mods.agecraft.core.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;

public class ItemLog extends ItemBlockExtendedMetadata {

	public ItemLog(int id) {
		super(id);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(player.capabilities.isCreativeMode) {
			return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return TreeRegistry.trees[meta].log;
	}
}
