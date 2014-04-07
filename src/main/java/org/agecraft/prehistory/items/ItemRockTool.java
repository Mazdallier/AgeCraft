package org.agecraft.prehistory.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemRockTool extends Item {

	public static Block[] blocksEffectiveAgainst = new Block[]{};
	
	private IIcon iconUp;
	private IIcon iconDown;
	
	public ItemRockTool() {
		setMaxDamage(32);
		setMaxStackSize(1);
		setDamage(new ItemStack(this), 1);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack container = stack.copy();
		container.setItemDamage(container.getItemDamage() - 1);
		return container;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.rockTool.name";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int i) {
		return iconUp;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		if(stack.hasTagCompound() && stack.stackTagCompound.getInteger("Type") == 1) {
			return iconDown;
		}
		return iconUp;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if(!world.isRemote) {	
			int i = 0;
			if(itemstack.hasTagCompound()) {
				i = itemstack.stackTagCompound.getInteger("Type");
				i = i == 0 ? 1 : 0;
				itemstack.stackTagCompound.setInteger("Type", i);
				return itemstack;
			}
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.stackTagCompound.setInteger("Type", 1);
			return itemstack;
		}
		return itemstack;
	}
	
	public int getAttackDamage(ItemStack stack) {
		if(stack.hasTagCompound() && stack.stackTagCompound.getInteger("Type") == 0) {
			return 2;
		}
		return 1;
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		for(int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if(blocksEffectiveAgainst[i] == block) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		for(int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if(blocksEffectiveAgainst[i] == block) {
				return 2.0F;
			}
		}
		return 0.1F;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entity1, EntityLivingBase entity2) {
		if(itemstack.hasTagCompound() && itemstack.stackTagCompound.getInteger("Type") == 1) {
			itemstack.damageItem(1, entity2);
		}
		itemstack.damageItem(2, entity2);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z, EntityLivingBase entiy) {
		if(itemstack.hasTagCompound() && itemstack.stackTagCompound.getInteger("Type") == 1) {
			itemstack.damageItem(2, entiy);
		} else {
			if((double) block.getBlockHardness(world, x, y, z) != 0.0D) {
				itemstack.damageItem(1, entiy);
			}
		}
		return true;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(entity.canAttackWithItem()) {
			if(!entity.hitByEntity(player)) {
				float damage = (float) getAttackDamage(stack);
				int i = 0;
				float extraDamage = 0.0F;
				if(entity instanceof EntityLivingBase) {
					extraDamage = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase) entity);
					i += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase) entity);
				}
				if(player.isSprinting()) {
					i++;
				}
				if(damage > 0.0F || extraDamage > 0.0F) {
					boolean criricalHit = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && entity instanceof EntityLivingBase;
					if(criricalHit && damage > 0.0F) {
						damage *= 1.5F;
					}
					damage += extraDamage;
					boolean fire = false;
					int j = EnchantmentHelper.getFireAspectModifier(player);
					if(entity instanceof EntityLivingBase && j > 0 && !entity.isBurning()) {
						fire = true;
						entity.setFire(1);
					}
					boolean attack = entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
					if(attack) {
						if(i > 0) {
							entity.addVelocity((double) (-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}
						if(criricalHit) {
							player.onCriticalHit(entity);
						}
						if(extraDamage > 0.0F) {
							player.onEnchantmentCritical(entity);
						}
						if(damage >= 18.0F) {
							player.triggerAchievement(AchievementList.overkill);
						}
						player.setLastAttacker(entity);
						if(entity instanceof EntityLivingBase) {
							EnchantmentHelper.func_151384_a((EntityLivingBase) entity, player);
						}
					}

					ItemStack itemstack = player.getCurrentEquippedItem();
					Object object = entity;
					
					if(entity instanceof EntityDragonPart) {
						IEntityMultiPart entityMultipart = ((EntityDragonPart) entity).entityDragonObj;
						if(entityMultipart != null && entityMultipart instanceof EntityLivingBase) {
							object = (EntityLivingBase) entityMultipart;
						}
					}
					if(itemstack != null && object instanceof EntityLivingBase) {
						itemstack.hitEntity((EntityLivingBase) object, player);
						if(itemstack.stackSize <= 0) {
							player.destroyCurrentEquippedItem();
						}
					}
					if(entity instanceof EntityLivingBase) {
						player.addStat(StatList.damageDealtStat, Math.round(damage * 10.0F));
						if(j > 0 && attack) {
							entity.setFire(j * 4);
						} else if(fire) {
							entity.extinguish();
						}
					}
					player.addExhaustion(0.3F);
				}
			}
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		iconUp = iconRegister.registerIcon("agecraft:ages/prehistory/rockToolUp");
		iconDown = iconRegister.registerIcon("agecraft:ages/prehistory/rockToolDown");
	}
}
