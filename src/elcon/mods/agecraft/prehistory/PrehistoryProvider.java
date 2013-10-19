package elcon.mods.agecraft.prehistory;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class PrehistoryProvider extends WorldProvider {

	public PrehistoryProvider() {
		setDimension(10);
	}

	@Override
	public float calculateCelestialAngle(long time, float partialTickTime) {
		int j = (int) (time % 24000L);
		float angle = ((float) j + partialTickTime) / 24000.0F - 0.25F;
		if(angle < 0.0F) {
			angle++;
		}
		if(angle > 1.0F) {
			angle--;
		}
		float tempAngle = angle;
		angle = 1.0F - (float) ((Math.cos((double) angle * Math.PI) + 1.0D) / 2.0D);
		angle = tempAngle + (angle - tempAngle) / 3.0F;
		return angle;
	}

	@Override
	protected void registerWorldChunkManager() {
		worldChunkMgr = new PrehistoryChunkManager(worldObj);
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new PrehistoryChunkProvider(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public String getDimensionName() {
		return "Prehistory";
	}
}
