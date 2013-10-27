package elcon.mods.agecraft.dna.storage;

import java.io.Serializable;

import elcon.mods.agecraft.dna.DNA;
import elcon.mods.agecraft.dna.ReproCell;

public class DNAStorageGene implements Serializable {

	private static final long serialVersionUID = 1L;

	public int id;
	public int allel1;
	public int allel2;
	public byte active;

	public DNAStorageGene(int id, int allel1, int allel2, byte active) {
		this.id = id;
		this.allel1 = allel1;
		this.allel2 = allel2;
		this.active = active;
	}
	
	public int getActive() {
		if(active == 0) {
			return allel1;
		} else if(active == 1) {
			return allel2;
		}
		return 0;
	}
	
	public int getInActive() {
		if(active == 0) {
			return allel2;
		} else if(active == 1) {
			return allel1;
		}
		return 0;
	}

	public String getValue() {
		return Integer.toString(allel1) + Integer.toString(allel2);
	}

	public ReproCell getReproCell() {
		if(allel1 == allel2) {
			return new ReproCell(allel1, true);
		} else {
			int i = DNA.random.nextInt(2);
			if(i == 0) {
				return new ReproCell(allel2, active == 1);
			}
			return new ReproCell(allel1, active == 0);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DNAStorageGene) {
			DNAStorageGene dna = (DNAStorageGene) obj;
			return id == dna.id && allel1 == dna.allel1 && allel2 == dna.allel2 && active == dna.active;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "[id=" + id + ", allel1=" + allel1 + ", allel2=" + allel2 + ", active=" + active + "]"; 
	}
}
