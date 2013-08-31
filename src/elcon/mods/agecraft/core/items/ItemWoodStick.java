package elcon.mods.agecraft.core.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.lang.LanguageManager;

public class ItemWoodStick extends Item {

	public ItemWoodStick(int id) {
		super(id - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getLocalizedName(stack);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.trees[stack.getItemDamage()].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "trees.stick";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return TreeRegistry.trees[meta].stick;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
