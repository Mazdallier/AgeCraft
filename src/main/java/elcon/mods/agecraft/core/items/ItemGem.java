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
import elcon.mods.agecraft.core.MetalRegistry.OreType;
import elcon.mods.core.lang.LanguageManager;

public class ItemGem extends Item {

	public ItemGem(int id) {
		super(id - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.metals[stack.getItemDamage()].name);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "metals.gem";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return MetalRegistry.metals[meta].ingot;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null) {
				if(MetalRegistry.metals[i].type == OreType.GEM && MetalRegistry.metals[i].hasIngot) {
					list.add(new ItemStack(id, 1, i));
				}
			}
		}
	}
}
