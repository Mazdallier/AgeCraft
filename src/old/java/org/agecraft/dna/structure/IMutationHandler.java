package org.agecraft.dna.structure;

import org.agecraft.dna.storage.DNAStorage;

public interface IMutationHandler {

	public DNAStorage mutate(DNAStorage dnaStorage);
}
