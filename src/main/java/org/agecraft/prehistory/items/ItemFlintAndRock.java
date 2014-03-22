package org.agecraft.prehistory.items;

import org.agecraft.ACCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemFlintAndRock extends Item {

	private IIcon icon;

	public ItemFlintAndRock() {
		setMaxDamage(16);
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName() {
		return "item.flintAndRock.name";
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(side == 0) {
			y--;
		}
		if(side == 1) {
			y++;
		}
		if(side == 2) {
			z--;
		}
		if(side == 3) {
			z++;
		}
		if(side == 4) {
			x--;
		}
		if(side == 5) {
			x++;
		}
		if(!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else {
			if(world.isAirBlock(x, y, z)) {
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlock(x, y, z, Blocks.fire);
			}
			stack.damageItem(1, player);
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/flintAndRock");
	}
}
