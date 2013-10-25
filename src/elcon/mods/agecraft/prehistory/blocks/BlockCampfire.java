package elcon.mods.agecraft.prehistory.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;
import elcon.mods.core.lang.LanguageManager;

public class BlockCampfire extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private Icon icon;
	
	public BlockCampfire(int id) {
		super(id, Material.wood);
		setStepSound(Block.soundWoodFootstep);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.campfire.name";
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCampfire();
	}
	
	@Override
	public int getLightValue(IBlockAccess blockAccess, int x, int y, int z) {
		if(blockAccess.getBlockId(x, y, z) == blockID) {
			TileEntityCampfire tile = (TileEntityCampfire) blockAccess.getBlockTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityCampfire();
			}
			return tile.isBurning() ? 15 : 0;
		}
		return super.getLightValue(blockAccess, x, y, z);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y - 1, z);
		if(id != 0 && id != blockID && world.isBlockOpaqueCube(x, y - 1, z)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem_do(world, x, y, z, new ItemStack(blockID, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem_do(world, x, y, z, new ItemStack(blockID, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(!world.isRemote) {
			TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityCampfire();
				world.setBlockTileEntity(x, y, z, tile);
			}
			if(tile.isBurning()) {
				entity.setFire(8);
			}
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(!world.isRemote) {
			TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityCampfire();
				world.setBlockTileEntity(x, y, z, tile);
			}
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				return;
			}
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("Logs")) {
				NBTTagList list = nbt.getTagList("Logs");
				for(int i = 0; i < list.tagCount(); i++) {
					ItemStack logStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) list.tagAt(i));
					tile.addLogs(logStack);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityCampfire();
				world.setBlockTileEntity(x, y, z, tile);
			}			
			return tile.onBlockActivated(player.rotationYaw, player.getCurrentEquippedItem());
		}
		return true;
	}
	
	@Override
	public int idDropped(int meta, Random random, int fortune) {
		return 0;
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
		return 201;
	}	
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/campfire");
	}
}
