package org.agecraft.prehistory.world;

import java.util.Random;

import net.minecraft.world.World;

import org.agecraft.prehistory.PrehistoryAge;

import elcon.mods.elconqore.world.WorldGenBase;

public class WorldGenRock extends WorldGenBase {

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		for(int i = 0; i < 64; i++) {
			int xx = x + random.nextInt(8) - random.nextInt(8);
			int yy = y + random.nextInt(4) - random.nextInt(4);
			int zz = z + random.nextInt(8) - random.nextInt(8);
			if(world.isAirBlock(xx, yy, zz) && (!world.provider.hasNoSky || yy < 128) && PrehistoryAge.rock.canBlockStay(world, xx, yy, zz)) {
				world.setBlock(xx, yy, zz, PrehistoryAge.rock, random.nextInt(), 16);
			}
		}
		return true;
	}
}
