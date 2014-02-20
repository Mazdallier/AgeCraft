package org.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.agecraft.core.registry.TreeRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.items.ItemBlockExtendedMetadata;
import elcon.mods.elconqore.tileentities.TileEntityMetadata;

public class ItemWoodDoor extends ItemBlockExtendedMetadata {

	private IIcon[] icons = new IIcon[4];
	private IIcon iconOverlay;

	public ItemWoodDoor(Block block) {
		super(block);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		int direction = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;

		byte offsetX = 0;
		byte offsetZ = 0;
		if(direction == 0) {
			offsetZ = 1;
		}
		if(direction == 1) {
			offsetX = -1;
		}
		if(direction == 2) {
			offsetZ = -1;
		}
		if(direction == 3) {
			offsetX = 1;
		}

		int hasSolidLeft = (world.isBlockNormalCubeDefault(x - offsetX, y, z - offsetZ, false) ? 1 : 0) + (world.isBlockNormalCubeDefault(x - offsetX, y + 1, z - offsetZ, false) ? 1 : 0);
		int hasSolidRight = (world.isBlockNormalCubeDefault(x + offsetX, y, z + offsetZ, false) ? 1 : 0) + (world.isBlockNormalCubeDefault(x + offsetX, y + 1, z + offsetZ, false) ? 1 : 0);
		boolean hasDoorLeft = world.getBlock(x - offsetX, y, z - offsetZ) == field_150939_a || world.getBlock(x - offsetX, y + 1, z - offsetZ) == field_150939_a;
		boolean hasDoorRight = world.getBlock(x + offsetX, y, z + offsetZ) == field_150939_a || world.getBlock(x + offsetX, y + 1, z + offsetZ) == field_150939_a;
		boolean flipped = false;

		if(hasDoorLeft && !hasDoorRight) {
			flipped = true;
		} else if(hasSolidRight > hasSolidLeft) {
			flipped = true;
		}

		world.setBlock(x, y, z, field_150939_a, 0, 2);
		TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
		if(tile != null) {
			tile.setTileMetadata(direction | stack.getItemDamage());
		}

		world.setBlock(x, y + 1, z, field_150939_a, 0, 2);
		TileEntityMetadata tile2 = (TileEntityMetadata) world.getTileEntity(x, y + 1, z);
		if(tile2 != null) {
			tile2.setTileMetadata((8 | (flipped ? 1 : 0)) | stack.getItemDamage());
		}
		world.markBlockForUpdate(x, y, z);
		world.markBlockForUpdate(x, y + 1, z);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return renderPass == 0 ? TreeRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 127)) / 128).woodColor : super.getColorFromItemStack(stack, renderPass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int renderPass) {
		return renderPass == 1 ? iconOverlay : icons[(meta & 96) / 32];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return getIconFromDamageForRenderPass(meta, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String[] doorTypes = new String[]{"Standard", "Solid", "Double", "Full"};
		for(int i = 0; i < doorTypes.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i]);
		}
		iconOverlay = iconRegister.registerIcon("agecraft:door/wood/doorOverlay");
	}
}
