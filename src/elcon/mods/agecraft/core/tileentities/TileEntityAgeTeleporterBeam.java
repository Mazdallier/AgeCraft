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
	private long field_82137_b;
	@SideOnly(Side.CLIENT)
	private float field_82138_c;
	
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
				e.setVelocity(e.motionX, 0.20D, e.motionZ);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public float func_82125_v_() {
		int i = (int) (worldObj.getTotalWorldTime() - field_82137_b);
		field_82137_b = worldObj.getTotalWorldTime();

		if(i > 1) {
			field_82138_c -= (float) i / 40.0F;

			if(field_82138_c < 0.0F) {
				field_82138_c = 0.0F;
			}
		}

		field_82138_c += 0.025F;

		if(field_82138_c > 1.0F) {
			field_82138_c = 1.0F;
		}

		return field_82138_c;
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
