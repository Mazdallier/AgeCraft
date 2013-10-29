package elcon.mods.agecraft.core.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.dna.DNA;
import elcon.mods.agecraft.dna.DNAUtil;
import elcon.mods.agecraft.dna.storage.DNAStorage;
import elcon.mods.core.color.Color;
import elcon.mods.core.items.ItemBlockName;
import elcon.mods.core.lang.LanguageManager;

public class ItemSaplingDNA extends ItemBlockName {
	
	public ItemSaplingDNA(int id) {
		super(id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = stack.getTagCompound();
			if(!nbt.hasKey("DNA")) {
				DNAStorage dna = DNA.createDNAStorage(Trees.treeDNA);
				NBTTagCompound tag = new NBTTagCompound();
				dna.writeToNBT(tag);
				nbt.setCompoundTag("DNA", tag);
			}
		}
		return stack;
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("DNA")) {
			DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
			dna.readFromNBT(nbt.getCompoundTag("DNA"));
			return LanguageManager.getLocalization("trees." + TreeRegistry.trees[dna.getGene(0, 0).getActive()].name) + " " + LanguageManager.getLocalization("trees.sapling");
		} else if(nbt.hasKey("Defaults")) {
			return LanguageManager.getLocalization("trees." + TreeRegistry.trees[nbt.getCompoundTag("Defaults").getInteger("WoodType")].name) + " " + LanguageManager.getLocalization("trees.sapling");
		}
		return super.getItemDisplayName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt.hasKey("DNA")) {
				DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
				dna.readFromNBT(nbt.getCompoundTag("DNA"));
				list.add(Color.TEXT_COLOR_PREFIX_DARK_BLUE + LanguageManager.getLocalization("trees." + TreeRegistry.trees[dna.getGene(0, 0).getActive()].name) + "-" + LanguageManager.getLocalization("trees." + TreeRegistry.trees[dna.getGene(0, 0).getInActive()].name) + " Wood");
				list.add(Color.TEXT_COLOR_PREFIX_DARK_BLUE + LanguageManager.getLocalization("trees." + TreeRegistry.trees[dna.getGene(0, 1).getActive()].name) + "-" + LanguageManager.getLocalization("trees." + TreeRegistry.trees[dna.getGene(0, 1).getInActive()].name) + " Leaves");
				list.add(Integer.toString(dna.getGene(0, 2).getActive(), 16));
				list.add(BiomeGenBase.biomeList[dna.getGene(1, 1).getActive()].biomeName);
				list.add("T: " + DNAUtil.intToPlusMin(dna.getGene(1, 1).getActive()) + Color.TEXT_COLOR_PREFIX_GRAY + " H: " + DNAUtil.intToPlusMin(dna.getGene(1, 2).getActive()));
				list.add("S: " + DNAUtil.intToSpeed(dna.getGene(2, 0).getActive()) + " T: " + DNAUtil.intToSpeed(dna.getGene(2, 1).getActive()) + " B: " + DNAUtil.intToSpeed(dna.getGene(2, 2).getActive()));
				list.add(Integer.toString(dna.getGene(2, 0).getActive() + 1) + "x" + Integer.toString(dna.getGene(2, 0).getActive() + 1));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("DNA")) {
			DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
			dna.readFromNBT(nbt.getCompoundTag("DNA"));
			return TreeRegistry.trees[dna.getGene(0, 0).getActive()].sapling;
		} else if(nbt.hasKey("Defaults")) {
			return TreeRegistry.trees[nbt.getCompoundTag("Defaults").getInteger("WoodType")].sapling;
		}
		return null;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
}
