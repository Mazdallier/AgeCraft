package elcon.mods.agecraft.core.blocks.tree;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.core.blocks.BlockExtendedMetadata;
import elcon.mods.core.lang.LanguageManager;

public class BlockLeaves extends BlockExtendedMetadata {

	public boolean fancyGraphics;

	private int[] adjacentTreeBlocks;

	public BlockLeaves(int id) {
		super(id, Material.leaves);
		this.fancyGraphics = true;
		setHardness(0.2F);
		setLightOpacity(1);
		setTickRandomly(true);
		setStepSound(Block.soundGrassFootstep);
		setCreativeTab(ACCreativeTabs.wood);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.trees[(stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "trees.leaves";
	}
	
	@Override
	public boolean isOpaqueCube() {
		return !fancyGraphics;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return TreeRegistry.trees[(meta - (meta & 3)) / 4].leafColor;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		if(TreeRegistry.trees[(meta - (meta & 3)) / 4].useBiomeColor) {
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
		return TreeRegistry.trees[(meta - (meta & 3)) / 4].leafColor;
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
			int meta = getMetadata(world, x, y, z);
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
	public int idDropped(int meta, Random random, int fortune) {
		return 0;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta;
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		return stack.getItemDamage() | 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return fancyGraphics ? TreeRegistry.trees[(meta - (meta & 3)) / 4].leaves : TreeRegistry.trees[(meta - (meta & 3)) / 4].leavesFast;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getIcon(side, getMetadata(blockAccess, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int id = blockAccess.getBlockId(x, y, z);
		return !fancyGraphics && (id == blockID || id == Trees.leavesDNA.blockID) ? false : super.shouldSideBeRendered(blockAccess, x, y, z, side);
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(blockID, 1, (meta - (meta & 3)) / 4);
	}
	
	@Override
	public void beginLeavesDecay(World world, int x, int y, int z) {
		setMetadata(world, x, y, z, getMetadata(world, x, y, z) | 2);
	}
	
	@Override
	public boolean isLeaves(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				list.add(new ItemStack(id, 1, i * 4));
			}
		}
	}
}
