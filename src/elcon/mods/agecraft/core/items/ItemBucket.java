package elcon.mods.agecraft.core.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.ItemFluidContainer;
import elcon.mods.agecraft.ACCreativeTabs;

public abstract class ItemBucket extends ItemFluidContainer {

	public ItemBucket(int id) {
		super(id);
		setCapacity(FluidContainerRegistry.BUCKET_VOLUME);
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.tools);
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if(pass == 0) {
			return getFluid(stack).getFluid().getIcon(getFluid(stack));
		}
		return getIconIndex(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 0) {
			return getFluid(stack).getFluid().getColor(getFluid(stack));
		}
		return 0xFFFFFF;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return hasFluid(stack) ? 1 : 16;
	}

	public boolean hasFluid(ItemStack stack) {
		return getFluid(stack) != null;
	}
	
	public abstract List<Fluid> getFluidBlacklist();
}
