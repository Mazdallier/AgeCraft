package elcon.mods.agecraft.core.blocks.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.BiomeRegistry;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.blocks.BlockExtendedContainer;
import elcon.mods.agecraft.core.tileentities.TileEntityDNATree;
import elcon.mods.agecraft.dna.storage.DNAStorage;

public class BlockSaplingDNA extends BlockExtendedContainer implements IPlantable {

	public BlockSaplingDNA(int id) {
		super(id, Material.plants);
		setHardness(0.0F);
		setStepSound(Block.soundGrassFootstep);
		setTickRandomly(true);
		setCreativeTab(ACCreativeTabs.wood);
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
	}
	
	public void growSapling(World world, int x, int y, int z, Random random) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 3);
		} else {
			growTree(world, x, y, z, random);
		}
	}

	public void growTree(World world, int x, int y, int z, Random random) {
		
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDNATree();
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(!world.isRemote) {
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("DNA")) {
				TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
				if(tile == null) {
					tile = new TileEntityDNATree();
					world.setBlockTileEntity(x, y, z, tile);
				}
				DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
				dna.readFromNBT(stack.getTagCompound().getCompoundTag("DNA"));
				tile.setDNA(dna);
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityDNATree();
			world.setBlockTileEntity(x, y, z, tile);
		}
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
			//TODO: change grow chance
			if(world.getBlockLightValue(x, y, z) >= 9 && random.nextInt(7) == 0) {
				growSapling(world, x, y, z, random);
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y ,z) && canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block soil = Block.blocksList[world.getBlockId(x, y - 1, z)];
		TileEntityDNATree tile = (TileEntityDNATree) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			System.out.println(world.getBiomeGenForCoords(x, z).biomeName);
			System.out.println(BiomeGenBase.biomeList[tile.getBiome()].biomeName);
			System.out.println("T: " + BiomeRegistry.getTemperature(world.getBiomeGenForCoords(x, z)) + " PT: " + BiomeRegistry.getTemperature(BiomeGenBase.biomeList[tile.getBiome()]) + " R: " + BiomeRegistry.canSurviveTemperature(world.getBiomeGenForCoords(x, z), BiomeRegistry.getTemperature(BiomeGenBase.biomeList[tile.getBiome()]), tile.getTemperature()));
			return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this)) 
					&& BiomeRegistry.canSurviveTemperature(world.getBiomeGenForCoords(x, z), BiomeRegistry.getTemperature(BiomeGenBase.biomeList[tile.getBiome()]), tile.getTemperature()) 
					&& BiomeRegistry.canSurviveHumidity(world.getBiomeGenForCoords(x, z), BiomeRegistry.getHumidity(BiomeGenBase.biomeList[tile.getBiome()]), tile.getHumidity());
		}
		return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && (soil != null && soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this));
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
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntityDNATree tile = (TileEntityDNATree) blockAccess.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityDNATree();
		}
		return blockAccess.getBlockMetadata(x, y, z) == 1 ? TreeRegistry.trees[tile.getWoodType()].sapling : TreeRegistry.trees[tile.getWoodType()].smallSapling;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				ItemStack stack = new ItemStack(id, 1, 0);
				//TODO: create default DNA
				list.add(stack);
			}
		}
	}
}
