package elcon.mods.agecraft.core.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.MetalRegistry.OreType;

public class ItemIngot extends Item {

	public ItemIngot(int id) {
		super(id - 256);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(ACCreativeTabs.metals);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return MetalRegistry.metals[meta].ingot;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].type == OreType.METAL && MetalRegistry.metals[i].hasIngot) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
