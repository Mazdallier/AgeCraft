package elcon.mods.agecraft.structure;

import net.minecraft.nbt.NBTTagCompound;

public abstract interface IStructureLogic {

	public abstract String getTypeUID();

	public abstract void validateStructure();
	
	public abstract void readFromNBT(NBTTagCompound nbt);

	public abstract void writeToNBT(NBTTagCompound nbt);
}
