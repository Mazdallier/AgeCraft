package org.agecraft.core.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

import org.agecraft.core.dna.DNA;
import org.agecraft.core.dna.IDNAOwner;
import org.agecraft.core.dna.storage.DNAStorage;
import org.agecraft.core.dna.storage.DNAStorageChromosome;
import org.agecraft.core.dna.storage.DNAStorageGene;
import org.agecraft.core.dna.structure.DNAObject;

import elcon.mods.elconqore.tileentities.TileEntityNBT;

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
	
	public String getPacketChannel() {
		return "ACTile";
	}
	
	@Override
	public Packet getDescriptionPacket() {
		dna.writeToNBT(getNBT());
		return super.getDescriptionPacket();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		dna = new DNAStorage(getNBT().getInteger("ID"));
		dna.readFromNBT(getNBT());
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		dna.writeToNBT(getNBT());
		super.writeToNBT(nbtTagCompound);
	}
}
