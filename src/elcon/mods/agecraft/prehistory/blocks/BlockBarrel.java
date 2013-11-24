package elcon.mods.agecraft.prehistory.blocks;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.recipes.RecipesBarrel;
import elcon.mods.agecraft.prehistory.recipes.RecipesBarrel.RecipeBarrel;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityBarrel;
import elcon.mods.core.blocks.BlockExtendedContainer;
import elcon.mods.core.lang.LanguageManager;

public class BlockBarrel extends BlockExtendedContainer implements IWailaBlock {

	public BlockBarrel(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundWoodFootstep);
		setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 1.0F, 0.875F);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntityBarrel tile = (TileEntityBarrel) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBarrel();
			world.setBlockTileEntity(x, y, z, tile);
		}
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(idDropped(metadata, world.rand, fortune), quantityDropped(metadata, fortune, world.rand), damageDropped(metadata));
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("WoodType", tile.woodType);
		nbt.setInteger("StickType", tile.stickType);
		nbt.setBoolean("HasLid", tile.hasLid);
		if(tile.hasLid) {
			if(tile.hasFluid()) {
				NBTTagCompound tag = new NBTTagCompound();
				tile.fluid.writeToNBT(tag);
				nbt.setCompoundTag("Fluid", tag);
			}
			if(tile.stickType >= 0) {
				if(tile.hasStack()) {
					NBTTagCompound tag = new NBTTagCompound();
					tile.stack.writeToNBT(tag);
					nbt.setCompoundTag("Stack", tag);
				}
			}
		}
		stack.setTagCompound(nbt);
		list.add(stack);
		return list;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntityBarrel tile = (TileEntityBarrel) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBarrel();
			world.setBlockTileEntity(x, y, z, tile);
		}
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			tile.woodType = nbt.getInteger("WoodType");
			tile.stickType = nbt.getInteger("StickType");
			tile.hasLid = nbt.getBoolean("HasLid");
			if(nbt.hasKey("Fluid")) {
				NBTTagCompound tag = nbt.getCompoundTag("Fluid").getCompoundTag("barrel");
				FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid(tag.getString("FluidName")), tag.getInteger("Amount"));
				if(tag.hasKey("Tag")) {
					fluidStack.tag = tag.getCompoundTag("Tag");
				}
				tile.fluid.setFluid(fluidStack);
			} else if(nbt.hasKey("Stack")) {
				NBTTagCompound tag = nbt.getCompoundTag("Stack");
				ItemStack soakStack = ItemStack.loadItemStackFromNBT(tag);
				tile.stack = soakStack;
				tile.ticksLeft = RecipesBarrel.getRecipe(soakStack).ticks;
			}
		}
		world.markBlockForUpdate(x, y, z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityBarrel tile = (TileEntityBarrel) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityBarrel();
			world.setBlockTileEntity(x, y, z, tile);
		}
		ItemStack stack = player.inventory.getCurrentItem();
		if(!player.isSneaking() && !tile.hasLid) {
			if(tile.hasStack()) {
				if(!player.capabilities.isCreativeMode) {
					if(!player.inventory.addItemStackToInventory(tile.stack.copy())) {
						return false;
					}
				}
				tile.stack = null;
				world.markBlockForUpdate(x, y, z);
				return true;
			} else if(stack != null) {
				if(stack.itemID == Trees.stick.itemID) {
					if(tile.stickType == -1) {
						tile.stickType = stack.getItemDamage();
						if(!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, ACUtil.consumeItem(stack));
						}
					} else {
						if(!player.capabilities.isCreativeMode) {
							if(!player.inventory.addItemStackToInventory(new ItemStack(Trees.stick.itemID, 1, tile.stickType))) {
								return false;
							}
						}
						tile.stickType = -1;
					}
					world.markBlockForUpdate(x, y, z);
					return true;
				}
				if(tile.hasFluid() && tile.stickType >= 0) {
					RecipeBarrel recipe = RecipesBarrel.getRecipe(stack);
					if(recipe != null && tile.fluid.getFluid().getFluid().getName().equals(recipe.fluid)) {
						tile.stack = stack.copy();
						tile.stack.stackSize = 1;
						tile.ticksLeft = RecipesBarrel.getRecipe(tile.stack).ticks;
						if(!player.capabilities.isCreativeMode) {
							player.inventory.setInventorySlotContents(player.inventory.currentItem, ACUtil.consumeItem(stack));
						}
						world.markBlockForUpdate(x, y, z);
						return true;
					}
				} else {
					FluidStack fluidStack = ACUtil.getFluidContainerStack(stack);
					if(fluidStack != null && !getFluidBlacklist().contains(fluidStack.getFluid())) {
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
	
	public List<Fluid> getFluidBlacklist() {
		return Arrays.asList(FluidRegistry.LAVA);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBarrel();
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
		return "tile.barrel.name";
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
		return 204;
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
		TileEntityBarrel tile = (TileEntityBarrel) getTileEntity(blockAccess, x, y, z);
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
				nbt.setInteger("StickType", -1);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntityBarrel tile = (TileEntityBarrel) accessor.getTileEntity();
		currenttip.add(0, EnumChatFormatting.WHITE + LanguageManager.getLocalization("trees." + TreeRegistry.trees[tile.woodType].name) + " " + LanguageManager.getLocalization(getUnlocalizedName()));
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntityBarrel tile = (TileEntityBarrel) accessor.getTileEntity();
		StringBuilder sb = new StringBuilder();
		if(tile.hasFluid()) {
			sb.append(LanguageManager.getLocalization(tile.fluid.getFluidType().getUnlocalizedName()));
			if(tile.hasStack()) {
				sb.append(" / ");
				sb.append(tile.stack.getDisplayName());
			}
		}
		if(sb.length() > 0) {
			currenttip.add(sb.toString());
		}
		return currenttip;
	}
}
