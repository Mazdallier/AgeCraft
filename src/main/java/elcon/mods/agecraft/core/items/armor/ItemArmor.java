package elcon.mods.agecraft.core.items.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.ArmorRegistry;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorMaterial;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorType;
import elcon.mods.core.lang.LanguageManager;

public abstract class ItemArmor extends Item {

	public ItemArmor(int id) {
		super(id - 256);
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.armor);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(ArmorRegistry.armorMaterials[getArmorMaterial(stack)].localization) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
		
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "armor.type." + ArmorRegistry.armorTypes[getArmorType(stack)].name;
	}

	@Override
	public String getUnlocalizedName() {
		return "armor.type.default";
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return getArmorDurability(stack);
	}
	
	//TODO: change this to use armor materials
	public int getDamageReduceAmount(ItemStack stack) {
		return 0;
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
	public Icon getIcon(ItemStack stack, int pass) {
		ArmorType armorType = ArmorRegistry.armorTypes[getArmorType(stack)];
		ArmorMaterial armorMaterial = getArmorMaterial(stack) != -1 ? ArmorRegistry.armorMaterials[getArmorMaterial(stack)] : null;
		if(pass == 1) {
			if(armorMaterial != null) {
				return armorMaterial.icons[armorType.id];
			}
		} else if(pass == 2) {
			if(armorMaterial != null && armorMaterial.hasOverlay) {
				return armorMaterial.iconsOverlay[armorType.id];
			}
		}
		return ResourcesCore.emptyTexture;
	}
	
	public static Icon getBackgroundIcon(int armorTypeID) {
		if(ArmorRegistry.armorTypes[armorTypeID] != null) {
			return ArmorRegistry.armorTypes[armorTypeID].backgroundIcon;
		}
		return ResourcesCore.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		for(int j = 0; j < ArmorRegistry.armorMaterials.length; j++) {
			if(ArmorRegistry.armorMaterials[j] != null) {
				ItemStack stack = new ItemStack(id, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound nbt2 = new NBTTagCompound();
				nbt2.setInteger("Type", getArmorType());
				nbt2.setInteger("Material", j);
				if(ArmorRegistry.armorMaterials[j].hasColors) {
					nbt.setInteger("Color", ArmorRegistry.armorMaterials[j].defaultColor);
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
			nbt.setCompoundTag("Armor", nbt2);
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
		NBTTagCompound nbt = getArmorNBT(stack);
		return ArmorRegistry.armorMaterials[getArmorMaterial(stack)].durability;
	}
	
	public boolean hasArmorColor(ItemStack stack) {
		return ArmorRegistry.armorMaterials[getArmorMaterial(stack)].hasColors;
	}
	
	public int getArmorColor(ItemStack stack) {
		NBTTagCompound nbt = getArmorNBT(stack);
		if(hasArmorColor(stack)) {
			if(!nbt.hasKey("Color")) {
				nbt.setInteger("Color", ArmorRegistry.armorMaterials[getArmorMaterial(stack)].defaultColor);
			}
			return nbt.getInteger("Color");
		}
		return -1;
	}
}
