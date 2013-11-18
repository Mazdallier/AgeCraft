package elcon.mods.agecraft.core.blocks.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;
import elcon.mods.agecraft.dna.DNA;
import elcon.mods.agecraft.dna.storage.DNAStorage;
import elcon.mods.core.ECUtilClient;
import elcon.mods.core.blocks.BlockExtendedContainer;
import elcon.mods.core.lang.LanguageManager;

public class BlockLeavesDNA extends BlockExtendedContainer {
	
	private int[] adjacentTreeBlocks;
	
	public BlockLeavesDNA(int id) {
		super(id, Material.leaves);
		setHardness(0.2F);
		setLightOpacity(1);
		setTickRandomly(true);
		setStepSound(Block.soundGrassFootstep);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "trees.leaves";
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("DNA")) {
			TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
			DNAStorage dna = new DNAStorage(tile.getDNAObject());
			dna.readFromNBT(stack.getTagCompound().getCompoundTag("DNA"));
			tile.setDNA(dna);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		byte size = 1;
		int range = size + 1;
		if(world.checkChunksExist(x - range, y - range, z - range, x + range, y + range, z + range)) {
			for(int i = -size; i <= size; ++i) {
				for(int j = -size; j <= size; ++j) {
					for(int k = -size; k <= size; ++k) {
						int blockID = world.getBlockId(x + i, y + j, z + k);
						if(Block.blocksList[blockID] != null) {
							Block.blocksList[blockID].beginLeavesDecay(world, x + i, y + j, z + k);
						}
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(!world.isRemote) {
			TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
			int chance = 12 - tile.getBreedingSpeed();
			if(random.nextInt(chance) == 0) {
				int side = random.nextInt(6);
				int xx = x + ForgeDirection.VALID_DIRECTIONS[side].offsetX;
				int yy = y + ForgeDirection.VALID_DIRECTIONS[side].offsetY;
				int zz = z + ForgeDirection.VALID_DIRECTIONS[side].offsetZ;
				if(world.getBlockId(xx, yy, zz) == blockID) {
					TileEntityDNATree tileOther = (TileEntityDNATree) getTileEntity(world, xx, yy, zz);
					DNAStorage dnaNew = DNA.reproduce(tile.getDNA(), tileOther.getDNA());
					tile.setDNA(dnaNew);
					tileOther.setDNA(dnaNew);
					world.markBlockForUpdate(x, y, z);
					world.markBlockForUpdate(xx, yy, zz);
					world.playAuxSFX(2005, x, y, z, 0);
					world.playAuxSFX(2005, xx, yy, zz, 0);
				}
			}
			int meta = world.getBlockMetadata(x, y, z);
			if((meta & 2) != 0 && (meta & 1) == 0) {
				byte size = 4;
				int range = size + 1;
				byte treeSize = 32;
				int treeSize2 = treeSize * treeSize;
				int radius = treeSize / 2;

				if(adjacentTreeBlocks == null) {
					adjacentTreeBlocks = new int[treeSize * treeSize * treeSize];
				}
				if(world.checkChunksExist(x - range, y - range, z - range, x + range, y + range, z + range)) {
					for(int i = -size; i <= size; ++i) {
						for(int j = -size; j <= size; ++j) {
							for(int k = -size; k <= size; ++k) {
								int blockID = world.getBlockId(x + i, y + j, z + k);
								Block block = Block.blocksList[blockID];
								if(block != null && block.canSustainLeaves(world, x + i, y + j, z + k)) {
									adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + k + radius] = 0;
								} else if(block != null && block.isLeaves(world, x + i, y + j, z + k)) {
									adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + k + radius] = -2;
								} else {
									adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + k + radius] = -1;
								}
							}
						}
					}
					for(int ii = 1; ii <= 4; ++ii) {
						for(int i = -size; i <= size; ++i) {
							for(int j = -size; j <= size; ++j) {
								for(int k = -size; k <= size; ++k) {
									if(adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + k + radius] == ii - 1) {
										if(adjacentTreeBlocks[(i + radius - 1) * treeSize2 + (j + radius) * treeSize + k + radius] == -2) {
											adjacentTreeBlocks[(i + radius - 1) * treeSize2 + (j + radius) * treeSize + k + radius] = ii;
										}
										if(adjacentTreeBlocks[(i + radius + 1) * treeSize2 + (j + radius) * treeSize + k + radius] == -2) {
											adjacentTreeBlocks[(i + radius + 1) * treeSize2 + (j + radius) * treeSize + k + radius] = ii;
										}
										if(adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius - 1) * treeSize + k + radius] == -2) {
											adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius - 1) * treeSize + k + radius] = ii;
										}
										if(adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius + 1) * treeSize + k + radius] == -2) {
											adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius + 1) * treeSize + k + radius] = ii;
										}
										if(adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + (k + radius - 1)] == -2) {
											adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + (k + radius - 1)] = ii;
										}
										if(adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + k + radius + 1] == -2) {
											adjacentTreeBlocks[(i + radius) * treeSize2 + (j + radius) * treeSize + k + radius + 1] = ii;
										}
									}
								}
							}
						}
					}
				}
				if(adjacentTreeBlocks[radius * treeSize2 + radius * treeSize + radius] >= 0) {
					world.setBlockMetadataWithNotify(x, y, z, meta - 2, 4);
				} else {
					removeLeaves(world, x, y, z);
				}
			}
		}
	}

	private void removeLeaves(World world, int x, int y, int z) {
		dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if(world.canLightningStrikeAt(x, y + 1, z) && !world.doesBlockHaveSolidTopSurface(x, y - 1, z) && random.nextInt(15) == 1) {
			double d0 = (double) ((float) x + random.nextFloat());
			double d1 = (double) y - 0.05D;
			double d2 = (double) ((float) z + random.nextFloat());
			world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		int dropChance = (tile.getSaplingDropRate() + 1) * 10;
		ItemStack stack = new ItemStack(idDropped(metadata, world.rand, fortune), quantityDropped(metadata, fortune, world.rand) * (world.rand.nextInt(dropChance) == 0 ? 1 : 0), damageDropped(metadata));
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		tile.getDNA().writeToNBT(tag);
		nbt.setCompoundTag("DNA", tag);
		stack.setTagCompound(nbt);
		list.add(stack);
		return list;
	}
	
	@Override
	public int idDropped(int meta, Random random, int fortune) {
		return Trees.saplingDNA.blockID;
	}
	
	@Override
	public int damageDropped(int meta) {
		return 0;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		ItemStack stack = new ItemStack(blockID, 1, 0);
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
		tile.getDNA().writeToNBT(tag);
		nbt.setCompoundTag("DNA", tag);
		stack.setTagCompound(nbt);
		return stack;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDNATree();
	}
	
	@Override
	public void beginLeavesDecay(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 2, 3);
	}
	
	@Override
	public boolean isLeaves(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return !((BlockLeaves) Trees.leaves).fancyGraphics;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(blockAccess, x, y, z);
		if(TreeRegistry.trees[tile.getLeaveType()].useBiomeColor) {
			int r = 0;
			int g = 0;
			int b = 0;
			for(int k = -1; k <= 1; ++k) {
				for(int i = -1; i <= 1; ++i) {
					int color = blockAccess.getBiomeGenForCoords(x + i, z + k).getBiomeFoliageColor();
					r += (color & 16711680) >> 16;
					g += (color & 65280) >> 8;
					b += color & 255;
				}
			}
			return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
		}
		return TreeRegistry.trees[tile.getLeaveType()].leavesColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int id = blockAccess.getBlockId(x, y, z);
		return !((BlockLeaves) Trees.leaves).fancyGraphics && (id == blockID || id == Trees.leaves.blockID) ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
		TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			meta = tile.getLeaveType();
		}
		return ECUtilClient.addBlockDestroyEffects(world, x, y, z, meta, effectRenderer, this, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
		int x = target.blockX;
		int y = target.blockY;
		int z = target.blockZ;
		int meta = 0;
		TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			meta = tile.getLeaveType();
		}
		return ECUtilClient.addBlockHitEffects(world, target, effectRenderer, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return Trees.leaves.getIcon(side, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(blockAccess, x, y, z);
		return Trees.leaves.getIcon(side, tile.getLeaveType() * 4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
	}
}
