package org.agecraft.core.items.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.ACUtil;
import org.agecraft.core.AgeCraftCoreClient;
import org.agecraft.core.registry.ToolEnhancementMaterialRegistry;
import org.agecraft.core.registry.ToolMaterialRegistry;
import org.agecraft.core.registry.ToolRegistry;
import org.agecraft.core.registry.ToolRegistry.Tool;
import org.agecraft.core.registry.ToolRegistry.ToolCreativeEntry;
import org.agecraft.core.registry.ToolRodMaterialRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public abstract class ItemTool extends Item {

	public ItemTool() {
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.tools);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(ToolRegistry.instance.get(getToolType(stack)).hasHead) {
			return LanguageManager.getLocalization(ToolMaterialRegistry.instance.get(getToolMaterial(stack)).localization) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
		} else {
			return LanguageManager.getLocalization(ToolRodMaterialRegistry.instance.get(getToolRodMaterial(stack)).localization) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tools.type." + ToolRegistry.instance.get(getToolType(stack)).name;
	}

	@Override
	public String getUnlocalizedName() {
		return "tools.type.default";
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entityLiving, EntityLivingBase entity) {
		ACUtil.damageItem(stack, ToolRegistry.instance.get(getToolType(stack)).damageEntity, entity);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
		ACUtil.damageItem(stack, ToolRegistry.instance.get(getToolType(stack)).damageBlock, entity);
		return true;
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getToolDurability(stack);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int metadata) {
		return isEffectiveAgainstBlock(stack, block) && canHarvestBlock(stack, block, metadata) ? getToolEfficiency(stack) : 1.0F;
	}

	public boolean canHarvestBlock(ItemStack stack, Block block, int meta) {
		return canHarvestBlock(block, stack);
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return isEffectiveAgainstBlock(stack, block);
	}

	public int getBaseAttackDamage(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(entity.canAttackWithItem()) {
			if(!entity.hitByEntity(player)) {
				float f = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
				int knockbackModifier = 0;
				float enchantmentModifier = 0.0F;
				if(entity instanceof EntityLivingBase) {
					enchantmentModifier = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase) entity);
					knockbackModifier += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase) entity);
				}
				if(player.isSprinting()) {
					knockbackModifier++;
				}
				if(f > 0.0F || enchantmentModifier > 0.0F) {
					boolean flag = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && entity instanceof EntityLivingBase;

					if(flag && f > 0.0F) {
						f *= 1.5F;
					}

					f += enchantmentModifier;
					boolean flag1 = false;
					int j = EnchantmentHelper.getFireAspectModifier(player);

					if(entity instanceof EntityLivingBase && j > 0 && !entity.isBurning()) {
						flag1 = true;
						entity.setFire(1);
					}

					boolean flag2 = entity.attackEntityFrom(DamageSource.causePlayerDamage(player), f);

					if(flag2) {
						if(knockbackModifier > 0) {
							entity.addVelocity((double) (-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * (float) knockbackModifier * 0.5F), 0.1D, (double) (MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * (float) knockbackModifier * 0.5F));
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}

						if(flag) {
							player.onCriticalHit(entity);
						}

						if(enchantmentModifier > 0.0F) {
							player.onEnchantmentCritical(entity);
						}

						if(f >= 18.0F) {
							player.triggerAchievement(AchievementList.overkill);
						}

						player.setLastAttacker(entity);

						if(entity instanceof EntityLivingBase) {
							EnchantmentHelper.func_151384_a((EntityLivingBase) entity, player);
						}

						EnchantmentHelper.func_151385_b(player, entity);
						ItemStack itemstack = player.getCurrentEquippedItem();
						Object object = entity;

						if(entity instanceof EntityDragonPart) {
							IEntityMultiPart ientitymultipart = ((EntityDragonPart) entity).entityDragonObj;

							if(ientitymultipart != null && ientitymultipart instanceof EntityLivingBase) {
								object = (EntityLivingBase) ientitymultipart;
							}
						}
						if(itemstack != null && object instanceof EntityLivingBase) {
							itemstack.hitEntity((EntityLivingBase) object, player);

							if(itemstack.stackSize <= 0) {
								player.destroyCurrentEquippedItem();
							}
						}
						if(entity instanceof EntityLivingBase) {
							player.addStat(StatList.damageDealtStat, Math.round(f * 10.0F));
							if(j > 0) {
								entity.setFire(j * 4);
							}
						}
						player.addExhaustion(0.3F);
					} else if(flag1) {
						entity.extinguish();
					}
				}
			}
		}
		return true;
	}

	public boolean isEffectiveAgainstBlock(ItemStack stack, Block block) {
		Block[] blocksEffectiveAgainst = ToolRegistry.instance.get(getToolType(stack)).blocksEffectiveAgainst;
		for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
			if(Block.getIdFromBlock(blocksEffectiveAgainst[i]) == Block.getIdFromBlock(block)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 3;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.instance.get(getToolType(stack));
		if(pass == 0 && tool.hasRod) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && ToolRodMaterialRegistry.instance.get(toolRodMaterial) != null) {
				return ToolRodMaterialRegistry.instance.get(toolRodMaterial).icons[tool.id];
			}
		} else if(pass == 1 && tool.hasHead) {
			int toolMaterial = getToolMaterial(stack);
			if(toolMaterial != -1 && ToolMaterialRegistry.instance.get(toolMaterial) != null) {
				return ToolMaterialRegistry.instance.get(toolMaterial).icons[tool.id];
			}
		} else if(pass == 2 && tool.hasEnhancements) {
			int toolEnhancement = getToolEnhancementMaterial(stack);
			if(toolEnhancement != -1 && ToolEnhancementMaterialRegistry.instance.get(toolEnhancement) != null) {
				return ToolEnhancementMaterialRegistry.instance.get(toolEnhancement).icons[tool.id];
			}
		}
		return AgeCraftCoreClient.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
		if(ToolRegistry.toolCreativeEntries.containsKey(ToolRegistry.getToolID(item))) {
			ArrayList<ToolCreativeEntry> entries = ToolRegistry.toolCreativeEntries.get(ToolRegistry.getToolID(item));
			for(ToolCreativeEntry entry : entries) {
				ItemStack stack = new ItemStack(item, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound nbt2 = new NBTTagCompound();
				nbt2.setInteger("Type", entry.tool);
				nbt2.setInteger("Material", entry.toolMaterial);
				nbt2.setInteger("RodMaterial", entry.toolRodMaterial);
				nbt2.setInteger("EnhancementMaterial", entry.toolEnhancementMaterial);
				nbt.setTag("Tool", nbt2);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}

	public NBTTagCompound getToolNBT(ItemStack stack) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if(nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		if(!nbt.hasKey("Tool")) {
			NBTTagCompound nbt2 = new NBTTagCompound();
			nbt2.setInteger("Type", 0);
			nbt2.setInteger("Material", -1);
			nbt2.setInteger("RodMaterial", -1);
			nbt2.setInteger("EnhancementMaterial", -1);
			nbt.setTag("Tool", nbt2);
		}
		return nbt.getCompoundTag("Tool");
	}

	public int getToolType(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("Type");
	}

	public int getToolMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("Material");
	}

	public int getToolRodMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("RodMaterial");
	}

	public int getToolEnhancementMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("EnhancementMaterial");
	}

	public int getToolDurability(ItemStack stack) {
		Tool tool = ToolRegistry.instance.get(getToolType(stack));
		return (tool.hasHead ? ToolMaterialRegistry.instance.get(getToolMaterial(stack)).durability : 0) + (tool.hasRod ? ToolRodMaterialRegistry.instance.get(getToolRodMaterial(stack)).durability : 0);
	}

	public float getToolEfficiency(ItemStack stack) {
		Tool tool = ToolRegistry.instance.get(getToolType(stack));
		return (tool.hasHead ? ToolMaterialRegistry.instance.get(getToolMaterial(stack)).efficiency : 0) + (tool.hasRod ? ToolRodMaterialRegistry.instance.get(getToolRodMaterial(stack)).efficiency : 0);
	}

	public int getToolAttackStrength(ItemStack stack) {
		if(!ToolRegistry.instance.get(getToolType(stack)).hasHead) {
			return ToolRodMaterialRegistry.instance.get(getToolRodMaterial(stack)).attackStrength;
		}
		return ToolMaterialRegistry.instance.get(getToolMaterial(stack)).attackStrength;
	}

	public int getToolHarvestLevel(ItemStack stack) {
		Tool tool = ToolRegistry.instance.get(getToolType(stack));
		return (tool.hasHead ? ToolMaterialRegistry.instance.get(getToolMaterial(stack)).harvestLevel : 0);
	}
}
