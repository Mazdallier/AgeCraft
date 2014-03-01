package org.agecraft.prehistory.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import org.agecraft.core.registry.TreeRegistry;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.items.ItemBlockName;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemPrehistoryCampfire extends ItemBlockName {

	public ItemPrehistoryCampfire(Block block) {
		super(block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				return;
			}
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("Logs")) {
				int[] logs = new int[TreeRegistry.instance.getAll().length];
				StringBuilder sb = new StringBuilder();
				NBTTagList nbtList = nbt.getTagList("Logs", 10);
				for(int i = 0; i < nbtList.tagCount(); i++) {
					ItemStack logStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbtList.getCompoundTagAt(i));
					logs[logStack.getItemDamage()]++;
				}
				for(int i = 0; i < logs.length; i++) {
					if(logs[i] > 0) {
						sb.append(logs[i]);
						sb.append("x");
						sb.append(LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(i).name));
						if(i < (nbtList.tagCount() - 1)) {
							sb.append(", ");
						}
					}
				}
				list.add(sb.toString());
			}
		}
	}
}
