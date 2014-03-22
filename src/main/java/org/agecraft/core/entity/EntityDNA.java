package org.agecraft.core.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.agecraft.core.dna.DNA;
import org.agecraft.core.dna.IDNAOwner;
import org.agecraft.core.dna.storage.DNAStorage;
import org.agecraft.core.dna.storage.DNAStorageChromosome;
import org.agecraft.core.dna.storage.DNAStorageGene;
import org.agecraft.core.dna.structure.DNAObject;
import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityDNA extends EntityCreature implements IDNAOwner {
	
	private DNAStorage dna;
	private DNAStorage dnaCache;
	
	public EntityDNA(World world) {
		super(world);
		createDNA(getDNAObject());
	}
	
	@Override
	public abstract DNAObject getDNAObject();
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObjectByDataType(15, 5);
	}
	
	@Override
	public void func_145781_i(int dataID) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			if(dataID == 15) {
				dnaCache = null;
			}
		}
	}
	
	public DNAStorage getDNA() {
		return dna;
	}
	
	public void createDNA(DNAObject dnaObject) {
		setDNA(DNA.createDNAStorage(dnaObject));
	}
	
	public void setDNA(DNAStorage dna) {
		this.dna = dna;
		updateDNA();
	}
	
	public void updateDNA() {
		ItemStack stack = new ItemStack(PrehistoryAge.fakeStone);
		NBTTagCompound tag = new NBTTagCompound();
		dna.writeToNBT(tag);
		stack.setTagCompound(tag);
		dataWatcher.updateObject(15, stack);
	}
	
	public DNAStorageChromosome getChromosome(int chromosomeID) {
		DNAStorageChromosome chromosome = dna.chromosomes[chromosomeID];
		updateDNA();
		return chromosome;
	}
	
	public void setChromosome(int chromosomeID, DNAStorageChromosome chromosome) {
		dna.chromosomes[chromosomeID] = chromosome;
		updateDNA();
	}
	
	public DNAStorageGene getGene(int chromosomeID, int geneID) {
		DNAStorageGene gene = dna.chromosomes[chromosomeID].genes[geneID];
		updateDNA();
		return gene;
	}

	public void setGene(int chromosomeID, int geneID, DNAStorageGene gene) {
		dna.chromosomes[chromosomeID].genes[geneID] = gene;
		updateDNA();
	}
	
	@SideOnly(Side.CLIENT)
	public DNAStorage getDNAClient() {
		if(dnaCache != null) {
			return dnaCache;
		}
		DNAStorage dna = new DNAStorage(getDNAObject());
		dna.readFromNBT(dataWatcher.getWatchableObjectItemStack(15).getTagCompound());
		dnaCache = dna;
		return dna;
	}
	
	@SideOnly(Side.CLIENT)
	public DNAStorageChromosome getChromosomeClient(int chromosomeID) {
		return getDNAClient().chromosomes[chromosomeID];
	}
	
	@SideOnly(Side.CLIENT)
	public DNAStorageGene getGeneClient(int chromosomeID, int geneID) {
		return getDNAClient().chromosomes[chromosomeID].genes[geneID];
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		dna.readFromNBT(nbt.getCompoundTag("DNA"));
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		NBTTagCompound tag = new NBTTagCompound();
		dna.writeToNBT(tag);
		nbt.setTag("DNA", tag);
	}
}
