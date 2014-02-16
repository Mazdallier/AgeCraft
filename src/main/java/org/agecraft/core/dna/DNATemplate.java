package org.agecraft.core.dna;

import org.agecraft.core.dna.structure.DNAObject;

public class DNATemplate {

	public DNAObject dnaObject;
	public int[][] allel1;
	public int[][] allel2;
	
	public DNATemplate(DNAObject dnaObject) {
		this.dnaObject = dnaObject;
		allel1 = new int[dnaObject.chromosomeCount][];
		allel2 = new int[dnaObject.chromosomeCount][];
		for(int i = 0; i < dnaObject.chromosomeCount; i++) {
			allel1[i] = new int[dnaObject.chromosomes[i].geneCount];
			allel2[i] = new int[dnaObject.chromosomes[i].geneCount];
		}
	}
	
	public DNATemplate(int dnaObjectID) {
		this(DNA.getDNAObject(dnaObjectID));
	}
	
	public boolean hasAllel(int chromosomeID, int geneID) {
		return allel1[chromosomeID][geneID] >= 0;
	}
	
	public int getAllel1(int chromosomeID, int geneID) {
		return allel1[chromosomeID][geneID];
	}
	
	public int getAllel2(int chromosomeID, int geneID) {
		return allel2[chromosomeID][geneID];
	}
	
	public void setAllel(int chromosomeID, int geneID, int allel) {
		setAllel(chromosomeID, geneID, allel, allel);
	}
	
	public void setAllel(int chromosomeID, int geneID, int allel1, int allel2) {
		this.allel1[chromosomeID][geneID] = allel1;
		this.allel2[chromosomeID][geneID] = allel2;
	}
}
