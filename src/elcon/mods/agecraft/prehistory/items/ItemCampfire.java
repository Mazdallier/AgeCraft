package elcon.mods.agecraft.prehistory.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.items.ItemBlockName;
import elcon.mods.core.lang.LanguageManager;

public class ItemCampfire extends ItemBlockName {

	public ItemCampfire(int id) {
		super(id);
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
				int[] logs = new int[TreeRegistry.trees.length];
				StringBuilder sb = new StringBuilder();
				NBTTagList nbtList = nbt.getTagList("Logs");
				for(int i = 0; i < nbtList.tagCount(); i++) {
					ItemStack logStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbtList.tagAt(i));
					logs[logStack.getItemDamage()]++;
				}
				for(int i = 0; i < logs.length; i++) {
					if(logs[i] > 0) {
						sb.append(logs[i]);
						sb.append("x");
						sb.append(LanguageManager.getLocalization("trees." + TreeRegistry.trees[i].name));
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
