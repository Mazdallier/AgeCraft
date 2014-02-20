package org.agecraft.core.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.agecraft.core.Trees;
import org.agecraft.core.dna.DNA;
import org.agecraft.core.dna.DNAUtil;
import org.agecraft.core.dna.storage.DNAStorage;
import org.agecraft.core.registry.TreeRegistry;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.items.ItemBlockName;
import elcon.mods.elconqore.lang.LanguageManager;

public class ItemSaplingDNA extends ItemBlockName {

	public ItemSaplingDNA(Block block) {
		super(block);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		if(!world.isRemote) {
			if(!stack.hasTagCompound()) {
				NBTTagCompound nbt = new NBTTagCompound();
				DNAStorage dna = DNA.createDNAStorage(Trees.treeDNA);
				NBTTagCompound tag = new NBTTagCompound();
				dna.writeToNBT(tag);
				nbt.setTag("DNA", tag);
				stack.setTagCompound(nbt);
			} else if(stack.getTagCompound().hasKey("Defaults")) {
				if(stack.getTagCompound().hasKey("DNA")) {
					stack.getTagCompound().removeTag("Defaults");
				} else {
					NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Defaults");
					DNAStorage dna = DNA.createDNAStorage(Trees.treeDNA);
					int treeType = tag.getInteger("TreeType");
					dna.getGene(0, 0).allel1 = treeType;
					dna.getGene(0, 0).allel2 = treeType;
					dna.getGene(0, 1).allel1 = treeType;
					dna.getGene(0, 1).allel2 = treeType;
					NBTTagCompound tagDNA = new NBTTagCompound();
					dna.writeToNBT(tagDNA);
					stack.getTagCompound().setTag("DNA", tagDNA);
					stack.getTagCompound().removeTag("Defaults");
				}
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("DNA")) {
			DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
			dna.readFromNBT(nbt.getCompoundTag("DNA"));
			return LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(dna.getGene(0, 0).getActive()).name) + " " + LanguageManager.getLocalization("trees.sapling");
		} else if(nbt.hasKey("Defaults")) {
			return LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(nbt.getCompoundTag("Defaults").getInteger("TreeType")).name) + " " + LanguageManager.getLocalization("trees.sapling");
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("DNA")) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
				dna.readFromNBT(nbt.getCompoundTag("DNA"));
				list.add(EnumChatFormatting.DARK_BLUE + LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(dna.getGene(0, 0).getActive()).name) + "-" + LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(dna.getGene(0, 0).getInActive()).name) + " Wood");
				list.add(EnumChatFormatting.DARK_BLUE + LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(dna.getGene(0, 1).getActive()).name) + "-" + LanguageManager.getLocalization("trees." + TreeRegistry.instance.get(dna.getGene(0, 1).getInActive()).name) + " Leaves");
				list.add(Integer.toString(dna.getGene(0, 2).getActive(), 16));
				list.add(BiomeGenBase.getBiome(dna.getGene(1, 1).getActive()).biomeName);
				list.add("T: " + DNAUtil.intToPlusMin(dna.getGene(1, 1).getActive()) + EnumChatFormatting.GRAY + " H: " + DNAUtil.intToPlusMin(dna.getGene(1, 2).getActive()));
				list.add("S: " + DNAUtil.intToSpeed(dna.getGene(2, 0).getActive()) + " T: " + DNAUtil.intToSpeed(dna.getGene(2, 1).getActive()) + " B: " + DNAUtil.intToSpeed(dna.getGene(2, 2).getActive()));
				list.add("Saplings: " +  DNAUtil.intToGoodBadSimple(dna.getGene(4, 0).getActive()));
				list.add("T: " + Integer.toString(dna.getGene(3, 0).getActive()) + "x" + Integer.toString(dna.getGene(3, 0).getActive()) + " L: " + Integer.toString(dna.getGene(3, 1).getActive()) + " H: " + Integer.toString(dna.getGene(3, 2).getActive()));
			} else {
				list.add(EnumChatFormatting.ITALIC + LanguageManager.getLocalization("gui.showdetails") + EnumChatFormatting.RESET);
			}
		}		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("DNA")) {
			DNAStorage dna = new DNAStorage(Trees.treeDNA.id);
			dna.readFromNBT(nbt.getCompoundTag("DNA"));
			return TreeRegistry.instance.get(dna.getGene(0, 0).getActive()).sapling;
		} else if(nbt.hasKey("Defaults")) {
			return TreeRegistry.instance.get(nbt.getCompoundTag("Defaults").getInteger("TreeType")).sapling;
		}
		return null;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
}
