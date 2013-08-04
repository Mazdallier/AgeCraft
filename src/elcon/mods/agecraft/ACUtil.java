package elcon.mods.agecraft;

import net.minecraft.world.World;

public class ACUtil {

	public static int getFirstUncoveredBlock(World world, int x, int z) {
		int i;
        for(i = 48; !world.isAirBlock(x + 3, i + 1, z + 3); i++) {
            ;
        }
        return i;
	}
}
