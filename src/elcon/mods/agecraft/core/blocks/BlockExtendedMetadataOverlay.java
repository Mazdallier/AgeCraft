package elcon.mods.agecraft.core.blocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.agecraft.core.tileentities.TileEntityMetadata;

public class BlockExtendedMetadataOverlay extends BlockContainerOverlay implements IBlockExtendedMetadata {

	public BlockExtendedMetadataOverlay(int id, Material material) {
		super(id, material);
	}
	
	public String getLocalizedName(ItemStack stack) {
		return getLocalizedName();
	}

	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMetadata();
	}	

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		return stack.getItemDamage();
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		if(player.getCurrentEquippedItem() == null) {
			return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
		}
		return player.getCurrentEquippedItem().getItem().getStrVsBlock(player.getCurrentEquippedItem(), this, getMetadata(world, x, y, z)) / getBlockHardness(world, x, y, z) / 30F;
	}
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		return breakBlock(this, player, world, x, y, z);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortune) {
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile != null && !tile.droppedBlock) {
			super.dropBlockAsItemWithChance(world, x, y, z, tile.getTileMetadata(), chance, fortune);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = quantityDropped(metadata, fortune, world.rand);
		for(int i = 0; i < count; i++) {
			int id = idDropped(metadata, world.rand, fortune);
			if(id > 0) {
				ret.add(new ItemStack(id, 1, getDroppedMetadata(world, x, y, z, metadata, fortune)));
			}
		}
		return ret;
	}
	
	public boolean breakBlock(IBlockExtendedMetadata block, EntityPlayer player, World world, int x, int y, int z) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		Block block2 = (Block) block;
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile != null && !tile.droppedBlock) {
			drops = block2.getBlockDropped(world, x, y, z, getMetadata(world, x, y, z), EnchantmentHelper.getFortuneModifier(player));
		}
		boolean hasBeenBroken = world.setBlockToAir(x, y, z);
		if(hasBeenBroken && !world.isRemote && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode) && shouldDropItems(world, x, y, z, tile.getTileMetadata(), player, player.getCurrentEquippedItem())) {
			for(ItemStack drop : drops) {
				block.dropAsStack(world, x, y, z, drop);
			}
			tile.droppedBlock = true;
		}
		return hasBeenBroken;
	}
	
	@Override
	public boolean shouldDropItems(World world, int x, int y, int z, int meta, EntityPlayer player, ItemStack stack) {
		return true;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return getMetadata(world, x, y, z);
	}
	
	@Override
	public int getMetadata(IBlockAccess blockAccess, int x, int y, int z) {
		if(Block.blocksList[blockAccess.getBlockId(x, y, z)] instanceof IBlockExtendedMetadata) {
			return ((TileEntityMetadata) blockAccess.getBlockTileEntity(x, y, z)).getTileMetadata();
		}
		return blockAccess.getBlockMetadata(x, y, z);
	}
	
	@Override
	public void setMetadata(World world, int x, int y, int z, int meta) {
		if(Block.blocksList[world.getBlockId(x, y, z)] instanceof IBlockExtendedMetadata) {
			((TileEntityMetadata) world.getBlockTileEntity(x, y, z)).setTileMetadata(meta);
		}
	}
	
	@Override
	public void dropAsStack(World world, int x, int y, int z, ItemStack stack) {
		dropBlockAsItem_do(world, x, y, z, stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getBlockOverlayTexture(side, getMetadata(blockAccess, x, y, z));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
		byte size = 4;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				for(int k = 0; k < size; k++) {
					double xx = (double) x + ((double) i + 0.5D) / (double) size;
					double yy = (double) y + ((double) j + 0.5D) / (double) size;
					double zz = (double) z + ((double) k + 0.5D) / (double) size;
					effectRenderer.addEffect((new EntityDiggingFX(world, xx, yy, zz, xx - (double) x - 0.5D, yy - (double) y - 0.5D, zz - (double) z - 0.5D, this, getMetadata(world, x, y, z))).applyColourMultiplier(x, y, z));
				}
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
		int x = target.blockX;
		int y = target.blockY;
		int z = target.blockZ;
		int sideHit = target.sideHit;
		Block block = Block.blocksList[world.getBlockId(x, y, z)];

		float f = 0.1F;
		double xx = (double) x + world.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinX();
		double yy = (double) y + world.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinY();
		double zz = (double) z + world.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinZ();
		if(sideHit == 0) {
			yy = (double) y + block.getBlockBoundsMinY() - (double) f;
		}
		if(sideHit == 1) {
			yy = (double) y + block.getBlockBoundsMaxY() + (double) f;
		}
		if(sideHit == 2) {
			zz = (double) z + block.getBlockBoundsMinZ() - (double) f;
		}
		if(sideHit == 3) {
			zz = (double) z + block.getBlockBoundsMaxZ() + (double) f;
		}
		if(sideHit == 4) {
			xx = (double) x + block.getBlockBoundsMinX() - (double) f;
		}
		if(sideHit == 5) {
			xx = (double) x + block.getBlockBoundsMaxX() + (double) f;
		}
		effectRenderer.addEffect((new EntityDiggingFX(world, xx, yy, zz, 0.0D, 0.0D, 0.0D, this, getMetadata(world, x, y, z))).applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
}
