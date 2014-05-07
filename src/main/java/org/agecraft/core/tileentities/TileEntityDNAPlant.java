package org.agecraft.core.tileentities;

import org.agecraft.core.Farming;
import org.agecraft.core.dna.storage.DNAStorage;

public class TileEntityDNAPlant extends TileEntityDNA {

	public TileEntityDNAPlant() {
		setDNAObjectID(Farming.plantDNA.id);
	}
	
	public TileEntityDNAPlant(int dnaID, DNAStorage dna) {
		super(dnaID, dna);
	}
	
	public TileEntityDNAPlant(Integer dnaID, DNAStorage dna) {
		super(dnaID, dna);
	}
}
