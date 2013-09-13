package elcon.mods.agecraft.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockPosition {
	
	public int x;
	public int y;
	public int z;

	public BlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPosition(BlockPosition pos) {
		x = pos.x;
		y = pos.y;
		z = pos.z;
	}

	public BlockPosition(NBTTagCompound nbt) {
		x = nbt.getInteger("coordX");
		y = nbt.getInteger("coordY");
		z = nbt.getInteger("coordZ");
	}

	public BlockPosition(TileEntity tile) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
	}
	
	public BlockPosition(int[] coords) {
		x = coords[0];
		y = coords[1];
		z = coords[2];
	}

	public BlockPosition copy() {
		return new BlockPosition(x, y, z);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setDouble("coordX", x);
		nbt.setDouble("coordY", y);
		nbt.setDouble("coordZ", z);
	}

	@Override
	public String toString() {
		return "{" + x + ", " + y + ", " + z + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BlockPosition)) {
			return false;
		}
		BlockPosition bp = (BlockPosition) obj;
		return bp.x == x && bp.y == y && bp.z == z;
	}

	@Override
	public int hashCode() {
		return (x & 0xFFF) | (y & 0xFF << 8) | (z & 0xFFF << 12);
	}

	public BlockPosition min(BlockPosition p) {
		return new BlockPosition(p.x > x ? x : p.x, p.y > y ? y : p.y, p.z > z ? z : p.z);
	}

	public BlockPosition max(BlockPosition p) {
		return new BlockPosition(p.x < x ? x : p.x, p.y < y ? y : p.y, p.z < z ? z : p.z);
	}
	
	public double distanceTo(BlockPosition p) {
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2) + Math.pow(z - p.z, 2));
	}

	public List<BlockPosition> getAdjacent(boolean includeVertical) {
		List<BlockPosition> a = new ArrayList<BlockPosition>();
		a.add(new BlockPosition(x + 1, y, z));
		a.add(new BlockPosition(x - 1, y, z));
		a.add(new BlockPosition(x, y, z + 1));
		a.add(new BlockPosition(x, y, z - 1));
		if(includeVertical) {
			a.add(new BlockPosition(x, y + 1, z));
			a.add(new BlockPosition(x, y - 1, z));
		}
		return a;
	}

	public TileEntity getTileEntity(World world) {
		return world.getBlockTileEntity(x, y, z);
	}

	public static TileEntity getAdjacentTileEntity(TileEntity start, ForgeDirection direction) {
		BlockPosition p = new BlockPosition(start);
		switch(direction) {
		case NORTH:
			p.z -= 1;
			break;
		case SOUTH:
			p.z += 1;
			break;
		case EAST:
			p.x += 1;
			break;
		case WEST:
			p.x -= 1;
			break;
		case UP:
			p.y += 1;
			break;
		case DOWN:
			p.y -= 1;
			break;
		default:
			break;
		}
		return start.worldObj.getBlockTileEntity(p.x, p.y, p.z);
	}

	public static TileEntity getAdjacentTileEntity(TileEntity start, ForgeDirection direction, Class<? extends TileEntity> targetClass) {
		TileEntity te = getAdjacentTileEntity(start, direction);
		if(targetClass.isAssignableFrom(te.getClass())) {
			return te;
		} else {
			return null;
		}
	}
	
	public static double distance(BlockPosition p1, BlockPosition p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));
	}
}