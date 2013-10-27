package elcon.mods.agecraft.dna.storage;

import java.io.Serializable;

import elcon.mods.agecraft.ACUtil;

public class DNAStorageChromosome implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public DNAStorageGene[] genes;
	
	public DNAStorageChromosome(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DNAStorageChromosome) {
			DNAStorageChromosome dna = (DNAStorageChromosome) obj;
			return id == dna.id && ACUtil.arraysEqual(genes, dna.genes);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "[id=" + id + ", genes=" + ACUtil.arrayToString(genes) + "]";
	}
}
