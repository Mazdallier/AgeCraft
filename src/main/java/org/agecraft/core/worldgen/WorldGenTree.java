package org.agecraft.core.worldgen;

import java.util.Random;

import org.agecraft.core.dna.storage.DNAStorage;

import net.minecraft.world.World;
import elcon.mods.elconqore.world.WorldGenBase;

public class WorldGenTree extends WorldGenBase {

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return false;
	}

	public void generateTree(World world, int x, int y, int z, DNAStorage dna) {
		
	}
}
