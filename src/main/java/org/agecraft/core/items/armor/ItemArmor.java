package org.agecraft.core.items.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.AgeCraftCoreClient;
import org.agecraft.core.registry.ArmorMaterialRegistry;
import org.agecraft.core.registry.ArmorMaterialRegistry.ArmorMaterial;
import org.agecraft.core.registry.ArmorTypeRegistry;
import org.agecraft.core.registry.ArmorTypeRegistry.ArmorType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public abstract class ItemArmor extends Item implements ISpecialArmor {

	public ItemArmor() {
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.armor);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(ArmorMaterialRegistry.instance.get(getArmorMaterial(stack)).localization) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
		
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "armor.type." + ArmorTypeRegistry.instance.get(getArmorType(stack)).name;
	}

	@Override
	public String getUnlocalizedName() {
		return "armor.type.default";
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return getArmorDurability(stack);
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		return null;
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack stack, int slotID) {
		return 0;
	}
	
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == getArmorType(stack);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		/*int armorIndex = EntityLiving.getArmorPosition(stack) - 1;
		ItemStack currentArmor = player.getCurrentArmor(armorIndex);
		if(currentArmor != null) {
			player.inventory.addItemStackToInventory(currentArmor.copy());
		}
		player.setCurrentItemOrArmor(armorIndex + 1, stack.copy());
		stack.stackSize = 0;*/
		return stack;
	}
	
	public abstract int getArmorType();
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass == 1) {
			return hasArmorColor(stack) ? getArmorColor(stack) : 0xFFFFFF;
		}
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		ArmorType armorType = ArmorTypeRegistry.instance.get(getArmorType(stack));
		ArmorMaterial armorMaterial = getArmorMaterial(stack) != -1 ? ArmorMaterialRegistry.instance.get(getArmorMaterial(stack)) : null;
		if(pass == 1) {
			if(armorMaterial != null) {
				return armorMaterial.icons[armorType.id];
			}
		} else if(pass == 2) {
			if(armorMaterial != null && armorMaterial.hasOverlay) {
				return armorMaterial.iconsOverlay[armorType.id];
			}
		}
		return AgeCraftCoreClient.emptyTexture;
	}
	
	public static IIcon getBackgroundIcon(int armorTypeID) {
		if(ArmorTypeRegistry.instance.get(armorTypeID) != null) {
			return ArmorTypeRegistry.instance.get(armorTypeID).backgroundIcon;
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
		for(int j = 0; j < ArmorMaterialRegistry.instance.getAll().length; j++) {
			if(ArmorMaterialRegistry.instance.get(j) != null) {
				ItemStack stack = new ItemStack(item, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound nbt2 = new NBTTagCompound();
				nbt2.setInteger("Type", getArmorType());
				nbt2.setInteger("Material", j);
				if(ArmorMaterialRegistry.instance.get(j).hasColors) {
					nbt.setInteger("Color", ArmorMaterialRegistry.instance.get(j).defaultColor);
				}
				nbt.setTag("Armor", nbt2);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}

	public NBTTagCompound getArmorNBT(ItemStack stack) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if(nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		if(!nbt.hasKey("Armor")) {
			NBTTagCompound nbt2 = new NBTTagCompound();
			nbt2.setInteger("Type", 0);
			nbt2.setInteger("Material", -1);
			nbt.setTag("Armor", nbt2);
		}
		return nbt.getCompoundTag("Armor");
	}

	public int getArmorType(ItemStack stack) {
		NBTTagCompound nbt = getArmorNBT(stack);
		return nbt.getInteger("Type");
	}

	public int getArmorMaterial(ItemStack stack) {
		NBTTagCompound nbt = getArmorNBT(stack);
		return nbt.getInteger("Material");
	}

	public int getArmorDurability(ItemStack stack) {
		return ArmorMaterialRegistry.instance.get(getArmorMaterial(stack)).durability;
	}
	
	public boolean hasArmorColor(ItemStack stack) {
		return ArmorMaterialRegistry.instance.get(getArmorMaterial(stack)).hasColors;
	}
	
	public int getArmorColor(ItemStack stack) {
		NBTTagCompound nbt = getArmorNBT(stack);
		if(hasArmorColor(stack)) {
			if(!nbt.hasKey("Color")) {
				nbt.setInteger("Color", ArmorMaterialRegistry.instance.get(getArmorMaterial(stack)).defaultColor);
			}
			return nbt.getInteger("Color");
		}
		return -1;
	}
}
