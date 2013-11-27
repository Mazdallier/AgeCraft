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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.BiomeRegistry;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;
import elcon.mods.agecraft.dna.storage.DNAStorage;
import elcon.mods.core.ECUtilClient;
import elcon.mods.core.blocks.BlockExtendedContainer;
import elcon.mods.core.lang.LanguageManager;

public class BlockSaplingDNA extends BlockExtendedContainer implements IPlantable {

	public BlockSaplingDNA(int id) {
		super(id, Material.plants);
		setHardness(0.0F);
		setStepSound(Block.soundGrassFootstep);
		setTickRandomly(true);
		setCreativeTab(ACCreativeTabs.wood);
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
	}

	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "trees.sapling";
	}

	public void growSapling(World world, int x, int y, int z, Random random) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 3);
		} else if(canSaplingGrow(world, x, y, z)) {
			growTree(world, x, y, z, random);
		}
	}

	public void growTree(World world, int x, int y, int z, Random random) {
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
		TreeRegistry.trees[tile.getGenerationType()].worldGen.generateTree(world, x, y, z, tile.getDNA());
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDNATree();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == Item.dyePowder && player.inventory.getCurrentItem().getItemDamage() == 15) {
			if(!world.isRemote) {
				if(!player.capabilities.isCreativeMode) {
					ACUtil.consumeItem(player.inventory.getCurrentItem());
				}
				growSapling(world, x, y, z, world.rand);
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(!world.isRemote) {
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("DNA")) {
				TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
				DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
				dna.readFromNBT(stack.getTagCompound().getCompoundTag("DNA"));
				tile.setDNA(dna);
				if(!canBlockStay(world, x, y, z)) {
					dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(idDropped(metadata, world.rand, fortune), quantityDropped(metadata, fortune, world.rand), damageDropped(metadata));
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		tile.getDNA().writeToNBT(tag);
		nbt.setCompoundTag("DNA", tag);
		stack.setTagCompound(nbt);
		list.add(stack);
		return list;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		super.onNeighborBlockChange(world, x, y, z, id);
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(!world.isRemote) {
			if(!canBlockStay(world, x, y, z)) {
				dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.setBlockToAir(x, y, z);
			}

			int chance = 12 - ((TileEntityDNATree) getTileEntity(world, x, y, z)).getDNA().getGene(2, 0).getActive();
			if(world.isRaining()) {
				chance -= 4;
			}
			if(world.getBlockLightValue(x, y, z) >= 9 && random.nextInt(chance) == 0) {
				growSapling(world, x, y, z, random);
			}
		}
	}

	public boolean canSaplingGrow(World world, int x, int y, int z) {
		TileEntityDNATree tile = (TileEntityDNATree) getTileEntity(world, x, y, z);
		if((tile.getTrunkSize() + 1) > 1) {
			for(int i = 0; i <= tile.getTrunkSize(); i++) {
				for(int j = 0; j <= tile.getTrunkSize(); j++) {
					if(world.getBlockId(x + i, y, z + j) != blockID || world.getBlockMetadata(x + i, y, z + j) != 1 || !tile.getDNA().equals(((TileEntityDNATree) getTileEntity(world, x + i, y, z + j)).getDNA())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block soil = Block.blocksList[world.getBlockId(x, y - 1, z)];
		TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this)) && BiomeRegistry.canSurviveTemperature(world.getBiomeGenForCoords(x, z), BiomeRegistry.getTemperature(BiomeGenBase.biomeList[tile.getBiome()]), tile.getTemperature() - 2)
					&& BiomeRegistry.canSurviveHumidity(world.getBiomeGenForCoords(x, z), BiomeRegistry.getHumidity(BiomeGenBase.biomeList[tile.getBiome()]), tile.getHumidity() - 2);
		}
		return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		if(meta == 0) {
			setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.6F, 0.75F);
		} else {
			setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 90;
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Plains;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z) {
		return blockID;
	}

	@Override
	public int getPlantMetadata(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
		TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			meta = tile.getWoodType();
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
			meta = tile.getWoodType();
		}
		return ECUtilClient.addBlockHitEffects(world, target, effectRenderer, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return TreeRegistry.trees[meta].sapling;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntityDNATree tile = (TileEntityDNATree) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityDNATree();
		}
		return blockAccess.getBlockMetadata(x, y, z) == 1 ? TreeRegistry.trees[tile.getWoodType()].sapling : TreeRegistry.trees[tile.getWoodType()].smallSapling;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				ItemStack stack = new ItemStack(id, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("TreeType", i);
				nbt.setCompoundTag("Defaults", tag);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}
}
