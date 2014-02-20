package org.agecraft.core.items.tools;

import javax.swing.Icon;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.agecraft.core.Tools;
import org.agecraft.core.registry.ToolRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrossbow extends ItemTool {

	public Icon[][] icons = new Icon[256][4];

	public ItemCrossbow(int id) {
		super(id);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
		int duration = getMaxItemUseDuration(stack) - itemInUseCount;
		boolean infiniteArrows = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		if(infiniteArrows || player.inventory.hasItem(Tools.bolt.itemID)) {
			int boltSlot = findBolt(player.inventory);
			ItemStack boltStack = boltSlot >= 0 ? player.inventory.getStackInSlot(boltSlot) : null;
			if(boltStack == null && player.capabilities.isCreativeMode) {
				ItemStack temp = new ItemStack(Tools.arrow.itemID, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("Type", 19);
				tag.setInteger("Material", 142);
				tag.setInteger("RodMaterial", 142);
				tag.setInteger("EnhancementMaterial", 0);
				nbt.setTag("Tool", tag);
				temp.setTagCompound(nbt);
				boltStack = temp;
			}
			float multiplier = (float) duration / 20.0F;
			multiplier = (multiplier * multiplier + multiplier * 2.0F) / 3.0F;
			if((double) multiplier < 0.1D) {
				return;
			}
			if(multiplier > 1.0F) {
				multiplier = 1.0F;
			}
			EntityBolt entityBolt = new EntityBolt(world, player, multiplier * 2.0F);
			entityBolt.setMaterials(getToolMaterial(boltStack), getToolRodMaterial(boltStack));
			if(multiplier == 1.0F) {
				entityBolt.setIsCritical(true);
			}
			entityBolt.damage = Tools.bolt.getToolAttackStrength(boltStack) * 0.5D;
			int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			if(punch > 0) {
				entityBolt.knockbackStrength = punch;
			}
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
				entityBolt.setFire(100);
			}
			stack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + multiplier * 0.5F);
			if(infiniteArrows) {
				entityBolt.canBePickedUp = 2;
			} else {
				boltStack.stackSize--;
				if(boltStack.stackSize <= 0) {
					boltStack = null;
				}
				player.inventory.setInventorySlotContents(boltSlot, boltStack);
			}
			if(!world.isRemote) {
				world.spawnEntityInWorld(entityBolt);
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.capabilities.isCreativeMode || findBolt(player.inventory) >= 0) {
			int boltSlot = findBolt(player.inventory);
			ItemStack boltStack = boltSlot >= 0 ? player.inventory.getStackInSlot(boltSlot) : null;
			NBTTagCompound tag = new NBTTagCompound();
			if(boltStack != null) {
				tag.setInteger("Material", Tools.bolt.getToolMaterial(boltStack));
				tag.setInteger("RodMaterial", Tools.bolt.getToolRodMaterial(boltStack));
			} else {
				tag.setInteger("Material", 142);
				tag.setInteger("RodMaterial", 142);
			}
			stack.getTagCompound().setCompoundTag("Bolt", tag);
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
	}
	
	public static int findBolt(InventoryPlayer inventory) {
		ItemStack stackBolt = null;
		int slot = -1;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stackSlot = inventory.getStackInSlot(i);
			if(stackSlot != null && stackSlot.itemID == Tools.bolt.itemID) {
				if(stackBolt == null || Tools.bolt.getToolAttackStrength(stackSlot) > Tools.bolt.getToolAttackStrength(stackBolt)) {
					stackBolt = stackSlot;
					slot = i;
				}
			}
		}
		return slot;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack stack, int renderPass) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(renderPass == 0) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				if(Minecraft.getMinecraft().thePlayer.getItemInUse() != null && ItemStack.areItemStacksEqual(stack, Minecraft.getMinecraft().thePlayer.getItemInUse())) {
					int duration = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
					if(duration >= 18) {
						return icons[toolRodMaterial][3];
					} else if(duration > 13) {
						return icons[toolRodMaterial][2];
					} else if(duration > 0) {
						return icons[toolRodMaterial][1];
					}
				}
				return icons[toolRodMaterial][0];
			}
		} else if(renderPass == 1) {
			int toolEnhancement = getToolEnhancementMaterial(stack);
			if(toolEnhancement != -1 && ToolRegistry.toolEnhancementMaterials[toolEnhancement] != null) {
				return ToolRegistry.toolEnhancementMaterials[toolEnhancement].icons[tool.id];
			}
		} else if(renderPass == 2 || renderPass == 3) {
			if(Minecraft.getMinecraft().thePlayer.getItemInUse() != null && ItemStack.areItemStacksEqual(stack, Minecraft.getMinecraft().thePlayer.getItemInUse())) {
				NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Bolt");
				int toolMaterial = tag.getInteger("Material");
				int toolRodMaterial = tag.getInteger("RodMaterial");
				int duration = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
				if(renderPass == 2) {
					if(duration >= 18) {
						return ((ItemBolt) Tools.bolt).iconsRod[toolRodMaterial][3];
					} else if(duration > 13) {
						return ((ItemBolt) Tools.bolt).iconsRod[toolRodMaterial][2];
					} else if(duration > 0) {
						return ((ItemBolt) Tools.bolt).iconsRod[toolRodMaterial][1];
					}
				} else if(renderPass == 3) {
					if(duration >= 18) {
						return ((ItemBolt) Tools.bolt).iconsHead[toolMaterial][3];
					} else if(duration > 13) {
						return ((ItemBolt) Tools.bolt).iconsHead[toolMaterial][2];
					} else if(duration > 0) {
						return ((ItemBolt) Tools.bolt).iconsHead[toolMaterial][1];
					}
				}
			}
		}
		return ResourcesCore.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(renderPass == 1) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				if(usingItem != null && ItemStack.areItemStacksEqual(stack, usingItem)) {
					int duration = player.getItemInUseDuration();
					if(duration >= 18) {
						return icons[toolRodMaterial][3];
					} else if(duration > 13) {
						return icons[toolRodMaterial][2];
					} else if(duration > 0) {
						return icons[toolRodMaterial][1];
					}
				}
				return icons[toolRodMaterial][0];
			}
		} else if(renderPass == 2 || renderPass == 3) {
			if(usingItem != null && ItemStack.areItemStacksEqual(stack, usingItem)) {
				NBTTagCompound tag = usingItem.getTagCompound().getCompoundTag("Bolt");
				int toolMaterial = tag.getInteger("Material");
				int toolRodMaterial = tag.getInteger("RodMaterial");
				int duration = player.getItemInUseDuration();
				if(renderPass == 2) {
					if(duration >= 18) {
						return ((ItemBolt) Tools.bolt).iconsRod[toolRodMaterial][3];
					} else if(duration > 13) {
						return ((ItemBolt) Tools.bolt).iconsRod[toolRodMaterial][2];
					} else if(duration > 0) {
						return ((ItemBolt) Tools.bolt).iconsRod[toolRodMaterial][1];
					}
				} else if(renderPass == 3) {
					if(duration >= 18) {
						return ((ItemBolt) Tools.bolt).iconsHead[toolMaterial][3];
					} else if(duration > 13) {
						return ((ItemBolt) Tools.bolt).iconsHead[toolMaterial][2];
					} else if(duration > 0) {
						return ((ItemBolt) Tools.bolt).iconsHead[toolMaterial][1];
					}
				}
			}
		}
		return getIcon(stack, renderPass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < ToolRegistry.toolRodMaterials.length; i++) {
			if(ToolRegistry.toolRodMaterials[i] != null) {
				for(int j = 0; j < 4; j++) {
					icons[i][j] = iconRegister.registerIcon("agecraft:tools/sticks/crossbow/" + ToolRegistry.toolRodMaterials[i].name + "/crossbow" + ECUtil.firstUpperCase(ToolRegistry.toolRodMaterials[i].name) + Integer.toString(j));
				}
			}
		}
	}
}
