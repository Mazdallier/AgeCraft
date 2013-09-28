package elcon.mods.agecraft.core.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class ItemWoodDoor extends ItemBlockExtendedMetadata {

	private Icon[] icons = new Icon[4];
	private Icon iconOverlay;

	public ItemWoodDoor(int id) {
		super(id);
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

		int hasSolidLeft = (world.isBlockNormalCube(x - offsetX, y, z - offsetZ) ? 1 : 0) + (world.isBlockNormalCube(x - offsetX, y + 1, z - offsetZ) ? 1 : 0);
		int hasSolidRight = (world.isBlockNormalCube(x + offsetX, y, z + offsetZ) ? 1 : 0) + (world.isBlockNormalCube(x + offsetX, y + 1, z + offsetZ) ? 1 : 0);
		boolean hasDoorLeft = world.getBlockId(x - offsetX, y, z - offsetZ) == getBlockID() || world.getBlockId(x - offsetX, y + 1, z - offsetZ) == getBlockID();
		boolean hasDoorRight = world.getBlockId(x + offsetX, y, z + offsetZ) == getBlockID() || world.getBlockId(x + offsetX, y + 1, z + offsetZ) == getBlockID();
		boolean flipped = false;

		if(hasDoorLeft && !hasDoorRight) {
			flipped = true;
		} else if(hasSolidRight > hasSolidLeft) {
			flipped = true;
		}

		world.setBlock(x, y, z, getBlockID(), 0, 2);
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			tile.setTileMetadata(direction | stack.getItemDamage());
		}

		world.setBlock(x, y + 1, z, getBlockID(), 0, 2);
		TileEntityMetadata tile2 = (TileEntityMetadata) world.getBlockTileEntity(x, y + 1, z);
		if(tile2 != null) {
			tile2.setTileMetadata((8 | (flipped ? 1 : 0)) | stack.getItemDamage());
		}
		world.markBlockForRenderUpdate(x, y, z);
		world.markBlockForRenderUpdate(x, y, z);
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
		return renderPass == 0 ? TreeRegistry.trees[(stack.getItemDamage() - (stack.getItemDamage() & 127)) / 128].woodColor : super.getColorFromItemStack(stack, renderPass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int meta, int renderPass) {
		return renderPass == 1 ? iconOverlay : icons[(meta & 96) / 32];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return getIconFromDamageForRenderPass(meta, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		String[] doorTypes = new String[]{"Standard", "Solid", "Double", "Full"};
		for(int i = 0; i < doorTypes.length; i++) {
			icons[i] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i]);
		}
		iconOverlay = iconRegister.registerIcon("agecraft:door/wood/doorOverlay");
	}
}
