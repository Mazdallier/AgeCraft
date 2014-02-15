package org.agecraft.prehistory.world;

import java.util.Random;

import org.agecraft.core.AgeCraftCore;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import elcon.mods.core.ECUtil;

public class WorldGenTeleportSphere extends WorldGenerator {

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		world.setBlock(i + 0, j + 2, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 2, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 2, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 3, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 3, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 3, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 4, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 4, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 0, j + 4, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 1, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 1, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 1, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 1, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 1, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 2, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 2, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 3, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 3, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 4, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 4, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 5, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 5, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 5, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 5, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 1, j + 5, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 0, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 0, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 0, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 1, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 1, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 2, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 2, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 3, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 3, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 4, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 4, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 5, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 5, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 6, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 6, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 2, j + 6, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 0, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 0, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 1, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 1, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 1, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 2, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 3, k + 6, AgeCraftCore.ageTeleporter.blockID);
		world.setBlock(i + 3, j + 4, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 4, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 5, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 5, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 6, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 6, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 3, j + 6, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 0, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 0, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 0, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 1, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 1, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 2, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 2, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 3, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 3, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 4, k + 0, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 4, k + 6, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 5, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 5, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 6, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 6, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 4, j + 6, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 1, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 1, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 1, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 1, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 1, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 2, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 2, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 3, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 3, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 4, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 4, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 5, k + 1, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 5, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 5, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 5, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 5, j + 5, k + 5, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 2, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 2, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 2, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 3, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 3, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 3, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 4, k + 2, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 4, k + 3, AgeCraftCore.ageTeleporterBlock.blockID);
		world.setBlock(i + 6, j + 4, k + 4, AgeCraftCore.ageTeleporterBlock.blockID);
		
		world.setBlock(i + 2, j + 2, k + 5, AgeCraftCore.ageTeleporterChest.blockID, 2, 0);
		world.setBlock(i + 2, j + 3, k + 5, AgeCraftCore.ageTeleporterChest.blockID, 2, 0);
		world.setBlock(i + 4, j + 2, k + 5, AgeCraftCore.ageTeleporterChest.blockID, 2, 0);
		world.setBlock(i + 4, j + 3, k + 5, AgeCraftCore.ageTeleporterChest.blockID, 2, 0);
		world.setBlock(i + 2, j + 2, k + 1, AgeCraftCore.ageTeleporterChest.blockID, 3, 0);
		world.setBlock(i + 2, j + 3, k + 1, AgeCraftCore.ageTeleporterChest.blockID, 3, 0);
		world.setBlock(i + 4, j + 2, k + 1, AgeCraftCore.ageTeleporterChest.blockID, 3, 0);
		world.setBlock(i + 4, j + 3, k + 1, AgeCraftCore.ageTeleporterChest.blockID, 3, 0);
		
		world.setBlock(i + 5, j + 2, k + 2, AgeCraftCore.ageTeleporterChest.blockID, 4, 0);
		world.setBlock(i + 5, j + 3, k + 2, AgeCraftCore.ageTeleporterChest.blockID, 4, 0);
		world.setBlock(i + 5, j + 2, k + 4, AgeCraftCore.ageTeleporterChest.blockID, 4, 0);
		world.setBlock(i + 5, j + 3, k + 4, AgeCraftCore.ageTeleporterChest.blockID, 4, 0);
		world.setBlock(i + 1, j + 2, k + 2, AgeCraftCore.ageTeleporterChest.blockID, 5, 0);
		world.setBlock(i + 1, j + 3, k + 2, AgeCraftCore.ageTeleporterChest.blockID, 5, 0);
		world.setBlock(i + 1, j + 2, k + 4, AgeCraftCore.ageTeleporterChest.blockID, 5, 0);
		world.setBlock(i + 1, j + 3, k + 4, AgeCraftCore.ageTeleporterChest.blockID, 5, 0);

        world.setBlock(i + 3, ECUtil.getFirstUncoveredBlock(world, i, k), k + 3, AgeCraftCore.ageTeleporterBeam.blockID);
		
		return true;
	}

}
