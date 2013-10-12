package elcon.mods.agecraft.dna.structure;

public class Chromosome {

	public int id;
	public String name;
	
	public Gene[] genes;
	public int geneCount = 0;
	
	public boolean hidden;
	
	public Chromosome(int id, String name, Gene[] genes) {
		this.id = id;
		this.name = name;
		this.genes = genes;
		this.geneCount = genes.length;
		this.hidden = false;
	}
	
	public Chromosome setHidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}
}
