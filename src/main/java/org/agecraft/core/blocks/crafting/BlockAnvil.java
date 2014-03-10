package org.agecraft.core.blocks.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.AgeCraft;
import org.agecraft.core.Stone;
import org.agecraft.core.blocks.IBlockFalling;
import org.agecraft.core.entity.EntityFallingBlock;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.tileentities.TileEntityAnvil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedContainer;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockAnvil extends BlockExtendedContainer implements IBlockFalling {

	public static String[] typeNames = new String[]{"stone", "bronze", "iron", "steel", "cobalt", "mithril", "adamantite"};
	public static String[] damageNames = new String[]{"intact", "slightlyDamaged", "veryDamaged"};

	public BlockAnvil() {
		super(Material.anvil);
		setHardness(5.0F);
		setResistance(10.0F);
		setLightOpacity(0);
		setStepSound(Block.soundTypeAnvil);
		setCreativeTab(ACCreativeTabs.crafting);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityAnvil();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityAnvil.class;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		((TileEntityAnvil) getTileEntity(world, x, y, z)).direction = (byte) (((MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 1) % 4);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(AgeCraft.instance, 13, world, x, y, z);
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(!world.isRemote) {
			fall(world, x, y, z);
		}
	}

	@Override
	public void fall(World world, int x, int y, int z) {
		if(canFall(world, x, y - 1, z) && y >= 0) {
			byte radius = 32;
			if(!BlockFalling.fallInstantly && world.checkChunksExist(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius)) {
				if(!world.isRemote) {
					EntityFallingBlock entity = new EntityFallingBlock(world, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this, world.getBlockMetadata(x, y, z));
					addFallData(world, x, y, z, entity);
					world.spawnEntityInWorld(entity);
				}
			} else {
				world.setBlockToAir(x, y, z);
				while(canFall(world, x, y - 1, z) && y > 0) {
					y--;
				}
				if(y > 0) {
					world.setBlock(x, y, z, this);
				}
			}
		}
	}

	@Override
	public void addFallData(World world, int x, int y, int z, EntityFallingBlock entity) {
		entity.setHurtEntities(true);
		TileEntityAnvil tile = (TileEntityAnvil) getTileEntity(world, x, y, z);
		NBTTagCompound nbt = new NBTTagCompound();
		tile.writeToNBT(nbt);
		entity.tileEntityData = nbt;
	}

	@Override
	public boolean canFall(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if(block.isAir(world, x, y, z)) {
			return true;
		} else if(block == Blocks.fire) {
			return true;
		} else {
			Material material = block.getMaterial();
			return material == Material.water ? true : material == Material.lava;
		}
	}

	@Override
	public DamageSource getFallDamageSource(EntityFallingBlock entity) {
		return DamageSource.anvil;
	}

	@Override
	public void onFallDamage(EntityFallingBlock entity, float fallingDamage, int damagePercentage) {
		if((double) entity.getRandom().nextFloat() < 0.05000000074505806D + (double) damagePercentage * 0.05D) {
			int damage = entity.tileEntityData.getInteger("Damage");
			damage++;
			if(damage > 2) {
				entity.broken = true;
			}
			entity.tileEntityData.setInteger("Damage", damage);
		}
	}

	@Override
	public void onFallEnded(World world, int x, int y, int z, int meta) {
		world.playAuxSFX(1022, x, y, z, 0);
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
		return 120;
	}

	@Override
	public int tickRate(World world) {
		return 2;
	}

	public String getLocalizedName(ItemStack stack) {
		int damage = stack.getItemDamage() & 3;
		return damage == 0 ? "" : (LanguageManager.getLocalization("crafting.anvil." + damageNames[damage]) + " ") + LanguageManager.getLocalization(getUnlocalizedName(stack)) + " " + LanguageManager.getLocalization(getUnlocalizedName());
	}

	public String getUnlocalizedName(ItemStack stack) {
		int type = (stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4;
		if(type == 0) {
			return "stone.types.stone";
		}
		return "metals." + typeNames[type];
	}

	@Override
	public String getUnlocalizedName() {
		return "crafting.anvil";
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityAnvil tile = (TileEntityAnvil) getTileEntity(world, x, y, z);
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		Item item = getItemDropped(metadata, world.rand, fortune);
		if(item != null) {
			ret.add(new ItemStack(item, 1, tile.damage + (tile.type * 4)));

		}
		if(tile.inventory.getStackInSlot(0) != null) {
			ret.add(tile.inventory.getStackInSlotOnClosing(0));
		}
		return ret;
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int direction = ((TileEntityAnvil) getTileEntity(blockAccess, x, y, z)).direction;
		if(direction != 3 && direction != 1) {
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int damage = meta & 3;
		int type = (meta - damage) / 4;
		if(type == 0) {
			return Stone.stone.getIcon(side, 0);
		}
		return MetalRegistry.instance.get(typeNames[type]).blocks[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int type = ((TileEntityAnvil) getTileEntity(blockAccess, x, y, z)).type;
		if(type == 0) {
			return Stone.stone.getIcon(side, 0);
		}
		return MetalRegistry.instance.get(typeNames[type]).blocks[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < typeNames.length; i++) {
			list.add(new ItemStack(item, 1, i * 4));
		}
	}
}
