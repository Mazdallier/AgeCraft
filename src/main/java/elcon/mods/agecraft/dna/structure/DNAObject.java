package elcon.mods.agecraft.dna.structure;

import elcon.mods.agecraft.dna.DNA;

public class DNAObject {

	public int id;
	public String name;
	public Class object;
	
	public Chromosome[] chromosomes;
	public int chromosomeCount;
	
	public Mutation[] mutations;
	public int mutationCount;
	
	public IMutationHandler mutationHandler;
	
	public DNAObject(int id, String name, Class object, Chromosome[] chromosomes) {
		this.id = id;
		this.name = name;
		this.object = object;
		
		this.chromosomes = chromosomes;
		this.chromosomeCount = chromosomes.length;
		
		this.mutations = null;
		this.mutationCount = 0;
		
		this.mutationHandler = null;
		
		DNA.registerDNAObject(id, this);
	}
	
	public DNAObject(int id, String name, Class object, Chromosome[] chromosomes, Mutation[] mutations) {
		this.id = id;
		this.name = name;
		this.object = object;
		
		this.chromosomes = chromosomes;
		this.chromosomeCount = chromosomes.length;
		
		this.mutations = mutations;
		this.mutationCount = mutations.length;
		
		this.mutationHandler = null;
		
		DNA.registerDNAObject(id, this);
	}
	
	public DNAObject(int id, String name, Class object, Chromosome[] chromosomes, IMutationHandler mutationHandler) {
		this.id = id;
		this.name = name;
		this.object = object;
		
		this.chromosomes = chromosomes;
		this.chromosomeCount = chromosomes.length;
		
		this.mutations = null;
		this.mutationCount = 0;
		
		this.mutationHandler = mutationHandler;
		
		DNA.registerDNAObject(id, this);
	}
}
