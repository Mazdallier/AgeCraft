package elcon.mods.agecraft.core.blocks.tree;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.blocks.BlockExtendedMetadata;
import elcon.mods.agecraft.lang.LanguageManager;

public class BlockWoodLadder extends BlockExtendedMetadata {

	private Icon icon;

	public BlockWoodLadder(int id) {
		super(id, Material.circuits);
		setHardness(0.4F);
		setStepSound(soundLadderFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.trees[(stack.getItemDamage() - (stack.getItemDamage() & 7)) / 8].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "trees.ladder";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int direction = getMetadata(blockAccess, x, y, z) & 7;
		float f = 0.125F;
		if(direction == 2) {
			setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}
		if(direction == 3) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}
		if(direction == 4) {
			setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		if(direction == 5) {
			setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return (meta - (meta & 7));
	}

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		int direction = 0;
		if((direction == 0 || side == 2) && world.isBlockSolidOnSide(x, y, z + 1, NORTH)) {
			direction = 2;
		}
		if((direction == 0 || side == 3) && world.isBlockSolidOnSide(x, y, z - 1, SOUTH)) {
			direction = 3;
		}
		if((direction == 0 || side == 4) && world.isBlockSolidOnSide(x + 1, y, z, WEST)) {
			direction = 4;
		}
		if((direction == 0 || side == 5) && world.isBlockSolidOnSide(x - 1, y, z, EAST)) {
			direction = 5;
		}
		return direction | stack.getItemDamage();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return world.isBlockSolidOnSide(x - 1, y, z, EAST) || world.isBlockSolidOnSide(x + 1, y, z, WEST) || world.isBlockSolidOnSide(x, y, z - 1, SOUTH) || world.isBlockSolidOnSide(x, y, z + 1, NORTH);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		int meta = getMetadata(world, x, y, z);
		int direction = meta & 7;
		boolean removed = false;

		if(direction == 2 && world.isBlockSolidOnSide(x, y, z + 1, NORTH)) {
			removed = true;
		}
		if(direction == 3 && world.isBlockSolidOnSide(x, y, z - 1, SOUTH)) {
			removed = true;
		}
		if(direction == 4 && world.isBlockSolidOnSide(x + 1, y, z, WEST)) {
			removed = true;
		}
		if(direction == 5 && world.isBlockSolidOnSide(x - 1, y, z, EAST)) {
			removed = true;
		}
		if(!removed) {
			dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
		super.onNeighborBlockChange(world, x, y, z, id);
	}

	@Override
	public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 111;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return TreeRegistry.trees[(meta - (meta & 7)) / 8].woodColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		return TreeRegistry.trees[(meta - (meta & 7)) / 8].woodColor;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				list.add(new ItemStack(id, 1, (i * 8)));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ladder");
	}
}
