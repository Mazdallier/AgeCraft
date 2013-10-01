package elcon.mods.agecraft.core.items.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.ArmorRegistry;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorType;
import elcon.mods.core.lang.LanguageManager;

public class ItemArmor extends Item {

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

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		ArmorType armor = ArmorRegistry.armorTypes[getArmorType(stack)];
		if(pass == 1) {
			int armorMaterial = getArmorMaterial(stack);
			if(armorMaterial != -1 && ArmorRegistry.armorMaterials[armorMaterial] != null) {
				return ArmorRegistry.armorMaterials[armorMaterial].icons[armor.id];
			}
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
		for(int i = 0; i < ArmorRegistry.armorTypes.length; i++) {
			if(ArmorRegistry.armorTypes[i] != null) {
				for(int j = 0; j < ArmorRegistry.armorMaterials.length; j++) {
					ItemStack stack = new ItemStack(id, 1, 0);
					NBTTagCompound nbt = new NBTTagCompound();
					NBTTagCompound nbt2 = new NBTTagCompound();
					nbt2.setInteger("Type", i);
					nbt2.setInteger("Material", j);
					nbt.setTag("Armor", nbt2);
					stack.setTagCompound(nbt);
					list.add(stack);
				}
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
}
