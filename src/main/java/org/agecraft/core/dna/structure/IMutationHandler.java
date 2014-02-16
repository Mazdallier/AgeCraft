package org.agecraft.core.dna.structure;

import org.agecraft.core.dna.storage.DNAStorage;

public interface IMutationHandler {

	public DNAStorage mutate(DNAStorage dnaStorage);
}
