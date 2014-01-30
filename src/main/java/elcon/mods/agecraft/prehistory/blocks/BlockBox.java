package elcon.mods.agecraft.prehistory.blocks;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaBlock;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBox;
import elcon.mods.core.blocks.BlockExtendedContainer;
import elcon.mods.core.lang.LanguageManager;

public class BlockBox extends BlockExtendedContainer implements IWailaBlock {

	public BlockBox(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundWoodFootstep);
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 0.5F, 0.875F);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityBox tile = (TileEntityBox) getTileEntity(world, x, y, z);
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(idDropped(metadata, world.rand, fortune), quantityDropped(metadata, fortune, world.rand), damageDropped(metadata));
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("WoodType", tile.woodType);
		nbt.setBoolean("HasLid", tile.hasLid);
		if(tile.hasLid) {
			NBTTagList tagList = new NBTTagList();
			for(int i = 0; i < tile.stacks.length; i++) {
				if(tile.stacks[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Slot", (byte) i);
					tile.stacks[i].writeToNBT(tag);
					tagList.appendTag(tag);
				}
			}
			nbt.setTag("Stacks", tagList);
		}
		stack.setTagCompound(nbt);
		list.add(stack);
		return list;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntityBox tile = (TileEntityBox) getTileEntity(world, x, y, z);
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			tile.woodType = nbt.getInteger("WoodType");
			tile.hasLid = nbt.getBoolean("HasLid");
			if(nbt.hasKey("Stacks")) {
				NBTTagList tagList = nbt.getTagList("Stacks");
				for(int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
					int slot = tag.getByte("Slot") & 255;
					ItemStack boxStack = ItemStack.loadItemStackFromNBT(tag);
					if(boxStack != null && slot >= 0 && slot < 4) {
						tile.stacks[slot] = boxStack;
					}
				}
			}
		}
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityBox tile = (TileEntityBox) getTileEntity(world, x, y, z);
		ItemStack stack = player.inventory.getCurrentItem();
		if(!player.isSneaking() && !tile.hasLid) {
			byte index = 0;
			if(hitX < 0.5F && hitZ < 0.5F) {
				index = 0;
			} else if(hitX >= 0.5F && hitZ < 0.5F) {
				index = 1;
			} else if(hitX < 0.5F && hitZ >= 0.5F) {
				index = 2;
			} else if(hitX >= 0.5F && hitZ >= 0.5F) {
				index = 3;
			}
			if(tile.stacks[index] != null) {
				if(!player.capabilities.isCreativeMode) {
					if(!player.inventory.addItemStackToInventory(tile.stacks[index].copy())) {
						return false;
					}
				}
				tile.stacks[index] = null;
				world.markBlockForUpdate(x, y, z);
				return true;
			} else if(stack != null) {
				tile.stacks[index] = stack.copy();
				if(!player.capabilities.isCreativeMode) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}
				world.markBlockForUpdate(x, y, z);
				return true;
			} else {
				tile.hasLid = !tile.hasLid;
				world.markBlockForUpdate(x, y, z);
				return true;
			}
		} else {
			tile.hasLid = !tile.hasLid;
			world.markBlockForUpdate(x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBox();
	}
	
	@Override
	public Class<?> getTileEntityClass() {
		return TileEntityBox.class;
	}

	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + TreeRegistry.trees[stack.getTagCompound().getInteger("WoodType")].name) + " " + LanguageManager.getLocalization(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "tile.box.name";
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
		return 205;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return TreeRegistry.trees[0].planks;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntityBox tile = (TileEntityBox) getTileEntity(blockAccess, x, y, z);
		return TreeRegistry.trees[tile.woodType].planks;
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
				nbt.setInteger("WoodType", i);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0).get(0);
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntityBox tile = (TileEntityBox) accessor.getTileEntity();
		for(int i = 0; i < tile.stacks.length; i++) {
			if(tile.stacks[i] != null) {
				currenttip.add(Integer.toString(tile.stacks[i].stackSize) + "x " + tile.stacks[i].getDisplayName());
			}
		}
		return currenttip;
	}
}
