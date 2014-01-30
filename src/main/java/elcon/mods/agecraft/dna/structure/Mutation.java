package elcon.mods.agecraft.dna.structure;

public class Mutation {

	public int dnaObjectID;
	public int chromosomeID;
	public int geneID;
	public int allel1;
	public int allel2;
	
	public int newAllel1;
	public int newAllel2;

	public Mutation(int dnaObjectID, int chromosomeID, int geneID, int allel1, int allel2, int newAllel1, int newAllel2) {
		this.dnaObjectID = dnaObjectID;
		this.chromosomeID = chromosomeID;
		this.geneID = geneID;
		this.allel1 = allel1;
		this.allel2 = allel2;
		this.newAllel1 = newAllel1;
		this.newAllel2 = newAllel2;
	}
}
