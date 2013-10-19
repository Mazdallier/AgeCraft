package elcon.mods.agecraft.core.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBlock extends Entity {
	
	@SideOnly(Side.CLIENT)
	public Icon texture;
	public float shadowSize = 0;
	public float rotationX = 0;
	public float rotationY = 0;
	public float rotationZ = 0;
	public double xSize, ySize, zSize;
	private int brightness = -1;

	public EntityBlock(World world) {
		super(world);
		preventEntitySpawning = false;
		noClip = true;
		isImmuneToFire = true;
	}

	public EntityBlock(World world, double xPos, double yPos, double zPos) {
		super(world);
		setPositionAndRotation(xPos, yPos, zPos, 0, 0);
	}

	public EntityBlock(World world, double xPos, double yPos, double zPos, double xSize, double ySize, double zSize) {
		this(world);
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		setPositionAndRotation(xPos, yPos, zPos, 0, 0);
		this.motionX = 0.0;
		this.motionY = 0.0;
		this.motionZ = 0.0;
	}

	@Override
	public void setPosition(double x, double y, double z) {
		super.setPosition(x, y, z);
		boundingBox.minX = posX;
		boundingBox.minY = posY;
		boundingBox.minZ = posZ;

		boundingBox.maxX = posX + xSize;
		boundingBox.maxY = posY + ySize;
		boundingBox.maxZ = posZ + zSize;
	}

	@Override
	public void moveEntity(double x, double y, double z) {
		setPosition(posX + x, posY + y, posZ + z);
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound data) {
		xSize = data.getDouble("xSize");
		ySize = data.getDouble("ySize");
		zSize = data.getDouble("zSize");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound data) {
		data.setDouble("xSize", xSize);
		data.setDouble("ySize", ySize);
		data.setDouble("zSize", zSize);
	}

	@Override
	public int getBrightnessForRender(float par1) {
		return brightness > 0 ? brightness : super.getBrightnessForRender(par1);
	}
}
