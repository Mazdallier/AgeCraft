package elcon.mods.agecraft.dna;

import java.util.HashMap;
import java.util.Random;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.dna.storage.DNAStorage;
import elcon.mods.agecraft.dna.storage.DNAStorageChromosome;
import elcon.mods.agecraft.dna.storage.DNAStorageGene;
import elcon.mods.agecraft.dna.structure.DNAObject;
import elcon.mods.core.color.ColorRGBA;

public class DNA {

	public static Random random = new Random();

	public static HashMap<Integer, DNAObject> dnaObjects = new HashMap<Integer, DNAObject>();
	
	public static DNAObject getDNAObject(int id) {
		if(dnaObjects.containsKey(id)) {
			return dnaObjects.get(id);
		}
		return null;
	}
	
	public static void registerDNAObject(int id, DNAObject dnaObject) {
		if(dnaObjects.containsKey(id)) {		
			ACLog.warning("[DNA] " + dnaObject.name + " is trying to override existing DNA object: " + dnaObjects.get(id));
		}
		dnaObjects.put(id, dnaObject);
	}
	
	public static int getIDForName(String name) {
		for(DNAObject dnaObject : dnaObjects.values()) {
			if(dnaObject != null) {
				if(dnaObject.name.equals(name)) {
					return dnaObject.id;
				}
			}
		}
		return -1;
	}
	
	public static int getIDForClass(Class c) {
		for(DNAObject dnaObject : dnaObjects.values()) {
			if(dnaObject != null) {
				if(dnaObject.object.equals(c) || dnaObject.object == c) {
					return dnaObject.id;
				}
			}
		}
		return -1;
	}
	
	public static int generateAllel(int min, int max) {
		return min + random.nextInt((max - min) + 1);
	}
	
	public static byte generateActive(ReproCell r1, ReproCell r2) {
		if(r1.active == r2.active) {
			return (byte) (r1.active ? 0 : 1);
		} else if(r1.active) {
			return 0;
		} else if(r2.active) {
			return 1;
		}
		return 0;
	}
	
	public static DNAStorage createDNAStorage(DNAObject dna) {
		DNAStorage storage = new DNAStorage(dna);
		DNAStorageChromosome[] storageChromosomes = new DNAStorageChromosome[dna.chromosomeCount];
		for(int i = 0; i < dna.chromosomeCount; i++) {
			if(dna.chromosomes[i] != null) {
				storageChromosomes[i] = new DNAStorageChromosome(dna.chromosomes[i].id);
				DNAStorageGene[] storageGenes = new DNAStorageGene[dna.chromosomes[i].geneCount];
				for(int j = 0; j < dna.chromosomes[i].geneCount; j++) {
					if(dna.chromosomes[i].genes[j] != null) {
						int allel = generateAllel(dna.chromosomes[i].genes[j].minValue, dna.chromosomes[i].genes[j].maxValue);
						storageGenes[j] = new DNAStorageGene(dna.chromosomes[i].genes[j].id, allel, allel, (byte) 0);
					}
				}
				storageChromosomes[i].genes = storageGenes;
			}
		}
		storage.chromosomes = storageChromosomes;
		return storage;
	}
	
	public static DNAStorage createDNAStorageFromTemplate(DNAObject dna, DNATemplate template) {
		DNAStorage storage = new DNAStorage(dna);
		DNAStorageChromosome[] storageChromosomes = new DNAStorageChromosome[dna.chromosomeCount];
		for(int i = 0; i < dna.chromosomeCount; i++) {
			if(dna.chromosomes[i] != null) {
				storageChromosomes[i] = new DNAStorageChromosome(dna.chromosomes[i].id);
				DNAStorageGene[] storageGenes = new DNAStorageGene[dna.chromosomes[i].geneCount];
				for(int j = 0; j < dna.chromosomes[i].geneCount; j++) {
					if(dna.chromosomes[i].genes[j] != null) {
						if(template.hasAllel(i, j)) {
							storageGenes[j] = new DNAStorageGene(dna.chromosomes[i].genes[j].id, template.getAllel1(i, j), template.getAllel2(i, j), (byte) 0);
						} else {
							int allel = generateAllel(dna.chromosomes[i].genes[j].minValue, dna.chromosomes[i].genes[j].maxValue);
							storageGenes[j] = new DNAStorageGene(dna.chromosomes[i].genes[j].id, allel, allel, (byte) 0);
						}
					}
				}
				storageChromosomes[i].genes = storageGenes;
			}
		}
		storage.chromosomes = storageChromosomes;
		return storage;
	}
	
