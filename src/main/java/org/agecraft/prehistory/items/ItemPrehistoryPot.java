package org.agecraft.prehistory.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.items.ItemBlockName;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemPrehistoryPot extends ItemBlockName {

	private IIcon icon;
	private IIcon iconLid;
	
	public ItemPrehistoryPot(Block block) {
		super(block);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return icon;
		}
		NBTTagCompound nbt = stack.getTagCompound();
		return nbt.hasKey("HasLid") && nbt.getBoolean("HasLid") ? iconLid : icon;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/pot");
		iconLid = iconRegister.registerIcon("agecraft:ages/prehistory/potLid");
	}
}
