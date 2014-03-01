package org.agecraft.prehistory.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.agecraft.prehistory.PrehistoryAge;
import org.agecraft.prehistory.blocks.BlockBed;
import org.agecraft.prehistory.tileentities.TileEntityPrehistoryBed;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.items.ItemBlockName;

public class ItemPrehistoryBed extends ItemBlockName {

	private IIcon icon;
	private IIcon iconOverlay;

	public ItemPrehistoryBed(Block block) {
		super(block);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(side != 1) {
			return false;
		} else {
			y++;
			int color = 0xFFFFFF;
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Color")) {
				color = stack.getTagCompound().getInteger("Color");
			}
			BlockBed bed = (BlockBed) PrehistoryAge.bed;
			int direction = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			byte dirX = 0;
			byte dirZ = 0;
			if(direction == 0) {
				dirZ = 1;
			}
			if(direction == 1) {
				dirX = -1;
			}
			if(direction == 2) {
				dirZ = -1;
			}
			if(direction == 3) {
				dirX = 1;
			}
			if(player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x + dirX, y, z + dirZ, side, stack)) {
				if(world.isAirBlock(x, y, z) && world.isAirBlock(x + dirX, y, z + dirZ) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + dirX, y - 1, z + dirZ)) {
					TileEntityPrehistoryBed tile = new TileEntityPrehistoryBed();
					tile.direction = (byte) direction;
					tile.isFoot = true;
					tile.color = color;
					world.setBlock(x, y, z, bed, 0, 3);
					world.setTileEntity(x, y, z, tile);
					if(world.getBlock(x, y, z) == bed) {
						tile = new TileEntityPrehistoryBed();
						tile.direction = (byte) direction;
						tile.isFoot = false;
						tile.color = color;
						world.setBlock(x + dirX, y, z + dirZ, bed, 0, 3);
						world.setTileEntity(x + dirX, y, z + dirZ, tile);
					}
					stack.stackSize--;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Color")) {
			return stack.getTagCompound().getInteger("Color");
		}
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}	
	
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass) {
		if(renderPass == 2) {
			return iconOverlay;
		}
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/bed");
		iconOverlay = iconRegister.registerIcon("agecraft:ages/prehistory/bedOverlay");
	}
}
