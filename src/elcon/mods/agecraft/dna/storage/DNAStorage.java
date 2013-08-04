package elcon.mods.agecraft.dna.storage;

import java.io.Serializable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import elcon.mods.agecraft.dna.structure.DNAObject;

public class DNAStorage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int id;
	public DNAStorageChromosome[] chromosomes;

	public DNAStorage(DNAObject dnaObject) {
		if(dnaObject != null) {
			id = dnaObject.id;
			chromosomes = new DNAStorageChromosome[dnaObject.chromosomeCount];
		}
	}

	public DNAStorage(int id) {
		this.id = id;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		id = nbt.getInteger("ID");
		NBTTagList nbtList = nbt.getTagList("Chromosomes");
		DNAStorageChromosome[] storageChromosomes = new DNAStorageChromosome[nbtList.tagCount()];
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound nbtTag = (NBTTagCompound) nbtList.tagAt(i);
			if(nbtTag != null) {
				int chromosomeID = nbtTag.getInteger("id");
				storageChromosomes[i] = new DNAStorageChromosome(chromosomeID);
				NBTTagList genes = nbtTag.getTagList("Genes");
				DNAStorageGene[] storageGenes = new DNAStorageGene[genes.tagCount()];
				for(int j = 0; j < genes.tagCount(); j++) {
					NBTTagCompound tag = (NBTTagCompound) genes.tagAt(j);
					if(tag != null) {
						int geneID = tag.getInteger("id");
						int allel1 = tag.getInteger("allel1");
						int allel2 = tag.getInteger("allel2");
						byte active = tag.getByte("active");
						storageGenes[j] = new DNAStorageGene(chromosomeID, geneID, allel1, allel2, active);
					}
				}
				storageChromosomes[i].genes = storageGenes;
			}
		}
		chromosomes = storageChromosomes;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("ID", id);
		if(chromosomes != null) {
			NBTTagList nbtList = new NBTTagList();
			for(int i = 0; i < chromosomes.length; i++) {
				if(chromosomes[i] != null) {
					NBTTagCompound nbtTag = new NBTTagCompound();
					NBTTagList genes = new NBTTagList();
					nbtTag.setInteger("id", chromosomes[i].id);
					for(int j = 0; j < chromosomes[i].genes.length; j++) {
						if(chromosomes[i].genes[j] != null) {
							NBTTagCompound tag = new NBTTagCompound();
							tag.setInteger("id", chromosomes[i].genes[j].id);
							tag.setInteger("allel1", chromosomes[i].genes[j].allel1);
							tag.setInteger("allel2", chromosomes[i].genes[j].allel2);
							tag.setByte("active", chromosomes[i].genes[j].active);
							genes.appendTag(tag);
						}
					}
					nbtTag.setTag("Genes", genes);
					nbtList.appendTag(nbtTag);
				}
			}
			nbt.setTag("Chromosomes", nbtList);
		}
	}
}
