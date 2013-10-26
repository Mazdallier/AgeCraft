package elcon.mods.agecraft.core.tileentities;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAgeTeleporterBeam extends TileEntity {

	@SideOnly(Side.CLIENT)
	private long lastTime;
	@SideOnly(Side.CLIENT)
	private float rotation;
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		List<Entity> list = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1.0, yCoord + (134 - yCoord), zCoord + 1.0));
		Iterator<Entity> i = list.iterator();
		while(i.hasNext()) {
			Entity e = i.next();
			if(e != null) {
				e.motionY = 0.20D;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public float func_82125_v_() {
		int delta = (int) (worldObj.getTotalWorldTime() - lastTime);
		lastTime = worldObj.getTotalWorldTime();

		if(delta > 1) {
			rotation -= (float) delta / 40.0F;

			if(rotation < 0.0F) {
				rotation = 0.0F;
			}
		}
		rotation += 0.025F;
		if(rotation > 1.0F) {
			rotation = 1.0F;
		}
		return rotation;
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
