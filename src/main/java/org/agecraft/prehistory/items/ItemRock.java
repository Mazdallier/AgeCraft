package org.agecraft.prehistory.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.AgeCraft;
import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRock extends ItemBlock {

	private IIcon icon;

	public ItemRock(Block block) {
		super(block);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return field_150939_a.getLocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return field_150939_a.getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName() {
		return field_150939_a.getUnlocalizedName();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			if(!world.isRemote) {
				if(hasTwoRocks(player.inventory)) {
					player.openGui(AgeCraft.instance, 20, world, (int) player.posX, (int) player.posY, (int) player.posZ);
				}
			}
			return true;
		}
		return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			if(player.isSneaking()) {
				if(hasTwoRocks(player.inventory)) {
					player.openGui(AgeCraft.instance, 30, world, (int) player.posX, (int) player.posY, (int) player.posZ);
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
	public IIcon getIconFromDamage(int i) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/rock");
	}

	private boolean hasTwoRocks(InventoryPlayer inv) {
		int count = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null) {
				if(Item.getIdFromItem(stack.getItem()) == Block.getIdFromBlock(PrehistoryAge.rock)) {
					count += stack.stackSize;
				}
			}
		}
		return count >= 2;
	}
}
