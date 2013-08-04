package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import elcon.mods.agecraft.prehistory.CampfireRecipes;
import elcon.mods.agecraft.prehistory.blocks.BlockCampfire;

public class TileEntityCampfire extends TileEntity {

	public int tick = 0;
	public int timeLeft = 0;
	public int fuel = 0;
	public boolean hasFuel = false;
	public boolean isBurning = false;
	public boolean canBurn = false;
	public byte logType = 0;
	
	public boolean hasSpit = false;
	public byte spitStage = 0;
	public byte spitDirection = 0;
	public ItemStack spitStack = null;
	public ItemStack originalStack = null;
	public int cookTime = 0;
	public boolean cooked = false;
	
	public int delay = 5;
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(200);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);

			dos.writeInt(tick);
			dos.writeInt(timeLeft);
			dos.writeInt(fuel);
			
			dos.writeBoolean(hasFuel);
			dos.writeBoolean(isBurning);
			dos.writeBoolean(canBurn);
			dos.writeByte(logType);
			
			dos.writeBoolean(hasSpit);
			dos.writeByte(spitStage);
			dos.writeByte(spitDirection);
			dos.writeBoolean(spitStack != null);
			if(spitStack != null) {
				dos.writeInt(spitStack.itemID);
				dos.writeInt(spitStack.getItemDamage());
			}
			dos.writeInt(cookTime);
			dos.writeBoolean(cooked);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "ACTile";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;
		return packet;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();		
		if(isBurning && tick % 7 == 0) {
			double d = xCoord + 0.5F;
		    double d1 = yCoord + 0.37F;
		    double d2 = zCoord + 0.5F;
		    double d3 = 0.22D;
		    double d4 = 0.27D;
		    worldObj.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("flame", d + 0.1D, d1, d2, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("flame", d, d1, d2 + 0.1D, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("flame", d, d1, d2 - 0.1D, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("flame", d - 0.1D, d1, d2, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("smoke", d + 0.1D, d1, d2 + 0.1D, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("smoke", d - 0.1D, d1, d2 - 0.1D, 0.0D, 0.0D, 0.0D);
		    worldObj.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
		if(canBurn) {
			if(tick == 30 && isBurning) {
				if(worldObj.isRaining()) {
					fuel = 0;
					hasFuel = false;
					isBurning = false;
					canBurn = false;
					BlockCampfire.updateCampfireBlockState(false, worldObj, xCoord, yCoord, zCoord);
				} else {			
					if(timeLeft > 0) {
						isBurning = true;
						timeLeft --;
					} else {
						timeLeft = 0;
						if(fuel > 0) {
							fuel--;
							isBurning = true;
							timeLeft = 30;
							if(fuel <= 0) {
								hasFuel = false;
								fuel = 0;
							}
						} else {
							fuel = 0;
							hasFuel = false;
							isBurning = false;
							canBurn = false;
							BlockCampfire.updateCampfireBlockState(false, worldObj, xCoord, yCoord, zCoord);
						}
					}
					tick = 0;
				}
			} else {
				tick++;
			}
		}
		if(hasSpit && spitStack != null) {
			if(!isBurning) {
				if(!worldObj.isRemote) {
					EntityItem entity = new EntityItem(worldObj, xCoord, yCoord, zCoord, new ItemStack(spitStack.itemID, 1, spitStack.getItemDamage()));
					float f3 = 0.05F;
	                entity.motionX = (double)((float)worldObj.rand.nextGaussian() * f3);
	                entity.motionY = (double)((float)worldObj.rand.nextGaussian() * f3 + 0.2F);
	                entity.motionZ = (double)((float)worldObj.rand.nextGaussian() * f3);
					spitStack = null;
				}
				spitStack = null;
			} else {
				if(cookTime <= 3600) {
					cookTime++;
				}
				if(!cooked && cookTime >= 2000 && cookTime < 3000) {
					if(!worldObj.isRemote) {
						originalStack = spitStack;
						spitStack = CampfireRecipes.getRecipeOutput(spitStack);
						cooked = true;
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				} else if(cookTime == 3000) {
					if(!worldObj.isRemote) {
						spitStack = CampfireRecipes.getRecipeOutputBurned(originalStack);
						
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		timeLeft = nbt.getInteger("TimeLeft");
		fuel = nbt.getInteger("Fuel");
		hasFuel = nbt.getBoolean("HasFuel");
		isBurning = nbt.getBoolean("Burning");
		canBurn = nbt.getBoolean("CanBurn");
		logType = nbt.getByte("LogType");
		
		hasSpit = nbt.getBoolean("HasSpit");
		spitStage = nbt.getByte("SpitStage");
		spitDirection = nbt.getByte("SpitDirection");
		if(nbt.hasKey("SpitStack")) {
			spitStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("SpitStack"));
		}
		cookTime = nbt.getInteger("CookTime");
		cooked = nbt.getBoolean("Cooked");
	}	
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("TimeLeft", timeLeft);
		nbt.setInteger("Fuel", fuel);
		nbt.setBoolean("HasFuel", hasFuel);
		nbt.setBoolean("Burning", isBurning);
		nbt.setBoolean("CanBurn", canBurn);
		nbt.setByte("LogType", logType);
		
		nbt.setBoolean("HasSpit", hasSpit);
		nbt.setByte("SpitStage", spitStage);
		nbt.setByte("SpitDirection", spitDirection);
		if(spitStack != null) {
			NBTTagCompound stack = new NBTTagCompound();
			spitStack.writeToNBT(stack);
			nbt.setTag("SpitStack", stack);
		}
		nbt.setInteger("CookTime", cookTime);
		nbt.setBoolean("Cooked", cooked);
	}
}
