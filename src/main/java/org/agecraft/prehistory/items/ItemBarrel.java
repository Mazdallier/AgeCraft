package org.agecraft.prehistory.items;

import java.util.List;

import org.agecraft.core.TreeRegistry;
import org.agecraft.prehistory.PrehistoryAge;
import org.agecraft.prehistory.blocks.BlockBarrel;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.ECUtil;
import elcon.mods.core.items.ItemBlockName;
import elcon.mods.core.lang.LanguageManager;

public class ItemBarrel extends ItemBlockName {

	private Icon icons[];
	
	public ItemBarrel(int id) {
		super(id);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockBarrel) PrehistoryAge.barrel).getLocalizedName(stack);
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
			NBTTagCompound tag = nbt.getCompoundTag("Fluid").getCompoundTag("barrel");
			FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(tag.getString("FluidName")), tag.getInteger("Amount"));
			if(tag.hasKey("Tag")) {
				fluidStack.tag = tag.getCompoundTag("Tag");
			}
			list.add(LanguageManager.getLocalization(fluidStack.getFluid().getUnlocalizedName()));
		} else if(nbt.hasKey("Stack")) {
			NBTTagCompound tag = nbt.getCompoundTag("Stack");
			ItemStack soakStack = ItemStack.loadItemStackFromNBT(tag);
			list.add(soakStack.getDisplayName());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return icons[0];
		}
		NBTTagCompound nbt = stack.getTagCompound();
		return icons[nbt.getInteger("WoodType")];
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[TreeRegistry.trees.length];
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				icons[i] = iconRegister.registerIcon("agecraft:ages/prehistory/barrel" + ECUtil.firstUpperCase(TreeRegistry.trees[i].name));
			}
		}
	}
}
