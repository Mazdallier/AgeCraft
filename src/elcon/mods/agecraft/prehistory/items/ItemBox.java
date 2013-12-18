package elcon.mods.agecraft.prehistory.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.prehistory.PrehistoryAge;
import elcon.mods.agecraft.prehistory.blocks.BlockBox;
import elcon.mods.core.ECUtil;
import elcon.mods.core.items.ItemBlockName;
import elcon.mods.core.lang.LanguageManager;

public class ItemBox extends ItemBlockName {

	private Icon[] icons;
	
	public ItemBox(int id) {
		super(id);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockBox) PrehistoryAge.box).getLocalizedName(stack);
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
				NBTTagList tagList = nbt.getTagList("Stacks");
				for(int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
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
				icons[i] = iconRegister.registerIcon("agecraft:ages/prehistory/box" + ECUtil.firstUpperCase(TreeRegistry.trees[i].name));
			}
		}
	}
}
