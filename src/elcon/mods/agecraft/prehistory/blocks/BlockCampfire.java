package elcon.mods.agecraft.prehistory.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.lang.LanguageManager;
import elcon.mods.agecraft.prehistory.CampfireRecipes;
import elcon.mods.agecraft.prehistory.PrehistoryAge;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class BlockCampfire extends BlockContainer {

	public boolean onFire = false;
	
	public BlockCampfire(int i, boolean on) {
		super(i, Material.wood);
		onFire = on;
		setLightValue(onFire ? 1.0F : 0.0F);
		setStepSound(Block.soundWoodFootstep);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f1, float f2, float f3) {
		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			world.setBlockTileEntity(x, y, z, new TileEntityCampfire());
		}	
		if(player.inventory.getCurrentItem() != null) {
			int itemID = player.inventory.getCurrentItem().itemID;
			if(itemID == Item.flintAndSteel.itemID) {
				if(tile.fuel > 0) {
					tile.canBurn = true;
					tile.hasFuel = true;
					tile.isBurning = true;
					tile.tick = 30;
					player.inventory.getCurrentItem().setItemDamage(player.inventory.getCurrentItem().getItemDamage() + 1);
					updateCampfireBlockState(true, world, x, y, z);
				}
				return true;
			} else if(itemID == Item.stick.itemID) {
				if(tile.spitStage < 3) {
					tile.spitDirection = (byte) (MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 1);
					tile.spitStage++;
					tile.hasSpit = true;
					player.inventory.getCurrentItem().stackSize--;
					world.markBlockForUpdate(x, y, z);
				}
				return true;
			} else if(CampfireRecipes.isFuel(player.inventory.getCurrentItem())) {
				int value = CampfireRecipes.getFuel(player.inventory.getCurrentItem());
				if(value > 0) {
					if(tile.isBurning) {
						tile.fuel += value;
						tile.canBurn = true;
						tile.isBurning = true;
						tile.hasFuel = true;
						player.inventory.consumeInventoryItem(itemID);
						return true;
					} else {
						tile.fuel += value;
						tile.hasFuel = true;
						tile.canBurn = true;
						player.inventory.consumeInventoryItem(itemID);
						return true;
					}
				}
				return true;
			} else if(CampfireRecipes.hasRecipe(player.inventory.getCurrentItem())) {
				if(tile.isBurning && tile.spitStage >= 3 && tile.spitStack == null) {
					tile.spitStack = new ItemStack(player.inventory.getCurrentItem().itemID, 1, player.inventory.getCurrentItem().getItemDamage());
					//tile.spitRotation = 0;
					tile.cookTime = 0;
					tile.cooked = false;
					tile.originalStack = tile.spitStack;
					player.inventory.getCurrentItem().stackSize--;
					world.markBlockForUpdate(x, y, z);
				}
				return true;
			} else if(tile.spitStack != null) {
				player.inventory.addItemStackToInventory(tile.spitStack);
				tile.spitStack = null;
				//tile.spitRotation = 0;
				tile.cookTime = 0;
				tile.cooked = false;
				tile.originalStack = null;
				world.markBlockForUpdate(x, y, z);
				return true;
			}
		} else if(tile.spitStack != null) {
			player.inventory.addItemStackToInventory(tile.spitStack);
			tile.spitStack = null;
			//tile.spitRotation = 0;
			tile.cookTime = 0;
			tile.cooked = false;
			tile.originalStack = null;
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			if(tile.spitStack != null) {
				EntityItem entity = new EntityItem(world, x, y, z, tile.spitStack);
				float f3 = 0.05F;
                entity.motionX = (double)((float)world.rand.nextGaussian() * f3);
                entity.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                entity.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                world.spawnEntityInWorld(entity);
			}
		}		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		int i = world.getBlockId(x, y - 1, z);
		if(i != 0 && i != blockID && world.isBlockOpaqueCube(x, y - 1, z)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem_do(world, x, y, z, new ItemStack(PrehistoryAge.campfireOff.blockID, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem_do(world, x, y, z, new ItemStack(PrehistoryAge.campfireOff.blockID, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}	

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
		if(onFire) {
			entity.setFire(10);
		}
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		super.onBlockClicked(world, x, y, z, player);
		if(onFire) {
			player.setFire(5);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	public int idDropped(int i, Random random, int j) {
		return PrehistoryAge.campfireOff.blockID;
	}
	
	@Override
	public int damageDropped(int i) {
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
		return 200;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
	
	public static void updateCampfireBlockState(boolean enabled, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(enabled) {
			world.setBlock(x, y, z, PrehistoryAge.campfireOn.blockID);
		} else {
			world.setBlock(x, y, z, PrehistoryAge.campfireOff.blockID);
		}
		
		if(tile != null) {
			tile.validate();
			world.setBlockTileEntity(x, y, z, tile);
		}
	}
}
