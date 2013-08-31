package elcon.mods.agecraft.core.items.tool;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolCreativeEntry;
import elcon.mods.agecraft.lang.LanguageManager;

public class ItemTool extends Item {
	
	public ItemTool(int id) {
		super(id - 256);
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.tools);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getLocalizedName(stack);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization(ToolRegistry.toolMaterials[getToolMaterial(stack)].localization) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tools.type." + ToolRegistry.tools[getToolType(stack)].name;
	}

	@Override
	public String getUnlocalizedName() {
		return "tools.type.default";
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entityLiving, EntityLivingBase entity) {
		stack.damageItem(ToolRegistry.tools[getToolType(stack)].damageEntity, entity);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityLivingBase entity) {
		stack.damageItem(ToolRegistry.tools[getToolType(stack)].damageBlock, entity);
		return true;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getToolDurability(stack);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int metadata) {
		Block[] blocksEffectiveAgainst = ToolRegistry.tools[getToolType(stack)].blocksEffectiveAgainst;
		for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
			if(blocksEffectiveAgainst[i] == block) {
				return getToolEfficiency(stack);
			}
		}
		return 1.0F;
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		Block[] blocksEffectiveAgainst = ToolRegistry.tools[getToolType(stack)].blocksEffectiveAgainst;
		for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
			if(blocksEffectiveAgainst[i] == block) {
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
		//TODO: increase to 3 for enhancements
		return 2;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(pass == 0 && tool.hasRod) {
			return ToolRegistry.toolRodMaterials[getToolMaterial(stack)].icons[tool.id];
		} else if(pass == 1 && tool.hasHead) {
			return ToolRegistry.toolMaterials[getToolMaterial(stack)].icons[tool.id];
		}/* else if(pass == 2 && tool.hasEnhancements) {
			return ToolRegistry.toolEnhancementMaterials[getToolMaterial(stack)].icons[tool.id];
		}*/
		return ResourcesCore.missingTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		if(ToolRegistry.toolCreativeEntries.containsKey(id - 12520)) {
			ArrayList<ToolCreativeEntry> entries = ToolRegistry.toolCreativeEntries.get(id - 12520);
			for(ToolCreativeEntry entry : entries) {
				ItemStack stack = new ItemStack(12520 + entry.tool, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound nbt2 = new NBTTagCompound();
				nbt2.setInteger("Type", entry.tool);
				nbt2.setInteger("Material", entry.toolMaterial);
				nbt2.setInteger("RodMaterial", entry.toolRodMaterial);
				nbt2.setInteger("EnhancementMaterial", entry.toolEnhancement);
				nbt.setTag("Tool", nbt2);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}

	private NBTTagCompound getToolNBT(ItemStack stack) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if(nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		if(!nbt.hasKey("Tool")) {
			NBTTagCompound nbt2 = new NBTTagCompound();
			nbt2.setInteger("Type", 0);
			nbt2.setInteger("Material", 0);
			nbt2.setInteger("RodMaterial", 0);
			nbt2.setInteger("EnhancementMaterial", 0);
			nbt.setCompoundTag("Tool", nbt2);
		}
		return nbt.getCompoundTag("Tool");
	}

	private int getToolType(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("Type");
	}

	private int getToolMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("Material");
	}

	private int getToolRodMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("RodMaterial");
	}

	//TODO: add enhancement functionality
	@SuppressWarnings("unused")
	private int getToolEnhancementMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("EnhancementMaterial");
	}

	private int getToolDurability(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].durability + ToolRegistry.toolRodMaterials[getToolRodMaterial(stack)].durability;
	}

	private float getToolEfficiency(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].efficiency + ToolRegistry.toolRodMaterials[getToolRodMaterial(stack)].efficiency;
	}

	//TODO: add attack strength
	@SuppressWarnings("unused")
	private int getToolAttackStrength(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].attackStrength + ToolRegistry.toolRodMaterials[getToolRodMaterial(stack)].attackStrength;
	}
}
