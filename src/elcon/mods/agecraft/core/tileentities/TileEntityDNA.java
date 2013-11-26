package elcon.mods.agecraft.core.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import elcon.mods.agecraft.dna.DNA;
import elcon.mods.agecraft.dna.IDNAOwner;
import elcon.mods.agecraft.dna.storage.DNAStorage;
import elcon.mods.agecraft.dna.storage.DNAStorageChromosome;
import elcon.mods.agecraft.dna.storage.DNAStorageGene;
import elcon.mods.agecraft.dna.structure.DNAObject;
import elcon.mods.core.tileentities.TileEntityNBT;

public class TileEntityDNA extends TileEntityNBT implements IDNAOwner {
	
	public int dnaID;
	private DNAStorage dna;
	
	public TileEntityDNA() {
		
	}
	
	public TileEntityDNA(int dnaID, DNAStorage dna) {
		this.dnaID = dnaID;
		this.dna = dna;
	}
	
	public TileEntityDNA(Integer dnaID, DNAStorage dna) {
		this(dnaID.intValue(), dna);
	}
	
	@Override
	public DNAObject getDNAObject() {
		return DNA.getDNAObject(dnaID);
	}
	
	public void setDNAObjectID(int id) {
		dnaID = id;
		createDNA(getDNAObject());
	}
	
	public DNAStorage getDNA() {
		return dna;
	}
	
	public void setDNA(DNAStorage dna) {
		this.dna = dna;
	}
	
	public void createDNA(DNAObject dnaObject) {
		dna = DNA.createDNAStorage(dnaObject);
	}
	
	public DNAStorageChromosome getChromosome(int chromosomeID) {
		return dna.chromosomes[chromosomeID];
	}
	
	public void setChromosome(int chromosomeID, DNAStorageChromosome chromosome) {
		dna.chromosomes[chromosomeID] = chromosome;
	}
	
	public DNAStorageGene getGene(int chromosomeID, int geneID) {
		return dna.chromosomes[chromosomeID].genes[geneID];
	}

	public void setGene(int chromosomeID, int geneID, DNAStorageGene gene) {
		dna.chromosomes[chromosomeID].genes[geneID] = gene;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		dna.writeToNBT(nbt);
		return super.getDescriptionPacket();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		dna = new DNAStorage(nbt.getInteger("ID"));
		dna.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		dna.writeToNBT(nbt);
		super.writeToNBT(nbtTagCompound);
	}
	
	@Override
	public int getPacketID() {
		return 91;
	}
}
