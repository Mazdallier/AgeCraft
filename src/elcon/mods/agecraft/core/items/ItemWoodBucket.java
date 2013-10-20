package elcon.mods.agecraft.core.items;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.core.lang.LanguageManager;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemWoodBucket extends ItemBucket {

	public ItemWoodBucket(int id) {
		super(id);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.trees[stack.getItemDamage()].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tools.bucket";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		return TreeRegistry.trees[stack.getItemDamage()].bucket;
	}
	
	@Override
	public List<Fluid> getFluidBlacklist() {
		return Arrays.asList(FluidRegistry.LAVA);
	}
}
