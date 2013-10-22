package elcon.mods.agecraft.core.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.core.lang.LanguageManager;

public class ItemMetalBucket extends ItemBucket {

	public ItemMetalBucket(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(false);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.metals[stack.getItemDamage()].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "metals.bucket";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		return MetalRegistry.metals[stack.getItemDamage()].bucket;
	}
	
	@Override
	public List<Fluid> getFluidBlacklist(ItemStack stack) {
		return new ArrayList<Fluid>();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasIngot) {
				list.add(new ItemStack(id, 1, i));
			}
		}
	}
}
