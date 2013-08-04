package elcon.mods.agecraft.structure;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public abstract interface ITileStructure {

	public abstract String getTypeUID();

	public abstract void validateStructure();

	public abstract void onStructureReset();

	public abstract ITileStructure getCentralTE();

	public abstract void setCentralTE(TileEntity tileEntity);

	public abstract IInventory getInventory();

	public abstract void makeMaster();

	public abstract boolean isMaster();

	public abstract boolean isIntegratedIntoStructure();
}
