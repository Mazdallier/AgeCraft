package org.agecraft.prehistory.blocks;

import java.util.ArrayList;
import java.util.List;

import org.agecraft.ACCreativeTabs;
import org.agecraft.ACUtil;
import org.agecraft.core.DustRegistry;
import org.agecraft.core.DustRegistry.Dust;
import org.agecraft.prehistory.tileentities.TileEntityPot;

import mcp.mobius.waila.api.IWailaBlock;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.blocks.BlockExtendedContainer;
import elcon.mods.core.lang.LanguageManager;

public class BlockPot extends BlockExtendedContainer implements IWailaBlock {

	public boolean renderSolid = false;
	private Icon iconsSide[] = new Icon[2];
	private Icon iconsTop[] = new Icon[2];

	public BlockPot(int id) {
		super(id, Material.clay);
		setHardness(0.6F);
		setStepSound(Block.soundStoneFootstep);
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 0.625F, 0.875F);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityPot tile = (TileEntityPot) getTileEntity(world, x, y, z);
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(idDropped(metadata, world.rand, fortune), quantityDropped(metadata, fortune, world.rand), damageDropped(metadata));
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("HasLid", tile.hasLid);
		if(tile.hasLid) {
			if(tile.hasFluid()) {
				NBTTagCompound tag = new NBTTagCompound();
				tile.fluid.writeToNBT(tag);
				nbt.setCompoundTag("Fluid", tag);
			} else if(tile.hasDust()) {
				NBTTagCompound tag = new NBTTagCompound();
				tile.dust.writeToNBT(tag);
				nbt.setCompoundTag("Dust", tag);
			}
		}
		stack.setTagCompound(nbt);
		list.add(stack);
		return list;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntityPot tile = (TileEntityPot) getTileEntity(world, x, y, z);
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			tile.hasLid = nbt.getBoolean("HasLid");
			if(nbt.hasKey("Fluid")) {
				NBTTagCompound tag = nbt.getCompoundTag("Fluid").getCompoundTag("pot");
				FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(tag.getString("FluidName")), tag.getInteger("Amount"));
				if(tag.hasKey("Tag")) {
					fluidStack.tag = tag.getCompoundTag("Tag");
				}
				tile.fluid.setFluid(fluidStack);
			} else if(nbt.hasKey("Dust")) {
				NBTTagCompound tag = nbt.getCompoundTag("Dust");
				ItemStack dustStack = ItemStack.loadItemStackFromNBT(tag);
				tile.dust = dustStack;
			}
		}
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityPot tile = (TileEntityPot) getTileEntity(world, x, y, z);
		ItemStack stack = player.inventory.getCurrentItem();
		if(!player.isSneaking() && !tile.hasLid) {
			if(tile.hasDust()) {
				if(!player.capabilities.isCreativeMode) {
					if(!player.inventory.addItemStackToInventory(tile.dust.copy())) {
						return false;
					}
				}
				tile.dust = null;
				world.markBlockForUpdate(x, y, z);
				return true;
			} else if(stack != null) {
				if(!tile.hasFluid()) {
					Dust dust = DustRegistry.getDust(stack);
					if(dust != null) {
						tile.dust = stack.copy();
						tile.dust.stackSize = 1;
						if(!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, ACUtil.consumeItem(stack));
						}
						world.markBlockForUpdate(x, y, z);
						return true;
					}
				} 
				if(!tile.hasDust()) {
					FluidStack fluidStack = ACUtil.getFluidContainerStack(stack);
					if(fluidStack != null) {
						int amount = tile.fluid.fill(fluidStack, true);
						if(amount > 0) {
							if(!player.capabilities.isCreativeMode) {
								player.inventory.setInventorySlotContents(player.inventory.currentItem, ACUtil.drainFluidContainer(stack, amount));
							}
							return true;
						}
					} else {
						FluidStack availableFluid = tile.fluid.getFluid();
						if(availableFluid != null) {
							ItemStack filled = ACUtil.fillFluidContainer(stack, availableFluid);
							fluidStack = ACUtil.getFluidContainerStack(filled);
							if(fluidStack != null) {
								if(!player.capabilities.isCreativeMode) {
									if(stack.stackSize > 1) {
										if(!player.inventory.addItemStackToInventory(filled)) {
											return false;
										} else {
											player.inventory.setInventorySlotContents(player.inventory.currentItem, ACUtil.consumeItem(stack));
										}
									} else {
										player.inventory.setInventorySlotContents(player.inventory.currentItem, ACUtil.consumeItem(stack));
										player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
									}
								}
								tile.fluid.drain(fluidStack.amount, true);
								return true;
							}
						}
					}
				}
				tile.hasLid = !tile.hasLid;
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
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntityPot tile = (TileEntityPot) getTileEntity(blockAccess, x, y, z);
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, tile.hasLid ? 0.6875F : 0.625F, 0.875F);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}

	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "tile.pot.name";
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityPot();
	}
	
	@Override
	public Class<?> getTileEntityClass() {
		return TileEntityPot.class;
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
		return 202;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return iconsTop[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if((side == 0 || side == 1) && renderSolid) {
			return iconsTop[1];
		}
		TileEntityPot tile = (TileEntityPot) getTileEntity(blockAccess, x, y, z);
		if(side == 0) {
			return iconsTop[1];
		} else if(side == 1) {
			return iconsTop[tile.hasLid ? 1 : 0];
		}
		return iconsSide[tile.hasLid ? 1 : 0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconsSide[0] = iconRegister.registerIcon("agecraft:ages/prehistory/potSide");
		iconsTop[0] = iconRegister.registerIcon("agecraft:ages/prehistory/potTop");
		iconsSide[1] = iconRegister.registerIcon("agecraft:ages/prehistory/potLidSide");
		iconsTop[1] = iconRegister.registerIcon("agecraft:ages/prehistory/potLidTop");
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
		TileEntityPot tile = (TileEntityPot) accessor.getTileEntity();
		if(tile.hasFluid()) {
			currenttip.add(LanguageManager.getLocalization(tile.fluid.getFluidType().getUnlocalizedName()));
		} else if(tile.hasDust()) {
			currenttip.add(tile.dust.getDisplayName());
		}
		return currenttip;
	}
}
