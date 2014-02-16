package org.agecraft.core.dna.storage;

import java.io.Serializable;

import org.agecraft.ACUtil;

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

	public DNAStorageChromosome copy() {
		DNAStorageChromosome dna = new DNAStorageChromosome(id);
		dna.genes = new DNAStorageGene[genes.length];
		for(int i = 0; i < genes.length; i++) {
			if(genes[i] != null) {
				dna.genes[i] = genes[i].copy();
			}
		}
		return dna;
	}
}
