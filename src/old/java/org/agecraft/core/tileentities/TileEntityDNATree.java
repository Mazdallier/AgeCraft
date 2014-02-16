package org.agecraft.core.tileentities;

import org.agecraft.core.Trees;
import org.agecraft.dna.storage.DNAStorage;

public class TileEntityDNATree extends TileEntityDNA {
	
	public TileEntityDNATree() {
		setDNAObjectID(Trees.treeDNA.id);
	}
	
	public TileEntityDNATree(int dnaID, DNAStorage dna) {
		super(dnaID, dna);
	}
	
	public TileEntityDNATree(Integer dnaID, DNAStorage dna) {
		super(dnaID, dna);
	}
	
	public int getWoodType() {
		return getGene(0, 0).getActive();
	}
	
	public int getLeafType() {
		return getGene(0, 1).getActive();
	}
	
	public int getLeafColor() {
		return getGene(0, 2).getActive();
	}
	
	public int getBiome() {
		return getGene(1, 0).getActive();
	}
	
	public int getTemperature() {
		return getGene(1, 1).getActive();
	}
	
	public int getHumidity() {
		return getGene(1, 2).getActive();
	}
	
	public int getSaplingGrowSpeed() {
		return getGene(2, 0).getActive();
	}
	
	public int getGrowSpeed() {
		return getGene(2, 1).getActive();
	}
	
	public int getBreedingSpeed() {
		return getGene(2, 2).getActive();
	}
	
	public int getTrunkSize() {
		return getGene(3, 0).getActive();
	}
	
	public int getLeafSize() {
		return getGene(3, 1).getActive();
	}
	
	public int getHeight() {
		return getGene(3, 2).getActive();
	}
	
	public int getGenerationType() {
		return getGene(3, 3).getActive();
	}
	
	public int getSaplingDropRate() {
		return getGene(4, 0).getActive();
	}
	
	public int getSappiness() {
		return getGene(4, 1).getActive();
	}
	
	public int getFruit() {
		return getGene(4, 2).getActive();
	}
	
	public int getFruitDropRate() {
		return getGene(4, 3).getActive();
	}
}
