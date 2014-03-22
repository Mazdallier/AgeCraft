package org.agecraft.core.entity;

import java.util.Arrays;

import org.agecraft.core.dna.structure.Chromosome;
import org.agecraft.core.dna.structure.DNAObject;
import org.agecraft.core.dna.structure.Gene;
import org.agecraft.core.dna.structure.IMutationHandler;
import org.agecraft.core.dna.structure.Mutation;

public class DNAObjectAnimal extends DNAObject {

	public static Chromosome[] defaultChromosomes = new Chromosome[] {
		new Chromosome(0, "general", new Gene[]{
			new Gene(0, "gender")
		})
	};
	
	public DNAObjectAnimal(int id, String name, Class object) {
		this(id, name, object, Arrays.copyOf(defaultChromosomes, defaultChromosomes.length));
	}
	
	public DNAObjectAnimal(int id, String name, Class object, Mutation[] mutations) {
		this(id, name, object, Arrays.copyOf(defaultChromosomes, defaultChromosomes.length), mutations);
	}
	
	public DNAObjectAnimal(int id, String name, Class object, IMutationHandler mutationHandler) {
		this(id, name, object, Arrays.copyOf(defaultChromosomes, defaultChromosomes.length), mutationHandler);
	}
	
	public DNAObjectAnimal(int id, String name, Class object, Chromosome[] chromosomes) {
		super(id, name, object, chromosomes);
	}
	
	public DNAObjectAnimal(int id, String name, Class object, Chromosome[] chromosomes, Mutation[] mutations) {
		super(id, name, object, chromosomes, mutations);
	}
	
	public DNAObjectAnimal(int id, String name, Class object, Chromosome[] chromosomes, IMutationHandler mutationHandler) {
		super(id, name, object, chromosomes, mutationHandler);
	}
}
