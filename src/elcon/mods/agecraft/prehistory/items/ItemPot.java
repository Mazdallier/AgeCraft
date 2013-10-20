package elcon.mods.agecraft.prehistory.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.items.ItemBlockName;
import elcon.mods.core.lang.LanguageManager;

public class ItemPot extends ItemBlockName {

	public ItemPot(int id) {
		super(id);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return;
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("Fluid")) {
			NBTTagCompound tag = nbt.getCompoundTag("Fluid").getCompoundTag("pot");
			FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(tag.getString("FluidName")), tag.getInteger("Amount"));
			if(tag.hasKey("Tag")) {
				fluidStack.tag = tag.getCompoundTag("Tag");
			}
			list.add(LanguageManager.getLocalization(fluidStack.getFluid().getUnlocalizedName()));
		} else if(nbt.hasKey("Dust")) {
			NBTTagCompound tag = nbt.getCompoundTag("Dust");
			ItemStack dustStack = ItemStack.loadItemStackFromNBT(tag);
			list.add(dustStack.getDisplayName());
		}
	}
}
