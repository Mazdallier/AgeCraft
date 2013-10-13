package elcon.mods.agecraft.core.tileentities;

import elcon.mods.agecraft.core.Trees;

public class TileEntityDNATree extends TileEntityDNA {
	
	public TileEntityDNATree() {
		setDNAObjectID(Trees.treeDNA.id);
	}
	
	public int getWoodType() {
		return getGene(0, 0).getActive();
	}
	
	public int getLeaveType() {
		return getGene(0, 1).getActive();
	}
	
	public int getLeaveColor() {
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
	
	public int getLeaveSize() {
		return getGene(3, 1).getActive();
	}
	
	public int getHeight() {
		return getGene(3, 2).getActive();
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
