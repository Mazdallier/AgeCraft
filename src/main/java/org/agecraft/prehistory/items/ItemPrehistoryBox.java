package org.agecraft.prehistory.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.prehistory.PrehistoryAge;
import org.agecraft.prehistory.blocks.BlockPrehistoryBox;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;
import elcon.mods.elconqore.items.ItemBlockName;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemPrehistoryBox extends ItemBlockName {

	private IIcon[] icons;
	
	public ItemPrehistoryBox(Block block) {
		super(block);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockPrehistoryBox) PrehistoryAge.box).getLocalizedName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			return;
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("Stacks")) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				NBTTagList tagList = nbt.getTagList("Stacks", 10);
				for(int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
					ItemStack boxStack = ItemStack.loadItemStackFromNBT(tag);
					list.add(Integer.toString(boxStack.stackSize) + "x " + boxStack.getDisplayName());
				}
			} else {
				list.add(EnumChatFormatting.ITALIC + LanguageManager.getLocalization("gui.showdetails") + EnumChatFormatting.RESET);
			}
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
			return icons[0];
		}
		NBTTagCompound nbt = stack.getTagCompound();
		return icons[nbt.getInteger("WoodType")];
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[TreeRegistry.instance.getAll().length];
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				icons[i] = iconRegister.registerIcon("agecraft:ages/prehistory/box" + EQUtil.firstUpperCase(TreeRegistry.instance.get(i).name));
			}
		}
	}
}