	public static DNAStorage reproduce(DNAStorage d1, DNAStorage d2) {
		DNAStorage child = new DNAStorage(d1.id);
		DNAStorageChromosome[] chromosomes = new DNAStorageChromosome[d1.chromosomes.length];
		for(int i = 0; i < d1.chromosomes.length; i++) {
			DNAStorageChromosome chromosome = new DNAStorageChromosome(i);
			DNAStorageGene[] genes = new DNAStorageGene[d1.chromosomes[i].genes.length];
			for(int j = 0; j < d1.chromosomes[i].genes.length; j++) {
				ReproCell r1 = d1.chromosomes[i].genes[j].getReproCell();
				ReproCell r2 = d2.chromosomes[i].genes[j].getReproCell();
				
				if(dnaObjects.get(d1.id).chromosomes[d1.chromosomes[i].id].genes[d1.chromosomes[i].genes[j].id].avarage) {
					int blend = (r1.allel + r2.allel) / 2;
					r1.allel = blend;
					r2.allel = blend;
				} else if(dnaObjects.get(d1.id).chromosomes[d1.chromosomes[i].id].genes[d1.chromosomes[i].genes[j].id].blend) {
					ColorRGBA color = new ColorRGBA(r1.allel);
					color.multiply(new ColorRGBA(r2.allel));
					r1.allel = color.rgb();
					r2.allel = color.rgb();
				} else if(dnaObjects.get(d1.id).mutationCount > 0) {
					for(int k = 0; k < dnaObjects.get(d1.id).mutationCount; k++) {
						if(dnaObjects.get(d1.id).mutations[k] != null && dnaObjects.get(d1.id).mutations[k].chromosomeID == d1.chromosomes[i].id && dnaObjects.get(d1.id).mutations[k].geneID == d1.chromosomes[i].genes[j].id) {
							if(dnaObjects.get(d1.id).mutations[k].allel1 == r1.allel && dnaObjects.get(d1.id).mutations[k].allel2 == r2.allel) {
								r1.allel = dnaObjects.get(d1.id).mutations[k].newAllel1;
								r2.allel = dnaObjects.get(d1.id).mutations[k].newAllel2;
							}
						}
					}
				}
				
				genes[j] = new DNAStorageGene(j, r1.allel, r2.allel, generateActive(r1, r2));
			}
			chromosome.genes = genes;
			chromosomes[i] = chromosome;
		}
		child.chromosomes = chromosomes;
		
		if(dnaObjects.get(d1.id).mutationHandler != null) {
			child = dnaObjects.get(d1.id).mutationHandler.mutate(child);
		}
		
		return child;
	}
	
	public static String dnaToString(DNAStorage dnaStorage) {
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(dnaStorage.id) + "\n");
		for(int i = 0; i < dnaStorage.chromosomes.length; i++) {
			sb.append("chromosome " + dnaStorage.chromosomes[i].id + "\n");
			for(int j = 0; j < dnaStorage.chromosomes[i].genes.length; j++) {
				sb.append("    gene " + Integer.toString(dnaStorage.chromosomes[i].genes[j].id) + " = " + Integer.toString(dnaStorage.chromosomes[i].genes[j].allel1) + "," + Integer.toString(dnaStorage.chromosomes[i].genes[j].allel2) + " (active: " + Byte.toString(dnaStorage.chromosomes[i].genes[j].active) + ")\n");
			}
		}		
		return sb.toString();
	}
}
