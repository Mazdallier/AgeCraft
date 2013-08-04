package elcon.mods.agecraft.dna.storage;

import java.io.Serializable;

public class DNAStorageChromosome implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public DNAStorageGene[] genes;
	
	public DNAStorageChromosome(int id) {
		this.id = id;
	}
}
