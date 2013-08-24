package elcon.mods.agecraft.prehistory.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.AgeCraft;
import elcon.mods.agecraft.prehistory.PrehistoryAge;

public class ItemRock extends ItemBlock {

	private Icon icon;
	
	public ItemRock(int i) {
		super(i);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getLocalizedName(stack);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getLocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			if(player.isSneaking()) {
				if(hasTwoRocks(player.inventory)) {
					player.openGui(AgeCraft.instance, 10, world, (int) player.posX, (int) player.posY, (int) player.posZ);
					return stack;
				}
			}
		}
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/rock");
	}
	
	private boolean hasTwoRocks(InventoryPlayer inv) {
		int count = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null) {
				if(stack.itemID == PrehistoryAge.rock.itemID) {
					count += stack.stackSize;
				}
			}
		}
		return count >= 2;
	}
}
